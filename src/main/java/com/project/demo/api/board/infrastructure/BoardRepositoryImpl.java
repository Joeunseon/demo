package com.project.demo.api.board.infrastructure;

import java.time.LocalDate;
import java.util.List;

import org.springframework.util.StringUtils;

import com.project.demo.api.board.application.dto.BoardListDTO;
import com.project.demo.api.board.application.dto.BoardRequestDTO;
import com.project.demo.api.board.domain.QBoardEntity;
import com.project.demo.api.user.domain.QUserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Long countBySearch(BoardRequestDTO dto) {
        QBoardEntity board = QBoardEntity.boardEntity;
        QUserEntity user = QUserEntity.userEntity;
        BooleanBuilder builder = new BooleanBuilder();

        if ( StringUtils.hasText(dto.getSearchStartDt()) )
            builder.and(board.regDt.goe(LocalDate.parse(dto.getSearchStartDt()).atStartOfDay()));
        
        if ( StringUtils.hasText(dto.getSearchEndDt()) )
            builder.and(board.regDt.loe(LocalDate.parse(dto.getSearchEndDt()).atTime(23, 59, 59)));
        
        var writerSubQuery = JPAExpressions.select(user.userNm)
                                .from(user)
                                .where(user.userSeq.eq(board.regSeq));
        
        if ( dto.getSearchCondition() != null && StringUtils.hasText(dto.getSearchKeyword()) ) {
            switch ( dto.getSearchCondition() ) {
                case ALL:
                    builder.and(board.title.like("%" + dto.getSearchKeyword() + "%")
                            .or(board.content.like("%" + dto.getSearchKeyword() + "%"))
                            .or(Expressions.stringTemplate("({0})", writerSubQuery).like("%" + dto.getSearchKeyword() + "%")));
                    break;
                case TITLE: 
                    builder.and(board.title.like("%" + dto.getSearchKeyword() + "%"));
                    break;
                case CONTENT:
                    builder.and(board.content.like("%"+dto.getSearchKeyword()+"%"));
                    break;
                case WRITER:
                    builder.and(Expressions.stringTemplate("({0})", writerSubQuery).like("%" + dto.getSearchKeyword() + "%"));
            }
        }

        return queryFactory.select(board.count())
                            .from(board)
                            .where(builder)
                            .fetchFirst();
    }

    public List<BoardListDTO> findBySearch(BoardRequestDTO dto) {
        QBoardEntity board = QBoardEntity.boardEntity;
        QUserEntity user = QUserEntity.userEntity;
        BooleanBuilder builder = new BooleanBuilder();

        dto.calculateOffSet();

        if ( StringUtils.hasText(dto.getSearchStartDt()) )
            builder.and(board.regDt.goe(LocalDate.parse(dto.getSearchStartDt()).atStartOfDay()));
        
        if ( StringUtils.hasText(dto.getSearchEndDt()) )
            builder.and(board.regDt.loe(LocalDate.parse(dto.getSearchEndDt()).atTime(23, 59, 59)));
        
        var writerSubQuery = JPAExpressions.select(user.userNm)
                                .from(user)
                                .where(user.userSeq.eq(board.regSeq));
        
        if ( dto.getSearchCondition() != null && StringUtils.hasText(dto.getSearchKeyword()) ) {
            switch ( dto.getSearchCondition() ) {
                case ALL:
                    builder.and(board.title.like("%" + dto.getSearchKeyword() + "%")
                            .or(board.content.like("%" + dto.getSearchKeyword() + "%"))
                            .or(Expressions.stringTemplate("({0})", writerSubQuery).like("%" + dto.getSearchKeyword() + "%")));
                    break;
                case TITLE: 
                    builder.and(board.title.like("%" + dto.getSearchKeyword() + "%"));
                    break;
                case CONTENT:
                    builder.and(board.content.like("%"+dto.getSearchKeyword()+"%"));
                    break;
                case WRITER:
                    builder.and(Expressions.stringTemplate("({0})", writerSubQuery).like("%" + dto.getSearchKeyword() + "%"));
            }
        }
        
        return queryFactory.select(Projections.constructor(BoardListDTO.class,
                                Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} DESC)", board.regDt),
                                board.boardSeq,
                                board.title,
                                JPAExpressions.select(user.userNm)
                                        .from(user)
                                        .where(user.userSeq.eq(board.regSeq)),
                                board.regDt,
                                board.viewCnt
                            ))
                            .from(board)
                            .where(builder)
                            .orderBy(Expressions.numberTemplate(Long.class, "ROW_NUMBER() OVER(ORDER BY {0} DESC)", board.regDt).desc())
                            .limit(dto.getPageScale())
                            .offset(dto.getOffSet())
                            .fetch();
    }
}

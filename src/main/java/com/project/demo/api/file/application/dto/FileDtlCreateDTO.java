package com.project.demo.api.file.application.dto;

import java.time.LocalDateTime;

import com.project.demo.api.file.domain.FileDtlEntity;
import com.project.demo.api.file.domain.FileMstrEntity;
import com.project.demo.common.constant.DelYn;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "파일 상세 등록 진행을 위한 DTO")
public class FileDtlCreateDTO {

    @Schema(description = "파일 Entity")
    private FileMstrEntity fileMstr;

    @Schema(description = "저장 파일 명")
    private String streNm;

    @Schema(description = "원본 파일 명")
    private String oriNm;

    @Schema(description = "파일 저장 경로")
    private String filePath;

    @Schema(description = "파일 확장자")
    private String extsn;

    @Schema(description = "파일 사이즈")
    private Long fileSize;

    @Schema(description = "파일 정렬렬 순서")
    private Integer fileOrder;

    @Builder
    public FileDtlCreateDTO(FileMstrEntity fileMstr, String streNm, String oriNm, String filePath, String extsn, Long fileSize, Integer fileOrder) {

        this.fileMstr = fileMstr;
        this.streNm = streNm;
        this.oriNm = oriNm;
        this.filePath = filePath;
        this.extsn = extsn;
        this.fileSize = fileSize;
        this.fileOrder = fileOrder;
    }

    public FileDtlEntity toEntity(Long userSeq) {

        return FileDtlEntity.builder()
                            .fileMstr(fileMstr)
                            .streNm(streNm)
                            .oriNm(oriNm)
                            .filePath(filePath)
                            .extsn(extsn)
                            .fileSize(fileSize)
                            .fileOrder(fileOrder)
                            .delYn(DelYn.N)
                            .regDt(LocalDateTime.now())
                            .regSeq(userSeq)
                            .build();
    }
}

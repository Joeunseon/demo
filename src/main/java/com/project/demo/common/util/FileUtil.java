package com.project.demo.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.demo.api.file.application.dto.FileDtlCreateDTO;
import com.project.demo.api.file.domain.FileDtlEntity;
import com.project.demo.api.file.domain.FileMstrEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileUtil {

    @Value("${spring.servlet.multipart.location}")
    private String UPLOAD_PATH;

    /**
     * 파일 업로드
     * @param files
     * @param entity
     * @return
     * @throws Exception
     */
    public List<FileDtlEntity> uploadFile(MultipartFile[] files, FileMstrEntity entity) throws Exception {
        
        // 파일 경로
        String basePath = getBasePath();
        // 파일 경로 확인 및 생성
        createDirectoryIfNotExists(basePath);

        List<FileDtlEntity> fileList = new ArrayList<>();

        Integer fileOrder = 1;
        for (MultipartFile file : files) {
            // 원본 파일명
            String orignalFileNm = file.getOriginalFilename();
            if ( !StringUtils.hasText(orignalFileNm) )
                continue;

            // 파일 확장자
            String fileExtsn = getFileExtension(orignalFileNm);
            // 저장 파일명
            String streFileNm = getUniqueFileNm(generateTimestampedFileName(fileExtsn), basePath);
            // 파일 사이즈
            Long fileSize = file.getSize();

            Path filePath = Paths.get(basePath, streFileNm);
            try {
                Files.write(filePath, file.getBytes());
            } catch (IOException e) {
                log.error("[fileUtil.uploadFile]", e);
                continue;
            }

            FileDtlCreateDTO fileDtlDTO = FileDtlCreateDTO.builder()
                                                .fileMstr(entity)
                                                .streNm(streFileNm)
                                                .oriNm(orignalFileNm)
                                                .filePath(filePath.toString())
                                                .extsn(fileExtsn)
                                                .fileSize(fileSize)
                                                .fileOrder(fileOrder)
                                                .build();

            fileList.add(fileDtlDTO.toEntity(entity.getRegSeq()));
            fileOrder++;
        }

        return fileList;
    }

    /**
     * 기본 저장경로 취득 (년월)
     * @return
     */
    private String getBasePath() {
        String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        return UPLOAD_PATH + File.separator + datePath;
    }

    /**
     * 디렉토리 생성 및 확인
     * @param basePath
     * @throws Exception
     */
    private void createDirectoryIfNotExists(String basePath) throws Exception {
        File directory = new File(basePath);
        if ( !directory.exists() && !directory.mkdirs() )
            throw new IOException("[FileUtil] Failed to create directory: " + basePath);
        
    }

     /**
     * 확장자 취득
     * @param filename
     * @return
     */
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

     /**
     * 저장 파일명 취득(날짜_시간)
     * @param extension
     * @return
     */
    private String generateTimestampedFileName(String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return timestamp + "." + extension;
    }

     /**
     * 유니크 파일명 취득
     * @param fileNm
     * @param basePath
     * @return
     */
    public String getUniqueFileNm(String fileNm, String basePath) {
        File file = new File(basePath, fileNm);

        if ( !file.exists() )
            return fileNm;

        int cnt = 0;
        String baseNm = fileNm.substring(0, fileNm.lastIndexOf("."));
        String extsn = fileNm.substring(fileNm.lastIndexOf("."));
        while (file.exists()) {
            cnt++;
            file = new File(basePath, baseNm + "_" + cnt + extsn);
        }

        return file.getName();
    }
}

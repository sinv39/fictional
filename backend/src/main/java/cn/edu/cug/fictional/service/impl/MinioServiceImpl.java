package cn.edu.cug.fictional.service.impl;

import cn.edu.cug.fictional.config.MinioConfig;
import cn.edu.cug.fictional.exception.BusinessException;
import cn.edu.cug.fictional.service.MinioService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * MinIO文件服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    @Override
    public String uploadVideo(MultipartFile file, String fileName) {
        return uploadFile(file, fileName, minioConfig.getVideoBucket());
    }

    @Override
    public String uploadPhoto(MultipartFile file, String fileName) {
        return uploadFile(file, fileName, minioConfig.getPhotoBucket());
    }

    private String uploadFile(MultipartFile file, String fileName, String bucketName) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            String objectName = UUID.randomUUID().toString() + "-" + fileName + extension;
            
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            
            log.info("文件上传成功: {}/{}", bucketName, objectName);
            return objectName;
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    public String getPresignedUrl(String bucketName, String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            log.error("获取预签名URL失败: {}/{}", bucketName, objectName, e);
            throw new BusinessException("获取文件访问地址失败");
        }
    }
}


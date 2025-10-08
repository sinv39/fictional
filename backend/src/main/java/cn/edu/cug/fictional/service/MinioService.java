package cn.edu.cug.fictional.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * MinIO文件服务接口
 */
public interface MinioService {
    
    /**
     * 上传视频
     */
    String uploadVideo(MultipartFile file, String fileName);
    
    /**
     * 上传图片
     */
    String uploadPhoto(MultipartFile file, String fileName);
    
    /**
     * 获取预签名URL
     */
    String getPresignedUrl(String bucketName, String objectName);
}


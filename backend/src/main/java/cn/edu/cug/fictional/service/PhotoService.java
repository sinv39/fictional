package cn.edu.cug.fictional.service;

import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.dto.response.PhotoResponse;
import cn.edu.cug.fictional.dto.response.UploadResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片服务接口
 */
public interface PhotoService {
    
    /**
     * 查看所有图片
     */
    PageResponse<PhotoResponse> listPhotos(Integer page, Integer size);
    
    /**
     * 上传图片
     */
    UploadResponse uploadPhoto(MultipartFile file, String photoName, Long uploaderId);
}


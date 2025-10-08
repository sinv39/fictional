package cn.edu.cug.fictional.service;

import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.dto.response.UploadResponse;
import cn.edu.cug.fictional.dto.response.VideoResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频服务接口
 */
public interface VideoService {
    
    /**
     * 查看所有视频
     */
    PageResponse<VideoResponse> listVideos(Integer page, Integer size);
    
    /**
     * 上传视频
     */
    UploadResponse uploadVideo(MultipartFile file, String videoName, Long uploaderId);
}


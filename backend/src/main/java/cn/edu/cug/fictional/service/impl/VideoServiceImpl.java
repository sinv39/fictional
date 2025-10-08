package cn.edu.cug.fictional.service.impl;

import cn.edu.cug.fictional.config.MinioConfig;
import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.dto.response.UploadResponse;
import cn.edu.cug.fictional.dto.response.VideoResponse;
import cn.edu.cug.fictional.entity.User;
import cn.edu.cug.fictional.entity.Video;
import cn.edu.cug.fictional.mapper.UserMapper;
import cn.edu.cug.fictional.mapper.VideoMapper;
import cn.edu.cug.fictional.service.MinioService;
import cn.edu.cug.fictional.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 视频服务实现类
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoMapper videoMapper;
    private final UserMapper userMapper;
    private final MinioService minioService;
    private final MinioConfig minioConfig;

    @Override
    public PageResponse<VideoResponse> listVideos(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Video> videos = videoMapper.selectWithPage(offset, size);
        Long total = videoMapper.count();
        
        List<VideoResponse> responseList = videos.stream().map(video -> {
            VideoResponse response = new VideoResponse();
            response.setId(video.getId());
            response.setVideoName(video.getVideoName());
            response.setVideoUrl(minioService.getPresignedUrl(minioConfig.getVideoBucket(), video.getVideoPath()));
            response.setUploader(video.getUploaderName());
            response.setUploadTime(video.getUploadTime());
            return response;
        }).collect(Collectors.toList());
        
        return new PageResponse<>(total, responseList);
    }

    @Override
    public UploadResponse uploadVideo(MultipartFile file, String videoName, Long uploaderId) {
        // 上传到MinIO
        String path = minioService.uploadVideo(file, videoName);
        
        // 获取上传者信息
        User user = userMapper.selectById(uploaderId);
        
        // 保存视频信息到数据库
        Video video = new Video();
        video.setVideoName(videoName);
        video.setVideoPath(path);
        video.setFileSize(file.getSize());
        video.setUploaderId(uploaderId);
        video.setUploaderName(user.getRealName());
        video.setStatus(1);
        
        videoMapper.insert(video);
        
        // 返回预签名URL
        String url = minioService.getPresignedUrl(minioConfig.getVideoBucket(), path);
        return new UploadResponse(video.getId(), videoName, url);
    }
}


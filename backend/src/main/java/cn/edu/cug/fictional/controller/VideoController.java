package cn.edu.cug.fictional.controller;

import cn.edu.cug.fictional.dto.response.ApiResponse;
import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.dto.response.UploadResponse;
import cn.edu.cug.fictional.dto.response.VideoResponse;
import cn.edu.cug.fictional.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 视频控制器
 */
@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    /**
     * 查看所有视频
     */
    @GetMapping
    public ApiResponse<PageResponse<VideoResponse>> listVideos(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.success(videoService.listVideos(page, size));
    }

    /**
     * 上传视频
     */
    @PutMapping
    public ApiResponse<UploadResponse> uploadVideo(
            @RequestParam("file") MultipartFile file,
            @RequestParam("videoName") String videoName,
            Authentication authentication) {
        Long uploaderId = Long.parseLong(authentication.getName());
        return ApiResponse.success(videoService.uploadVideo(file, videoName, uploaderId));
    }
}


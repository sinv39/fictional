package cn.edu.cug.fictional.controller;

import cn.edu.cug.fictional.dto.response.ApiResponse;
import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.dto.response.PhotoResponse;
import cn.edu.cug.fictional.dto.response.UploadResponse;
import cn.edu.cug.fictional.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片控制器
 */
@RestController
@RequestMapping("/api/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    /**
     * 查看所有图片
     */
    @GetMapping
    public ApiResponse<PageResponse<PhotoResponse>> listPhotos(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer size) {
        return ApiResponse.success(photoService.listPhotos(page, size));
    }

    /**
     * 上传图片
     */
    @PutMapping
    public ApiResponse<UploadResponse> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("photoName") String photoName,
            Authentication authentication) {
        Long uploaderId = Long.parseLong(authentication.getName());
        return ApiResponse.success(photoService.uploadPhoto(file, photoName, uploaderId));
    }
}


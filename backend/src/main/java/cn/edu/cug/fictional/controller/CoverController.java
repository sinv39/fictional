package cn.edu.cug.fictional.controller;

import cn.edu.cug.fictional.dto.response.ApiResponse;
import cn.edu.cug.fictional.dto.response.CoverResponse;
import cn.edu.cug.fictional.service.CoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 封面控制器
 */
@RestController
@RequestMapping("/api/cover")
@RequiredArgsConstructor
public class CoverController {

    private final CoverService coverService;

    /**
     * 获取主页封面
     */
    @GetMapping("/home")
    public ApiResponse<CoverResponse> getHomeCover() {
        return ApiResponse.success(coverService.getHomeCover());
    }

    /**
     * 获取社团介绍封面
     */
    @GetMapping("/intro")
    public ApiResponse<CoverResponse> getIntroCover() {
        return ApiResponse.success(coverService.getIntroCover());
    }

    /**
     * 更新主页封面（需要ADMIN权限）
     */
    @PutMapping("/home")
    public ApiResponse<Void> updateHomeCover(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        Long updaterId = Long.parseLong(authentication.getName());
        coverService.updateHomeCover(file, updaterId);
        return ApiResponse.success();
    }

    /**
     * 更新介绍页封面（需要ADMIN权限）
     */
    @PutMapping("/intro")
    public ApiResponse<Void> updateIntroCover(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "introduction", required = false) String introduction,
            Authentication authentication) {
        Long updaterId = Long.parseLong(authentication.getName());
        coverService.updateIntroCover(file, introduction, updaterId);
        return ApiResponse.success();
    }
}


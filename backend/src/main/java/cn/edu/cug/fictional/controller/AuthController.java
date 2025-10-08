package cn.edu.cug.fictional.controller;

import cn.edu.cug.fictional.dto.request.ApplyRequest;
import cn.edu.cug.fictional.dto.request.LoginRequest;
import cn.edu.cug.fictional.dto.response.ApiResponse;
import cn.edu.cug.fictional.dto.response.LoginResponse;
import cn.edu.cug.fictional.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }

    /**
     * 申请加入
     */
    @PostMapping("/apply")
    public ApiResponse<Void> apply(@Valid @RequestBody ApplyRequest request) {
        authService.apply(request);
        return ApiResponse.success();
    }
}


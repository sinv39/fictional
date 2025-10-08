package cn.edu.cug.fictional.service;

import cn.edu.cug.fictional.dto.request.ApplyRequest;
import cn.edu.cug.fictional.dto.request.LoginRequest;
import cn.edu.cug.fictional.dto.response.LoginResponse;

/**
 * 认证服务接口
 */
public interface AuthService {
    
    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 申请加入
     */
    void apply(ApplyRequest request);
}


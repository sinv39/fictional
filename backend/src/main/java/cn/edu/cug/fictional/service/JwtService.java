package cn.edu.cug.fictional.service;

import cn.edu.cug.fictional.entity.User;

/**
 * JWT服务接口
 */
public interface JwtService {
    
    /**
     * 生成Token
     */
    String generateToken(User user);
    
    /**
     * 从Token中获取学号
     */
    String getStudentIdFromToken(String token);
    
    /**
     * 验证Token
     */
    boolean validateToken(String token);
}


package cn.edu.cug.fictional.service;

import cn.edu.cug.fictional.dto.response.CoverResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 封面服务接口
 */
public interface CoverService {
    
    /**
     * 获取主页封面
     */
    CoverResponse getHomeCover();
    
    /**
     * 获取介绍页封面
     */
    CoverResponse getIntroCover();
    
    /**
     * 更新主页封面
     */
    void updateHomeCover(MultipartFile file, Long updaterId);
    
    /**
     * 更新介绍页封面
     */
    void updateIntroCover(MultipartFile file, String introduction, Long updaterId);
}


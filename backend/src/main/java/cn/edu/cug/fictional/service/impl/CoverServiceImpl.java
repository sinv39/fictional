package cn.edu.cug.fictional.service.impl;

import cn.edu.cug.fictional.config.MinioConfig;
import cn.edu.cug.fictional.dto.response.CoverResponse;
import cn.edu.cug.fictional.entity.CoverConfig;
import cn.edu.cug.fictional.exception.BusinessException;
import cn.edu.cug.fictional.mapper.CoverConfigMapper;
import cn.edu.cug.fictional.service.CoverService;
import cn.edu.cug.fictional.service.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 封面服务实现类
 */
@Service
@RequiredArgsConstructor
public class CoverServiceImpl implements CoverService {

    private final CoverConfigMapper coverConfigMapper;
    private final MinioService minioService;
    private final MinioConfig minioConfig;

    @Override
    public CoverResponse getHomeCover() {
        CoverConfig config = coverConfigMapper.selectByType("HOME");
        if (config == null) {
            throw new BusinessException("主页封面配置不存在");
        }
        String url = minioService.getPresignedUrl(minioConfig.getPhotoBucket(), config.getCoverPath());
        return new CoverResponse(url);
    }

    @Override
    public CoverResponse getIntroCover() {
        CoverConfig config = coverConfigMapper.selectByType("INTRO");
        if (config == null) {
            throw new BusinessException("介绍页封面配置不存在");
        }
        String url = minioService.getPresignedUrl(minioConfig.getPhotoBucket(), config.getCoverPath());
        return new CoverResponse(url, config.getIntroduction());
    }

    @Override
    public void updateHomeCover(org.springframework.web.multipart.MultipartFile file, Long updaterId) {
        // 上传新封面到MinIO
        String path = minioService.uploadPhoto(file, "home-cover");
        
        // 更新数据库
        coverConfigMapper.updateCoverPath("HOME", path, updaterId);
    }

    @Override
    public void updateIntroCover(org.springframework.web.multipart.MultipartFile file, String introduction, Long updaterId) {
        // 如果有新图片，上传到MinIO
        if (file != null && !file.isEmpty()) {
            String path = minioService.uploadPhoto(file, "intro-cover");
            coverConfigMapper.updateCoverPath("INTRO", path, updaterId);
        }
        
        // 更新介绍文字
        if (introduction != null && !introduction.isEmpty()) {
            coverConfigMapper.updateIntroduction("INTRO", introduction, updaterId);
        }
    }
}


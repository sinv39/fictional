package cn.edu.cug.fictional.service.impl;

import cn.edu.cug.fictional.config.MinioConfig;
import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.dto.response.PhotoResponse;
import cn.edu.cug.fictional.dto.response.UploadResponse;
import cn.edu.cug.fictional.entity.Photo;
import cn.edu.cug.fictional.entity.User;
import cn.edu.cug.fictional.mapper.PhotoMapper;
import cn.edu.cug.fictional.mapper.UserMapper;
import cn.edu.cug.fictional.service.MinioService;
import cn.edu.cug.fictional.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 图片服务实现类
 */
@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoMapper photoMapper;
    private final UserMapper userMapper;
    private final MinioService minioService;
    private final MinioConfig minioConfig;

    @Override
    public PageResponse<PhotoResponse> listPhotos(Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Photo> photos = photoMapper.selectWithPage(offset, size);
        Long total = photoMapper.count();
        
        List<PhotoResponse> responseList = photos.stream().map(photo -> {
            PhotoResponse response = new PhotoResponse();
            response.setId(photo.getId());
            response.setPhotoName(photo.getPhotoName());
            response.setPhotoUrl(minioService.getPresignedUrl(minioConfig.getPhotoBucket(), photo.getPhotoPath()));
            response.setUploader(photo.getUploaderName());
            response.setUploadTime(photo.getUploadTime());
            return response;
        }).collect(Collectors.toList());
        
        return new PageResponse<>(total, responseList);
    }

    @Override
    public UploadResponse uploadPhoto(MultipartFile file, String photoName, Long uploaderId) {
        // 上传到MinIO
        String path = minioService.uploadPhoto(file, photoName);
        
        // 获取上传者信息
        User user = userMapper.selectById(uploaderId);
        
        // 保存图片信息到数据库
        Photo photo = new Photo();
        photo.setPhotoName(photoName);
        photo.setPhotoPath(path);
        photo.setFileSize(file.getSize());
        photo.setUploaderId(uploaderId);
        photo.setUploaderName(user.getRealName());
        photo.setStatus(1);
        
        photoMapper.insert(photo);
        
        // 返回预签名URL
        String url = minioService.getPresignedUrl(minioConfig.getPhotoBucket(), path);
        return new UploadResponse(photo.getId(), photoName, url);
    }
}


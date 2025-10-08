package cn.edu.cug.fictional.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 图片实体类
 */
@Data
public class Photo {
    private Long id;
    private String photoName;
    private String photoPath;
    private Long fileSize;
    private Integer width;
    private Integer height;
    private Long uploaderId;
    private String uploaderName;
    private LocalDateTime uploadTime;
    private Integer status;  // 1-正常，0-删除
}


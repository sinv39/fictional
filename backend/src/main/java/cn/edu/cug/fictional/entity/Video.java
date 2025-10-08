package cn.edu.cug.fictional.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 视频实体类
 */
@Data
public class Video {
    private Long id;
    private String videoName;
    private String videoPath;
    private Long fileSize;
    private Integer duration;
    private Long uploaderId;
    private String uploaderName;
    private LocalDateTime uploadTime;
    private Integer status;  // 1-正常，0-删除
}


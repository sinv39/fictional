package cn.edu.cug.fictional.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 视频响应
 */
@Data
public class VideoResponse {
    private Long id;
    private String videoName;
    private String videoUrl;
    private String uploader;
    private LocalDateTime uploadTime;
}


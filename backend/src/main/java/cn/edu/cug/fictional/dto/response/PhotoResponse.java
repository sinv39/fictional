package cn.edu.cug.fictional.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 图片响应
 */
@Data
public class PhotoResponse {
    private Long id;
    private String photoName;
    private String photoUrl;
    private String uploader;
    private LocalDateTime uploadTime;
}


package cn.edu.cug.fictional.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 大事记响应
 */
@Data
public class EventResponse {
    private Long id;
    private String title;
    private String content;
    private String publisher;
    private LocalDateTime createTime;
}


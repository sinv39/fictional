package cn.edu.cug.fictional.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 申请响应
 */
@Data
public class ApplicationResponse {
    private Long id;
    private String studentId;
    private LocalDateTime applyTime;
    private String status;
}


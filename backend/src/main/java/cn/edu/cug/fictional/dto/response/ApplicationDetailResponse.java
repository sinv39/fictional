package cn.edu.cug.fictional.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 申请详情响应
 */
@Data
public class ApplicationDetailResponse {
    private Long id;
    private String studentId;
    private String college;
    private String realName;
    private String dormitory;
    private String phone;
    private String qq;
    private String introduction;
    private LocalDateTime applyTime;
    private String status;
}


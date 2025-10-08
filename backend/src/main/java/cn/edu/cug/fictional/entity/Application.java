package cn.edu.cug.fictional.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 申请实体类
 */
@Data
public class Application {
    private Long id;
    private String studentId;
    private String realName;
    private String college;
    private String dormitory;
    private String phone;
    private String qq;
    private String password;
    private String introduction;
    private String status;  // PENDING / APPROVED / REJECTED
    private LocalDateTime applyTime;
    private LocalDateTime handleTime;
    private Long handlerId;
    private String rejectReason;
}


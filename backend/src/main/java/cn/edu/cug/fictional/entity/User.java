package cn.edu.cug.fictional.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
public class User {
    private Long id;
    private String studentId;
    private String password;
    private String realName;
    private String college;
    private String dormitory;
    private String phone;
    private String qq;
    private String introduction;
    private String role;  // USER / ADMIN
    private Integer status;  // 1-正常，0-禁用
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}


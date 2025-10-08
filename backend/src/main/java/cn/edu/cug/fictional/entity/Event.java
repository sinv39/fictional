package cn.edu.cug.fictional.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 大事记实体类
 */
@Data
public class Event {
    private Long id;
    private String title;
    private String content;
    private Long publisherId;
    private String publisherName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer status;  // 1-正常，0-删除
}


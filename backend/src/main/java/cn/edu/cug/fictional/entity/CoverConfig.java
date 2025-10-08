package cn.edu.cug.fictional.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 封面配置实体类
 */
@Data
public class CoverConfig {
    private Long id;
    private String coverType;  // HOME / INTRO
    private String coverPath;
    private String introduction;
    private LocalDateTime updateTime;
    private Long updaterId;
}


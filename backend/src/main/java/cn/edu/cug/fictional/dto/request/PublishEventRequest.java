package cn.edu.cug.fictional.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 发布大事记请求
 */
@Data
public class PublishEventRequest {
    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;
}


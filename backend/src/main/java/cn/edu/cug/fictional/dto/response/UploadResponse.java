package cn.edu.cug.fictional.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 上传响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {
    private Long id;
    private String name;
    private String url;
}


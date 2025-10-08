package cn.edu.cug.fictional.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封面响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoverResponse {
    private String coverUrl;
    private String introduction;

    public CoverResponse(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}


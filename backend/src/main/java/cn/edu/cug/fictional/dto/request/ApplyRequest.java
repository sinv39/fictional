package cn.edu.cug.fictional.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 申请加入请求
 */
@Data
public class ApplyRequest {
    @NotBlank(message = "学号不能为空")
    private String studentId;

    @NotBlank(message = "学院不能为空")
    private String college;

    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @NotBlank(message = "宿舍号不能为空")
    private String dormitory;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "QQ号不能为空")
    private String qq;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String introduction;
}


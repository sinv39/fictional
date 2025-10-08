package cn.edu.cug.fictional.exception;

import cn.edu.cug.fictional.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    /**
     * 权限异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<?> handleAccessDeniedException(AccessDeniedException e) {
        log.error("权限异常: {}", e.getMessage());
        return ApiResponse.error("无权访问此接口");
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ApiResponse<?> handleValidationException(Exception e) {
        String message = "参数校验失败";
        if (e instanceof MethodArgumentNotValidException) {
            var result = ((MethodArgumentNotValidException) e).getBindingResult();
            if (result.hasErrors() && result.getFieldError() != null) {
                message = result.getFieldError().getDefaultMessage();
            }
        } else if (e instanceof BindException) {
            var result = ((BindException) e).getBindingResult();
            if (result.hasErrors() && result.getFieldError() != null) {
                message = result.getFieldError().getDefaultMessage();
            }
        }
        log.error("参数校验异常: {}", message);
        return ApiResponse.error(message);
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("系统异常: ", e);
        return ApiResponse.error("系统异常，请联系管理员");
    }
}


package cn.edu.cug.fictional.controller;

import cn.edu.cug.fictional.dto.response.ApiResponse;
import cn.edu.cug.fictional.dto.response.ApplicationDetailResponse;
import cn.edu.cug.fictional.dto.response.ApplicationResponse;
import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 申请审批控制器
 */
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * 查看所有申请
     */
    @GetMapping
    public ApiResponse<PageResponse<ApplicationResponse>> listApplications(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.success(applicationService.listApplications(status, page, size));
    }

    /**
     * 查看申请详情
     */
    @GetMapping("/{id}")
    public ApiResponse<ApplicationDetailResponse> getApplicationDetail(@PathVariable Long id) {
        return ApiResponse.success(applicationService.getApplicationDetail(id));
    }

    /**
     * 同意申请
     */
    @PostMapping("/{id}/approve")
    public ApiResponse<Void> approveApplication(@PathVariable Long id, Authentication authentication) {
        Long handlerId = Long.parseLong(authentication.getName());
        applicationService.approveApplication(id, handlerId);
        return ApiResponse.success();
    }

    /**
     * 拒绝申请
     */
    @PostMapping("/{id}/reject")
    public ApiResponse<Void> rejectApplication(@PathVariable Long id, Authentication authentication) {
        Long handlerId = Long.parseLong(authentication.getName());
        applicationService.rejectApplication(id, handlerId);
        return ApiResponse.success();
    }
}


package cn.edu.cug.fictional.service;

import cn.edu.cug.fictional.dto.response.ApplicationDetailResponse;
import cn.edu.cug.fictional.dto.response.ApplicationResponse;
import cn.edu.cug.fictional.dto.response.PageResponse;

/**
 * 申请服务接口
 */
public interface ApplicationService {
    
    /**
     * 查看所有申请
     */
    PageResponse<ApplicationResponse> listApplications(String status, Integer page, Integer size);
    
    /**
     * 查看申请详情
     */
    ApplicationDetailResponse getApplicationDetail(Long id);
    
    /**
     * 同意申请
     */
    void approveApplication(Long id, Long handlerId);
    
    /**
     * 拒绝申请
     */
    void rejectApplication(Long id, Long handlerId);
}


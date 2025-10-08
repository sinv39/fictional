package cn.edu.cug.fictional.service.impl;

import cn.edu.cug.fictional.dto.response.ApplicationDetailResponse;
import cn.edu.cug.fictional.dto.response.ApplicationResponse;
import cn.edu.cug.fictional.dto.response.PageResponse;
import cn.edu.cug.fictional.entity.Application;
import cn.edu.cug.fictional.entity.User;
import cn.edu.cug.fictional.exception.BusinessException;
import cn.edu.cug.fictional.mapper.ApplicationMapper;
import cn.edu.cug.fictional.mapper.UserMapper;
import cn.edu.cug.fictional.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 申请服务实现类
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper applicationMapper;
    private final UserMapper userMapper;

    @Override
    public PageResponse<ApplicationResponse> listApplications(String status, Integer page, Integer size) {
        int offset = (page - 1) * size;
        List<Application> applications = applicationMapper.selectByStatusWithPage(status, offset, size);
        Long total = applicationMapper.countByStatus(status);
        
        List<ApplicationResponse> responseList = applications.stream().map(app -> {
            ApplicationResponse response = new ApplicationResponse();
            response.setId(app.getId());
            response.setStudentId(app.getStudentId());
            response.setApplyTime(app.getApplyTime());
            response.setStatus(app.getStatus());
            return response;
        }).collect(Collectors.toList());
        
        return new PageResponse<>(total, responseList);
    }

    @Override
    public ApplicationDetailResponse getApplicationDetail(Long id) {
        Application app = applicationMapper.selectById(id);
        if (app == null) {
            throw new BusinessException("申请不存在");
        }
        
        ApplicationDetailResponse response = new ApplicationDetailResponse();
        response.setId(app.getId());
        response.setStudentId(app.getStudentId());
        response.setCollege(app.getCollege());
        response.setRealName(app.getRealName());
        response.setDormitory(app.getDormitory());
        response.setPhone(app.getPhone());
        response.setQq(app.getQq());
        response.setIntroduction(app.getIntroduction());
        response.setApplyTime(app.getApplyTime());
        response.setStatus(app.getStatus());
        return response;
    }

    @Override
    @Transactional
    public void approveApplication(Long id, Long handlerId) {
        Application app = applicationMapper.selectById(id);
        if (app == null) {
            throw new BusinessException("申请不存在");
        }
        if (!"PENDING".equals(app.getStatus())) {
            throw new BusinessException("申请已处理");
        }
        
        // 创建用户账号，使用申请时填写的密码
        User user = new User();
        user.setStudentId(app.getStudentId());
        user.setPassword(app.getPassword());  // 直接使用申请时已加密的密码
        user.setRealName(app.getRealName());
        user.setCollege(app.getCollege());
        user.setDormitory(app.getDormitory());
        user.setPhone(app.getPhone());
        user.setQq(app.getQq());
        user.setIntroduction(app.getIntroduction());
        user.setRole("USER");
        user.setStatus(1);
        
        userMapper.insert(user);
        
        // 删除申请记录
        applicationMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void rejectApplication(Long id, Long handlerId) {
        Application app = applicationMapper.selectById(id);
        if (app == null) {
            throw new BusinessException("申请不存在");
        }
        if (!"PENDING".equals(app.getStatus())) {
            throw new BusinessException("申请已处理");
        }
        
        // 删除申请记录
        applicationMapper.deleteById(id);
    }
}


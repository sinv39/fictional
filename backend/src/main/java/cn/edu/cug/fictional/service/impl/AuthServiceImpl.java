package cn.edu.cug.fictional.service.impl;

import cn.edu.cug.fictional.dto.request.ApplyRequest;
import cn.edu.cug.fictional.dto.request.LoginRequest;
import cn.edu.cug.fictional.dto.response.LoginResponse;
import cn.edu.cug.fictional.entity.Application;
import cn.edu.cug.fictional.entity.User;
import cn.edu.cug.fictional.exception.BusinessException;
import cn.edu.cug.fictional.mapper.ApplicationMapper;
import cn.edu.cug.fictional.mapper.UserMapper;
import cn.edu.cug.fictional.service.AuthService;
import cn.edu.cug.fictional.service.JwtService;
import cn.edu.cug.fictional.util.Argon2Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现类
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final ApplicationMapper applicationMapper;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        // 查询用户
        User user = userMapper.selectByStudentId(request.getStudentId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 验证密码
        if (!Argon2Util.verify(user.getPassword(), request.getPassword())) {
            throw new BusinessException("密码错误");
        }
        
        // 生成Token
        String token = jwtService.generateToken(user);
        
        return new LoginResponse(token, user.getStudentId(), user.getRealName(), user.getRole());
    }

    @Override
    public void apply(ApplyRequest request) {
        // 检查是否已经是社团成员
        User existUser = userMapper.selectByStudentId(request.getStudentId());
        if (existUser != null) {
            throw new BusinessException("您已经是社团的一员了！");
        }
        
        // 检查是否有待审批的申请
        Application existApp = applicationMapper.selectLatestByStudentId(request.getStudentId());
        if (existApp != null && "PENDING".equals(existApp.getStatus())) {
            throw new BusinessException("待审批");
        }
        
        // 创建申请
        Application application = new Application();
        application.setStudentId(request.getStudentId());
        application.setRealName(request.getRealName());
        application.setCollege(request.getCollege());
        application.setDormitory(request.getDormitory());
        application.setPhone(request.getPhone());
        application.setQq(request.getQq());
        application.setPassword(Argon2Util.hash(request.getPassword()));  // 加密用户填写的密码
        application.setIntroduction(request.getIntroduction());
        application.setStatus("PENDING");
        
        applicationMapper.insert(application);
    }
}


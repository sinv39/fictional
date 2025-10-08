package cn.edu.cug.fictional.mapper;

import cn.edu.cug.fictional.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据学号查询用户
     */
    User selectByStudentId(@Param("studentId") String studentId);
    
    /**
     * 插入用户
     */
    int insert(User user);
    
    /**
     * 根据ID查询用户
     */
    User selectById(@Param("id") Long id);
}


package cn.edu.cug.fictional.mapper;

import cn.edu.cug.fictional.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 申请Mapper
 */
@Mapper
public interface ApplicationMapper {
    
    /**
     * 插入申请
     */
    int insert(Application application);
    
    /**
     * 根据学号查询最新申请
     */
    Application selectLatestByStudentId(@Param("studentId") String studentId);
    
    /**
     * 分页查询申请列表
     */
    List<Application> selectByStatusWithPage(@Param("status") String status, 
                                             @Param("offset") Integer offset, 
                                             @Param("limit") Integer limit);
    
    /**
     * 统计申请数量
     */
    Long countByStatus(@Param("status") String status);
    
    /**
     * 根据ID查询申请
     */
    Application selectById(@Param("id") Long id);
    
    /**
     * 更新申请状态
     */
    int updateStatus(@Param("id") Long id, 
                    @Param("status") String status, 
                    @Param("handlerId") Long handlerId);
    
    /**
     * 删除申请
     */
    int deleteById(@Param("id") Long id);
}


package cn.edu.cug.fictional.mapper;

import cn.edu.cug.fictional.entity.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 大事记Mapper
 */
@Mapper
public interface EventMapper {
    
    /**
     * 插入大事记
     */
    int insert(Event event);
    
    /**
     * 分页查询大事记列表
     */
    List<Event> selectWithPage(@Param("offset") Integer offset, 
                               @Param("limit") Integer limit);
    
    /**
     * 统计大事记总数
     */
    Long count();
}


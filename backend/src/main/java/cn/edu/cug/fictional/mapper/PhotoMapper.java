package cn.edu.cug.fictional.mapper;

import cn.edu.cug.fictional.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 图片Mapper
 */
@Mapper
public interface PhotoMapper {
    
    /**
     * 插入图片
     */
    int insert(Photo photo);
    
    /**
     * 分页查询图片列表
     */
    List<Photo> selectWithPage(@Param("offset") Integer offset, 
                               @Param("limit") Integer limit);
    
    /**
     * 统计图片总数
     */
    Long count();
    
    /**
     * 根据ID查询图片
     */
    Photo selectById(@Param("id") Long id);
}


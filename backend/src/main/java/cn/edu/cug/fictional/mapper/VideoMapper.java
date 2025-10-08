package cn.edu.cug.fictional.mapper;

import cn.edu.cug.fictional.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 视频Mapper
 */
@Mapper
public interface VideoMapper {
    
    /**
     * 插入视频
     */
    int insert(Video video);
    
    /**
     * 分页查询视频列表
     */
    List<Video> selectWithPage(@Param("offset") Integer offset, 
                               @Param("limit") Integer limit);
    
    /**
     * 统计视频总数
     */
    Long count();
    
    /**
     * 根据ID查询视频
     */
    Video selectById(@Param("id") Long id);
}


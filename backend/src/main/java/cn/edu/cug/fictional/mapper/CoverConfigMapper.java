package cn.edu.cug.fictional.mapper;

import cn.edu.cug.fictional.entity.CoverConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 封面配置Mapper
 */
@Mapper
public interface CoverConfigMapper {
    
    /**
     * 根据类型查询封面配置
     */
    CoverConfig selectByType(@Param("coverType") String coverType);
    
    /**
     * 更新封面路径
     */
    int updateCoverPath(@Param("coverType") String coverType, 
                       @Param("coverPath") String coverPath, 
                       @Param("updaterId") Long updaterId);
    
    /**
     * 更新介绍文字
     */
    int updateIntroduction(@Param("coverType") String coverType, 
                          @Param("introduction") String introduction, 
                          @Param("updaterId") Long updaterId);
}

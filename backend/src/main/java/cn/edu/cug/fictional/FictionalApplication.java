package cn.edu.cug.fictional;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 中国地质大学科幻界社团管理系统
 * 主启动类
 * 
 * @author Fictional Team
 * @since 2024
 */
@SpringBootApplication
@MapperScan("cn.edu.cug.cs.gtl.fictional.mapper")
public class FictionalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FictionalApplication.class, args);
        System.out.println("========================================");
        System.out.println("科幻界社团管理系统启动成功！");
        System.out.println("访问地址: http://localhost:10104");
        System.out.println("========================================");
    }
}


-- ========================================
-- 中国地质大学科幻界社团管理系统
-- 数据库初始化脚本
-- ========================================

-- 如果数据库已存在则删除（谨慎使用）
-- DROP DATABASE IF EXISTS fiction;

-- 创建数据库
CREATE DATABASE IF NOT EXISTS fiction DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE fiction;

-- ========================================
-- 表1：用户表 (tb_user)
-- 存储社团成员信息
-- ========================================
DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
    id BIGINT AUTO_INCREMENT COMMENT '用户ID',
    student_id VARCHAR(20) NOT NULL COMMENT '学号',
    password VARCHAR(255) NOT NULL COMMENT '密码（ARGON2加密）',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    college VARCHAR(100) NOT NULL COMMENT '学院',
    dormitory VARCHAR(50) NOT NULL COMMENT '宿舍号',
    phone VARCHAR(20) NOT NULL COMMENT '手机号',
    qq VARCHAR(20) NOT NULL COMMENT 'QQ号',
    introduction TEXT COMMENT '个人简介',
    role VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色（USER/ADMIN）',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态（1-正常，0-禁用）',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_student_id (student_id),
    KEY idx_role (role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ========================================
-- 表2：申请表 (tb_application)
-- 存储入社申请信息
-- ========================================
DROP TABLE IF EXISTS tb_application;
CREATE TABLE tb_application (
    id BIGINT AUTO_INCREMENT COMMENT '申请ID',
    student_id VARCHAR(20) NOT NULL COMMENT '学号',
    real_name VARCHAR(50) NOT NULL COMMENT '真实姓名',
    college VARCHAR(100) NOT NULL COMMENT '学院',
    dormitory VARCHAR(50) NOT NULL COMMENT '宿舍号',
    phone VARCHAR(20) NOT NULL COMMENT '手机号',
    qq VARCHAR(20) NOT NULL COMMENT 'QQ号',
    introduction TEXT COMMENT '个人简介',
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态（PENDING-待审批/APPROVED-已通过/REJECTED-已拒绝）',
    apply_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    handle_time DATETIME COMMENT '处理时间',
    handler_id BIGINT COMMENT '处理人ID',
    reject_reason VARCHAR(255) COMMENT '拒绝原因',
    PRIMARY KEY (id),
    KEY idx_student_id (student_id),
    KEY idx_status (status),
    KEY idx_apply_time (apply_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='申请表';

-- ========================================
-- 表3：视频表 (tb_video)
-- 存储社团视频信息
-- ========================================
DROP TABLE IF EXISTS tb_video;
CREATE TABLE tb_video (
    id BIGINT AUTO_INCREMENT COMMENT '视频ID',
    video_name VARCHAR(200) NOT NULL COMMENT '视频名称',
    video_path VARCHAR(500) NOT NULL COMMENT 'MinIO存储路径',
    file_size BIGINT NOT NULL COMMENT '文件大小（字节）',
    duration INT COMMENT '时长（秒）',
    uploader_id BIGINT NOT NULL COMMENT '上传者ID',
    uploader_name VARCHAR(50) NOT NULL COMMENT '上传者姓名',
    upload_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态（1-正常，0-删除）',
    PRIMARY KEY (id),
    KEY idx_uploader_id (uploader_id),
    KEY idx_upload_time (upload_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='视频表';

-- ========================================
-- 表4：图片表 (tb_photo)
-- 存储社团图片信息
-- ========================================
DROP TABLE IF EXISTS tb_photo;
CREATE TABLE tb_photo (
    id BIGINT AUTO_INCREMENT COMMENT '图片ID',
    photo_name VARCHAR(200) NOT NULL COMMENT '图片名称',
    photo_path VARCHAR(500) NOT NULL COMMENT 'MinIO存储路径',
    file_size BIGINT NOT NULL COMMENT '文件大小（字节）',
    width INT COMMENT '图片宽度',
    height INT COMMENT '图片高度',
    uploader_id BIGINT NOT NULL COMMENT '上传者ID',
    uploader_name VARCHAR(50) NOT NULL COMMENT '上传者姓名',
    upload_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上传时间',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态（1-正常，0-删除）',
    PRIMARY KEY (id),
    KEY idx_uploader_id (uploader_id),
    KEY idx_upload_time (upload_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='图片表';

-- ========================================
-- 表5：大事记表 (tb_event)
-- 存储社团大事记
-- ========================================
DROP TABLE IF EXISTS tb_event;
CREATE TABLE tb_event (
    id BIGINT AUTO_INCREMENT COMMENT '大事记ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    publisher_id BIGINT NOT NULL COMMENT '发布者ID',
    publisher_name VARCHAR(50) NOT NULL COMMENT '发布者姓名',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态（1-正常，0-删除）',
    PRIMARY KEY (id),
    KEY idx_publisher_id (publisher_id),
    KEY idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='大事记表';

-- ========================================
-- 表6：封面配置表 (tb_cover_config)
-- 存储网站封面配置
-- ========================================
DROP TABLE IF EXISTS tb_cover_config;
CREATE TABLE tb_cover_config (
    id BIGINT AUTO_INCREMENT COMMENT '配置ID',
    cover_type VARCHAR(50) NOT NULL COMMENT '封面类型（HOME-主页封面/INTRO-介绍封面）',
    cover_path VARCHAR(500) NOT NULL COMMENT 'MinIO存储路径',
    introduction TEXT COMMENT '社团介绍文字（仅INTRO类型使用）',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    updater_id BIGINT COMMENT '更新者ID',
    PRIMARY KEY (id),
    UNIQUE KEY uk_cover_type (cover_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='封面配置表';

-- ========================================
-- 初始化数据
-- ========================================

-- 1. 插入初始管理员账户
-- 注意：这里的密码是 "admin123" 经过 ARGON2 加密后的示例值
-- 实际使用时需要用真实的 ARGON2 加密值替换
-- 在线加密工具：https://argon2.online/ 或使用后端代码生成
INSERT INTO tb_user (student_id, password, real_name, college, dormitory, phone, qq, introduction, role) 
VALUES (
    'admin', 
    '$argon2id$v=19$m=65536,t=3,p=4$placeholder', 
    '系统管理员', 
    '计算机学院', 
    '-', 
    '13800000000', 
    '10000', 
    '系统初始管理员账户，请在首次登录后修改密码', 
    'ADMIN'
);

-- 2. 插入默认封面配置
INSERT INTO tb_cover_config (cover_type, cover_path, introduction) 
VALUES 
(
    'HOME', 
    'default/home-cover.jpg', 
    NULL
),
(
    'INTRO', 
    'default/intro-cover.jpg', 
    '中国地质大学科幻界成立于2020年，是一个充满创意和想象力的学生社团。我们致力于传播科幻文化，组织各类科幻活动，包括科幻小说阅读分享、科幻电影观影讨论、科幻写作创作等。社团成员来自不同学院，共同探索科幻世界的无限可能。我们欢迎所有热爱科幻的同学加入！'
);

-- ========================================
-- 测试数据（可选，开发环境使用）
-- ========================================

-- 插入测试用户
-- INSERT INTO tb_user (student_id, password, real_name, college, dormitory, phone, qq, introduction, role) 
-- VALUES 
-- ('2021001', '$argon2id$v=19$m=65536,t=3,p=4$placeholder', '张三', '地球科学学院', '学1-101', '13800138001', '123456789', '我热爱科幻文学', 'USER'),
-- ('2021002', '$argon2id$v=19$m=65536,t=3,p=4$placeholder', '李四', '信息工程学院', '学2-202', '13800138002', '987654321', '对科幻电影很感兴趣', 'USER');

-- 插入测试申请
-- INSERT INTO tb_application (student_id, real_name, college, dormitory, phone, qq, introduction, status) 
-- VALUES 
-- ('2021003', '王五', '材料科学学院', '学3-303', '13800138003', '111222333', '想加入社团学习科幻创作', 'PENDING');

-- 插入测试大事记
-- INSERT INTO tb_event (title, content, publisher_id, publisher_name) 
-- VALUES 
-- ('社团成立大会', '2020年10月1日，中国地质大学科幻界正式成立，首批成员共50人参加了成立大会。', 1, '系统管理员'),
-- ('首届科幻征文比赛', '2021年春季，社团举办了首届科幻征文比赛，收到投稿作品30余篇，评选出优秀作品10篇。', 1, '系统管理员');

-- ========================================
-- 脚本执行完成
-- ========================================

SELECT '数据库初始化完成！' AS message;
SELECT CONCAT('创建了 ', COUNT(*), ' 张表') AS table_count FROM information_schema.tables WHERE table_schema = 'fiction';
SELECT '请记得修改管理员密码！' AS reminder;


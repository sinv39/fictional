# 中国地质大学科幻界社团管理系统

## 🎉 项目简介

完整的前后端分离社团管理系统，包含用户认证、内容管理、文件上传、申请审批等功能。

**版本**：v1.2.1  
**更新日期**：2025-10-08  
**部署方式**：本地编译 + Docker部署

---

## 📁 项目结构

```
fictional/
├── README.md              # 本文档
├── backend/               # 🔧 后端代码（Spring Boot 3）
│   ├── src/              # Java源代码
│   ├── pom.xml           # Maven配置
│   └── target/           # 编译产物（jar包）
├── frontend/              # 🎨 前端代码（Vue 3）
│   ├── src/              # Vue源代码
│   ├── dist/             # 构建产物
│   ├── package.json      # npm配置
│   ├── Dockerfile.simple # Docker镜像（简化版）
│   └── nginx.conf        # Nginx配置
├── deploy/                # 🚀 部署文件
│   ├── docker-compose.yml
│   ├── Dockerfile        # 后端Docker镜像
│   ├── deploy.sh         # 部署脚本
│   └── README.md
└── docs/                  # 📚 文档
    ├── sql/              # SQL脚本
    │   ├── init.sql     # 初始化脚本
    │   └── update-application-table.sql
    ├── API文档.md
    ├── 数据库文档.md
    ├── 部署教程.md
    └── 页面设计.html    # 页面设计原型
```

---

## 🛠️ 技术栈

### 后端
- Java 17
- Spring Boot 3.2.0
- Spring Security + JWT
- MyBatis 3.0.3
- MySQL 8.0
- MinIO 8.5.7
- Argon2密码加密

### 前端
- Vue 3.3.4
- Element Plus 2.4
- Vue Router 4.2
- Pinia 2.1
- Axios 1.6
- Vite 4.5

---

## 🚀 快速部署

### 步骤1：初始化MinIO（首次部署）

```bash
# Windows本地运行
init-minio.bat
```

**作用**：
- 连接到您部署的MinIO（123.60.40.72:10102）
- 创建photo-bucket和video-bucket
- 上传默认封面图片

### 步骤2：初始化MySQL数据库

```bash
# 在您的MySQL中执行
mysql -h 123.60.40.72 -P 10101 -u root -p fiction < docs\sql\init.sql
```

### 步骤3：编译并上传

```bash
# Windows本地
双击运行：编译和上传.bat
```

**自动完成**：
- 编译后端
- 构建前端
- 上传所有文件到服务器

### 步骤4：服务器部署

```bash
# 连接到服务器
ssh root@123.60.40.72

# 执行部署
cd /home/fiction/deploy
bash ./deploy.sh
```

**部署内容**：
- 启动后端服务（连接您的MySQL和MinIO）
- 启动前端服务

### 访问地址

- **前端**：http://123.60.40.72:10100
- **后端API**：http://123.60.40.72:10104
- **管理员**：admin / 12345678

---

## 📋 部署前准备

### 1. 外部服务（您自己部署）

- ✅ MySQL数据库：123.60.40.72:10101
- ✅ MinIO对象存储：123.60.40.72:10102

### 2. 服务器要求

- ✅ Docker已安装（20.10+）
- ✅ Docker Compose已安装（1.29+）

### 3. 本地准备

- ✅ JDK 17+（编译后端）
- ✅ Maven 3.6+（编译后端）
- ✅ Node.js 18+（构建前端）
- ✅ mc客户端（初始化MinIO）

---

## 📱 功能清单

### 公开功能
- ✅ 首页 - 封面展示 + 社团介绍
- ✅ 登录 - 用户认证
- ✅ 申请加入 - 用户设置密码申请 ⭐

### 成员功能
- ✅ 视频列表 - 在线播放
- ✅ 图片列表 - 图片查看器
- ✅ 大事记 - 时间线展示

### 管理功能（ADMIN）
- ✅ 视频管理 - 上传和管理
- ✅ 图片管理 - 上传和管理
- ✅ 大事记管理 - 发布和管理
- ✅ 申请审批 - 审批后删除记录 ⭐
- ✅ 封面管理 - 更新主页/介绍封面 ⭐

---

## 📚 文档列表

| 文档 | 说明 |
|------|------|
| docs/API文档.md | API接口规范 |
| docs/数据库文档.md | 数据库表结构 |
| docs/部署教程.md | Docker部署教程 |
| docs/页面设计.html | 页面设计原型（可视化） |
| docs/sql/init.sql | 数据库初始化脚本（Docker自动执行） |
| deploy/README.md | 部署文件说明 |

---

## 🔄 更新部署

### 更新后端

```bash
# 1. 本地编译
cd backend
mvn clean package -DskipTests

# 2. 上传
scp target/fictional-1.0-SNAPSHOT.jar root@123.60.40.72:/home/fiction/backend/target/

# 3. 服务器重启
ssh root@123.60.40.72
cd /home/fiction/deploy
docker-compose restart fictional-backend
```

### 更新前端

```bash
# 1. 本地构建
cd frontend
npm run build

# 2. 上传
scp -r dist root@123.60.40.72:/home/fiction/frontend/

# 3. 服务器重启
ssh root@123.60.40.72
cd /home/fiction/deploy
docker-compose restart fictional-frontend
```

---

## 🔒 权限说明

| 角色 | 可访问内容 |
|------|-----------|
| **游客** | 首页、登录、申请加入 |
| **成员(USER)** | 游客权限 + 视频、图片、大事记 |
| **管理员(ADMIN)** | 所有内容 + 管理后台 |

---

## 📊 端口配置

**Docker部署的服务**：
- **前端**：10100
- **后端**：10104

**外部服务（您自己部署）**：
- **MySQL数据库**：123.60.40.72:10101
- **MinIO对象存储**：123.60.40.72:10102

---

## 🔧 Docker管理

### 查看容器状态

```bash
cd /home/fiction/deploy
docker-compose ps
```

应看到2个容器：
- fictional-backend（后端）
- fictional-frontend（前端）

### 查看日志

```bash
# 所有服务
docker-compose logs -f

# 单个服务
docker-compose logs -f fictional-backend
docker-compose logs -f fictional-frontend
```

### 重启服务

```bash
docker-compose restart
```

### 停止服务

```bash
docker-compose stop
```

---

## 🐛 常见问题

**Q1：如何查看页面设计？**  
A：浏览器打开 `docs/页面设计.html`

**Q2：如何上传文件到服务器？**  
A：运行 `编译和上传.bat` 或使用 `上传到服务器.bat`

**Q3：部署失败怎么办？**  
A：查看 `docs/部署教程.md` 的故障排查部分

**Q4：如何更新代码？**  
A：重新编译、上传，然后 `docker-compose restart`

---

## 📞 获取帮助

1. 查看 `docs/部署教程.md` - 完整部署指南
2. 查看 `docs/API文档.md` - API接口文档
3. 查看 `docs/数据库文档.md` - 数据库设计
4. 打开 `docs/页面设计.html` - 查看页面设计
5. 查看 `deploy/README.md` - Docker详细说明

---

## ✅ 项目状态

- ✅ 后端开发完成
- ✅ 前端开发完成
- ✅ Docker配置完成
- ✅ 部署脚本完成
- ✅ 文档编写完成

---

**🎉 所有准备工作已完成，可以开始部署了！**

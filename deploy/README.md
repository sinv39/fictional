# Docker部署说明

## 📦 部署内容

Docker Compose只部署**前后端服务**（2个容器）：

### 1. 后端服务（fictional-backend）
- **镜像**：fictional-backend:1.2.0
- **基础镜像**：eclipse-temurin:17-jre-jammy
- **端口**：10104
- **连接**：外部MySQL（123.60.40.72:10101）和MinIO（123.60.40.72:10102）

### 2. 前端服务（fictional-frontend）
- **镜像**：fictional-frontend:1.2.0
- **基础镜像**：nginx:alpine
- **端口**：10100
- **依赖**：后端服务

---

## 🔧 外部服务要求

### MySQL数据库（您需要自己部署）
- **地址**：123.60.40.72:10101
- **用户**：root
- **密码**：aGF0c3VuZSBtaWt1
- **数据库**：fiction
- **初始化**：执行 `docs/sql/init.sql`

### MinIO对象存储（您需要自己部署）
- **地址**：123.60.40.72:10102
- **Access Key**：miku
- **Secret Key**：aGF0c3VuZSBtaWt1
- **初始化**：运行 `init-minio.bat`

---

## 🚀 部署步骤

### 1. 初始化MinIO

```bash
# Windows本地运行
init-minio.bat
```

### 2. 初始化MySQL

```bash
mysql -h 123.60.40.72 -P 10101 -u root -p fiction < docs/sql/init.sql
```

### 3. 部署Docker服务

```bash
cd /home/fiction/deploy
bash ./deploy.sh
```

---

## 📊 服务架构

```
浏览器
  ↓
fictional-frontend (nginx:10100)
  ↓ proxy /api/*
fictional-backend (spring-boot:10104)
  ├─► 外部MySQL (123.60.40.72:10101)
  └─► 外部MinIO (123.60.40.72:10102)
```

---

## ✅ 验证部署

### 检查容器

```bash
docker-compose ps
```

应显示2个容器：
- fictional-backend
- fictional-frontend

### 测试访问

```bash
# 前端
curl http://localhost:10100

# 后端
curl http://localhost:10104/api/cover/home
```

---

## 📋 常用命令

```bash
# 查看日志
docker-compose logs -f

# 重启服务
docker-compose restart

# 停止服务
docker-compose stop

# 删除容器
docker-compose down
```

---

**部署版本**：v1.2.2  
**部署内容**：仅前后端（2个容器）  
**外部依赖**：MySQL + MinIO（您自己部署）

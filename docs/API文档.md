# API接口文档

## 📡 基础信息

**Base URL**：`http://localhost:10104`  
**协议**：HTTP/HTTPS  
**数据格式**：JSON  
**字符编码**：UTF-8

---

## 🔐 认证方式

### JWT Token认证

**获取Token**：调用登录接口  
**使用Token**：在请求头中添加

```http
Authorization: Bearer {token}
```

**Token有效期**：24小时

---

## 📋 统一响应格式

### 成功响应

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    // 业务数据
  }
}
```

### 失败响应

```json
{
  "code": 1,
  "msg": "错误信息",
  "data": null
}
```

### 分页响应

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "total": 100,
    "list": [
      // 数据列表
    ]
  }
}
```

---

## 📚 接口列表

### 1. 认证接口

#### 1.1 用户登录

**接口**：`POST /api/auth/login`  
**权限**：公开  
**说明**：用户登录获取JWT Token

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| studentId | String | 是 | 学号 |
| password | String | 是 | 密码 |

**请求示例**：

```json
{
  "studentId": "admin",
  "password": "12345678"
}
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "studentId": "admin",
    "realName": "系统管理员",
    "role": "ADMIN"
  }
}
```

**错误响应**：

```json
{
  "code": 1,
  "msg": "用户不存在",
  "data": null
}
```

---

#### 1.2 申请加入

**接口**：`POST /api/auth/apply`  
**权限**：公开  
**说明**：提交入社申请（用户设置密码）

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| studentId | String | 是 | 学号 |
| realName | String | 是 | 真实姓名 |
| college | String | 是 | 学院 |
| dormitory | String | 是 | 宿舍号 |
| phone | String | 是 | 手机号 |
| qq | String | 是 | QQ号 |
| password | String | 是 | 密码（至少6位）⭐ |
| introduction | String | 否 | 个人简介 |

**请求示例**：

```json
{
  "studentId": "2024001",
  "realName": "张三",
  "college": "计算机学院",
  "dormitory": "学1-101",
  "phone": "13800138000",
  "qq": "123456789",
  "password": "mypassword123",
  "introduction": "热爱科幻文化"
}
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": null
}
```

---

### 2. 封面接口

#### 2.1 获取主页封面

**接口**：`GET /api/cover/home`  
**权限**：公开  
**说明**：获取主页封面图片URL

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "url": "http://123.60.40.72:10102/photos/default/home-cover.jpg?X-Amz-Algorithm=..."
  }
}
```

---

#### 2.2 获取介绍封面

**接口**：`GET /api/cover/intro`  
**权限**：公开  
**说明**：获取社团介绍封面和文字

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "url": "http://123.60.40.72:10102/photos/default/intro-cover.jpg?...",
    "introduction": "中国地质大学科幻界成立于2020年..."
  }
}
```

---

#### 2.3 更新主页封面

**接口**：`PUT /api/cover/home`  
**权限**：ADMIN  
**说明**：上传新的主页封面

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | File | 是 | 封面图片文件 |

**请求示例**：

```http
PUT /api/cover/home HTTP/1.1
Content-Type: multipart/form-data
Authorization: Bearer {token}

file: [binary data]
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": null
}
```

---

#### 2.4 更新介绍封面

**接口**：`PUT /api/cover/intro`  
**权限**：ADMIN  
**说明**：更新介绍封面图片或文字

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | File | 否 | 封面图片文件 |
| introduction | String | 否 | 介绍文字 |

**说明**：file和introduction至少提供一个

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": null
}
```

---

### 3. 视频接口

#### 3.1 获取视频列表

**接口**：`GET /api/videos`  
**权限**：需要登录  
**说明**：分页获取视频列表

**请求参数**：

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| page | Integer | 否 | 1 | 页码 |
| size | Integer | 否 | 10 | 每页数量 |

**请求示例**：

```http
GET /api/videos?page=1&size=10
Authorization: Bearer {token}
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "total": 25,
    "list": [
      {
        "id": 1,
        "videoName": "社团宣传片",
        "url": "http://123.60.40.72:10102/videos/xxx.mp4?...",
        "fileSize": 52428800,
        "duration": 180,
        "uploaderName": "系统管理员",
        "uploadTime": "2024-01-15T10:30:00"
      }
    ]
  }
}
```

---

#### 3.2 上传视频

**接口**：`PUT /api/videos`  
**权限**：ADMIN  
**说明**：上传视频文件到MinIO

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | File | 是 | 视频文件 |
| videoName | String | 是 | 视频名称 |

**请求示例**：

```http
PUT /api/videos HTTP/1.1
Content-Type: multipart/form-data
Authorization: Bearer {token}

file: [binary data]
videoName: 社团宣传片
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "videoName": "社团宣传片",
    "videoPath": "videos/2024/01/xxx.mp4"
  }
}
```

---

### 4. 图片接口

#### 4.1 获取图片列表

**接口**：`GET /api/photos`  
**权限**：需要登录  
**说明**：分页获取图片列表

**请求参数**：

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| page | Integer | 否 | 1 | 页码 |
| size | Integer | 否 | 20 | 每页数量 |

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "total": 50,
    "list": [
      {
        "id": 1,
        "photoName": "活动照片1",
        "url": "http://123.60.40.72:10102/photos/xxx.jpg?...",
        "fileSize": 2048000,
        "width": 1920,
        "height": 1080,
        "uploaderName": "系统管理员",
        "uploadTime": "2024-01-15T14:20:00"
      }
    ]
  }
}
```

---

#### 4.2 上传图片

**接口**：`PUT /api/photos`  
**权限**：ADMIN  
**说明**：上传图片文件到MinIO

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| file | File | 是 | 图片文件 |
| photoName | String | 是 | 图片名称 |

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "photoName": "活动照片1",
    "photoPath": "photos/2024/01/xxx.jpg"
  }
}
```

---

### 5. 大事记接口

#### 5.1 获取大事记列表

**接口**：`GET /api/events`  
**权限**：需要登录  
**说明**：分页获取大事记列表

**请求参数**：

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| page | Integer | 否 | 1 | 页码 |
| size | Integer | 否 | 10 | 每页数量 |

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "total": 15,
    "list": [
      {
        "id": 1,
        "title": "社团成立大会",
        "content": "2020年10月1日，中国地质大学科幻界正式成立...",
        "publisherName": "系统管理员",
        "createTime": "2020-10-01T09:00:00"
      }
    ]
  }
}
```

---

#### 5.2 发布大事记

**接口**：`POST /api/events`  
**权限**：ADMIN  
**说明**：发布新的大事记

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| title | String | 是 | 标题 |
| content | String | 是 | 内容 |

**请求示例**：

```json
{
  "title": "社团成立大会",
  "content": "2020年10月1日，中国地质大学科幻界正式成立..."
}
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "title": "社团成立大会",
    "publisherName": "系统管理员",
    "createTime": "2024-01-15T16:30:00"
  }
}
```

---

### 6. 申请管理接口

#### 6.1 获取申请列表

**接口**：`GET /api/applications`  
**权限**：ADMIN  
**说明**：分页获取申请列表

**请求参数**：

| 参数 | 类型 | 必填 | 默认值 | 说明 |
|------|------|------|--------|------|
| status | String | 否 | - | 状态筛选（PENDING/APPROVED/REJECTED） |
| page | Integer | 否 | 1 | 页码 |
| size | Integer | 否 | 10 | 每页数量 |

**请求示例**：

```http
GET /api/applications?status=PENDING&page=1&size=10
Authorization: Bearer {token}
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "total": 5,
    "list": [
      {
        "id": 1,
        "studentId": "2024001",
        "applyTime": "2024-01-15T08:00:00",
        "status": "PENDING"
      }
    ]
  }
}
```

---

#### 6.2 获取申请详情

**接口**：`GET /api/applications/:id`  
**权限**：ADMIN  
**说明**：查看申请详细信息

**路径参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | Long | 是 | 申请ID |

**请求示例**：

```http
GET /api/applications/1
Authorization: Bearer {token}
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": {
    "id": 1,
    "studentId": "2024001",
    "realName": "张三",
    "college": "计算机学院",
    "dormitory": "学1-101",
    "phone": "13800138000",
    "qq": "123456789",
    "introduction": "热爱科幻文化",
    "applyTime": "2024-01-15T08:00:00",
    "status": "PENDING"
  }
}
```

**说明**：不返回密码字段（安全考虑）

---

#### 6.3 同意申请

**接口**：`POST /api/applications/:id/approve`  
**权限**：ADMIN  
**说明**：同意入社申请，自动创建用户账号并删除申请记录

**路径参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | Long | 是 | 申请ID |

**请求示例**：

```http
POST /api/applications/1/approve
Authorization: Bearer {token}
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": null
}
```

**业务逻辑**：
1. 检查申请是否存在且状态为PENDING
2. 创建tb_user记录（使用申请中的加密密码）
3. 删除tb_application记录

---

#### 6.4 拒绝申请

**接口**：`POST /api/applications/:id/reject`  
**权限**：ADMIN  
**说明**：拒绝入社申请并删除申请记录

**路径参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| id | Long | 是 | 申请ID |

**请求示例**：

```http
POST /api/applications/1/reject
Authorization: Bearer {token}
```

**响应示例**：

```json
{
  "code": 0,
  "msg": "success",
  "data": null
}
```

**业务逻辑**：
1. 检查申请是否存在且状态为PENDING
2. 直接删除tb_application记录

---

## 🔒 权限说明

### 公开接口（7个）

无需认证即可访问：

```
GET  /api/cover/home          # 获取主页封面
GET  /api/cover/intro         # 获取介绍封面
POST /api/auth/login          # 用户登录
POST /api/auth/apply          # 申请加入
GET  /api/videos              # 查看视频列表
GET  /api/photos              # 查看图片列表
GET  /api/events              # 查看大事记列表
```

### 需要登录（所有USER和ADMIN）

```
GET  /api/videos              # 查看视频
GET  /api/photos              # 查看图片
GET  /api/events              # 查看大事记
```

### 仅管理员（ADMIN）

```
PUT  /api/videos                    # 上传视频
PUT  /api/photos                    # 上传图片
PUT  /api/cover/home                # 更新主页封面
PUT  /api/cover/intro               # 更新介绍封面
POST /api/events                    # 发布大事记
GET  /api/applications              # 查看申请列表
GET  /api/applications/:id          # 查看申请详情
POST /api/applications/:id/approve  # 同意申请
POST /api/applications/:id/reject   # 拒绝申请
```

---

## 📝 请求示例

### 使用curl

#### 登录

```bash
curl -X POST http://localhost:10104/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": "admin",
    "password": "12345678"
  }'
```

#### 获取视频列表（需要Token）

```bash
curl -X GET "http://localhost:10104/api/videos?page=1&size=10" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

#### 上传视频（需要Token）

```bash
curl -X PUT http://localhost:10104/api/videos \
  -H "Authorization: Bearer {token}" \
  -F "file=@/path/to/video.mp4" \
  -F "videoName=社团宣传片"
```

---

### 使用JavaScript (Axios)

#### 登录

```javascript
import axios from 'axios';

const login = async () => {
  const response = await axios.post('/api/auth/login', {
    studentId: 'admin',
    password: '12345678'
  });
  
  const token = response.data.data.token;
  localStorage.setItem('token', token);
};
```

#### 获取视频列表

```javascript
const getVideos = async (page = 1, size = 10) => {
  const response = await axios.get('/api/videos', {
    params: { page, size },
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
  });
  
  return response.data.data;
};
```

#### 上传视频

```javascript
const uploadVideo = async (file, videoName) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('videoName', videoName);
  
  const response = await axios.put('/api/videos', formData, {
    headers: {
      'Authorization': `Bearer ${localStorage.getItem('token')}`,
      'Content-Type': 'multipart/form-data'
    }
  });
  
  return response.data;
};
```

---

## ⚠️ 错误码说明

| 错误码 | 说明 |
|--------|------|
| 0 | 成功 |
| 1 | 业务错误（msg中包含详细信息） |
| 401 | 未登录或Token无效 |
| 403 | 权限不足 |
| 500 | 服务器内部错误 |

---

## 📌 注意事项

### 1. 文件上传限制

- 视频文件：最大500MB
- 图片文件：最大10MB
- 支持的图片格式：jpg, jpeg, png, gif
- 支持的视频格式：mp4, avi, mov

### 2. Token管理

- Token有效期：24小时
- Token过期后需要重新登录
- 建议在前端实现自动续期机制

### 3. CORS配置

后端已配置CORS，允许所有来源访问：

```java
config.addAllowedOrigin("*");
config.addAllowedHeader("*");
config.addAllowedMethod("*");
```

### 4. 字符编码

- 请求：UTF-8
- 响应：UTF-8
- 数据库：utf8mb4

确保客户端使用UTF-8编码发送和接收数据。

### 5. 分页说明

- 页码从1开始
- 默认每页10条（大事记）或20条（图片）
- 最大每页100条

---

## 🔍 接口测试

### 测试工具

推荐使用以下工具测试API：

1. **Postman** - 图形化界面
2. **curl** - 命令行工具
3. **浏览器DevTools** - F12 Network面板

### 测试顺序

1. 测试公开接口（不需要Token）
2. 调用登录接口获取Token
3. 测试需要认证的接口
4. 测试管理员接口

---

**API版本**：v1.2.1  
**文档更新**：2025-10-08  
**接口数量**：16个


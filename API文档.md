# API规范
遵守restful api规范
## 请求值
所有查询请求使用"GET"，需要上传文件时使用"PUT"，其余的使用"POST"
## 响应值示例
```json
{
    "code":0,
    "msg":"success",
    "data":{
        "name":"miku"
    }
}
```
code为0或1，默认为0。当code为1时，前端需要显示msg里的信息

---

# 接口列表

## 1. 首页相关

### 1.1 获取主页封面
- **接口**: `GET /api/cover/home`
- **权限**: 无需登录
- **请求参数**: 无
- **响应示例**:
```json
{
    "code": 0,
    "msg": "success",
    "data": {
        "coverUrl": "https://minio.example.com/photo-bucket/home-cover.jpg"
    }
}
```

### 1.2 获取社团介绍封面
- **接口**: `GET /api/cover/intro`
- **权限**: 无需登录
- **请求参数**: 无
- **响应示例**:
```json
{
    "code": 0,
    "msg": "success",
    "data": {
        "coverUrl": "https://minio.example.com/photo-bucket/intro-cover.jpg",
        "introduction": "中国地质大学科幻界成立于..."
    }
}
```

## 2. 用户认证相关

### 2.1 用户登录
- **接口**: `POST /api/auth/login`
- **权限**: 无需登录
- **请求参数**:
```json
{
    "studentId": "2021001",
    "password": "123456"
}
```
- **响应示例**:
```json
{
    "code": 0,
    "msg": "success",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "studentId": "2021001",
        "realName": "张三",
        "role": "USER"
    }
}
```
- **错误响应**:
```json
{
    "code": 1,
    "msg": "用户不存在",
    "data": null
}
```
或
```json
{
    "code": 1,
    "msg": "密码错误",
    "data": null
}
```

### 2.2 申请加入
- **接口**: `POST /api/auth/apply`
- **权限**: 无需登录
- **请求参数**:
```json
{
    "studentId": "2021001",
    "college": "计算机学院",
    "realName": "张三",
    "dormitory": "学1-101",
    "phone": "13800138000",
    "qq": "123456789",
    "introduction": "我热爱科幻..."
}
```
- **响应示例**:
```json
{
    "code": 0,
    "msg": "申请成功",
    "data": null
}
```
- **错误响应**:
```json
{
    "code": 1,
    "msg": "您已经是社团的一员了！",
    "data": null
}
```
或
```json
{
    "code": 1,
    "msg": "待审批",
    "data": null
}
```

## 3. 视频管理

### 3.1 查看所有视频
- **接口**: `GET /api/videos`
- **权限**: 无需登录
- **请求参数**: 
  - `page`: 页码（可选，默认1）
  - `size`: 每页数量（可选，默认10）
- **响应示例**:
```json
{
    "code": 0,
    "msg": "success",
    "data": {
        "total": 50,
        "list": [
            {
                "id": 1,
                "videoName": "2024年社团年会",
                "videoUrl": "https://minio.example.com/video-bucket/xxx.mp4",
                "uploader": "张三",
                "uploadTime": "2024-10-01 14:30:00"
            }
        ]
    }
}
```

### 3.2 上传视频
- **接口**: `PUT /api/videos`
- **权限**: 需要 ADMIN 角色
- **请求参数**: FormData
  - `file`: 视频文件
  - `videoName`: 视频名称
- **响应示例**:
```json
{
    "code": 0,
    "msg": "上传成功",
    "data": {
        "id": 1,
        "videoName": "2024年社团年会",
        "videoUrl": "https://minio.example.com/video-bucket/xxx.mp4"
    }
}
```

## 4. 图片管理

### 4.1 查看所有图片
- **接口**: `GET /api/photos`
- **权限**: 无需登录
- **请求参数**: 
  - `page`: 页码（可选，默认1）
  - `size`: 每页数量（可选，默认20）
- **响应示例**:
```json
{
    "code": 0,
    "msg": "success",
    "data": {
        "total": 100,
        "list": [
            {
                "id": 1,
                "photoName": "社团合影",
                "photoUrl": "https://minio.example.com/photo-bucket/xxx.jpg",
                "uploader": "李四",
                "uploadTime": "2024-09-20 10:00:00"
            }
        ]
    }
}
```

### 4.2 上传图片
- **接口**: `PUT /api/photos`
- **权限**: 需要 ADMIN 角色
- **请求参数**: FormData
  - `file`: 图片文件
  - `photoName`: 图片名称
- **响应示例**:
```json
{
    "code": 0,
    "msg": "上传成功",
    "data": {
        "id": 1,
        "photoName": "社团合影",
        "photoUrl": "https://minio.example.com/photo-bucket/xxx.jpg"
    }
}
```

## 5. 大事记管理

### 5.1 查看所有大事记
- **接口**: `GET /api/events`
- **权限**: 无需登录
- **请求参数**: 
  - `page`: 页码（可选，默认1）
  - `size`: 每页数量（可选，默认10）
- **响应示例**:
```json
{
    "code": 0,
    "msg": "success",
    "data": {
        "total": 30,
        "list": [
            {
                "id": 1,
                "title": "社团成立大会",
                "content": "2020年10月1日，中国地质大学科幻界正式成立...",
                "publisher": "王五",
                "createTime": "2024-09-15 16:00:00"
            }
        ]
    }
}
```

### 5.2 发布大事记
- **接口**: `POST /api/events`
- **权限**: 需要 ADMIN 角色
- **请求参数**:
```json
{
    "title": "社团成立大会",
    "content": "2020年10月1日，中国地质大学科幻界正式成立..."
}
```
- **响应示例**:
```json
{
    "code": 0,
    "msg": "发布成功",
    "data": {
        "id": 1,
        "title": "社团成立大会",
        "createTime": "2024-09-15 16:00:00"
    }
}
```

## 6. 申请审批管理

### 6.1 查看所有申请
- **接口**: `GET /api/applications`
- **权限**: 需要 ADMIN 角色
- **请求参数**: 
  - `status`: 申请状态（可选，PENDING/APPROVED/REJECTED）
  - `page`: 页码（可选，默认1）
  - `size`: 每页数量（可选，默认10）
- **响应示例**:
```json
{
    "code": 0,
    "msg": "success",
    "data": {
        "total": 15,
        "list": [
            {
                "id": 1,
                "studentId": "2021001",
                "applyTime": "2024-09-10 09:00:00",
                "status": "PENDING"
            }
        ]
    }
}
```

### 6.2 查看申请详情
- **接口**: `GET /api/applications/{id}`
- **权限**: 需要 ADMIN 角色
- **请求参数**: 路径参数 id
- **响应示例**:
```json
{
    "code": 0,
    "msg": "success",
    "data": {
        "id": 1,
        "studentId": "2021001",
        "college": "计算机学院",
        "realName": "张三",
        "dormitory": "学1-101",
        "phone": "13800138000",
        "qq": "123456789",
        "introduction": "我热爱科幻...",
        "applyTime": "2024-09-10 09:00:00",
        "status": "PENDING"
    }
}
```

### 6.3 同意申请
- **接口**: `POST /api/applications/{id}/approve`
- **权限**: 需要 ADMIN 角色
- **请求参数**: 路径参数 id
- **响应示例**:
```json
{
    "code": 0,
    "msg": "已同意申请",
    "data": null
}
```

### 6.4 拒绝申请
- **接口**: `POST /api/applications/{id}/reject`
- **权限**: 需要 ADMIN 角色
- **请求参数**: 路径参数 id
- **响应示例**:
```json
{
    "code": 0,
    "msg": "已拒绝申请",
    "data": null
}
```

---

# 认证说明

## JWT Token
- 登录成功后，后端返回 JWT Token
- 前端需要在后续请求的 Header 中携带 Token
- Header 格式: `Authorization: Bearer {token}`

## 权限控制
- **公开接口**：无需 Token
- **需要登录**：需要有效 Token
- **ADMIN 专属**：需要有效 Token 且角色为 ADMIN
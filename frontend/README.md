# 科幻界社团管理系统 - 前端

## 技术栈

- Vue 3.3
- Vue Router 4.2
- Pinia 2.1
- Element Plus 2.4
- Axios 1.6
- Vite 4.5

## 快速开始

### 安装依赖
```bash
cd frontend
npm install
```

### 开发模式
```bash
npm run dev
```
访问: http://localhost:3000

### 生产构建
```bash
npm run build
```

## 项目结构

```
frontend/
├── public/              # 静态资源
├── src/
│   ├── api/            # API接口
│   ├── assets/         # 资源文件
│   ├── components/     # 公共组件
│   ├── router/         # 路由配置
│   ├── stores/         # 状态管理
│   ├── utils/          # 工具函数
│   ├── views/          # 页面组件
│   ├── App.vue         # 根组件
│   └── main.js         # 入口文件
├── index.html          # HTML模板
├── package.json        # 依赖配置
└── vite.config.js      # Vite配置
```

## 功能页面

### 公开页面
- 首页 - 展示封面和社团介绍
- 登录 - 用户登录
- 申请加入 - 填写申请表
- 视频列表 - 浏览视频
- 图片列表 - 浏览图片
- 大事记 - 查看大事记

### 管理后台（需要ADMIN权限）
- 上传视频
- 上传图片
- 发布大事记
- 申请审批

## 环境变量

后端API地址在 `vite.config.js` 中配置代理：
```javascript
proxy: {
  '/api': {
    target: 'http://localhost:10104',
    changeOrigin: true
  }
}
```

## 开发说明

1. 使用 Element Plus 作为UI组件库
2. 使用 Pinia 进行状态管理
3. 使用 Axios 进行HTTP请求
4. JWT Token存储在 localStorage
5. 路由守卫自动验证登录状态


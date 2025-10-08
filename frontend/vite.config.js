import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  server: {
    port: 10100,  // 前端开发端口改为10100
    proxy: {
      '/api': {
        target: 'http://123.60.40.72:10104',  // API请求代理到后端
        changeOrigin: true
      }
    }
  }
})


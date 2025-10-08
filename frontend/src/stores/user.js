import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi } from '@/api'
import { ElMessage } from 'element-plus'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || '{}'))

  // 是否已登录
  const isLoggedIn = () => {
    return !!token.value
  }

  // 是否是管理员
  const isAdmin = () => {
    return userInfo.value.role === 'ADMIN'
  }

  // 登录
  const login = async (loginForm) => {
    try {
      const res = await loginApi(loginForm)
      
      if (res.code === 0) {
        token.value = res.data.token
        userInfo.value = {
          studentId: res.data.studentId,
          realName: res.data.realName,
          role: res.data.role
        }
        
        // 保存到localStorage
        localStorage.setItem('token', res.data.token)
        localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
        
        ElMessage.success('登录成功')
        return true
      }
      return false
    } catch (error) {
      console.error('登录失败:', error)
      return false
    }
  }

  // 登出
  const logout = () => {
    token.value = ''
    userInfo.value = {}
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
    ElMessage.success('已退出登录')
  }

  return {
    token,
    userInfo,
    isLoggedIn,
    isAdmin,
    login,
    logout
  }
})


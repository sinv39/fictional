<template>
  <el-container class="admin-layout">
    <el-aside width="200px" class="admin-aside">
      <div class="admin-logo">
        <el-icon><Setting /></el-icon>
        管理后台
      </div>
      <el-menu
        :default-active="activeMenu"
        @select="handleMenuSelect"
        background-color="#001529"
        text-color="#fff"
        active-text-color="#409eff"
      >
        <el-menu-item index="/admin/videos">
          <el-icon><VideoPlay /></el-icon>
          <span>视频管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/photos">
          <el-icon><PictureFilled /></el-icon>
          <span>图片管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/events">
          <el-icon><Calendar /></el-icon>
          <span>大事记管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/applications">
          <el-icon><Document /></el-icon>
          <span>申请审批</span>
        </el-menu-item>
        <el-menu-item index="/admin/covers">
          <el-icon><Picture /></el-icon>
          <span>封面管理</span>
        </el-menu-item>
      </el-menu>
      
      <div class="admin-footer">
        <el-button type="text" @click="$router.push('/')" style="color: #fff">
          <el-icon><Back /></el-icon>
          返回前台
        </el-button>
      </div>
    </el-aside>
    
    <el-container>
      <el-header class="admin-header">
        <div class="header-right">
          <span>{{ userStore.userInfo.realName }}</span>
          <el-button type="text" @click="handleLogout">
            <el-icon><SwitchButton /></el-icon>
            退出
          </el-button>
        </div>
      </el-header>
      <el-main class="admin-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)

const handleMenuSelect = (index) => {
  router.push(index)
}

const handleLogout = () => {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
}

.admin-aside {
  background: #001529;
  color: #fff;
  display: flex;
  flex-direction: column;
}

.admin-logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  font-weight: bold;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  gap: 10px;
}

.admin-footer {
  margin-top: auto;
  padding: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.admin-header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding: 0 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.admin-main {
  background: #f0f2f5;
  padding: 20px;
}
</style>


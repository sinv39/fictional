import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { ElMessage } from 'element-plus'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/views/Layout.vue'),
    redirect: '/home',
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'videos',
        name: 'Videos',
        component: () => import('@/views/Videos.vue'),
        meta: { title: '视频列表', requiresAuth: true }
      },
      {
        path: 'photos',
        name: 'Photos',
        component: () => import('@/views/Photos.vue'),
        meta: { title: '图片列表', requiresAuth: true }
      },
      {
        path: 'events',
        name: 'Events',
        component: () => import('@/views/Events.vue'),
        meta: { title: '大事记', requiresAuth: true }
      },
      {
        path: 'apply',
        name: 'Apply',
        component: () => import('@/views/Apply.vue'),
        meta: { title: '申请加入' }
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/views/admin/AdminLayout.vue'),
    redirect: '/admin/videos',
    meta: { requiresAuth: true, requiresAdmin: true },
    children: [
      {
        path: 'videos',
        name: 'AdminVideos',
        component: () => import('@/views/admin/AdminVideos.vue'),
        meta: { title: '视频管理', requiresAuth: true, requiresAdmin: true }
      },
      {
        path: 'photos',
        name: 'AdminPhotos',
        component: () => import('@/views/admin/AdminPhotos.vue'),
        meta: { title: '图片管理', requiresAuth: true, requiresAdmin: true }
      },
      {
        path: 'events',
        name: 'AdminEvents',
        component: () => import('@/views/admin/AdminEvents.vue'),
        meta: { title: '大事记管理', requiresAuth: true, requiresAdmin: true }
      },
      {
        path: 'applications',
        name: 'AdminApplications',
        component: () => import('@/views/admin/AdminApplications.vue'),
        meta: { title: '申请审批', requiresAuth: true, requiresAdmin: true }
      },
      {
        path: 'covers',
        name: 'AdminCovers',
        component: () => import('@/views/admin/AdminCovers.vue'),
        meta: { title: '封面管理', requiresAuth: true, requiresAdmin: true }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? `${to.meta.title} - 科幻界` : '科幻界'
  
  const userStore = useUserStore()
  
  // 需要登录
  if (to.meta.requiresAuth) {
    if (!userStore.isLoggedIn()) {
      ElMessage.warning('请先登录')
      next('/home')  // 跳转到首页而不是登录页
      return
    }
  }
  
  // 需要管理员权限
  if (to.meta.requiresAdmin) {
    if (!userStore.isAdmin()) {
      ElMessage.error('需要管理员权限')
      next('/home')  // 跳转到首页
      return
    }
  }
  
  next()
})

export default router


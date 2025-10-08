<template>
  <div class="home">
    <!-- 主页封面 -->
    <div class="cover-section" v-if="homeCover">
      <img :src="homeCover" alt="主页封面" class="cover-image">
      <div class="cover-overlay">
        <h1 class="cover-title">中国地质大学科幻界</h1>
        <p class="cover-subtitle">探索科幻世界，启迪未来思维</p>
      </div>
    </div>

    <!-- 社团介绍 -->
    <div class="intro-section" v-if="introCover">
      <el-card class="intro-card">
        <template #header>
          <h2 class="section-title">
            <el-icon><InfoFilled /></el-icon>
            社团介绍
          </h2>
        </template>
        <div class="intro-content">
          <img :src="introCover" alt="介绍封面" class="intro-image">
          <p class="intro-text">{{ introduction }}</p>
        </div>
      </el-card>
    </div>

    <!-- 快速入口 -->
    <div class="quick-links">
      <h2 class="section-title">快速入口</h2>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="link-card" @click="$router.push('/videos')">
            <div class="link-content">
              <el-icon class="link-icon" :size="40"><VideoPlay /></el-icon>
              <h3>视频中心</h3>
              <p>观看社团活动视频</p>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="link-card" @click="$router.push('/photos')">
            <div class="link-content">
              <el-icon class="link-icon" :size="40"><PictureFilled /></el-icon>
              <h3>相册</h3>
              <p>浏览精彩照片</p>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="link-card" @click="$router.push('/events')">
            <div class="link-content">
              <el-icon class="link-icon" :size="40"><Calendar /></el-icon>
              <h3>大事记</h3>
              <p>了解社团历程</p>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="12" :md="6">
          <el-card shadow="hover" class="link-card" @click="$router.push('/apply')">
            <div class="link-content">
              <el-icon class="link-icon" :size="40"><EditPen /></el-icon>
              <h3>申请加入</h3>
              <p>成为社团一员</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getHomeCover, getIntroCover } from '@/api'

const homeCover = ref('')
const introCover = ref('')
const introduction = ref('')

onMounted(async () => {
  try {
    const homeRes = await getHomeCover()
    homeCover.value = homeRes.data.coverUrl

    const introRes = await getIntroCover()
    introCover.value = introRes.data.coverUrl
    introduction.value = introRes.data.introduction
  } catch (error) {
    console.error('加载封面失败:', error)
  }
})
</script>

<style scoped>
.home {
  padding: 0;
}

.cover-section {
  position: relative;
  height: 500px;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 30px;
}

.cover-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.cover-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
}

.cover-title {
  font-size: 48px;
  font-weight: bold;
  margin-bottom: 15px;
}

.cover-subtitle {
  font-size: 24px;
}

.intro-section {
  margin-bottom: 30px;
}

.intro-content {
  display: flex;
  gap: 30px;
  align-items: flex-start;
}

.intro-image {
  width: 300px;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
  flex-shrink: 0;
}

.intro-text {
  flex: 1;
  line-height: 1.8;
  font-size: 16px;
  color: #666;
}

.quick-links {
  margin-bottom: 30px;
}

.section-title {
  font-size: 24px;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.link-card {
  cursor: pointer;
  transition: all 0.3s;
  margin-bottom: 20px;
}

.link-card:hover {
  transform: translateY(-5px);
}

.link-content {
  text-align: center;
  padding: 20px 0;
}

.link-icon {
  color: #409eff;
  margin-bottom: 15px;
}

.link-content h3 {
  font-size: 20px;
  margin-bottom: 10px;
}

.link-content p {
  color: #999;
  font-size: 14px;
}

@media (max-width: 768px) {
  .cover-title {
    font-size: 32px;
  }
  
  .cover-subtitle {
    font-size: 18px;
  }
  
  .intro-content {
    flex-direction: column;
  }
  
  .intro-image {
    width: 100%;
  }
}
</style>


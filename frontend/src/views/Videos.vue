<template>
  <div class="videos-page">
    <el-card>
      <template #header>
        <h2 class="page-title">
          <el-icon><VideoPlay /></el-icon>
          视频列表
        </h2>
      </template>
      
      <el-empty v-if="!loading && videoList.length === 0" description="暂无视频" />
      
      <el-row :gutter="20" v-loading="loading">
        <el-col
          v-for="video in videoList"
          :key="video.id"
          :xs="24"
          :sm="12"
          :md="8"
          :lg="6"
        >
          <el-card shadow="hover" class="video-card">
            <div class="video-thumbnail" @click="playVideo(video)">
              <el-icon class="play-icon" :size="50"><VideoPlay /></el-icon>
            </div>
            <div class="video-info">
              <h3 class="video-title">{{ video.videoName }}</h3>
              <p class="video-meta">
                <el-icon><User /></el-icon>
                {{ video.uploader }}
              </p>
              <p class="video-meta">
                <el-icon><Clock /></el-icon>
                {{ formatDate(video.uploadTime) }}
              </p>
            </div>
          </el-card>
        </el-col>
      </el-row>
      
      <el-pagination
        v-if="total > pageSize"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadVideos"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
    
    <!-- 视频播放对话框 -->
    <el-dialog v-model="showPlayer" :title="currentVideo?.videoName" width="80%">
      <video
        v-if="currentVideo"
        :src="currentVideo.videoUrl"
        controls
        style="width: 100%"
      />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getVideoList } from '@/api'

const loading = ref(false)
const videoList = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(12)
const showPlayer = ref(false)
const currentVideo = ref(null)

const loadVideos = async () => {
  try {
    loading.value = true
    const res = await getVideoList({
      page: currentPage.value,
      size: pageSize.value
    })
    videoList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('加载视频失败:', error)
  } finally {
    loading.value = false
  }
}

const playVideo = (video) => {
  currentVideo.value = video
  showPlayer.value = true
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.split(' ')[0]
}

onMounted(() => {
  loadVideos()
})
</script>

<style scoped>
.page-title {
  font-size: 24px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.video-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.video-card:hover {
  transform: translateY(-5px);
}

.video-thumbnail {
  width: 100%;
  height: 150px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 4px;
  margin-bottom: 15px;
}

.play-icon {
  color: white;
  cursor: pointer;
}

.video-info {
  padding: 0 5px;
}

.video-title {
  font-size: 16px;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.video-meta {
  font-size: 14px;
  color: #999;
  display: flex;
  align-items: center;
  gap: 5px;
  margin-bottom: 5px;
}
</style>


<template>
  <div class="photos-page">
    <el-card>
      <template #header>
        <h2 class="page-title">
          <el-icon><PictureFilled /></el-icon>
          图片列表
        </h2>
      </template>
      
      <el-empty v-if="!loading && photoList.length === 0" description="暂无图片" />
      
      <el-row :gutter="20" v-loading="loading">
        <el-col
          v-for="photo in photoList"
          :key="photo.id"
          :xs="12"
          :sm="8"
          :md="6"
          :lg="4"
        >
          <el-card shadow="hover" class="photo-card" @click="viewPhoto(photo)">
            <img :src="photo.photoUrl" :alt="photo.photoName" class="photo-img">
            <div class="photo-info">
              <p class="photo-title">{{ photo.photoName }}</p>
              <p class="photo-meta">{{ formatDate(photo.uploadTime) }}</p>
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
        @current-change="loadPhotos"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
    
    <!-- 图片查看器 -->
    <el-image-viewer
      v-if="showViewer"
      :url-list="imageUrls"
      :initial-index="currentIndex"
      @close="showViewer = false"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { getPhotoList } from '@/api'

const loading = ref(false)
const photoList = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(20)
const showViewer = ref(false)
const currentIndex = ref(0)

const imageUrls = computed(() => {
  return photoList.value.map(p => p.photoUrl)
})

const loadPhotos = async () => {
  try {
    loading.value = true
    const res = await getPhotoList({
      page: currentPage.value,
      size: pageSize.value
    })
    photoList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('加载图片失败:', error)
  } finally {
    loading.value = false
  }
}

const viewPhoto = (photo) => {
  currentIndex.value = photoList.value.findIndex(p => p.id === photo.id)
  showViewer.value = true
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.split(' ')[0]
}

onMounted(() => {
  loadPhotos()
})
</script>

<style scoped>
.page-title {
  font-size: 24px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.photo-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.photo-card:hover {
  transform: translateY(-5px);
}

.photo-img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 4px;
}

.photo-info {
  margin-top: 10px;
}

.photo-title {
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 5px;
}

.photo-meta {
  font-size: 12px;
  color: #999;
}
</style>


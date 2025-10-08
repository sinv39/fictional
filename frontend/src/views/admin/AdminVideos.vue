<template>
  <div class="admin-videos">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>视频管理</h3>
          <el-button type="primary" @click="showUpload = true">
            <el-icon><Plus /></el-icon>
            上传视频
          </el-button>
        </div>
      </template>
      
      <el-table :data="videoList" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="videoName" label="视频名称" />
        <el-table-column prop="uploaderName" label="上传者" width="120" />
        <el-table-column prop="uploadTime" label="上传时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="previewVideo(row)">
              预览
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-if="total > pageSize"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadVideos"
        style="margin-top: 20px"
      />
    </el-card>
    
    <!-- 上传对话框 -->
    <el-dialog v-model="showUpload" title="上传视频" width="600px">
      <el-form :model="uploadForm" label-width="100px">
        <el-form-item label="视频名称">
          <el-input v-model="uploadForm.videoName" placeholder="请输入视频名称" />
        </el-form-item>
        <el-form-item label="选择视频">
          <el-upload
            ref="uploadRef"
            :auto-upload="false"
            :limit="1"
            accept="video/*"
            :on-change="handleFileChange"
          >
            <el-button type="primary">选择文件</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showUpload = false">取消</el-button>
        <el-button type="primary" :loading="uploading" @click="handleUpload">
          上传
        </el-button>
      </template>
    </el-dialog>
    
    <!-- 预览对话框 -->
    <el-dialog v-model="showPreview" :title="currentVideo?.videoName" width="80%">
      <video v-if="currentVideo" :src="currentVideo.videoUrl" controls style="width: 100%" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getVideoList, uploadVideo } from '@/api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const uploading = ref(false)
const videoList = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const showUpload = ref(false)
const showPreview = ref(false)
const currentVideo = ref(null)
const uploadRef = ref()

const uploadForm = ref({
  videoName: '',
  file: null
})

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

const handleFileChange = (file) => {
  uploadForm.value.file = file.raw
}

const handleUpload = async () => {
  if (!uploadForm.value.videoName) {
    ElMessage.warning('请输入视频名称')
    return
  }
  
  if (!uploadForm.value.file) {
    ElMessage.warning('请选择视频文件')
    return
  }
  
  try {
    uploading.value = true
    const formData = new FormData()
    formData.append('file', uploadForm.value.file)
    formData.append('videoName', uploadForm.value.videoName)
    
    await uploadVideo(formData)
    
    ElMessage.success('上传成功')
    showUpload.value = false
    uploadForm.value = { videoName: '', file: null }
    uploadRef.value.clearFiles()
    loadVideos()
  } catch (error) {
    console.error('上传失败:', error)
  } finally {
    uploading.value = false
  }
}

const previewVideo = (video) => {
  currentVideo.value = video
  showPreview.value = true
}

onMounted(() => {
  loadVideos()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>


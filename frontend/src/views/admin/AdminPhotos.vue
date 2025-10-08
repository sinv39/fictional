<template>
  <div class="admin-photos">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>图片管理</h3>
          <el-button type="primary" @click="showUpload = true">
            <el-icon><Plus /></el-icon>
            上传图片
          </el-button>
        </div>
      </template>
      
      <el-table :data="photoList" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column label="缩略图" width="100">
          <template #default="{ row }">
            <el-image
              :src="row.photoUrl"
              fit="cover"
              style="width: 60px; height: 60px"
              preview-teleported
              :preview-src-list="[row.photoUrl]"
            />
          </template>
        </el-table-column>
        <el-table-column prop="photoName" label="图片名称" />
        <el-table-column prop="uploaderName" label="上传者" width="120" />
        <el-table-column prop="uploadTime" label="上传时间" width="180" />
      </el-table>
      
      <el-pagination
        v-if="total > pageSize"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadPhotos"
        style="margin-top: 20px"
      />
    </el-card>
    
    <!-- 上传对话框 -->
    <el-dialog v-model="showUpload" title="上传图片" width="600px">
      <el-form :model="uploadForm" label-width="100px">
        <el-form-item label="图片名称">
          <el-input v-model="uploadForm.photoName" placeholder="请输入图片名称" />
        </el-form-item>
        <el-form-item label="选择图片">
          <el-upload
            ref="uploadRef"
            :auto-upload="false"
            :limit="1"
            accept="image/*"
            :on-change="handleFileChange"
            list-type="picture-card"
          >
            <el-icon><Plus /></el-icon>
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getPhotoList, uploadPhoto } from '@/api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const uploading = ref(false)
const photoList = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const showUpload = ref(false)
const uploadRef = ref()

const uploadForm = ref({
  photoName: '',
  file: null
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

const handleFileChange = (file) => {
  uploadForm.value.file = file.raw
}

const handleUpload = async () => {
  if (!uploadForm.value.photoName) {
    ElMessage.warning('请输入图片名称')
    return
  }
  
  if (!uploadForm.value.file) {
    ElMessage.warning('请选择图片文件')
    return
  }
  
  try {
    uploading.value = true
    const formData = new FormData()
    formData.append('file', uploadForm.value.file)
    formData.append('photoName', uploadForm.value.photoName)
    
    await uploadPhoto(formData)
    
    ElMessage.success('上传成功')
    showUpload.value = false
    uploadForm.value = { photoName: '', file: null }
    uploadRef.value.clearFiles()
    loadPhotos()
  } catch (error) {
    console.error('上传失败:', error)
  } finally {
    uploading.value = false
  }
}

onMounted(() => {
  loadPhotos()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>


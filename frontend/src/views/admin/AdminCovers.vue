<template>
  <div class="admin-covers">
    <!-- 主页封面 -->
    <el-card style="margin-bottom: 20px;">
      <template #header>
        <h3>主页封面</h3>
      </template>
      
      <div class="cover-section">
        <div class="current-cover">
          <h4>当前封面</h4>
          <el-image
            v-if="homeCoverUrl"
            :src="homeCoverUrl"
            fit="cover"
            style="width: 100%; height: 300px; border-radius: 8px;"
          />
          <el-empty v-else description="暂无封面" />
        </div>
        
        <div class="upload-section">
          <h4>上传新封面</h4>
          <el-upload
            ref="homeUploadRef"
            :auto-upload="false"
            :limit="1"
            accept="image/*"
            :on-change="handleHomeFileChange"
            list-type="picture-card"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
          <el-button
            type="primary"
            :loading="homeUploading"
            @click="handleUpdateHomeCover"
            style="margin-top: 10px;"
          >
            更新主页封面
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 介绍页封面 -->
    <el-card>
      <template #header>
        <h3>介绍页封面</h3>
      </template>
      
      <div class="cover-section">
        <div class="current-cover">
          <h4>当前封面</h4>
          <el-image
            v-if="introCoverUrl"
            :src="introCoverUrl"
            fit="cover"
            style="width: 100%; height: 300px; border-radius: 8px;"
          />
          <el-empty v-else description="暂无封面" />
          
          <div style="margin-top: 20px;">
            <h4>当前介绍文字</h4>
            <p style="line-height: 1.8; color: #606266;">{{ currentIntroduction }}</p>
          </div>
        </div>
        
        <div class="upload-section">
          <h4>上传新封面（可选）</h4>
          <el-upload
            ref="introUploadRef"
            :auto-upload="false"
            :limit="1"
            accept="image/*"
            :on-change="handleIntroFileChange"
            list-type="picture-card"
          >
            <el-icon><Plus /></el-icon>
          </el-upload>
          
          <h4 style="margin-top: 20px;">更新介绍文字（可选）</h4>
          <el-input
            v-model="introductionText"
            type="textarea"
            :rows="6"
            placeholder="请输入社团介绍文字"
          />
          
          <el-button
            type="primary"
            :loading="introUploading"
            @click="handleUpdateIntroCover"
            style="margin-top: 10px;"
          >
            更新介绍页
          </el-button>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getHomeCover, getIntroCover, updateHomeCover, updateIntroCover } from '@/api'
import { ElMessage } from 'element-plus'

const homeCoverUrl = ref('')
const introCoverUrl = ref('')
const currentIntroduction = ref('')
const introductionText = ref('')

const homeUploadRef = ref()
const introUploadRef = ref()
const homeFile = ref(null)
const introFile = ref(null)
const homeUploading = ref(false)
const introUploading = ref(false)

const loadCovers = async () => {
  try {
    const homeRes = await getHomeCover()
    homeCoverUrl.value = homeRes.data.coverUrl

    const introRes = await getIntroCover()
    introCoverUrl.value = introRes.data.coverUrl
    currentIntroduction.value = introRes.data.introduction
    introductionText.value = introRes.data.introduction
  } catch (error) {
    console.error('加载封面失败:', error)
  }
}

const handleHomeFileChange = (file) => {
  homeFile.value = file.raw
}

const handleIntroFileChange = (file) => {
  introFile.value = file.raw
}

const handleUpdateHomeCover = async () => {
  if (!homeFile.value) {
    ElMessage.warning('请选择图片文件')
    return
  }

  try {
    homeUploading.value = true
    const formData = new FormData()
    formData.append('file', homeFile.value)

    await updateHomeCover(formData)

    ElMessage.success('主页封面更新成功')
    homeFile.value = null
    homeUploadRef.value.clearFiles()
    loadCovers()
  } catch (error) {
    console.error('更新失败:', error)
  } finally {
    homeUploading.value = false
  }
}

const handleUpdateIntroCover = async () => {
  if (!introFile.value && !introductionText.value) {
    ElMessage.warning('请至少选择图片或填写介绍文字')
    return
  }

  try {
    introUploading.value = true
    const formData = new FormData()
    
    if (introFile.value) {
      formData.append('file', introFile.value)
    }
    
    if (introductionText.value) {
      formData.append('introduction', introductionText.value)
    }

    await updateIntroCover(formData)

    ElMessage.success('介绍页更新成功')
    introFile.value = null
    if (introUploadRef.value) {
      introUploadRef.value.clearFiles()
    }
    loadCovers()
  } catch (error) {
    console.error('更新失败:', error)
  } finally {
    introUploading.value = false
  }
}

onMounted(() => {
  loadCovers()
})
</script>

<style scoped>
.cover-section {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
}

.current-cover h4,
.upload-section h4 {
  color: #606266;
  margin-bottom: 15px;
  font-size: 16px;
}

@media (max-width: 768px) {
  .cover-section {
    grid-template-columns: 1fr;
  }
}
</style>


<template>
  <div class="admin-events">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>大事记管理</h3>
          <el-button type="primary" @click="showDialog = true">
            <el-icon><Plus /></el-icon>
            发布大事记
          </el-button>
        </div>
      </template>
      
      <el-table :data="eventList" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="publisherName" label="发布者" width="120" />
        <el-table-column prop="createTime" label="发布时间" width="180" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewDetail(row)">
              查看
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
        @current-change="loadEvents"
        style="margin-top: 20px"
      />
    </el-card>
    
    <!-- 发布对话框 -->
    <el-dialog v-model="showDialog" title="发布大事记" width="800px">
      <el-form :model="eventForm" label-width="100px">
        <el-form-item label="标题">
          <el-input v-model="eventForm.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="eventForm.content"
            type="textarea"
            :rows="8"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" :loading="publishing" @click="handlePublish">
          发布
        </el-button>
      </template>
    </el-dialog>
    
    <!-- 详情对话框 -->
    <el-dialog v-model="showDetail" :title="currentEvent?.title" width="800px">
      <div v-if="currentEvent">
        <p style="line-height: 1.8; white-space: pre-wrap">{{ currentEvent.content }}</p>
        <el-divider />
        <div style="color: #999; font-size: 14px">
          <p>发布者：{{ currentEvent.publisherName }}</p>
          <p>发布时间：{{ currentEvent.createTime }}</p>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getEventList, publishEvent } from '@/api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const publishing = ref(false)
const eventList = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const showDialog = ref(false)
const showDetail = ref(false)
const currentEvent = ref(null)

const eventForm = ref({
  title: '',
  content: ''
})

const loadEvents = async () => {
  try {
    loading.value = true
    const res = await getEventList({
      page: currentPage.value,
      size: pageSize.value
    })
    eventList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('加载大事记失败:', error)
  } finally {
    loading.value = false
  }
}

const handlePublish = async () => {
  if (!eventForm.value.title) {
    ElMessage.warning('请输入标题')
    return
  }
  
  if (!eventForm.value.content) {
    ElMessage.warning('请输入内容')
    return
  }
  
  try {
    publishing.value = true
    await publishEvent(eventForm.value)
    
    ElMessage.success('发布成功')
    showDialog.value = false
    eventForm.value = { title: '', content: '' }
    loadEvents()
  } catch (error) {
    console.error('发布失败:', error)
  } finally {
    publishing.value = false
  }
}

const viewDetail = (event) => {
  currentEvent.value = event
  showDetail.value = true
}

onMounted(() => {
  loadEvents()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>


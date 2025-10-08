<template>
  <div class="events-page">
    <el-card>
      <template #header>
        <h2 class="page-title">
          <el-icon><Calendar /></el-icon>
          大事记
        </h2>
      </template>
      
      <el-empty v-if="!loading && eventList.length === 0" description="暂无大事记" />
      
      <el-timeline v-loading="loading">
        <el-timeline-item
          v-for="event in eventList"
          :key="event.id"
          :timestamp="formatDate(event.createTime)"
          placement="top"
        >
          <el-card shadow="hover" class="event-card">
            <h3 class="event-title">{{ event.title }}</h3>
            <p class="event-content">{{ event.content }}</p>
            <div class="event-footer">
              <span class="event-publisher">
                <el-icon><User /></el-icon>
                {{ event.publisher }}
              </span>
            </div>
          </el-card>
        </el-timeline-item>
      </el-timeline>
      
      <el-pagination
        v-if="total > pageSize"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadEvents"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getEventList } from '@/api'

const loading = ref(false)
const eventList = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

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

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ')
}

onMounted(() => {
  loadEvents()
})
</script>

<style scoped>
.page-title {
  font-size: 24px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.event-card {
  margin-top: 10px;
}

.event-title {
  font-size: 18px;
  margin-bottom: 15px;
  color: #409eff;
}

.event-content {
  line-height: 1.8;
  color: #666;
  margin-bottom: 15px;
}

.event-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 10px;
  border-top: 1px solid #eee;
}

.event-publisher {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 14px;
  color: #999;
}
</style>


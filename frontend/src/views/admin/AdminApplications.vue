<template>
  <div class="admin-applications">
    <el-card>
      <template #header>
        <div class="card-header">
          <h3>申请审批</h3>
          <el-radio-group v-model="statusFilter" @change="loadApplications">
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button label="PENDING">待审批</el-radio-button>
            <el-radio-button label="APPROVED">已通过</el-radio-button>
            <el-radio-button label="REJECTED">已拒绝</el-radio-button>
          </el-radio-group>
        </div>
      </template>
      
      <el-table :data="applicationList" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="applyTime" label="申请时间" width="180" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag v-if="row.status === 'PENDING'" type="warning">待审批</el-tag>
            <el-tag v-else-if="row.status === 'APPROVED'" type="success">已通过</el-tag>
            <el-tag v-else-if="row.status === 'REJECTED'" type="danger">已拒绝</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewDetail(row)">
              查看详情
            </el-button>
            <template v-if="row.status === 'PENDING'">
              <el-button type="success" size="small" @click="handleApprove(row)">
                同意
              </el-button>
              <el-button type="danger" size="small" @click="handleReject(row)">
                拒绝
              </el-button>
            </template>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        v-if="total > pageSize"
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="loadApplications"
        style="margin-top: 20px"
      />
    </el-card>
    
    <!-- 详情对话框 -->
    <el-dialog
      v-model="showDetail"
      title="申请详情"
      width="600px"
    >
      <el-descriptions v-if="currentApplication" :column="1" border>
        <el-descriptions-item label="学号">
          {{ currentApplication.studentId }}
        </el-descriptions-item>
        <el-descriptions-item label="姓名">
          {{ currentApplication.realName }}
        </el-descriptions-item>
        <el-descriptions-item label="学院">
          {{ currentApplication.college }}
        </el-descriptions-item>
        <el-descriptions-item label="宿舍">
          {{ currentApplication.dormitory }}
        </el-descriptions-item>
        <el-descriptions-item label="手机">
          {{ currentApplication.phone }}
        </el-descriptions-item>
        <el-descriptions-item label="QQ">
          {{ currentApplication.qq }}
        </el-descriptions-item>
        <el-descriptions-item label="个人简介">
          {{ currentApplication.introduction || '无' }}
        </el-descriptions-item>
        <el-descriptions-item label="申请时间">
          {{ currentApplication.applyTime }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag v-if="currentApplication.status === 'PENDING'" type="warning">
            待审批
          </el-tag>
          <el-tag v-else-if="currentApplication.status === 'APPROVED'" type="success">
            已通过
          </el-tag>
          <el-tag v-else-if="currentApplication.status === 'REJECTED'" type="danger">
            已拒绝
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getApplicationList, getApplicationDetail, approveApplication, rejectApplication } from '@/api'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const applicationList = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const statusFilter = ref('')
const showDetail = ref(false)
const currentApplication = ref(null)

const loadApplications = async () => {
  try {
    loading.value = true
    const res = await getApplicationList({
      status: statusFilter.value,
      page: currentPage.value,
      size: pageSize.value
    })
    applicationList.value = res.data.list
    total.value = res.data.total
  } catch (error) {
    console.error('加载申请失败:', error)
  } finally {
    loading.value = false
  }
}

const viewDetail = async (application) => {
  try {
    const res = await getApplicationDetail(application.id)
    currentApplication.value = res.data
    showDetail.value = true
  } catch (error) {
    console.error('加载详情失败:', error)
  }
}

const handleApprove = async (application) => {
  try {
    await ElMessageBox.confirm('确认同意该申请？', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await approveApplication(application.id)
    ElMessage.success('已同意申请')
    loadApplications()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('审批失败:', error)
    }
  }
}

const handleReject = async (application) => {
  try {
    await ElMessageBox.confirm('确认拒绝该申请？', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await rejectApplication(application.id)
    ElMessage.success('已拒绝申请')
    loadApplications()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('审批失败:', error)
    }
  }
}

onMounted(() => {
  loadApplications()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>


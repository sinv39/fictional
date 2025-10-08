<template>
  <div class="apply-page">
    <el-card>
      <template #header>
        <h2 class="card-title">
          <el-icon><EditPen /></el-icon>
          申请加入科幻界
        </h2>
      </template>
      
      <el-alert
        title="申请须知"
        type="info"
        :closable="false"
        style="margin-bottom: 20px"
      >
        <p>1. 请如实填写以下信息</p>
        <p>2. 请设置一个安全的登录密码（至少6位）</p>
        <p>3. 提交后等待管理员审核</p>
        <p>4. 审核通过后即可使用学号和密码登录</p>
      </el-alert>
      
      <el-form
        ref="formRef"
        :model="applyForm"
        :rules="rules"
        label-width="100px"
        size="large"
      >
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="applyForm.studentId" placeholder="请输入学号" />
        </el-form-item>
        
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="applyForm.realName" placeholder="请输入真实姓名" />
        </el-form-item>
        
        <el-form-item label="学院" prop="college">
          <el-input v-model="applyForm.college" placeholder="请输入学院" />
        </el-form-item>
        
        <el-form-item label="宿舍号" prop="dormitory">
          <el-input v-model="applyForm.dormitory" placeholder="例如：学1-101" />
        </el-form-item>
        
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="applyForm.phone" placeholder="请输入手机号" />
        </el-form-item>
        
        <el-form-item label="QQ号" prop="qq">
          <el-input v-model="applyForm.qq" placeholder="请输入QQ号" />
        </el-form-item>
        
        <el-form-item label="设置密码" prop="password">
          <el-input
            v-model="applyForm.password"
            type="password"
            placeholder="请设置登录密码"
            show-password
          />
        </el-form-item>
        
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="applyForm.confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            show-password
          />
        </el-form-item>
        
        <el-form-item label="个人简介" prop="introduction">
          <el-input
            v-model="applyForm.introduction"
            type="textarea"
            :rows="4"
            placeholder="请简单介绍一下自己，为什么想加入科幻界..."
          />
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            :loading="loading"
            @click="handleSubmit"
            size="large"
          >
            提交申请
          </el-button>
          <el-button @click="handleReset" size="large">
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { applyJoin } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

const applyForm = reactive({
  studentId: '',
  realName: '',
  college: '',
  dormitory: '',
  phone: '',
  qq: '',
  password: '',
  confirmPassword: '',
  introduction: ''
})

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入密码'))
  } else if (value.length < 6) {
    callback(new Error('密码长度至少6位'))
  } else {
    if (applyForm.confirmPassword !== '') {
      formRef.value.validateField('confirmPassword')
    }
    callback()
  }
}

const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== applyForm.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  realName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' }
  ],
  college: [
    { required: true, message: '请输入学院', trigger: 'blur' }
  ],
  dormitory: [
    { required: true, message: '请输入宿舍号', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  qq: [
    { required: true, message: '请输入QQ号', trigger: 'blur' },
    { pattern: /^\d{5,11}$/, message: '请输入正确的QQ号', trigger: 'blur' }
  ],
  password: [
    { validator: validatePassword, trigger: 'blur' }
  ],
  confirmPassword: [
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    
    // 提交数据时不包含confirmPassword
    const { confirmPassword, ...submitData } = applyForm
    await applyJoin(submitData)
    
    ElMessage.success('申请提交成功，请等待审核')
    
    // 3秒后跳转到首页
    setTimeout(() => {
      router.push('/')
    }, 3000)
  } catch (error) {
    console.error('提交失败:', error)
  } finally {
    loading.value = false
  }
}

const handleReset = () => {
  formRef.value.resetFields()
}
</script>

<style scoped>
.apply-page {
  max-width: 800px;
  margin: 0 auto;
}

.card-title {
  font-size: 24px;
  display: flex;
  align-items: center;
  gap: 10px;
}
</style>


import request from '@/utils/request'

// ==================== 封面管理 ====================

/**
 * 获取主页封面
 */
export function getHomeCover() {
  return request({
    url: '/cover/home',
    method: 'get'
  })
}

/**
 * 获取介绍封面
 */
export function getIntroCover() {
  return request({
    url: '/cover/intro',
    method: 'get'
  })
}

/**
 * 更新主页封面
 */
export function updateHomeCover(formData) {
  return request({
    url: '/cover/home',
    method: 'put',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

/**
 * 更新介绍封面
 */
export function updateIntroCover(formData) {
  return request({
    url: '/cover/intro',
    method: 'put',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// ==================== 用户认证 ====================

/**
 * 用户登录
 */
export function login(data) {
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

/**
 * 申请加入
 */
export function applyJoin(data) {
  return request({
    url: '/auth/apply',
    method: 'post',
    data
  })
}

// ==================== 视频管理 ====================

/**
 * 获取视频列表
 */
export function getVideoList(params) {
  return request({
    url: '/videos',
    method: 'get',
    params
  })
}

/**
 * 上传视频
 */
export function uploadVideo(formData) {
  return request({
    url: '/videos',
    method: 'put',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// ==================== 图片管理 ====================

/**
 * 获取图片列表
 */
export function getPhotoList(params) {
  return request({
    url: '/photos',
    method: 'get',
    params
  })
}

/**
 * 上传图片
 */
export function uploadPhoto(formData) {
  return request({
    url: '/photos',
    method: 'put',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// ==================== 大事记管理 ====================

/**
 * 获取大事记列表
 */
export function getEventList(params) {
  return request({
    url: '/events',
    method: 'get',
    params
  })
}

/**
 * 发布大事记
 */
export function publishEvent(data) {
  return request({
    url: '/events',
    method: 'post',
    data
  })
}

// ==================== 申请审批 ====================

/**
 * 获取申请列表
 */
export function getApplicationList(params) {
  return request({
    url: '/applications',
    method: 'get',
    params
  })
}

/**
 * 获取申请详情
 */
export function getApplicationDetail(id) {
  return request({
    url: `/applications/${id}`,
    method: 'get'
  })
}

/**
 * 同意申请
 */
export function approveApplication(id) {
  return request({
    url: `/applications/${id}/approve`,
    method: 'post'
  })
}

/**
 * 拒绝申请
 */
export function rejectApplication(id) {
  return request({
    url: `/applications/${id}/reject`,
    method: 'post'
  })
}

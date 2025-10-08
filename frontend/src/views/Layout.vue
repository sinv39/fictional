<template>
  <div class="layout">
    <nav-bar />
    <div class="main-content">
      <router-view />
    </div>
    <footer class="footer">
      <p>© 2024 中国地质大学科幻界 版权所有</p>
    </footer>
  </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue'
</script>

<style scoped>
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.main-content {
  flex: 1;
  max-width: 1400px;
  width: 100%;
  margin: 0 auto;
  padding: 20px;
}

.footer {
  background: #fff;
  text-align: center;
  padding: 20px;
  border-top: 1px solid #e5e7eb;
  color: #666;
}
</style>


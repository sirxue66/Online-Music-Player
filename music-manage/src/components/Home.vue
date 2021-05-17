<template>
  <div>
    <!-- 头导航 -->
    <header-page></header-page>
    <!-- 侧边导航路由 -->
    <asider-page></asider-page>
    <!-- 子路由展示出口 -->
    <div class="content-box">
    <router-view></router-view>
  </div>
  </div>
</template>
<script>
import HeaderPage from "./Header"
import AsiderPage from "./Asider"
export default {
  components: {
    HeaderPage,
    AsiderPage
  }
}
</script>

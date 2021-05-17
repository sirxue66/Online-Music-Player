<template>
  <div class="scroll-top" @click="toTop">
    <div class="box-in"></div>
  </div>
</template>
<script>
export default {
  methods: {
    // 返回顶部的方法
    toTop(){
      // 兼容性   有的浏览器叫documentElement，有的浏览为叫body
      document.documentElement.scrollTop = document.body.scrollTop = 0;
    }
  },
}
</script>
<style lang="scss" scoped>
  @import "../assets/css/scroll-top.scss"
</style>

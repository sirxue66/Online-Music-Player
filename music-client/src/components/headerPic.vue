<template>
  <div class="headerPic">
    <!--  使用elementUi中的走马灯 做 轮播图 -->
    <!-- type="card" -->
    <el-carousel :interval="3000" height="400px">
      <el-carousel-item v-for="(item, index) in picList" :key="index">
        <img :src="item.picImg">
      </el-carousel-item>
    </el-carousel>
  </div>
</template>
<script>
export default {
  data() {
    return {
      picList: [
        {picImg: require('../assets/img/headerPic/pic01.jpg')},
        {picImg: require('../assets/img/headerPic/pic02.jpg')},
        {picImg: require('../assets/img/headerPic/pic03.jpg')},
        {picImg: require('../assets/img/headerPic/pic04.jpg')},
        {picImg: require('../assets/img/headerPic/pic05.jpg')},
        {picImg: require('../assets/img/headerPic/pic06.jpg')},
        {picImg: require('../assets/img/headerPic/pic07.jpg')},
        {picImg: require('../assets/img/headerPic/pic08.jpg')},
        {picImg: require('../assets/img/headerPic/pic09.jpg')}
      ]
    }
  },
}
</script>
<style lang="scss" scoped>
@import '../assets/css/headerPic.scss'
</style>

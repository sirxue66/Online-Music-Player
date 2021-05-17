<template>
  <div class="search">
    <nav class="searchList-nav">
      <span :class="{isActive: activeComponent == 'SearchSong'}" @click="changeComponent('SearchSong')">歌曲</span>
      <span :class="{isActive: activeComponent == 'SearchSinger'}" @click="changeComponent('SearchSinger')">歌手</span>
    </nav>
    <!-- 切换显示组件 -->
    <component :is="activeComponent"></component>
  </div>
</template>
<script>
import SearchSong from "./search/searchSong"
import SearchSinger from "./search/searchSinger"
export default {
  components: {
    SearchSong,
    SearchSinger
  },
  data() {
    return {
      activeComponent: "SearchSong"
    }
  },
  methods: {
    // 切换显示组件
    changeComponent(compon){
      this.activeComponent = compon;
    }
  },
}
</script>
<style lang="scss" scoped>
@import "../assets/css/search.scss"
</style>

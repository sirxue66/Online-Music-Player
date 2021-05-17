<template>
  <div id="app">
    <the-header></the-header>
    <router-view class="music-content"/>
    <play-songlist></play-songlist>
    <to-top></to-top>
    <play-page></play-page>
    <the-footer></the-footer>
  </div>
</template>

<script>
import TheHeader from "./components/theHeader"
import ToTop from "./components/toTop"
import TheFooter from "./components/theFooter"
import PlayPage from "./components/playPage"
import PlaySonglist from "./components/playSongList"
export default {
  name: 'App',
  components: {
    TheHeader,
    ToTop,
    TheFooter,
    PlayPage,
    PlaySonglist
  }
}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>

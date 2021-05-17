<template>
  <div class="the-header">
    <div class="header-logo" @click="goHome">
      <i class="el-icon-headset"></i>
      <span>MusicPlayer</span>
    </div>
    <div class="navbar">
      <ul>
        <li :class="{active: nav == item.name}" v-for="(item,index) in navMsg" :key="index" @click="goPage(item.path,item.name)">
          {{item.name}}
        </li>
        <li>
          <div class="header-search">
            <input type="text" placeholder="搜索歌曲" v-model="searchWords" @keyup.enter="search()">
            <div class="search-btn" @click="search()"><i class="el-icon-search"></i></div>
          </div>
        </li>
        <!-- 为了保证在一个dom中的key的值不重复，在index + 一个user，随便加一个就行 -->
        <li v-for="(item,index) in userMsg" :key="index+'user'" @click="goLogin(item.path)" v-show="!isLogin">
          {{item.name}}
        </li>
      </ul>
    </div>
    <div class="header-right" v-show="isLogin">
        <div id="user" @click="goSetting">
          <img :src="getImageUrl(avator)">
        </div>
        <el-button type="primary" @click="logout">退出</el-button>
      </div>
  </div>
</template>
<script>
import {mapGetters} from 'vuex'
import {tool} from "../mixins/tool"
export default {
  mixins: [tool],
  data() {
    return {
      navMsg: [
        {name: "首页", path: "/"},
        {name: "歌单", path: "/songList"},
        {name: "歌手", path: "/singer"},
        {name: "我的收藏", path: "/myMusic"},
      ],
      userMsg: [
        {name: "登录", path: "/login"},
        {name: "注册", path: "/registered"}
      ],
      searchWords: "",
      // 判断点击哪一个nav
      nav: "",

    }
  },
  computed: {
    ...mapGetters([
      'isLogin',
      'avator',
      'isSearch',    //用来在搜索页面再次调用搜索功能，控制的变量
    ])
  },
  methods: {
    // 返回主页
    goHome(){
      if(this.$route.path == "/"){
        console.log("到达目的地。");
      } else {
      this.$router.push("/");
      }
    },
    // 页面跳转
    goPage(paths,name){
      this.nav = name;
      if(this.$route.path == paths){
        console.log("到达目的地。");
      } else {
      this.$router.push(paths);
      }
    },
    // 转向登录注册页面
    goLogin(paths){
      if(this.$route.path == paths){
        console.log("到达目的地。");
      } else {
      this.$router.push(paths);
      }
    },
    // 搜索跳转
    search(){
      this.$router.push({path:"/search",query:{keyWords:this.searchWords}});
      this.$store.commit('setIsSearch',!this.isSearch);
    },
    // 跳转个人设置页面
    goSetting(){
      if(this.$route.path == "/setting"){
        console.log("到达目的地。");
      } else {
      this.$router.push("/setting");
      }
    },
    // 退出登录
    logout(){
      this.$store.commit('setIsLogin',false);
      this.$router.go(0);
      this.$router.push("/");
    }
  },
}
</script>
<style lang="scss" scoped>
  @import "../assets/css/the-header.scss"
</style>

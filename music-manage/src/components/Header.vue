<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div class="btn" @click="changeCollapse()">
      <i class="el-icon-menu"></i>
    </div>
    <div class="title">MusicPlayer 后台管理</div>
    <div class="header-right">
      <div class="screen" @click="fullScreen()">
        <i class="el-icon-full-screen"></i>
      </div>
      <div class="user-avator">
        <img src="../assets/img/header.jpg">
      </div>
      <el-dropdown class="user" trigger="click">
        <!-- {{userName}} -->
        <span class="username">
          {{userName}}
          <i class="el-icon-caret-bottom"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="logOut()">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>
<script>
import bus from "../assets/js/bus";
export default {
  data() {
    return {
      collapse: false,
      fullscreen: false
    };
  },
  computed: {
    userName(){
      return localStorage.getItem("userName");
    }
  },
  methods: {
    // 侧边栏折叠
    changeCollapse() {
      this.collapse = !this.collapse;
      bus.$emit("collapse", this.collapse);
    },
    // 全屏事件
    fullScreen() {
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
          //适配浏览器兼容问题，支持谷歌览器
          document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
          //支持火狐浏览器
          document.mozCancelFullScreen();
        } else if (document.msExitFullScreen) {
          //ie浏览器
          document.msExitFullScreen();
        }
      } else {
        let element = document.documentElement;
        if (element.requestFullscreen) {
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        } else if (element.msRequestFullScreen) {
          element.msRequestFullScreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    },
    // 退出登录
    logOut(){
      localStorage.removeItem("userName");
      this.$router.push("/");
    }
  }
};
</script>
<style scoped>
.header {
  position: relative;
  background-color: rgb(16, 16, 17);
  width: 100%;
  height: 70px;
  font-size: 20px;
  color: white;
  /* 带阴影 */
  box-sizing: border-box;
}
.btn {
  float: left;
  padding: 0 20px;
  line-height: 70px;
  /* 小手图标 */
  cursor: pointer;
}
.title {
  float: left;
  line-height: 70px;
  text-align: center;
}
.header-right {
  float: right;
  margin-right: 40px;
  /* 内容默认横着排列 */
  display: flex;
  line-height: 70px;
  align-items: center;
}
.screen {
  margin-right: 20px;
  font-size: 25px;
}
.user-avator img{
  margin-left: 10px;
  height: 50px;
  width: 50px;
  border-radius: 50%;
  display: block;
}
.user{
  margin-left: 12px;
}
.username{
  color: aliceblue;
  cursor: pointer;
}
</style>

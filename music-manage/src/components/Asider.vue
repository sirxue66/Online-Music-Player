<template>
  <div class="sidebar">
    <!-- 巧妙之处 :default-active="onRoutes"  没有写死，默认路由会根据登录成功之后的跳转地址改变而改变-->
    <el-menu
    class="sidebar-menu"
    :default-active="onRoutes"
    :collapse="collapse"
    text-color="#708090"
    active-text-color="yellow"
    background-color="#444444"
    router>
      <template v-for="item in items">
        <el-menu-item :index="item.index" :key="item.index">
          <i :class="item.icon"></i>
          <span style="color:white;">{{item.title}}</span>
        </el-menu-item>
      </template>
    </el-menu>
  </div>
</template>
<script>
import bus from "../assets/js/bus"
export default {
  data() {
    return {
      // 是否折叠的控制变量
      collapse: false,
      items: [
        {
          icon: "el-icon-s-home",
          index: "manage",
          title: "系统首页"
        },{
          icon: "el-icon-user",
          index: "consumer",
          title: "用户管理"
        },{
          icon: "el-icon-s-custom",
          index: "singer",
          title: "歌手管理"
        },{
          icon: "el-icon-headset",
          index: "songList",
          title: "歌单管理"
        },{
          icon: "el-icon-chat-line-round",
          index: "comment",
          title: "评论管理"
        }
      ]
    }
  },
  computed: {
    onRoutes(){
      return this.$route.path.replace('/','');
    }
  },
  created() {
    // 通过bus进行组件通信，   接收$on
    bus.$on("collapse",msg => {
      this.collapse = msg;
    })
  },
}
</script>
<style scoped>
.sidebar{
  display:block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: auto;
  /* background-color: rgb(114, 111, 113); */
}
.sidebar-menu{
  width: 150px;
}
/* menu 中的循环高度为100% */
.sidebar >ul{
  height: 100%;
}
</style>

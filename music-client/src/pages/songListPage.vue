<template>
  <div class="song-list">
    <ul class="song-list-header">
      <li v-for="(item,index) in style" :key="index" :class="{active:item.name===activeName}"
       @click="changeStyle(item.name)">
        {{item.name}}
      </li>
    </ul>
    <!-- 歌单列表 -->
    <div class="content-list">
        <ul class="section-content">
          <li class="content-item" v-for="(item,index) in data" :key="index+'song'">
            <!-- 因为只有歌手中有name，通过是否传入name来判断跳转页面 -->
            <div class="kuo" @click="goDetail(item)">
              <img :src="getImageUrl(item.pic)" class="item-img">
              <!-- 添加播放图形svg  在main.js中已经引入 -->
              <div class="mask">
                <svg class="icon">
                  <use xlink:href="#icon-bofang"></use>
                </svg>
              </div>
            </div>
            <!--歌单标题 -->
            <p class="item-name">{{item.title}}</p>
          </li>
        </ul>
      </div>
      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
        background
        layout="total,prev, pager, next"
        :page-size="pageSize"
        :current-page="currentPage"
        :total="allSongList.length"
        @current-change="handlePage">
      </el-pagination>
      </div>
  </div>
</template>
<script>
import { tool } from "../mixins/tool"
import {allSongListApi, getSongListByStyle} from "../api/index"
export default {
  mixins: [tool],
  data() {
    return {
      style: [
        {name: "所有风格"},
        {name: "华语"},
        {name: "粤语"},
        {name: "日韩"},
        {name: "欧美"},
        {name: "轻音乐"},
        {name: "BGM"},
        {name: "乐器"},
      ],
      allSongList:[],
      activeName: "所有风格",     //当前所在歌单列表
      pageSize:20,
      currentPage:1,
    }
  },
  computed:{
        //计算当前表格中的数据
        data(){
            return this.allSongList.slice((this.currentPage - 1)*this.pageSize,this.currentPage*this.pageSize)
        },
  },
  created() {
    this.getAllSongList();
  },
  methods: {
    // 选择风格
    changeStyle(style){
      this.activeName = style;
      this.getListByStyle(style);
    },
    // 获取所有歌单
    getAllSongList(){
      allSongListApi().then(response => {
        this.allSongList = response;
      })
    },
    // 根据风格获取歌单
    getListByStyle(style){
      if(style === "所有风格"){
        this.getAllSongList();
      } else {
        getSongListByStyle(style).then(response => {
          this.allSongList = response;
        })
      }
    },
    // 分页操作
    handlePage(nowPage){
      this.currentPage = nowPage;
    },
    // 转向详情页面
    goDetail(item){
    this.$store.commit('setTempList',item);
    this.$router.push({path:'/songlistDetail',query:{songlistID:item.id}});
  }
  },
}
</script>
<style lang="scss" scoped>
  @import "../assets/css/song-list.scss";
  @import "../assets/css/content-list.scss";
</style>

<template>
    <div class="singer">
        <ul class="singer-header">
            <li v-for="(item,index) in singerStyle" :key="index" @click="selectSonger(item.value)"
                :class="{active:item.value==activeName}">
                {{item.name}}
            </li>
            <li><el-input v-model="searchWords" size="mini" prefix-icon="el-icon-search" placeholder="输入歌手"></el-input></li>
        </ul>
        <!-- 歌手列表 -->
    <div class="content-list">
        <ul class="section-content">
          <li class="content-item" v-for="(item,index) in data" :key="index+'singer'">
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
            <p class="item-name">{{item.name}}</p>
          </li>
        </ul>
      </div>
      <!-- 分页 -->
            <div class="pagination">
                <el-pagination @current-change="handlePage" background layout="total,prev,pager,next"
                        :current-page="currentPage" :page-size="pageSize" :total="allSingers.length">
                </el-pagination>
            </div>
    </div>
</template>
<script>
import {tool} from "../mixins/tool"
import{ allSingerApi, getSingerBySex } from "../api/index"
export default {
  mixins: [tool],
  data() {
    return {
      singerStyle: [
        {name: "全部歌手", value: "-1"},
        {name: "女歌手", value: "0"},
        {name: "男歌手", value: "1"},
        {name: "组合", value: "2"}
      ],
      activeName: "-1",
      allSingers: [],
      allSingersOne: [],
      pageSize: 20,
      currentPage:1,
      searchWords: ""     // 搜索关键词
    }
  },
  computed: {
    data(){
      return this.allSingers.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize);
    }
  },
  created() {
    this.getAllSingers();
  },
  methods: {
    // 处理分页
    handlePage(nowpage){
      this.currentPage = nowpage;
    },
  // 跳转详情页
  goDetail(item){
    this.$store.commit('setTempList',item);
    this.$router.push({path:'/singerDetail',query:{singerID:item.id}});
  },
  // 获取所有歌手
  getAllSingers(){
    allSingerApi().then(response => {
      this.allSingers = response;
      this.allSingersOne = response;

    })
  },
  // 根据性别查询歌手
  getSingersBySex(value){
    if(value == "-1"){
      this.getAllSingers();
    } else {
      getSingerBySex(value).then(response => {
        this.allSingers = response;
        this.allSingersOne = response;
      })
    };
  },
  // 选择性别
  selectSonger(value){
    this.activeName = value;
    this.getSingersBySex(value);
  }
  },
  watch: {
    searchWords: function(){
      this.allSingers = [];
      if(this.searchWords === ""){
        this.allSingers = this.allSingersOne;
      } else {
        this.allSingersOne.forEach(item => {
          if(item.name.includes(this.searchWords)){
            this.allSingers.push(item);
          }
        })
      }
      return this.allSingers;
    }
  }
}
</script>
<style lang="scss" scoped>
@import "../assets/css/singer.scss";
@import "../assets/css/content-list.scss";
</style>

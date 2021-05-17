<template>
  <div class="home">
    <header-pic></header-pic>
    <!-- 主体 -->
    <div class="section" v-for="(item,index) in dataList" :key="index">
      <div class="section-title">
        {{item.name}}
      </div>
      <div class="content-list">
        <ul class="section-content">
          <li class="content-item" v-for="(items,index) in (item.value)" :key="index">
            <!-- 因为只有歌手中有name，通过是否传入name来判断跳转页面 -->
            <div class="kuo" @click="goDetail(items,items.name)">
              <img :src="getImageUrl(items.pic)" class="item-img">
              <!-- 添加播放图形svg  在main.js中已经引入 -->
              <div class="mask">
                <svg class="icon">
                  <use xlink:href="#icon-bofang"></use>
                </svg>
              </div>
            </div>
            <!-- 歌手名字或者歌单标题 -->
            <p class="item-name">{{items.name || items.title}}</p>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import HeaderPic from "../components/headerPic"
import { tool } from "../mixins/tool"
import { allSingerApi, allSongListApi } from "../api/index"
export default {
  mixins: [tool],
  name: 'home',
  components: {
    HeaderPic,
  },
  data () {
    return {
      dataList: [
        {name: "歌手", value: []},
        {name: "歌单", value: []}
      ]
    }
  },
  created () {
    this.getSingers();
    this.getSongs();
  },
  methods: {
    // 获取前十名歌手
    getSingers(){
      allSingerApi().then(response => {
        this.dataList[0].value = response.slice(0,10);
      }).catch(err => {console.log("获取歌手出错！")})
    },
    // 获取前十个歌单
    getSongs(){
      allSongListApi().then(response => {
        this.dataList[1].value = response.slice(0,10);
      }).catch(err => {console.log("获取歌单出错！")})
    },
  // 跳转歌单或歌手下的歌曲详情页
  goDetail(item,isName){
    this.$store.commit('setTempList',item);
    if(isName){
      this.$router.push({path:'/singerDetail',query:{singerID:item.id}});
    } else {
      this.$router.push({path:'/songlistDetail',query:{songlistID:item.id}});
    }
  }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
@import '../assets/css/content-list.scss';
</style>

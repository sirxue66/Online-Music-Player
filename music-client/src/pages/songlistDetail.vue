<template>
  <div class="song-list-album">
    <!-- 图片及介绍 -->
   <div class="album-slide">
     <div class="album-img">
       <img :src="getImageUrl(tempList.pic)">
     </div>
     <div class="album-info">
       <h2>歌单简介：</h2>
       <span>
         {{tempList.introduction}}
       </span>
     </div>
   </div>
   <!-- 歌单名及评价 -->
   <div class="album-content">
     <div class="album-title">
       <span>{{tempList.title}}</span>
     </div>
     <div class="album-score">
       <div>
         <h2>歌单评分：</h2>
         <div>
           <!-- 将数值转换成五角星的插件 -->
           <el-rate v-model="averageScore" allow-half disabled></el-rate>
         </div>
       </div>
       <!-- 因为获取方法中/2，这里在加回来 -->
       <span>{{averageScore*2}}</span>
       <!-- 用户提交评价分 -->
       <div>
         <h2>评价：</h2>
          <div @click="addRank">
           <!-- 将数值转换成五角星的插件  allow-half 可以选择半个五角星,show-text 显示等级-->
           <el-rate v-model="consumerScore" allow-half show-text></el-rate>
         </div>
       </div>
     </div>
     <div class="songs-body">
       <!-- 歌单列表 -->
  <song-list :songList="songs"></song-list>
  <!-- 评论列表 -->
  <comment :playId="songlistID" :type="1"></comment>
     </div>
   </div>
  </div>
</template>
<script>
import {tool} from "../mixins/tool"
import {mapGetters} from "vuex"
import { listSongDetailApi, searchSongByIdApi, getAverageRank, addRankApi } from "../api/index"
import Comment from "../components/comment"
import SongList from "../components/songlist"
export default {
  mixins: [tool],
  components: {
    Comment,
    SongList
  },
  data() {
    return {
      songs: [],    //查询到的歌曲列表
      songlistID: "",      //当前歌单的id
      averageScore: 0,         //歌单评分
      consumerScore: 0,         //用户评分
    }
  },
  computed: {
    ...mapGetters([
      'listOfSongs',
      'tempList',             //当前歌单的信息
      'isLogin',              //验证当前是否登录
      'userID',               //登录用户的id
    ])
  },
  created() {
    this.songlistID = this.$route.query.songlistID;
    // console.log(this.tempList.id);
  },
  mounted() {
    this.getsongs();
    this.getRank();
  },
  methods: {
    // 获取歌曲列表
    getsongs(){
      listSongDetailApi(this.songlistID).then(response => {
        for(let item of response){
          this.getSongById(item.songId);
        }
        this.$store.commit('setListOfSongs',this.songs);
      }).catch(err => {
        console.log("getsongs方法出错");
      })
    },
    // 根据歌曲id查询歌曲
    getSongById(songid){
      searchSongByIdApi(songid).then(response => {
        this.songs.push(response);
      }).catch(err => {
        console.log("getSongById方法出错");
      })
    },
    // 获取歌单评分
    getRank(){
      getAverageRank(this.songlistID).then(response => {
        this.averageScore = response/2;
      }).catch(err => {
        console.log("获取评分失败");
      })
    },
    // 用户提交评分
    addRank(){
      if(this.isLogin){
      let params = new URLSearchParams();
      params.append('songListId',this.songlistID);
      params.append('consumerId',this.userID);
      params.append('score',this.consumerScore*2);
      addRankApi(params).then(response => {
        if(response.code === 1){
          this.$message.success(response.infoMsg);
          this.getRank();
        } else {
          this.$message.error(response.infoMsg);
        }
      })
      .catch(err => {
        this.$message.success("您已经评价过了！");
      })
      } else {
        this.consumerScore = 0;
        this.$message.success("请先登录");
      }
    },

  },
}
</script>
<style lang="scss" scoped>
  @import '../assets/css/song-list-album.scss';
</style>

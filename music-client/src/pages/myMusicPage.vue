<template>
  <div class="my-music">
    <!-- 未登录页面 -->
    <div style="text-align:center;height:600px;" v-show="!isLogin">
      <h1>请先登录</h1>
    <!-- <ul>
      <h1>种瓜得瓜，种豆得豆</h1>
    </ul> <br>
    <ul>
      <h1>要吃瓜，先种豆</h1>
    </ul><br>
    <ul>
      <h1>要收藏，你得先登录</h1>
    </ul> -->
  </div>
    <!-- 登录页面 -->
<!-- TODO  歌单的收藏，目前只给歌曲添加了收藏功能 -->
    <div v-show="isLogin">
        <div class="album-slide">
            <div class="album-img">
                <img :src="getImageUrl(avator)">
            </div>
            <ul class="album-info">
                <li>昵称:{{username}}</li>
                <li>性别:{{userSex}}</li>
                <li>生日：{{birth}}</li>
                <li>地区：{{location}}</li>
            </ul>
        </div>
        <div class="album-content">
            <div class="album-title">
                个性签名：{{introduction}}
            </div>
            <div class="songs-body">
               <!-- 歌曲列表 -->
            <div class="content">
      <ul>
        <li>
          <div class="song-item">
            <span class="item-index"></span>
            <span class="item-name" style="padding-left:30px;">歌曲</span>
            <span class="item-title">歌手</span>
            <span class="item-intro">专辑</span>
            <span class="item-controller">移除</span>
          </div>
        </li>
        <li v-for="(item,index) in collectList" :key="index">
          <div class="song-item">
            <span class="item-index">{{index+1}}</span>
            <span class="item-name" @click="play(item.id,item.url,item.pic,item.name,index,item.lyric)">
              <i class="el-icon-video-play"></i>
              {{SongName(item.name)}}
              </span>
            <span class="item-title">{{SingerName(item.name)}}</span>
            <span class="item-intro">{{item.introduction}}</span>
              <span @click="deleteCollect(item.id)" class="item-controller"><i class="el-icon-delete"></i></span>
          </div>
        </li>
      </ul>
    </div>
            </div>
        </div>
        </div>
    </div>
</template>
<script>
import{mapGetters} from "vuex"
import {tool} from "../mixins/tool"
import {getConsumerById, getCollectByUserId, searchSongByIdApi, deleteCollectById} from "../api/index"
import SongList from "../components/songlist"
export default {
  mixins: [tool],
  components: {
    SongList
  },
  data() {
    return {
      userSex:"",
      birth: "",
      location: "",
      introduction: "",
      collectList: [],
    }
  },
computed: {
  ...mapGetters([
    'avator','username','userID','listOfSongs','isLogin'
  ])
},
created() {
  if(this.isLogin){
  this.getUserMsg();
  this.getCollect();
  } else {
    this.$message.success("请先登录");
  }
},
methods: {
  // 获取用户信息
  getUserMsg(){
    getConsumerById(this.userID).then(response => {
      if(response.sex === 0){
        this.userSex = "女";
      }else {
        this.userSex = "男";
      };
      this.birth = this.changeBirth(response.birth);
      this.location = response.location;
      this.introduction = response.introduction;
    })
    },
  // 获取收藏列表
  getCollect(){
    this.collectList = [];
    getCollectByUserId(this.userID).then(response => {
      response.forEach(item => {
        this.getSong(item.songId);
      })
    }).catch(err => {
      console.log("获取收藏失败");
    })
    this.$store.commit('setListOfSongs',this.collectList);
    return this.collectList;
  },
  // 根据歌曲id查询歌曲
  getSong(id){
    searchSongByIdApi(id).then(response => {
      this.collectList.push(response);
    })
  },
  // 删除收藏歌曲
  deleteCollect(songId){
    this.$confirm('确定移除该收藏吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
    deleteCollectById(this.userID,songId).then(response => {
      if(response.code === 1){
        this.$message.success(response.infoMsg);
        this.getCollect();
      }else{
        this.$message.error(response.infoMsg);
      }
    })
        }).catch(err => {
          console.log(err);
        })
  },
  // 搜索出来的歌曲播放的方法
    play(id,url,pic,name,index,lyric){
      this.$store.commit('setId',id);
      this.$store.commit('setUrl',this.$store.state.Config.HOST+url);
      this.$store.commit('setPicUrl',this.$store.state.Config.HOST+pic);
      this.$store.commit('setTitle',name);
      this.$store.commit('setListIndex',index);
      this.$store.commit('setLyric',lyric);
    },
  }
}
</script>
<style lang="scss" scoped>
  @import "../assets/css/my-music.scss";
  @import "../assets/css/album-content.scss"
</style>

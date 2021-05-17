<template>
    <div class="play-bar" :class="{show:isShow}">
      <div class="item-up" @click="isShow = !isShow" :class="{turn:!isShow}">
        <i class="el-icon-arrow-up"></i>
      </div>
        <div class="kongjian">
          <!-- 转向歌曲详情页 -->
          <!-- TODO  不转向，直接控制歌词页面是否显示 -->
          <div class="toDatail" @click="toDetail">
            <i class="el-icon-d-arrow-left"></i>
          </div>
          <div class="songName">{{title}}</div>
            <!-- 上一首 -->
            <div class="item" @click="up">
                <svg class="icon">
                    <use xlink:href="#icon-ziyuanldpi"></use>
                </svg>
            </div>
            <!-- 下一首 -->
            <div class="item" @click="down">
                <svg class="icon">
                    <use xlink:href="#icon-ziyuanldpi1"></use>
                </svg>
            </div>
            <!-- 歌曲图片 -->
            <div class="item-img" >
                <img :src="picUrl"/>
            </div>
            <!-- 播放器 -->
            <div class="playing-speed">
            <song-audio></song-audio>
            </div>
                <!-- 收藏 -->
                <div class="item" @click="collectSong">
                    <svg class="icon" :class="{active:isLike}">
                        <use xlink:href="#icon-xihuan-shi"></use>
                    </svg>
                </div>
                <!-- 下载 -->
                <div class="item" @click="download" :class="{show: !isLogin}">
                    <svg class="icon">
                        <use xlink:href="#icon-xiazai"></use>
                    </svg>
                </div>
                <!-- 当前播放的歌曲列表 -->
                <div class="item" @click="showList">
                    <svg class="icon">
                        <use xlink:href="#icon-liebiao"></use>
                    </svg>
                </div>

            </div>
        </div>
</template>
<script>
import {mapGetters} from 'vuex';
import SongAudio from "./songAudio"
import { downLoad, addCollect } from "../api/index"
export default {
  components: {
    SongAudio
  },
    data(){
        return {
          isShow: false,        //控制播放条是否显示
          isLike: false,            //控制是否收藏样式
        }
    },
    computed: {
        ...mapGetters([
            'url',                //当前播放歌曲的url
            'picUrl',            //正在播放的音乐的图片
            'title',                  //歌名
            'listIndex',            //当前歌曲在歌单中的位置
            'listOfSongs',             //歌曲列表
            'autoNext',                    //自动播放下一首
            'id',                    //当前歌曲id
            'isLogin',                 //用户是否登录的判断标志
            'userID',                //当前登录用户ID
        ])
    },
    watch:{
      // 监控变量，自动播放下一首
      autoNext: function(){
        this.down();
      },
      id: function(){
        this.isLike = false;
      }
    },
    methods: {
      // 显示播放列表的操作
      showList(){
        this.$store.commit('setShowAside',true);
      },

      // 播放上一首
      up(){
        if(this.listIndex != -1 && this.listOfSongs.length > 1){    //当前处于不可能状态或者只有一首音乐的时候不执行）
                if(this.listIndex > 0){                                 //不是第一首音乐
                    this.$store.commit('setListIndex',this.listIndex - 1);  //直接返回上一首
                }else{                                                  //当前是第一首音乐
                    this.$store.commit('setListIndex',this.listOfSongs.length - 1);  //切换到倒数第一首
                }
                this.changePlay(this.listOfSongs[this.listIndex].url);
            }
      },
      // 播放下一首
      down(){
        if(this.listIndex != -1 && this.listOfSongs.length > 1){    //当前处于不可能状态或者只有只有一首音乐的时候不执行）
                if(this.listIndex < this.listOfSongs.length - 1){                                 //不是最后一首音乐
                    this.$store.commit('setListIndex',this.listIndex + 1);  //直接返回下一首
                }else{                                                      //当前是最后一首音乐
                    this.$store.commit('setListIndex',0);  //切换到第一首
                }
                this.changePlay(this.listOfSongs[this.listIndex].url);
            }
      },
      // 上一首下一首歌曲切换播放
      changePlay(url){
        // 执行播放前判断，如果url不为空，并且要播放的url不是当前url
        if(url && url != this.url){
                this.$store.commit('setId',this.listOfSongs[this.listIndex].id);
                this.$store.commit('setUrl',this.$store.state.Config.HOST+url);
                this.$store.commit('setPicUrl',this.$store.state.Config.HOST+this.listOfSongs[this.listIndex].pic);
                this.$store.commit('setTitle',this.listOfSongs[this.listIndex].name);
            }
      },
      // 转向歌曲详情页
      toDetail(){
        this.$router.push("/detail");
      },
      // 下载函数
      download(){
        downLoad(this.url).then(response => {
          let content = response.data;
          let eleLink = document.createElement('a');
          // 下载数据后的命名
          eleLink.download = `${this.title}.mp3`;
          eleLink.style.display = 'none';
          // 把歌曲字符内容转换成歌曲地址
          let blob = new Blob([content]);
          eleLink.href = URL.createObjectURL(blob);
          // 把连接地址加到document中
          document.body.appendChild(eleLink);
          // 触发点击
          eleLink.click();
          // 从document中移除这个控件
          document.body.removeChild(eleLink);
        })
        .catch(err => {
          console.log(err);
        })
      },
      // 收藏歌曲操作
      collectSong(){
        if(this.isLogin){
          let params = new URLSearchParams();
          params.append('userId',this.userID);
          params.append('type',0);
          params.append('songId',this.id);
          addCollect(params).then(response => {
            if(response.code === 2 || response.code === 1){
              this.$message.success(response.infoMsg);
              this.isLike = true;
            } else {
              this.$message.error(response.infoMsg);
            }
          }).catch(err => {
            console.log("collectSong方法报错");
          })
        }else {
          this.$notify.warning("请先登录");
        }
      }
    }
}
</script>
<style  lang="scss" scoped>
@import '../assets/css/play-bar.scss';
.songName{
  font-size: 16px;
  font-weight: bold;
  color: rgb(14, 13, 13);
}
.toDatail{
    display: block;
    margin-right: 50px;
    transform: rotate(90deg);
    color: red;
    cursor: pointer;
}
.turn{
  transform: rotate(180deg);
}
</style>

<template>
<div>
  <div class="comment">
    <h2>评论：</h2>
    <div class="comment-msg">
      <div class="comment-img">
        <img :src="isLogin ? getImageUrl(avator) : getImageUrl(img)">
      </div>
      <el-input class="comment-input" type="textarea" :rows="2" placeholder="请输入内容" v-model="comment">
      </el-input>
    </div>
    <el-button class="sub-btn" type="primary" @click="submitComment">评论</el-button>
  </div>
  <span>精彩评论：共{{commentList.length}}条评论</span>
  <ul class="popular" v-for="(item,index) in commentList" :key="index">
    <li>
      <div class="popular-img">
        <img :src="getImageUrl(userPic[index])">
      </div>
      <div class="popular-msg">
        <ul>
          <li class="name">{{userName[index]}}</li>
          <li class="time">{{item.createTime}}</li>
          <li class="content">{{item.content}}</li>
        </ul>
      </div>
      <div class="up" ref="up" @click="postUp(item.id,item.up,index)">
        <svg class="icon">
          <use xlink:href="#icon-zan"></use>
        </svg>
        {{item.up}}
      </div>
    </li>
  </ul>
</div>
</template>
<script>
import { tool } from "../mixins/tool"
import { mapGetters } from "vuex"
import { addCommentApi, likeComment, getAllComments, getConsumerById } from "../api/index"
export default {
  name: "comment",
  mixins: [tool],
  data() {
    return {
      comment: "",
      commentList: [],
      userName: [],        //用户昵称
      userPic: [],            //用户头像
      img: "/avatorImages/girl.png"               // 未登录用户的默认头像,在后端的地址
    }
  },
  props: [
    'playId',          //歌曲或歌单Id
    'type'                // 0歌曲    1歌单
  ],
  computed: {
    ...mapGetters([
      'id',
      'isLogin',
      'userID',       //登录用户id
      'avator'           //登录用户头像
    ])
  },
  mounted() {
    this.getComments();
  },
  methods: {
    // 提交评论
    submitComment(){
      if(this.isLogin){
        let params = new URLSearchParams();
        if(this.type === 0){
          params.append('songId',this.playId);
        }else{
          params.append('songListId',this.playId);
        }
        params.append('userId',this.userID);
        params.append('type',this.type);
        params.append('content',this.comment);
        addCommentApi(params).then(response => {
          if(response.code === 1){
            this.$message.success(response.infoMsg);
            this.getComments();
          } else {
            this.$message.error(response.infoMsg);
          }
        }).catch(err => {
          this.$notify.error("评论出错");
        })
      }else {
        this.comment = null;
        this.$notify.warning("请先登录");
      }
    },
    // 获取歌单或歌曲下的评论
    getComments(){
      getAllComments(this.type,this.playId).then(response => {
        this.commentList = response;
        for(let item of response){
          this.getUserInfo(item.userId);
        }
      }).catch(error => {
        console.log("getComments方法出错");
        this.$message.warring("评论加载失败");
      })
    },
    // 根据用户id获取用户昵称和头像
    getUserInfo(id){
      getConsumerById(id).then(response => {
        this.userName.push(response.username);
        this.userPic.push(response.avator);
      }).catch(err => {
        console.log("getUserInfo方法出错");
      })
    },
    // 点赞
    postUp(id,up,index){
      if(this.isLogin){
        let params = new URLSearchParams();
        params.append('id',id);
        params.append('up',up+1);
        likeComment(params).then(response => {
          if(response.code === 1){
            this.getComments();
            this.$refs.up[index].children[0].style.color = "blue";
          } else {
            this.$message.error(response.infoMsg);
          }
        }).catch(error => {
          console.log("postUp方法报错");
        })
      } else {
        this.$notify.warning("请先登录");
      }
    }
  },
}
</script>
<style lang="scss" scoped>
  @import "../assets/css/comment.scss"
</style>

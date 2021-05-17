<template>
  <div class="homePage">
    <el-row :gutter="20" style="margin-top:20px;">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="number">{{consumerCount}}</div>
          <div class="cardName">用户数量</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="number">{{songlistCount}}</div>
          <div class="cardName">歌单数量</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="number">{{songCount}}</div>
          <div class="cardName">歌曲数量</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="number">{{singerCount}}</div>
          <div class="cardName">歌手数量</div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="30" class="graphics">
      <el-col :span="8">
        <div class="title"><h2>用户性别分布图</h2></div>
        <div>
          <ve-pie :data="consumerSex" :theme="theme1"></ve-pie>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="title"><h2>歌曲风格统计图</h2></div>
        <ve-histogram :data="songStyle"></ve-histogram>
      </el-col>
      <el-col :span="8">
        <div class="title"><h2>歌手类型分布图</h2></div>
        <ve-pie :data="singerSex"></ve-pie>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { allConsumersApi, allSingerApi, allSongListApi, searchAllSongs } from "../api/index"
export default {
  data() {
    return {
      // 数量长度数据
      consumerCount: 0,
      singerCount: 0,
      songlistCount: 0,
      songCount: 0,
      // 用户性别分类图数据
      consumerSex: {
        columns: ['性别','总数'],
        rows: [
          {'性别': '男', '总数': 0},
          {'性别': '女', '总数': 0}
        ]
      },
      theme1: {
        color: ['#00FFFF', '#FF00FF']
      },
      // 歌手性别分类图数据
      singerSex: {
        columns: ['性别','总数'],
        rows: [
          {'性别':'男','总数':0},
          {'性别': '女', '总数':0},
          {'性别':'组合','总数':0}
        ]
      },
      // 歌曲风格分类图数据
      songStyle: {
        columns: ['风格','总数'],
        rows: [
          {'风格':'华语','总数':0},
          {'风格':'粤语','总数':0},
          {'风格':'日韩','总数':0},
          {'风格':'欧美','总数':0},
          {'风格':'轻音乐','总数':0},
          {'风格':'乐器','总数':0},
          {'风格':'BGM','总数':0}
        ]
      },
    }
  },
  mounted() {
    this.getCounts();
  },
  methods: {
    // 获取数量
    getCounts(){
      allConsumersApi().then(response => {
        this.consumerCount = response.length;
        let man = 0;
        let woman = 0;
        for(let item of response){
          if(item.sex === 1){
            man ++;
          } else {
            woman ++;
          }
        };
        this.consumerSex.rows[0]['总数'] = man;
        this.consumerSex.rows[1]['总数'] = woman;
      });
      allSingerApi().then(response => {
        this.singerCount = response.length;
        let man = 0;
        let woman = 0;
        let team = 0;
        for(let item of response){
          if(item.sex === 1){
            man ++;
          }
          else if(item.sex === 2){
            team ++;
          } else {
            woman ++;
          }
        };
        this.singerSex.rows[0]['总数'] = man;
        this.singerSex.rows[1]['总数'] = woman;
        this.singerSex.rows[2]['总数'] = team;
      });
      allSongListApi().then(response => {
        this.songlistCount = response.length;
        response.forEach(item => {
          this.divideSongListByStyle(item.style);
        })
      });
      searchAllSongs().then(response => {
        this.songCount = response.length;
      });
    },
  // 筛选歌单方法
  divideSongListByStyle(style){
    for (let item of this.songStyle.rows){
      if (style.includes(item['风格'])){
        item['总数'] ++;
      }
    }
  }

  }
}
</script>
<style scoped>
  .homePage {
    position: absolute;
    width: 100%;
    height: 100%;
    /* background-image: url("../assets/img/background12.jpg");
    background-size: cover; */
  }
  .el-card {
    display: flex;
    height: 200px;
    /* 内容居中 */
    align-items: center;
    background-color: rgb(10, 230, 238);
  }
  .el-card >>> .el-card__body {
    width: 100%;
  }
  .number {
    width: 100%;
    font-size: 45px;
    font-weight: bold;
    text-align: center;
  }
  .cardName {
    margin-top: 10px;
    font-size: 25px;
    text-align: center;
  }
  .graphics {
    margin-top: 130px;
  }
  .title{
    width: 100%;
    text-align: center;
    margin-bottom: 80px;
  }
</style>

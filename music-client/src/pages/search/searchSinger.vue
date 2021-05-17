<template>
<div class="search-songs">
  <div class="content">
      <ul>
        <li>
          <div class="song-item">
            <span class="item-index"></span>
            <span class="item-name" style="padding-left:30px;">歌手</span>
            <span class="item-title">性别</span>
            <span class="item-intro">地区</span>
          </div>
        </li>
        <li v-for="(item,index) in singerList" :key="index">
          <div class="song-item">
            <span class="item-index">{{index+1}}</span>
            <span class="item-name" @click="goSingerDetail(item)">
              <i class="el-icon-mic"></i>
              {{item.name}}
              </span>
            <span class="item-title">{{changeSex(item.sex)}}</span>
            <span class="item-intro">{{item.location}}</span>
          </div>
        </li>
      </ul>
    </div>
</div>
</template>
<script>
import { searchSingerByNameApi } from "../../api/index"
import {mapGetters} from "vuex"
import {tool} from "../../mixins/tool"
export default {
  mixins: [tool],
data() {
  return {
    singerList: [],

  }
},
mounted() {
  this.getSearchSingers();
},
computed: {
  ...mapGetters([
    'isSearch'
  ])
},
methods: {
  getSearchSingers(){
    if(!this.$route.query.keyWords){
      this.$message.warning("请输入歌手名字");
    } else {
      searchSingerByNameApi(this.$route.query.keyWords).then(response => {
        if(!response.length){
          this.$message.success("暂无数据");
        } else {
          this.singerList = response;
        }
      }).catch(err => {
        console.log("查询歌手列表出错");
      })
    }
  },
// 转向歌手详情页
  goSingerDetail(item){
    this.$store.commit('setTempList',item);
    this.$router.push({path:'/singerDetail',query:{singerID:item.id}});
  }
},
watch:{
  // 通过监控isSearch变量，来调用查询方法。
  isSearch: function(){
    this.getSearchSingers();
  }
}
}
</script>
<style lang="scss" scoped>
  @import "../../assets/css/search-songs.scss";
  @import "../../assets/css/album-content.scss"
</style>

<template>
<div class="search-songs">
  <song-list :songList="songList"></song-list>
</div>
</template>
<script>
import { searchSongLikeName } from "../../api/index"
import {mapGetters} from "vuex"
import SongList from "../../components/songlist"
export default {
data() {
  return {
    songList: [],

  }
},
components: {
  SongList
},
mounted() {
  this.getSearchSong();
},
computed: {
  ...mapGetters([
    'isSearch'
  ])
},
methods: {
  getSearchSong(){
    if(!this.$route.query.keyWords){
      this.$message.warning("请输入搜索内容");
    } else {
      searchSongLikeName(this.$route.query.keyWords).then(response => {
        if(!response.length){
          this.$message.success("暂无数据");
        } else {
          this.songList = response;
          this.$store.commit('setListOfSongs',response);
        }
      }).catch(err => {
        console.log("查询歌曲列表出错");
      })
    }
  },
},
watch:{
  // 通过监控isSearch变量，来调用查询方法。
  isSearch: function(){
    this.getSearchSong();
  }
}
}
</script>
<style lang="scss" scoped>
  @import "../../assets/css/search-songs.scss";
</style>

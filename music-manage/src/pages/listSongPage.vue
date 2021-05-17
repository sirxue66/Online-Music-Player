<template>
  <div class="table">
    <div class="container">
      <div style="height:15px;line-height:15px;margin-bottom:10px;">
        <span>
          <i class="el-icon-headset"></i>
          {{songListName}}--->歌曲库
        </span>
      </div>
      <div class="handle-box" style="margin-bottom:10px;">
        <el-button type="primary" size="mini" @click="addSong">添加歌曲</el-button>
        <el-input v-model="searchWords" size="mini" placeholder="请输入歌曲名字" class="searchName"></el-input>
        <el-button size="mini" type="primary" @click="deleteAll" style="float:right;">批量删除</el-button>
      </div>
    </div>
    <!-- 歌曲列表 -->
    <el-table
      size="mini"
      border
      style="width:100%;height:730px;overflow-y:auto;"
      :data="data"
      @selection-change="deleteSelectOptions"
    >
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="歌手-歌名" prop="name" width="200" align="center"></el-table-column>
      <el-table-column label="专辑" prop="introduction" width="200" align="center"></el-table-column>
      <el-table-column label="操作" align="right">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="deleteSong(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页footer -->
    <div class="pageFooter">
      <el-pagination
        background
        :current-page="nowPage"
        :page-size="pageSize"
        layout="total,prev, pager, next"
        :total="songs.length"
        @current-change="handlePage"
      ></el-pagination>
    </div>

    <!-- 添加歌曲的弹框 -->
    <el-dialog title="添加歌曲" :visible.sync="songDialog" width="700px" class="songList" center>
      <div><el-button type="primary" @click="addSelectSongs">批量添加</el-button></div>
      <el-table
      size="mini"
      border
      style="width:100%;height:500px;overflow-y:auto;"
      :data="songForm"
      @selection-change="selectSongs"
    >
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column label="歌手-歌名" prop="name" width="200" align="center"></el-table-column>
      <el-table-column label="专辑" prop="introduction" width="200" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="addSongTo(scope.row.id)">添加歌曲</el-button>
        </template>
      </el-table-column>
    </el-table>
    </el-dialog>
  </div>
</template>
<script>
import {
  listSongDetailApi,
  searchSongByIdApi,
  deleteListSong,
  searchAllSongs,
  addListSong
} from "../api/index.js";
export default {
  data() {
    return {
      // 接收前边页面传来的歌手id和歌手name
      songListId: "",
      songListName: "",
      songDialog: false,
      editSong: false,
      songForm: [],  //查询出来的所有歌曲
      songs: [],
      // 查询备用singers
      songsOne: [],
      searchWords: "",
      // 分页
      pageSize: 10,
      nowPage: 1,
      // 多选删除项
      selectoptions: [],
      // 多选添加项
      selectAddSongs: []
    };
  },
  computed: {
    // 一次性拿到列表数据，在前端计算实现分页功能,使用computed 中的data方法，view中直接绑定data 就可以了
    data() {
      return this.songs.slice(
        (this.nowPage - 1) * this.pageSize,
        this.nowPage * this.pageSize
      );
    }
  },
  created() {
    this.songListId = this.$route.query.id;
    this.songListName = this.$route.query.name;
    this.allData();
  },
  methods: {
    // 显示歌曲
    allData() {
      this.songs = [];
      this.songsOne = [];
      listSongDetailApi(this.songListId).then(response => {
        for(let item of response){
          this.getSongBySingerId(item.songId);
          // console.log("songID");
        }
        return this.songs;
        return this.songsOne;
      });
    },
    // 根据查出来的歌曲id查询歌曲
    getSongBySingerId(songId){
      searchSongByIdApi(songId).then(response => {
        // console.log("歌曲");
        this.songs.push(response);
        this.songsOne.push(response);
      })
    },
    // 分页函数
    handlePage(pageNum) {
      this.nowPage = pageNum;
    },
    // 获取所有歌曲列表
    getAllSongList(){
      searchAllSongs().then(response => {
        this.songForm = response;
      })
    },
    // 添加歌曲
    addSong() {
      this.songDialog = true;
      this.getAllSongList();
    },
    //执行添加一个歌曲操作
    addSongTo(id){
      let params = new URLSearchParams();
      params.append("songId", id);
      params.append("songListId", this.songListId);
      addListSong(params).then(response => {
        if (response.code === 1) {
          this.allData();
          this.$message.success(response.infoMsg);
        } else {
          this.$message.error(response.infoMsg);
        }
      })
    },
    // 批量添加歌曲
    selectSongs(selection){
      this.selectAddSongs = [];
      this.selectAddSongs = selection; //将选择项添加入数组中
      // console.log(this.selectAddSongs);
    },
    // 执行批量添加操作
// TODO   批量删除后逻辑正确，页面需要刷新才能显示正确数据，问题待解决，包括批量删除和批量添加；
    addSelectSongs(){
      this.selectAddSongs.forEach(item => {
        this.addSongTo(item.id);
      });
      this.selectAddSongs = [];
      this.$router.go(0);
      this.$message.success("添加成功");
    },

    // 删除单个歌曲
    deleteSong(songId) {
      this.$confirm("确定删除该歌曲吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteOne(songId);
        })
        .catch(() => {});
    },
    // 删除方法
    deleteOne(songId) {
      deleteListSong(songId,this.songListId).then(response => {
        if (response.code === 1) {
          this.allData();
          this.$message.success(response.infoMsg);
        } else {
          this.$message.error(response.infoMsg);
        }
      });
    },
    // 多选删除项
    deleteSelectOptions(select){
      this.selectoptions = [];
      this.selectoptions = select;
    },
    // 执行多项删除操作
    deleteAll(){
      // console.log(this.selectoptions);
      this.selectoptions.forEach(item => {
        this.deleteOne(item.id);
      });
      // this.allData();
      this.$router.go(0);
      this.selectoptions = [];
    },

  },
  watch: {
    searchWords: function() {
      this.songs = [];
      if (this.searchWords === "") {
        this.songs = this.songsOne;
      } else {
        this.songsOne.forEach(item => {
          if (item.name.includes(this.searchWords)) {
            this.songs.push(item);
          }
        });
      }
      return this.songs;
    }
  }
};
</script>
<style scoped>
.songpic {
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow: hidden;
}
.el-table::before {
  height: 0;
}
.searchName {
  width: 300px;
  display: inline-block;
}
.pageFooter {
  /* 灵活布局 */
  display: flex;
  /* 内容居中 */
  justify-content: center;
}
.play {
  position: absolute;
  z-index: 100;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  top: 15px;
  left: 15px;
}
.icon {
  width: 2em;
  height: 2em;
  color: rgb(255, 255, 255);
  fill: currentColor;
  overflow: hidden;
}
.songList{
  height: 800px;
  overflow: auto;
}
</style>

<template>
  <div class="table">
    <div class="container">
      <div style="height:15px;line-height:15px;margin-bottom:10px;">
        <span>
          <i class="el-icon-headset"></i>
          {{singerName}}---歌曲管理
        </span>
      </div>
      <div class="handle-box" style="margin-bottom:10px;">
        <el-button type="primary" size="mini" @click="songDialog = true">添加歌曲</el-button>
        <el-input v-model="searchWords" size="mini" placeholder="请输入歌曲名字" class="searchName"></el-input>
        <el-button size="mini" type="primary" @click="deleteAll" style="float:right;">批量删除</el-button>
      </div>
    </div>
    <!-- 歌曲列表 -->
    <el-table
      size="mini"
      border
      style="width:100%;height:750px;overflow-y:auto;"
      :data="data"
      @selection-change="deleteSelectOptions"
    >
      <el-table-column type="selection" width="30"></el-table-column>
      <el-table-column label="歌曲图片" width="120" align="center">
        <template slot-scope="scope">
          <div class="songpic">
            <img :src="getUrl(scope.row.pic)" style="width:100%;margin-left:20x;" />
          </div>
          <!-- 控制播放的svg -->
          <div class="play" @click="isSongPlay(scope.row.url,scope.row.name)">
            <div v-if="toggle == scope.row.name">
              <svg class="icon">
                <use xlink:href="#icon-zanting" />
              </svg>
            </div>
            <div v-if="toggle != scope.row.name">
              <svg class="icon">
                <use xlink:href="#icon-bofanganniu" />
              </svg>
            </div>
          </div>
          <!-- 上传按钮 -->
          <el-upload
            :action="uploadUrl(scope.row.id)"
            :before-upload="beforeUpload"
            :on-success="uploadSuccess"
          >
            <el-button type="primary" size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="歌手-歌名" prop="name" width="120" align="center"></el-table-column>
      <el-table-column label="专辑" prop="introduction" width="120" align="center"></el-table-column>
      <el-table-column label="歌词" align="center">
        <template slot-scope="scope">
          <ul style="height:100px;overflow:auto;">
            <li v-for="(item,index) in handleLyric(scope.row.lyric)" :key="index">{{item}}</li>
          </ul>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleSong(scope.row)">编辑</el-button>
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
    <el-dialog title="添加歌曲" :visible.sync="songDialog" width="500px" center>
      <el-form :model="songForm" ref="songForm" label-width="80px" action id="addtable">
        <div>
          <label>歌名</label>
          <el-input v-model="songForm.name" type="text" name="name"></el-input>
        </div>
        <div>
          <label>专辑</label>
          <el-input v-model="songForm.introduction" type="text" name="introduction"></el-input>
        </div>
        <div>
          <label>歌词</label>
          <el-input v-model="songForm.lyric" type="textarea" name="lyric"></el-input>
        </div>
        <div>
          <label>上传MP3文件</label>
          <input type="file" name="file" />
        </div>
      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="songDialog = false">取消</el-button>
        <el-button type="primary" size="mini" @click="addSong()">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑歌曲的弹框 -->
    <el-dialog title="编辑歌曲" :visible.sync="editSong" width="500px" center>
      <el-form :model="editForm" ref="editForm" label-width="80px">
        <el-form-item prop="name" label="歌手-歌名" size="mini">
          <el-input v-model="editForm.name" placeholder="歌手-歌名"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="专辑" size="mini">
          <el-input v-model="editForm.introduction" placeholder="专辑"></el-input>
        </el-form-item>
        <el-form-item prop="lyric" label="歌词" size="mini">
          <el-input v-model="editForm.lyric" placeholder="歌词" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="editSong = false">取消</el-button>
        <el-button type="primary" size="mini" @click="saveSong">确定</el-button>
      </span>
    </el-dialog>
    <song-audio></song-audio>
  </div>
</template>
<script>
import { mapState, mapGetters } from "vuex";
import { tool } from "../tools/index";
import "../assets/js/iconfont";
import SongAudio from "./songAudio"
import {
  searchSongBySingerId,
  updateSongApi,
  deleteSongApi
} from "../api/index.js";
export default {
  // vue中声明混入复用代码的方法 mixins
  mixins: [tool],
  components: {
    SongAudio
  },
  data() {
    return {
      // 接收前边页面传来的歌手id和歌手name
      singerId: "",
      singerName: "",
      toggle: "", //控制播放按钮形态
      songDialog: false,
      editSong: false,
      songForm: {
        name: "",
        // singerName: "",
        introduction: "",
        lyric: ""
      },
      editForm: {
        id: "",
        name: "",
        introduction: "",
        lyric: ""
      },
      songs: [],
      // 查询备用singers
      songsOne: [],
      searchWords: "",
      // 分页
      pageSize: 10,
      nowPage: 1,
      // 多选删除项
      selectoptions: [],
      // 为了重新获取state，定义的变量
      num: 0
    };
  },
  computed: {
    ...mapState([
      // 控制歌曲播放的变量
      "ipPlay"
    ]),
    // 一次性拿到列表数据，在前端计算实现分页功能,使用computed 中的data方法，view中直接绑定data 就可以了
    data() {
      return this.songs.slice(
        (this.nowPage - 1) * this.pageSize,
        this.nowPage * this.pageSize
      );
    }
  },
  created() {
    this.singerId = this.$route.query.id;
    this.singerName = this.$route.query.name;
    this.allData();
  },
  destroyed() {
    // 离开该页面执行将音乐控制变量设为false
    this.$store.commit("setIsPlay", false);
  },
  methods: {
    // 显示歌曲
    allData() {
      this.songs = [];
      this.songsOne = [];
      searchSongBySingerId(this.singerId).then(response => {
        this.songs = response;
        this.songsOne = response;
      });
    },
    // 分页函数
    handlePage(pageNum) {
      this.nowPage = pageNum;
    },
    // 添加歌曲
    addSong() {
      let _this = this;
      var form = new FormData(document.getElementById("addtable"));
      form.append("singerId", this.singerId);
      form.set("name", this.singerName + "-" + form.get("name"));
      if (!form.get("lyric")) {
        // 方便后面歌词读取
        form.set("lyric", "[00:00:00]暂无歌词");
      }
      // XMLHttpRequest 对象用于在后台与服务器交换数据,异步操作
      var req = new XMLHttpRequest();
      req.onreadystatechange = function() {
        //req.readyState == 4 获取到返回的完整数据
        //req.status == 200 和后台正常交互完成
        if (req.readyState == 4 && req.status == 200) {
          let res = JSON.parse(req.response);
          if (res.code) {
            // _this.getData();
            _this.songForm = {};
            _this.$message.success(res.infoMsg);
          } else {
            _this.$message.error(res.infoMsg);
          }
        }
      };
      req.open("post", `${_this.$store.state.HOST}/songs/add`, false); //false代表异步请求
      req.send(form);
      this.songDialog = false;
    },
    // 编辑歌曲信息
    handleSong(row) {
      this.editSong = true;
      this.editForm = {
        id: row.id,
        name: row.name,
        introduction: row.introduction,
        lyric: row.lyric
      };
    },
    // 保存歌曲信息
    saveSong() {
      let params = new URLSearchParams();
      params.append("id", this.editForm.id);
      params.append("name", this.editForm.name);
      params.append("introduction", this.editForm.introduction);
      params.append("lyric", this.editForm.lyric);
      updateSongApi(params)
        .then(response => {
          if (response.code === 1) {
            this.allData();
            this.$message.success(response.infoMsg);
          } else {
            this.$message.error(response.infoMsg);
          }
        })
        .catch(error => {
          console.log(error);
        });
      this.editSong = false;
    },
    // 更新头像
    uploadUrl(id) {
      return `${this.$store.state.HOST}/songs/updateSongPic?id=${id}`;
    },
    // 删除单个歌曲
    deleteSong(songId) {
      this.$confirm("确定删除该歌曲吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteOne();
        })
        .catch(() => {});
    },
    // 删除方法
    deleteOne(songId) {
      deleteSongApi(songId).then(response => {
        if (response.code === 1) {
          this.allData();
          this.$message.success(response.infoMsg);
        } else {
          this.$message.error(response.infoMsg);
        }
      });
    },
    // 控制歌曲播放
    // TODO   vuex改变state中的数据后，当前页面获取不到最新的state问题
    isSongPlay(url, name) {
      this.toggle = name;
      this.$store.commit("setUrl", this.$store.state.HOST + url);
      if (this.isPlay) {
        this.num = this.num+1
        // this.isPlay = false;
        this.$store.commit("setIsPlay", false);
        // console.log("点击");
      } else {
        this.num = this.num+1
        // this.isPlay = true;
        this.$store.commit("setIsPlay", true);
      }
    }
  },
  watch: {
    num: function(){
      this.isPlay = this.$store.state.isPlay;
    },
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
</style>

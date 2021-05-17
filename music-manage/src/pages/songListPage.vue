<template>
  <div class="table">
    <div class="container">
      <div class="handle-box" style="margin-bottom:10px;">
        <el-button type="primary" size="mini" @click="songListDialog = true">添加歌单</el-button>
        <el-input v-model="searchWords" size="mini" placeholder="请输入关键字" class="searchName"></el-input>
        <el-button size="mini" type="primary" @click="deleteAll" style="float:right;">批量删除</el-button>
      </div>
    </div>
    <!-- 歌手列表 -->
    <el-table size="mini" border style="width:100%;height:750px;overflow-y:auto;" :data="data" @selection-change = "deleteSelectOptions">
      <el-table-column type="selection" width="30"></el-table-column>
      <el-table-column label="歌单图片" width="120" align="center">
        <template slot-scope="scope">
          <div class="singerpic">
            <img :src="getUrl(scope.row.pic)" style="width:100%;margin-left:20x;" />
          </div>
          <el-upload
            :action="uploadUrl(scope.row.id)"
            :before-upload="beforeUpload"
            :on-success="uploadSuccess"
          >
            <el-button type="primary" size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column label="歌单名" prop="title" width="120" align="center"></el-table-column>
      <el-table-column label="风格" prop="style" align="center" width="120"></el-table-column>
      <el-table-column label="简介" prop="introduction" align="center" type="text"></el-table-column>
      <el-table-column label="歌曲管理" align="center" width="120">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="songsView(scope.row.id,scope.row.title)">歌曲管理</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleSongList(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="deleteSongList(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页footer -->
    <div class="pageFooter">
      <el-pagination background :current-page="nowPage" :page-size="pageSize"
      layout="total,prev, pager, next" :total="songList.length" @current-change="handlePage"></el-pagination>
    </div>
    <!-- 添加歌手的弹框 -->
    <el-dialog title="添加歌单" :visible.sync="songListDialog" width="500px" center>
      <el-form :model="songListForm" ref="songListForm" label-width="80px">
        <el-form-item prop="title" label="歌单名" size="mini">
          <el-input v-model="songListForm.title" placeholder="歌单名"></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="songListForm.style" placeholder="风格"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="歌单简介" size="mini">
          <el-input v-model="songListForm.introduction" placeholder="歌手简介" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="songListDialog = false">取消</el-button>
        <el-button type="primary" size="mini" @click="addSongList">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑歌手的弹框 -->
    <el-dialog title="编辑歌单" :visible.sync="editSongList" width="500px" center>
      <el-form :model="editForm" ref="editForm" label-width="80px">
        <el-form-item prop="title" label="歌单名" size="mini">
          <el-input v-model="editForm.title" placeholder="歌单名"></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="editForm.style" placeholder="风格"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="歌单简介" size="mini">
          <el-input v-model="editForm.introduction" placeholder="歌单简介" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="editSongList = false">取消</el-button>
        <el-button type="primary" size="mini" @click="saveSongList">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { tool } from "../tools/index";
import { allSongListApi, addSongListApi, editSongListApi, deleteSongListApi } from "../api/index"
export default {
  // vue中声明混入复用代码的方法 mixins
  mixins: [tool],
  data() {
    return {
      songListDialog: false,
      editSongList:false,
      songListForm: {
        title: "",
        style: "",
        introduction: ""
      },
      editForm: {
        id: "",
        name: "",
        style: "",
        introduction: ""
      },
      songList: [],

      songListOne: [],
      searchWords: "",
      // 分页
      pageSize: 10,
      nowPage: 1,
      // 多选删除项
      selectoptions: []
    };
  },
  computed: {
    // 一次性拿到列表数据，在前端计算实现分页功能,使用computed 中的data方法，view中直接绑定data 就可以了
    data() {
      return this.songList.slice(
        (this.nowPage - 1) * this.pageSize,
        this.nowPage * this.pageSize
      );
    }
  },
  created() {
    this.allData();
  },
  methods: {
    // 显示所有歌单
    allData() {
      this.songList = [];
      this.songListOne = [];
      allSongListApi().then(response => {
        this.songList = response;
        this.songListOne = response;
      });
    },
    // 分页函数
    handlePage(pageNum){
      this.nowPage = pageNum;
    },
    // 添加歌单
    addSongList() {
      let params = new URLSearchParams();
      params.append("title", this.songListForm.title);
      // 默认歌单头像
      params.append("pic", "/img/songListPic/songList.png");
      params.append("style", this.songListForm.style);
      params.append("introduction", this.songListForm.introduction);
      addSongListApi(params)
        .then(response => {
          if (response.code === 1) {
            this.allData();
            this.$message.success(response.infoMsg);
            this.songListForm = {};
          } else {
            this.$message.error(response.infoMsg);
          }
        })
        .catch(error => {
          console.log(error);
        });
      this.songListDialog = false;
    },
    // 编辑歌单信息
    handleSongList(row){
      this.editSongList = true;
      this.editForm = {
        id: row.id,
        title: row.title,
        style: row.style,
        introduction: row.introduction
      };
    },
    // 保存歌单信息
    saveSongList(){
      let params = new URLSearchParams();
      params.append("id", this.editForm.id);
      params.append("title", this.editForm.title);
      params.append("style", this.editForm.style);
      params.append("introduction", this.editForm.introduction);
      editSongListApi(params)
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
      this.editSongList = false;
    },
    // 更新头像
    uploadUrl(id) {
      return `${this.$store.state.HOST}/songList/updateSongListPic?id=${id}`;
    },
    // 删除单个歌单
    deleteSongList(songListId){
      this.$confirm('确定删除该歌单吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteOne(songListId);
         }).catch(() => {});
    },
    // 删除方法
    deleteOne(songListId){
       deleteSongListApi(songListId)
      .then(response => {
          if (response.code === 1) {
            this.allData();
            this.$message.success(response.infoMsg);
          } else {
            this.$message.error(response.infoMsg);
          }
        });
    },
    // 转向歌曲管理
    songsView(id,name){
      this.$router.push({path:"/listsong",query:{id,name}});
    }
  },
  watch: {
    searchWords: function() {
      this.songList = [];
      if (this.searchWords === "") {
        this.songList = this.songListOne;
      } else {
        this.songListOne.forEach(item => {
          if (item.title.includes(this.searchWords)) {
            this.songList.push(item);
          }
        });
      }
      return this.songList;
    }
  }
};
</script>
<style scoped>
.singerpic {
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
</style>


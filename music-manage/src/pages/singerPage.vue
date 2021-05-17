<template>
  <div class="table">
    <div class="container">
      <div class="handle-box" style="margin-bottom:10px;">
        <el-button type="primary" size="mini" @click="singerDialog = true">添加歌手</el-button>
        <el-input v-model="searchWords" size="mini" placeholder="请输入歌手名字" class="searchName"></el-input>
        <el-button size="mini" type="primary" @click="deleteAll" style="float:right;">批量删除</el-button>
      </div>
    </div>
    <!-- 歌手列表 -->
    <el-table size="mini" border style="width:100%;height:750px;overflow-y:auto;" :data="data" @selection-change = "deleteSelectOptions">
      <el-table-column type="selection" width="30"></el-table-column>
      <el-table-column label="歌手图片" width="120" align="center">
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
      <el-table-column label="歌手" prop="name" width="120" align="center"></el-table-column>
      <el-table-column label="性别" prop="sex" width="120" align="center">
        <template slot-scope="scope">{{changeSex(scope.row.sex)}}</template>
      </el-table-column>
      <el-table-column label="生日" prop="birth" width="120" align="center">
        <template slot-scope="scope">{{changeBirth(scope.row.birth)}}</template>
      </el-table-column>
      <el-table-column label="地区" prop="location" width="120" align="center"></el-table-column>
      <el-table-column label="简介" prop="introduction" align="center"></el-table-column>
      <el-table-column label="歌曲管理" align="center" width="120">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="songsView(scope.row.id,scope.row.name)">歌曲管理</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleSinger(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="deleteSinger(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页footer -->
    <div class="pageFooter">
      <el-pagination background :current-page="nowPage" :page-size="pageSize"
      layout="total,prev, pager, next" :total="singers.length" @current-change="handlePage"></el-pagination>
    </div>
    <!-- 添加歌手的弹框 -->
    <el-dialog title="添加歌手" :visible.sync="singerDialog" width="500px" center>
      <el-form :model="singerForm" ref="singerForm" label-width="80px">
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-input v-model="singerForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="singerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="singerForm.birth"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="singerForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="歌手简介" size="mini">
          <el-input v-model="singerForm.introduction" placeholder="歌手简介" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="singerDialog = false">取消</el-button>
        <el-button type="primary" size="mini" @click="addSinger()">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑歌手的弹框 -->
    <el-dialog title="编辑歌手" :visible.sync="editSinger" width="500px" center>
      <el-form :model="editForm" ref="editForm" label-width="80px">
        <el-form-item prop="name" label="歌手名" size="mini">
          <el-input v-model="editForm.name" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="editForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">组合</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="editForm.birth"
            style="width:100%"
          ></el-date-picker>
          <!-- <el-input v-model="editForm.birth" placeholder="生日"></el-input> -->
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="editForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="歌手简介" size="mini">
          <el-input v-model="editForm.introduction" placeholder="歌手简介" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="editSinger = false">取消</el-button>
        <el-button type="primary" size="mini" @click="saveSinger">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import {
  addSingerApi,
  allSingerApi,
  editSingerApi,
  deleteSingerApi
} from "../api/index";
import { tool } from "../tools/index";
export default {
  // vue中声明混入复用代码的方法 mixins
  mixins: [tool],
  data() {
    return {
      singerDialog: false,
      editSinger:false,
      singerForm: {
        name: "",
        sex: "",
        birth: "",
        location: "",
        introduction: ""
      },
      editForm: {
        id: "",
        name: "",
        sex: "",
        birth: "",
        location: "",
        introduction: ""
      },
      singers: [],
      // 查询备用singers
      singersOne: [],
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
      return this.singers.slice(
        (this.nowPage - 1) * this.pageSize,
        this.nowPage * this.pageSize
      );
    }
  },
  created() {
    this.allData();
  },
  methods: {
    // 显示所有歌手
    allData() {
      this.singers = [];
      this.singersOne = [];
      allSingerApi().then(response => {
        this.singers = response;
        this.singersOne = response;
      });
    },
    // 分页函数
    handlePage(pageNum){
      this.nowPage = pageNum;
    },
    // 添加歌手
    addSinger() {
      // 转换时间格式
      let birthDate = this.singerForm.birth;
      let birthTime =
        birthDate.getFullYear() +
        "-" +
        (birthDate.getMonth() + 1) +
        "-" +
        birthDate.getDate();
      let params = new URLSearchParams();
      params.append("name", this.singerForm.name);
      params.append("sex", this.singerForm.sex);
      // 默认歌手头像
      params.append("pic", "/img/singerPic/singer.jpg");
      params.append("birth", birthTime);
      params.append("location", this.singerForm.location);
      params.append("introduction", this.singerForm.introduction);
      addSingerApi(params)
        .then(response => {
          if (response.code === 1) {
            this.allData();
            this.$message.success(response.infoMsg);
            this.singerForm = {};
          } else {
            this.$message.error(response.infoMsg);
          }
        })
        .catch(error => {
          console.log(error);
        });
      this.singerDialog = false;
    },
    // 编辑歌手信息
    handleSinger(row){
      this.editSinger = true;
      this.editForm = {
        id: row.id,
        name: row.name,
        sex: row.sex,
        birth: this.changeBirth(row.birth),
        location: row.location,
        introduction: row.introduction
      };
    },
    // 保存歌手信息
    saveSinger(){
      let birthDate = new Date(this.editForm.birth);
      let birthTime = birthDate.getFullYear() + "-" + (birthDate.getMonth() + 1) + "-" + birthDate.getDate();
      let params = new URLSearchParams();
      params.append("id", this.editForm.id);
      params.append("name", this.editForm.name);
      params.append("sex", this.editForm.sex);
      params.append("birth", birthTime);
      params.append("location", this.editForm.location);
      params.append("introduction", this.editForm.introduction);
      editSingerApi(params)
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
      this.editSinger = false;
    },
    // 更新头像
    uploadUrl(id) {
      return `${this.$store.state.HOST}/singer/updateSingerPic?id=${id}`;
    },
    // 删除单个歌手
    deleteSinger(singerId){
      this.$confirm('确定删除该歌手吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteOne(singerId);
         }).catch(() => {});
    },
    // 删除方法
    deleteOne(singerId){
       deleteSingerApi(singerId)
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
      this.$router.push({path:"/songs",query:{id,name}});
    }
  },
  watch: {
    searchWords: function() {
      this.singers = [];
      if (this.searchWords === "") {
        this.singers = this.singersOne;
      } else {
        // for(let item of this.singersOne){
        //   if(item.name.includes(this.searchWords)){
        //     this.singers.push(item);
        //   }
        // }
        this.singersOne.forEach(item => {
          if (item.name.includes(this.searchWords)) {
            this.singers.push(item);
          }
        });
      }
      return this.singers;
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

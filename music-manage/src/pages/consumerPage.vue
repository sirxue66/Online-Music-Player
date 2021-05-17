<template>
  <div class="table">
    <div class="container">
      <div class="handle-box" style="margin-bottom:10px;">
        <el-button type="primary" size="mini" @click="consumerDialog = true">添加用户</el-button>
        <el-input v-model="searchWords" size="mini" placeholder="请输入用户名" class="searchName"></el-input>
        <el-button size="mini" type="primary" @click="deleteAll" style="float:right;">批量删除</el-button>
      </div>
    </div>
    <!-- 用户列表 -->
    <el-table size="mini" border style="width:100%;height:750px;overflow-y:auto;" :data="data" @selection-change = "deleteSelectOptions">
      <el-table-column type="selection" width="30"></el-table-column>
      <el-table-column label="用户头像" width="120" align="center">
        <template slot-scope="scope">
          <div class="consumerPic">
            <img :src="getUrl(scope.row.avator)" style="width:100%;margin-left:20x;" />
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
      <el-table-column label="用户名" prop="username" width="120" align="center"></el-table-column>
      <el-table-column label="性别" prop="sex" width="120" align="center">
        <template slot-scope="scope">{{changeSex(scope.row.sex)}}</template>
      </el-table-column>
      <el-table-column label="生日" prop="birth" width="120" align="center">
        <template slot-scope="scope">{{changeBirth(scope.row.birth)}}</template>
      </el-table-column>
      <el-table-column label="手机号" prop="phoneNum" align="center" width="120"></el-table-column>
      <el-table-column label="邮箱" prop="email" align="center" width="120"></el-table-column>
      <el-table-column label="地区" prop="location" width="120" align="center"></el-table-column>
      <el-table-column label="签名" prop="introduction" align="center"></el-table-column>
      <el-table-column label="操作" align="center" width="160">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleConsumer(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="deleteConsumer(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页footer -->
    <div class="pageFooter">
      <el-pagination background :current-page="nowPage" :page-size="pageSize"
      layout="total,prev, pager, next" :total="consumers.length" @current-change="handlePage"></el-pagination>
    </div>
    <!-- 添加用户的弹框 -->
    <el-dialog title="添加用户" :visible.sync="consumerDialog" width="500px" center>
      <el-form :model="consumerForm" ref="consumerForm" :rules="rules" label-width="80px">
        <el-form-item prop="username" label="用户名" size="mini">
          <el-input v-model="consumerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码" size="mini">
          <el-input v-model="consumerForm.password" placeholder="密码" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="consumerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="consumerForm.birth"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input v-model="consumerForm.phoneNum" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱" size="mini">
          <el-input v-model="consumerForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="consumerForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="个人签名" size="mini">
          <el-input v-model="consumerForm.introduction" placeholder="个人签名" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="consumerDialog = false">取消</el-button>
        <el-button type="primary" size="mini" @click="addConsumer">确定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑用户的弹框 -->
    <el-dialog title="编辑用户" :visible.sync="editConsumer" width="500px" center>
      <el-form :model="editForm" ref="editForm" :rules="rules" label-width="80px">
        <el-form-item prop="username" label="用户名" size="mini">
          <el-input v-model="editForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别" size="mini">
          <el-radio-group v-model="editForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="birth" label="生日" size="mini">
          <el-date-picker
            type="date"
            placeholder="选择日期"
            v-model="editForm.birth"
            style="width:100%"
          ></el-date-picker>
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机号" size="mini">
          <el-input v-model="editForm.phoneNum" placeholder="手机号"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱" size="mini">
          <el-input v-model="editForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区" size="mini">
          <el-input v-model="editForm.location" placeholder="地区"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="个人签名" size="mini">
          <el-input v-model="editForm.introduction" placeholder="个人签名" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" size="mini" @click="editConsumer = false">取消</el-button>
        <el-button type="primary" size="mini" @click="saveConsumer">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { tool } from "../tools/index";
import { allConsumersApi, addConsumerApi, editConsumerApi, deleteConsumerApi } from "../api/index"
export default {
  mixins: [tool],
  data() {
    // const checkPassword = (rule, value, callback) => {
    //     if (!value) {
    //       return callback(new Error('年龄不能为空'));
    //     }
    // }
    return {
      consumerDialog: false,
      editConsumer:false,
      consumerForm: {
        username: "",
        password: "",
        sex: "",
        birth: "",
        phoneNum: "",
        email: "",
        location: "",
        introduction: ""
      },
      editForm: {
        id: "",
        username: "",
        sex: "",
        birth: "",
        phoneNum: "",
        email: "",
        location: "",
        introduction: ""
      },
      consumers: [],
      // 查询备用consumers
      consumersOne: [],
      searchWords: "",
      // 分页
      pageSize: 10,
      nowPage: 1,
      // 多选删除项
      selectoptions: [],

      // 校验
      rules: {
        username: [
          {required: true, message: "请输入用户名", trigger: ["blur","change"]},
          {min: 3, max: 15, message: "请输入3-15个字符", trigger: ["blur","change"]}
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {min: 3, max: 10, message: "请输入3-10位数字或字母", trigger: "blur"}
        ],
        sex: [
          {required: true, message: "请选择性别", trigger: "blur"}
        ],
        birth: [
          {required: true, message: "请选择生日", trigger: "blur"}
        ],
        phoneNum: [
          {required: true, message: "请输入手机号", trigger: ["blur","change"]}
        ],
        email: [
          {type: "email", message: "请输入正确的邮箱", trigger: "blur"}
        ]
      }
    };
  },
  computed: {
    // 一次性拿到列表数据，在前端计算实现分页功能,使用computed 中的data方法，view中直接绑定data 就可以了
    data() {
      return this.consumers.slice(
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
      this.consumers = [];
      this.consumersOne = [];
      allConsumersApi().then(response => {
        this.consumers = response;
        this.consumersOne = response;
      });
    },
    // 分页函数
    handlePage(pageNum){
      this.nowPage = pageNum;
    },
    // 添加歌手
    addConsumer() {
      // 转换时间格式
      let birthDate = this.consumerForm.birth;
      let birthTime =
        birthDate.getFullYear() +
        "-" +
        (birthDate.getMonth() + 1) +
        "-" +
        birthDate.getDate();
      let params = new URLSearchParams();
      params.append("username", this.consumerForm.username);
      params.append("password", this.consumerForm.password);
      params.append("sex", this.consumerForm.sex);
      // 默认歌手头像
      params.append("avator", "/avatorImages/girl.png");
      params.append("birth", birthTime);
      params.append("phoneNum", this.consumerForm.phoneNum);
      params.append("email", this.consumerForm.email);
      params.append("location", this.consumerForm.location);
      params.append("introduction", this.consumerForm.introduction);
      addConsumerApi(params)
        .then(response => {
          if (response.code === 1) {
            this.allData();
            this.$message.success(response.infoMsg);
            this.consumerForm = {};
          } else {
            this.$message.error(response.infoMsg);
          }
        })
        .catch(error => {
          console.log(error);
        });
      this.consumerDialog = false;
    },
    // 编辑歌手信息
    handleConsumer(row){
      this.editConsumer = true;
      this.editForm = {
        id: row.id,
        username: row.username,
        sex: row.sex,
        birth: this.changeBirth(row.birth),
        phoneNum: row.phoneNum,
        email: row.email,
        location: row.location,
        introduction: row.introduction
      };
    },
    // 保存歌手信息
    saveConsumer(){
      let birthDate = new Date(this.editForm.birth);
      let birthTime = birthDate.getFullYear() + "-" + (birthDate.getMonth() + 1) + "-" + birthDate.getDate();
      let params = new URLSearchParams();
      params.append("id", this.editForm.id);
      params.append("username", this.editForm.username);
      params.append("sex", this.editForm.sex);
      params.append("birth", birthTime);
      params.append("phoneNum", this.editForm.phoneNum);
      params.append("email", this.editForm.email);
      params.append("location", this.editForm.location);
      params.append("introduction", this.editForm.introduction);
      editConsumerApi(params)
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
      this.editConsumer = false;
    },
    // 更新头像
    uploadUrl(id) {
      return `${this.$store.state.HOST}/consumer/updateConsumerPic?id=${id}`;
    },
    // 删除单个歌手
    deleteConsumer(consumerId){
      this.$confirm('确定删除该歌手吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteOne(consumerId);
         }).catch(() => {});
    },
    // 删除方法
    deleteOne(consumerId){
       deleteConsumerApi(consumerId)
      .then(response => {
          if (response.code === 1) {
            this.allData();
            this.$message.success(response.infoMsg);
          } else {
            this.$message.error(response.infoMsg);
          }
        });
    }
},
watch: {
    searchWords: function() {
      this.consumers = [];
      if (this.searchWords === "") {
        this.consumers = this.consumersOne;
      } else {
        this.consumersOne.forEach(item => {
          if (item.username.includes(this.searchWords)) {
            this.consumers.push(item);
          }
        });
      }
      return this.consumers;
    }
  }
}
</script>
<style scoped>
.consumerPic {
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

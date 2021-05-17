<template>
  <div class="setting">
    <div class="leftCol">
    <div class="settingsMainHeader">头像</div>
    <div>
      <img class="avatorimg" :src="getImageUrl(avator)">
      <el-upload :show-file-list="false" :action="uploadUrl()" :on-success="handleAvatorSuccess" :before-upload="beforeAvatorUpload">
        <el-button style="margin-left:20px;">更新头像</el-button>
      </el-upload>
    </div>
    </div>
    <!-- 个人信息展示 -->
    <div class="contentCol">
      <div class="info">
        <div class="title"><span> 个人资料 </span>
        <el-button type="primary" size="mini" class="edit" @click="editConsumer = true">编辑资料</el-button>
        </div>
        <hr>
        <div class="personal">
          <span>用户名：{{userMsg.username}} </span><br><br><br>
          <span>性别：{{changeSex(userMsg.sex)}}</span><br><br><br>
          <span>生日：{{changeBirth(userMsg.birth)}}</span><br><br><br>
          <span>手机号：{{userMsg.phoneNum}}</span><br><br><br>
          <span>邮箱：{{userMsg.email}}</span><br><br><br>
          <span>地区：{{userMsg.location}}</span><br><br><br>
          <span>签名：{{userMsg.introduction}}</span><br><br><br>
        </div>
      </div>
    </div>
    <!-- 用户信息编辑框 -->
    <el-dialog title="修改信息" :visible.sync="editConsumer" :rules="rules" width="500px" center>
      <el-form :model="editForm" ref="editForm" label-width="80px">
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
import {mapGetters} from "vuex"
import {getConsumerById, updateUserMsg} from "../api/index"
import {rules} from "../assets/data/form"
import {tool} from "../mixins/tool"
export default {
  mixins: [tool],
data() {
  return {
    userMsg:{},
    editForm: {
      id: "",
      username:"",
      sex:"",
      birth:"",
      phoneNum:"",
      email:"",
      location:"",
      introduction:""
    },
    editConsumer: false,
    rules,
  }
},
computed: {
  ...mapGetters([
    "userID", "avator"
  ])
},
created() {
this.getUserMsg();
},
methods: {
  // 获取用户信息
  getUserMsg(){
    getConsumerById(this.userID).then(response => {
      this.userMsg = response;
      this.editForm = response;
      this.editForm.birth = this.changeBirth(response.birth);
    })
  },
  // 保存修改的用户信息
  saveConsumer(){
      let params = new URLSearchParams();
      params.append("id", this.editForm.id);
      params.append("username", this.editForm.username);
      params.append("password", this.editForm.password);
      params.append("sex", this.editForm.sex);
      // 默认歌手头像
      params.append("avator", "/avatorImages/girl.png");
      params.append("birth", this.editForm.birth);
      params.append("phoneNum", this.editForm.phoneNum);
      params.append("email", this.editForm.email);
      params.append("location", this.editForm.location);
      params.append("introduction", this.editForm.introduction);
      updateUserMsg(params)
        .then(response => {
          if (response.code === 1) {
            this.getUserMsg();
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
//上传地址
        uploadUrl(){
            return `${this.$store.state.Config.HOST}/consumer/updateConsumerPic?id=${this.userID}`
        },
         //上传成功
        handleAvatorSuccess(res,file){
            if(res.code == 1){
                this.$store.commit('setAvator',res.avator);
                console.log(res.avator);
                this.$message.success("更新头像成功");
            }else{
                this.$message.error("更新头像失败");
            }
        },
        //上传之前的校验
        beforeAvatorUpload(file){
            const isJPG = file.type=='image/jpeg';
            const isLt10M = file.size /1024/1024<10;
            if(!isJPG){
                this.$message.error('上传头像图片只能是JPG格式');
                return false;
            }
            if(!isLt10M){
                this.$message.error('上传头像图片不能大于10MB');
                return false;
            }
            return true;
        }
},
watch:{
  "this.editForm.bitrh": function(){
    this.editForm.birth = birthDate;
    return birthDate.getFullYear() +"-" +(birthDate.getMonth() + 1) +"-" +birthDate.getDate();
  }
}
}
</script>
<style lang="scss" scoped>
@import "../assets/css/setting.scss";
@import "../assets/css/info.scss";
.avatorimg{
  width: 150px;
  height: 150px;
  border-radius: 45%;
}
</style>

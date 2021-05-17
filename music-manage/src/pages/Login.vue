<template>
  <div class="login-wrap">
    <div class="mg-title">MusicPlayer 后台管理登录</div>
    <div class="mg-login">
      <el-form :model="loginForm" :rules="rule" ref="loginForm">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="loginForm.password" placeholder="密码" @keyup.native.enter="logIn"></el-input>
        </el-form-item>
        <div class="login-button">
        <el-button type="primary" @click="logIn">登录</el-button>
      </div>
      </el-form>
    </div>
  </div>
</template>
<script>
import { getLoginStatus } from "../api/index"
export default {
  data() {
    return {
      loginForm: {
        username: "",
        password: ""
      },
      rule: {
        username: [{required: true, message: "请输入用户名", trigger: "blur"}],
        password: [{required: true, message: "请输入登录密码", trigger: "blur"}]
      }
    }
  },
  methods: {
    logIn(){
      // URLSearchParams 接口定义了一些实用的方法来处理 URL 的查询字符串。
      let params = new URLSearchParams();
      params.append("name",this.loginForm.username);
      params.append("password",this.loginForm.password);
      getLoginStatus(params).then(response => {
        if(response.code === 1){
          // 将用户名存入localstorage中
          localStorage.setItem("userName",this.loginForm.username);
          this.$router.push("/manage");
          this.$message.success(response.infoMsg);
        } else {
          this.$message.error(response.infoMsg);
        }
      });
    }
  },
}
</script>
<style scoped>
.login-wrap {
  position: relative;
  background: url("../assets/img/manage01.jpg");
  background-attachment: fixed;
  background-position: center;
  background-size: cover;
  width: 100%;
  height: 100%;
}
.mg-title{
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -230px;
  text-align: center;
  font-size: 30px;
  font-weight: 600;
  color: #fff;
}
.mg-login{
  position: absolute;
  width: 300px;
  height: 180px;
  left: 50%;
  top: 50%;
  margin-left: -200px;
  margin-top: -120px;
  padding: 50px;
  border-radius: 5px;
  background-color: #fff;
}
.login-button{
  text-align: center;
}
.login-button button{
width: 100%;
height: 40px;
}
</style>

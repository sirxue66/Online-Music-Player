<template>
<div>
   <login-logo></login-logo>
    <div class="signUp">
        <div class="signUp-head">
            <span>用户登录</span>
        </div>
        <el-form :model="loginForm" ref="loginForm" label-width="70px" :rules="rules" class="demo-ruleForm">
            <el-form-item prop="username" label="用户名">
                <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
                <el-input type="password" v-model="loginForm.password" placeholder="密码"></el-input>
            </el-form-item>
            <div class="login-btn">
                <el-button type="primary" @click="goRegistered">注册</el-button>
                <el-button type="primary" @click="login">确定</el-button>
            </div>
        </el-form>
    </div>
</div>
</template>
<script>
import LoginLogo from '../components/loginLoGo'
import {rules} from '../assets/data/form'
import {logInApi} from '../api/index'

export default {
    components: {
        LoginLogo
    },
    data() {
        return {
            loginForm: {
                username: '',       //用户名
                password: '',       //密码
            },
            rules,
        }
    },
    methods:{
        login(){
          this.$refs.loginForm.validate(valid => {
            if(valid){
              let _this = this;
            let params = new URLSearchParams();
            params.append('username',this.loginForm.username);
            params.append('password',this.loginForm.password);
            logInApi(params)
                .then(res => {
                    if(res.code == 1){
                        this.$message.success(res.infoMsg);
                        this.$store.commit('setIsLogin',true);
                        this.$store.commit('setUserId',res.consumerMsg.id);
                        this.$store.commit('setUserName',res.consumerMsg.username);
                        this.$store.commit('setAvator',res.consumerMsg.avator);
                        setTimeout(function(){
                            _this.$router.push("/");
                        },2000);
                    }else{
                        this.$message.error(res.infoMsg);
                    }
                })
                .catch(err => {
                    this.$message.error(res.infoMsg);
                })
            } else {
              this.$message.success("请输入正确信息！");
            }
          })

        },
        // 转向注册页面
        goRegistered(){
            this.$router.push("/registered");
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/sign-up.scss';
</style>

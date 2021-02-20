<template>
    <div class="loginBox">
        <Logo class="logoBox"></Logo>
        <Title class="titleBox"></Title>
        <el-form class="formBox" :model="loginModel" label-width="80px">
            <el-input v-model="loginModel.username" placeholder="用户名"></el-input>
            <el-input v-model="loginModel.password" placeholder="密码"></el-input>
            <el-image v-show="imageCodePicSrc" :src="imageCodePicSrc"></el-image>
            <el-input v-show="imageCodePicSrc" v-model="loginModel.captcha" placeholder="验证码"></el-input>
            <el-button type="primary" @click="submitLogin">登录 <i class="fa fa-spinner fa-spin" v-show="logining"></i></el-button>
            <el-button type="primary" @click="toRegister()">注册</el-button>
        </el-form>
    </div>
</template>

<script>
    import Logo from "../../components/Logo";
    import Title from "../../components/Title";

    export default {
        name: "Login",
        components: {Title, Logo},
        data() {
            return {
                logining: false,
                loginModel: {
                    username: "",
                    password: ""
                },
                imageCodePicSrc: null
            }
        },
        methods: {
            submitLogin() {
                this.logining = true;
                this.request.axiosLogin(this, this.loginModel, (data) => {
                    this.$store.commit('setToken', data);
                    this.$router.push('/')
                    this.logining = false;
                }, (error) => {
                    this.imageCodePicSrc = error.data.data;
                    this.logining = false;
                });
            },
            toRegister() {
                this.$router.push("/login-main/register");
            }
        }
    }
</script>

<style scoped>


    .loginBox {
        text-align: center;
    }

    .logoBox {
        margin-top: 60px;
    }

    .titleBox {
        display: block;
    }

    .formBox {
        width: 300px;
        margin: 0 auto;
    }

    .el-input {
        margin-top: 4px;
    }

    .el-button {
        margin-top: 8px;
    }

    .registerBox {
        margin-left: 10px;
    }


</style>

<template>
    <div class="headerBox">
        <Logo class="logoBox"/>
        <Title class="titleBox"/>
        <div class="logoutBox" @click="logout">
            {{getCurrentUser.displayName}} | 退出登录 <i class="fa fa-sign-out"></i>
        </div>
    </div>
</template>

<script>

    import Logo from "./Logo";
    import Title from "./Title";

    export default {
        name: "Header",
        components: {Title, Logo},
        computed: {
            getCurrentUser() {
                return this.$store.getters.getCurrentUser;
            }
        },
        methods: {
            logout: function () {
                this.$store.commit('setToken', null);
                // window.location = "/view/login-main/login";
                this.$router.push('/login-main/login')
            }
        }
    }

</script>

<style scoped>
    .headerBox {
        width: 100%;
        height: 60px;
        background: #2b3642;
    }

    .logoBox {
        float: left;
        border-right: 1px solid #3d4956;
    }

    .titleBox {
        margin-left: 50px;

    }

    .logoutBox {
        line-height: 60px;
        color: white;
        font-size: 18px;
        float: right;
        margin-right: 120px;
        cursor: pointer;
    }

    .logoutBox:hover {
        color: #4fc6d0;
    }


</style>
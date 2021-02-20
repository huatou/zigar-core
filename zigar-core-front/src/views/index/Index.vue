<template>
    <div id="app">
        <Header/>
        <Menu class="menuBox"/>
        <MainContent class="mainContentBox"/>
        <Footer class="footBox"/>
    </div>
</template>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
    }

    .menuBox {
        width: 200px;
        position: absolute;
        left: 0;
        top: 60px;
        bottom: 60px;
    }

    .mainContentBox {
        position: absolute;
        left: 200px;
        right: 0;
        top: 60px;
        bottom: 60px;
    }

    .footBox {
        position: absolute;
        bottom: 0;
        left: 0;
        right: 0;
    }

</style>
<script>
    import Header from "../../components/Header";
    import Menu from "../../components/Menu";
    import MainContent from "../../components/MainContent";
    import Footer from "../../components/Footer";

    export default {
        components: {Footer, MainContent, Menu, Header},
        methods: {
            getCurrentUser() {
                this.request.axiosGetCurrentUser(this, (data) => {
                    this.$store.commit('setCurrentUser', data);
                });
            }
        },
        mounted() {
            this.getCurrentUser();
        }

    }


</script>
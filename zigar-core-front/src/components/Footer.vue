<template>
    <div class="footBox">
        <span> © 2019 - {{new Date().getFullYear()}}券码后台管理系统</span>
    </div>
</template>

<script>
    import Title from "./Title";

    export default {
        name: "Footer",
        components: {Title}
    }
</script>

<style scoped>
    .footBox {
        background: #2b3642;
        color: white;
        padding-left: 100px;
        line-height: 60px;
    }

</style>

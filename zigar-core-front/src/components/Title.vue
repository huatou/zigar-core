<template>
    <label class="title" :style="{fontSize:fontSize}">{{title}}</label>
</template>

<script>
    export default {
        name: "Title",
        props: {
            fontSize: String
        },
        data() {
            return {
                title: this.global.PROJECT_NAME
            }
        }
    }
</script>

<style scoped>
    .title {
        font-size: 28px;
        font-weight: bold;
        line-height: 60px;
        height: 60px;
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        color: white;
    }
</style>
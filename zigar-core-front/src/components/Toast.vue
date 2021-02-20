<template>
    <div class="wrap" v-if="show">
        <div>{{text}}</div>
        <div>{{temp1}}</div>
    </div>
</template>

<script>
    export default {
        data () {
            return {
                show:false,
                temp1 : "你好vbyzc"
            }
        }
    }
</script>

<style scoped>
    .wrap{
        color:#fff;
        background-color: rgba(0,0,0,0.8);
        padding: 10px;
        position: fixed;
        top:50%;left: 50%;
        border-radius: 10px;;
    }
</style>
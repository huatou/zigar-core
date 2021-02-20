<template>
    <el-dialog
            title="提示"
            :visible.sync="visible"
            width="300px"
            center>
        <span>{{msg}}</span>
        <span slot="footer" class="dialog-footer">
    <el-button @click="visible = false;">取 消</el-button>
    <el-button type="primary" @click="confirm()">确 定</el-button>
  </span>
    </el-dialog>
</template>

<script>

    export default {
        name: "DialogConfirm",
        methods: {
            confirm() {
                this.visible = false;
                this.callback && this.callback();
            }
        }
    }

</script>

<style scoped>

</style>
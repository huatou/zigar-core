<template>
    <el-dialog title="修改密码" :visible.sync="visible" v-if="row" top="10vh">
        <el-form :model="row" size="mini">
            <el-form-item label="用户" label-width="120px">
                <label style="line-height: 40px;" class="h3">{{row.displayName+'('+row.username+')'}}</label>
            </el-form-item>
            <el-form-item label="密码" label-width="120px">
                <el-input v-model="row.password" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="visible = false">取 消</el-button>
            <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import BaseDialog from "../../../components/Dialog/BaseDialog";

    export default {
        name: "DialogChangePwd",
        extends: BaseDialog,
        methods: {
            submit() {
                let postModel = {
                    userId: this.row.userId,
                    password: this.row.password
                }
                this.request.axiosChangePwd(this, postModel, () => {
                    this.close();
                });
            }
        }
    }
</script>

<style scoped>

</style>
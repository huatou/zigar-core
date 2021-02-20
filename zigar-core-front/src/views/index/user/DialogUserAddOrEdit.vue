<template>
    <el-dialog :title="title()" :visible.sync="visible" v-if="row" top="10vh">
        <el-form :model="row" size="mini">
            <el-form-item label="用户名" label-width="120px">
                <el-input v-model="row.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="姓名" label-width="120px">
                <el-input v-model="row.displayName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="密码" label-width="120px" v-if="!row.userId">
                <el-input v-model="row.password" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dismiss()">取 消</el-button>
            <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import BaseDialog from "../../../components/Dialog/BaseDialog";

    export default {
        name: "DialogUserAddOrEdit",
        extends: BaseDialog,
        data() {
            return {
                module: "user"
            }
        },
        methods: {
            submit() {
                if (this.row.userId) {
                    this.request.axiosUpdateUser(this, this.row, () => {
                        this.close();
                    });
                } else {
                    this.request.axiosInsertUser(this, this.row, () => {
                        this.close();
                    });
                }
            },
            title: function () {
                if (this.row.userId) {
                    return "编辑";
                } else {
                    return "新增";
                }
            }
        }
    }
</script>

<style scoped>

</style>
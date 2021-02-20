<template>
    <el-dialog title="新增模块" :visible.sync="visible" v-if="row" top="10vh">
        <el-form :model="row" size="mini">
            <el-form-item label="模块名称" label-width="120px">
                <el-input v-model="row.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="模块编码" label-width="120px">
                <el-input v-model="row.code" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="排序" label-width="120px">
                <el-input v-model="row.sort" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="路由地址" label-width="120px">
                <el-input v-model="row.url" autocomplete="off"></el-input>
            </el-form-item>
<!--            <el-form-item label="图标" label-width="120px">-->
<!--                <el-input v-model="row.icon" autocomplete="off" class="float-left w-25"></el-input>-->
<!--                <i :class="row.icon" class="el-button ml-2" style="height: 40px"></i>-->
<!--            </el-form-item>-->
            <el-form-item label="操作项" label-width="120px">
                <el-checkbox-group v-model="checkedActions">
                    <el-checkbox label="menu">菜单</el-checkbox>
<!--                    <el-checkbox label="add">新增</el-checkbox>-->
<!--                    <el-checkbox label="edit">修改</el-checkbox>-->
<!--                    <el-checkbox label="delete">删除</el-checkbox>-->
                </el-checkbox-group>
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
        name: "DialogModuleAddOrEdit",
        extends: BaseDialog,
        data() {
            return {
                checkedActions: []
            }
        },
        computed: {},
        methods: {
            submit() {
                this.row.actions = this.checkedActions.join(',');
                if (!this.row.moduleId) {
                    this.request.axiosInsertModule(this, this.row, () => {
                        this.close();
                    });
                } else {
                    this.request.axiosUpdateModule(this, this.row, () => {
                        this.close();
                    });
                }
            }
        },
        watch: {
            visible() {
                if (this.visible) {
                    this.checkedActions = this.row && this.row.actions ? this.row.actions.split(",") : []
                }
            }
        }
    }
</script>

<style scoped>

</style>

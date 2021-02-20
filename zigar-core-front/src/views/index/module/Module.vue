<template>
    <div class="tableMainBox">
        <div class="searchBox">
            <el-input class="inputTextBox" v-model="queryModel.keyword" clearable
                      maxlength="40" prefix-icon="el-icon-search"
                      placeholder="请输入关键字搜索" size="mini"></el-input>
            <div class="el-button el-button--primary el-button--mini ml-2 float-left" @click="showAddOrEditDialog()">
                <i class="el-icon-plus mr-2"></i>新增模块
            </div>
        </div>
        <el-table class="tableBox" :data="list" border width="100%" size="mini"
                  v-loading="tableIsLoading" default-expand-all row-key="moduleId"
                  :tree-props="{children: 'children'}" :header-cell-style="getHeaderStyle()">
            <el-table-column type="index" align="center"></el-table-column>
            <el-table-column prop="sort" label="排序" width="160px"></el-table-column>
            <el-table-column prop="name" label="模块名称" width="160px"></el-table-column>
<!--            <el-table-column label="图标" width="60px" align="center">-->
<!--                <template slot-scope="scope">-->
<!--                    <i :class="scope.row.icon"></i>-->
<!--                </template>-->
<!--            </el-table-column>-->
            <el-table-column label="地址">
                <template slot-scope="scope">
                    <router-link :to="scope.row.url">{{scope.row.url}}</router-link>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column width="360px" label="操作">
                <template slot-scope="scope">
                    <el-button type="warning" size="mini" icon="el-icon-edit-outline"
                               @click="showAddOrEditDialog(scope.row)">编辑
                    </el-button>
                    <el-button type="danger" size="mini" icon="el-icon-delete" @click="remove(scope.row)">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                class="pageBox"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="queryModel.page"
                :page-sizes="[10,20, 50, 100]"
                :page-size="queryModel.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="queryModel.total">
        </el-pagination>
        <DialogModuleAddOrEdit ref="DialogModuleAddOrEdit"></DialogModuleAddOrEdit>
    </div>
</template>

<script>
    import BaseTableView from "../../../components/BaseTableView";
    import DialogModuleAddOrEdit from "./DialogModuleAddOrEdit";

    export default {
        name: "Module",
        components: {DialogModuleAddOrEdit},
        extends: BaseTableView,
        data() {
            return {
                module: "module",
            }
        },
        methods: {
            getList() {
                this.tableIsLoading = true;
                this.request.axiosGetModuleList(this, this.queryModel, (data) => {
                    this.tableIsLoading = false;
                    this.queryModel.total = data.total;
                    this.list = data.records;
                }, () => {
                    this.tableIsLoading = false;
                });
            },
            showAddOrEditDialog(row, isChild) {
                let model = {};
                if (row && isChild) {
                    model.isChild = isChild;
                    model.parentId = row.moduleId;
                    model.parentModuleName = row.moduleName;
                } else {
                    this.jquery.extend(model, row ? row : {});
                }
                this.$refs.DialogModuleAddOrEdit.show(model, () => {
                    this.getList();
                });
            },
            remove(row) {
                this.dialogConfirm("确定要删除此记录吗？", () => {
                    this.request.axiosDeleteModule(this, row, () => {
                        this.getList();
                    }, () => {
                        alert("删除失败");
                    });
                });
            }
        }
    }


</script>

<style scoped lang="scss">


    .searchBox {
        position: absolute;
        top: 10px;
        line-height: 40px;

        .startDateBox {
            display: block;
            float: left;
            margin-right: 10px;
            line-height: 28px;
        }

        .inputTextBox {
            display: block;
            float: left;
            width: 300px;
            line-height: 28px;
        }

    }


    .tableMainBox {
        position: absolute;
        left: 10px;
        right: 10px;
        top: 0;
        bottom: 0;

        .tableBox {
            position: absolute;
            top: 48px;
            bottom: 60px;
            overflow: scroll;
            left: 0;
            right: 0;
        }

        .pageBox {
            position: absolute;
            bottom: 16px;
            left: 0px;
            right: 0;
        }
    }


    .el-table::before {
        height: 0;
    }

    .el-table--fit {
        border-right: 1px solid #EBEEF5;
        border-bottom: 1px solid #EBEEF5;
    }

</style>
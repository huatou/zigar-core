<template>
    <div class="tableMainBox">
        <div class="searchBox" v-show="showSearchBox">
            <el-date-picker class="startDateBox" v-if="searchDateStart" v-model="queryModel.startDate" type="date"
                            size="mini"
                            :placeholder="searchDateStart.placeholder?searchDateStart.placeholder:defaultSearchDatePlaceholder"
                            @change="getData" clearable>
            </el-date-picker>
            <el-date-picker class="startDateBox" v-if="searchDateEnd" v-model="queryModel.endDate" type="date"
                            size="mini"
                            :placeholder="searchDateEnd.placeholder?searchDateEnd.placeholder:defaultSearchDatePlaceholder"
                            @change="getData" clearable>
            </el-date-picker>
            <el-input class="inputTextBox" v-show="!searchKeywordsHidden" v-model="queryModel.keyword" clearable
                      maxlength="40" prefix-icon="el-icon-search"
                      placeholder="请输入关键字搜索" size="mini"></el-input>
            <div class="el-button el-button--primary el-button--mini ml-2 float-left"
                 @click="actionHandlers['add'].action" v-if="actions.add">
                <i class="el-icon-plus mr-2"></i>增加
            </div>
        </div>
        <el-table class="tableBox" :data="list" border width="100%" size="mini"
                  v-loading="tableIsLoading" :headerCellStyle="getHeaderStyle()">
            <el-table-column type="index" align="center"></el-table-column>
            <el-table-column label="操作" :width="defaultActionBtnWidth*getActionHandlersSize()"
                             v-if="actionHandlers && getActionHandlersSize()!=0" align="center">
                <template slot-scope="scope">
                    <el-button v-for="(actionHandlerItem,actionHandlerItemIndex) in actionHandlers"
                               @click="actionHandlerItem.action(scope.row)"
                               :type="actionHandlerItem.type" size="mini"
                               :icon="actionHandlerItem.icon"
                               v-if="!actionHandlerItem.hidden"
                               :key="actionHandlerItemIndex">
                        {{actionHandlerItem.text}}
                    </el-button>
                </template>
            </el-table-column>
            <el-table-column v-for=" (item,index) in tableConfig " :fixed="item.fixed" :label="item.label"
                             :prop="item.prop"
                             :key="index"
                             :width="item.width?item.width:defaultRowWidth"
                             show-overflow-tooltip>
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
        <el-dialog title="新增" :visible.sync="addDialogVisible" v-if="currentEditRow" top="10vh">
            <el-form :model="currentEditRow">
                <el-form-item v-for="(item,index) in tableConfig" :label="item.label" label-width="120px" :key="index"
                              v-if="!item.unEditable">
                    <el-date-picker
                            v-if="item.type == 'date'||item.type == 'datetime'"
                            v-model="currentEditRow[item.prop]"
                            align="right"
                            :type="item.type"
                            placeholder="选择日期"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            class="w-100">
                    </el-date-picker>
                    <el-input v-else="item.type" v-model="currentEditRow[item.prop]" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="actionHandlers['add'].handler">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="编辑" :visible.sync="editDialogVisible" v-if="currentEditRow" top="10vh">
            <el-form :model="currentEditRow">
                <el-form-item v-for="(item,index) in tableConfig" :label="item.label" label-width="120px" :key="index"
                              v-if="!item.unEditable">
                    <el-date-picker
                            v-if="item.type == 'date'||item.type == 'datetime'"
                            v-model="currentEditRow[item.prop]"
                            align="right"
                            :type="item.type"
                            placeholder="选择日期"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            class="w-100">
                    </el-date-picker>
                    <el-input v-else="item.type" v-model="currentEditRow[item.prop]" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="actionHandlers['edit'].handler">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="提示" :visible.sync="deleteDialogVisible" width="30%">
            <span>确定删除此条记录吗</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="deleteDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="actionHandlers['delete'].handler">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        props: {
            searchKeywordsHidden: Boolean,
            searchDateStart: Object,
            searchDateEnd: Object,
            module: String,
            tableConfig: Array,
            actions: {
                type: Object,
                default: function () {
                    return {};
                }
            }
        },
        data() {
            return {
                defaultRowWidth: "200",
                defaultActionBtnWidth: "100",
                showSearchBox: this.searchDateStart || this.searchDateEnd || !this.searchKeywordsHidden || this.actions.add,
                defaultSearchDatePlaceholder: "请选择日期",
                defaultSearchDateType: 'date',
                tableIsLoading: false,
                list: [],
                queryModel: {
                    isPage: true,
                    keyword: null,
                    startDate: null,
                    endDate: null,
                    page: 1,
                    pageSize: 20
                },
                currentEditRow: null,
                addDialogVisible: false,
                editDialogVisible: false,
                deleteDialogVisible: false,
                actionHandlers: {
                    "add": {
                        hidden: true,
                        action: () => {
                            this.addDialogVisible = true;
                            this.currentEditRow = {};
                        }, handler: () => {
                            this.request.axiosPostData(this, this.module, this.currentEditRow, (data) => {
                                this.addDialogVisible = false;
                                this.getList();
                            });
                        }
                    },
                    "edit": {
                        text: "编辑",
                        icon: "el-icon-edit-outline",
                        type: "warning",
                        hidden: !this.actions.edit,
                        action: (row) => {
                            this.editDialogVisible = true;
                            this.currentEditRow = this.jquery.extend({}, row);
                        }, handler: () => {
                            this.request.axiosPutData(this, this.module, this.currentEditRow, (data) => {
                                this.editDialogVisible = false;
                                this.getList();
                            });
                        }
                    },
                    "delete": {
                        text: "删除",
                        icon: "el-icon-delete",
                        type: "danger",
                        hidden: !this.actions.delete,
                        action: (row) => {
                            this.deleteDialogVisible = true;
                            this.currentEditRow = this.jquery.extend({}, row);
                        }, handler: () => {
                            this.deleteDialogVisible = false;
                            this.request.axiosDeleteData(this, this.module, this.currentEditRow, (data) => {
                                this.getList();
                            });
                        }
                    }
                },
                defaultDialogWidth: "30%"
            }
        },
        watch: {
            "queryModel.keyword": function () {
                this.getData();
            }
        },
        methods: {
            getList() {
                this.tableIsLoading = true;
                this.request.axiosGetData(this, this.module, this.queryModel, (data) => {
                    this.tableIsLoading = false;
                    this.queryModel.total = data.total;
                    this.list = data.list;
                }, () => {
                    this.tableIsLoading = false;
                });
            },
            handleSizeChange(val) {
                this.queryModel.pageSize = val;
                this.getData();
            },
            handleCurrentChange(val) {
                this.queryModel.page = val;
                this.getData();
            },
            getActionHandlersSize() {
                let count = 0;
                for (let i = 0; i < Object.keys(this.actionHandlers).length; i++) {
                    if (!this.actionHandlers[Object.keys(this.actionHandlers)[i]].hidden) {
                        count++;
                    }
                }
                return count;
            },
            getHeaderStyle() {
                return {
                    background: "#fafafa",
                    color: 'black'
                }
            }
        },
        mounted() {
            if (!this.module) {
                alert("Table组件没有定义module");
                return;
            }
            if (!this.tableConfig) {
                alert("Table组件没有定义tableConfig");
                return;
            }
            this.getList();
        },
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

    .tableHeaderBox {
        background: #4FC6D0;
    }

    .el-table::before {
        height: 0;
    }

    .el-table--fit {
        border-right: 1px solid #EBEEF5;
        border-bottom: 1px solid #EBEEF5;
    }

</style>
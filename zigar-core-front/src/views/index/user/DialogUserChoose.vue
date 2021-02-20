<template>
    <el-dialog title="选择人员" :visible.sync="visible" top="10vh" :width="isMulti?'900px':'600px'">
        <div style="height: 300px;padding: 20px 0" v-show="isMulti">
            <div class="float-left h-100 m-2" style="width: 48%;">
                <div style="margin-bottom: 6px;">
                    <span class="bold" style="line-height: 28px;font-size: 18px;color: black">待选人员列表</span>
                    <el-input v-model="queryModel.keyword" clearable style="width: 200px;float: right"
                              maxlength="40" prefix-icon="el-icon-search"
                              placeholder="请输入关键字搜索" size="mini"></el-input>
                </div>
                <div class="border h-100 p-2">
                    <el-checkbox v-for="item,index in userList" :key="index" :label="item.displayName"
                                 v-model="item.isChecked" style="margin-right: 10px;width: 30%">
                    </el-checkbox>
                </div>
            </div>
            <div class="float-left h-100 m-2" style="width: 48%;">
                <div style="margin-bottom: 6px;">
                    <span class="bold" style="line-height: 28px;font-size: 18px;color: black">已选人员列表</span>
                </div>
                <div class="border h-100 p-2">
                    <el-checkbox v-for="item,index in userList" :key="index" :label="item.displayName"
                                 v-model="item.isChecked" v-show="item.isChecked" style="margin-right: 10px;width: 30%">
                    </el-checkbox>
                </div>
            </div>
        </div>
        <div style="height: 300px;" v-show="!isMulti">
            <el-input v-model="queryModel.keyword" clearable style="width: 200px;"
                      maxlength="40" prefix-icon="el-icon-search"
                      placeholder="请输入关键字搜索" size="mini"></el-input>
            <el-table :data="userList" border size="mini" v-loading="tableIsLoading"
                      :header-cell-style="getHeaderStyle()" style="margin-top: 5px;height: 260px;overflow: scroll">
                <el-table-column width="100px" label="姓名" prop="displayName" show-overflow-tooltip></el-table-column>
                <el-table-column width="100px" label="微信昵称" prop="nickName" show-overflow-tooltip></el-table-column>
                <el-table-column width="100px" label="电话" prop="phone" show-overflow-tooltip></el-table-column>
                <el-table-column width="80px" label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini" @click="choose(scope.row)">选择
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
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close()">取 消</el-button>
            <el-button v-show="isMulti" type="primary" @click="submit()">确定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import BaseDialog from "../../../components/Dialog/BaseDialog";

    export default {
        name: "DialogUserChoose",
        extends: BaseDialog,
        data() {
            return {
                userList: [],
                keyword: null,
                dRole: this.role,
                isMulti: false
            }
        },
        methods: {
            getList() {
                this.userList = [];
                this.request.axiosGetUser(this, this.queryModel, (data) => {
                    this.tableIsLoading = false;
                    this.queryModel.total = data.total;
                    // this.list = data.records;
                    data.records.forEach((user, index) => {
                        let item = this.jquery.extend({
                            label: user.displayName,
                            key: user.userId
                        }, user);
                        this.userList.push(item);
                    });
                });
            },
            choose(row) {
                this.close(row);
            },
            submit() {
                let chosenList = [];
                chosenList = this.userList.filter(user => user.isChecked);
                if (chosenList.length != 0) {
                    this.close(chosenList);
                } else {
                    alert("请选择人员");
                }

            }
        },
        watch: {
            keyword() {
                this.getList();
            },
            visible() {
                if (this.visible) {
                    this.getList();
                }
            }
        }
    }
</script>

<style scoped>

</style>

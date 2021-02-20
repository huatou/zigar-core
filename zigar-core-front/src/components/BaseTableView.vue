<template>

</template>

<script>
    export default {
        name: "BaseTableView",
        data() {
            return {
                defaultRowWidth: "200",//默认表格列宽度
                tableIsLoading: false,//当前表格是否正在加载
                list: [],//当前模块对应的列表
                //查询对象
                queryModel: {
                    isPage: true,
                    keyword: null,
                    page: 1,
                    pageSize: 20
                },
            }
        },
        methods: {
            //修改列表行数回调函数
            handleSizeChange(val) {
                this.queryModel.pageSize = val;
                this.getList();
            },

            //修改当前页回调函数
            handleCurrentChange(val) {
                this.queryModel.page = val;
                this.getList();
            },
            //表头的样式
            getHeaderStyle() {
                return {
                    background: "#fafafa",
                    color: 'black'
                }
            },
            //调用当前模块的删除方法
            remove(id) {
                this.dialogConfirm("确定要删除此记录吗？", () => {
                    this.request.axiosDeleteData(this, this.module, id, () => {
                        this.getList();
                    });
                });
            }
        },
        watch: {
            //监听关键字改变的回调函数
            "queryModel.keyword": function () {
                this.getList();
            }
        },
        mounted() {
            //表示当前组件不是窗口，在组件生成后回调该方法
            if (!this.isDialog) {
                this.getList && this.getList();
            }
        }
    }
</script>

<style scoped>

</style>

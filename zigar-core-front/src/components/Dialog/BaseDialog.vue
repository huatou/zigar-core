<template>
    <h1>hello</h1>
</template>

<script>
    import BaseTableView from "../BaseTableView";

    let callback1;
    export default {
        name: "BaseDialog",
        extends: BaseTableView,
        data() {
            return {
                isDialog: true,
                visible: false,
                row: {}
            }
        },
        methods: {
            show(row, callback) {
                this.visible = true;
                this.row = row ? this.jquery.extend({}, row) : {};
                callback1 = callback;
            },
            close(data1, data2, data3, data4) {
                this.visible = false;
                callback1 && callback1(data1, data2, data3, data4);
            },
            dismiss() {
                this.visible = false;
            }
        },
        watch: {
            visible() {
                if (this.visible) {
                    this.getData && this.getData();
                    //在窗口也是列表时使用
                    this.getList && this.getList();
                }
            }
        }
    }
</script>

<style scoped>

</style>
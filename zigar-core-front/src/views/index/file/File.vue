<template>
   <div> 123</div>
</template>

<script>


    export default {

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
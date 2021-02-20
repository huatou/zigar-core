<template>
    <SimpleTable module="userLoginLog" :tableConfig="tableConfig" :actions="actions"/>
</template>

<script>

    import SimpleTable from "../../../components/SimpleTable";

    export default {
        name: "UserLoginLog",
        components: {SimpleTable},
        data() {
            return {
                tableConfig: [
                    {
                        label: "用户名",
                        prop: "userName"
                    },
                    {
                        label: "姓名",
                        prop: "displayName"
                    },
                    {
                        label: "浏览器信息",
                        prop: "userAgent"
                    },
                    {
                        label: "操作系统",
                        prop: "os"
                    },
                    {
                        label: "登录时间",
                        prop: "loginTime",
                        type: 'datetime'
                    },
                    {
                        label: "ip",
                        prop: "ip"
                    }
                ],
                actions: {
                    delete: true
                }
            }
        }
    }
</script>

<style scoped>

</style>
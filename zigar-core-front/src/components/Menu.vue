<template>
    <div class="menuBox">
        <div v-for="(menuItem,index) in menuList">
            <div class="menuItem" @click="to(menuItem)" :class="{ active: menuItem.active, 'firstChild': index==0 }">
                <i :class="menuItem.icon"></i>
                {{menuItem.name}}
                <div v-if="menuItem.children" class="el-icon-arrow-down"></div>
            </div>
            <el-collapse-transition>
                <div v-show="menuItem.collapse">
                    <div class="menuChildrenItem" :class="{ active: menuChildItem.active, 'firstChild': index==0 }" v-for="(menuChildItem,childIndex) in menuItem.children"
                         :key="childIndex"
                         @click="to(menuChildItem)">
                        <i :class="menuChildItem.icon"></i>
                        {{menuChildItem.name}}
                    </div>
                </div>
            </el-collapse-transition>
        </div>
    </div>
</template>

<script>

    export default {
        name: "Menu",
        computed: {
            menuList() {
                return this.$store.getters.getCurrentUser.menuList;
            }
        },
        methods: {
            to(menuItem) {
                if (menuItem.children) {
                    if (!menuItem.collapse) {
                        menuItem.collapse = true;
                    } else {
                        menuItem.collapse = !menuItem.collapse;
                    }
                } else {
                    this.$router.push(menuItem.url);
                    this.changeBg(this.menuList);
                    menuItem.active = true;
                }
                this.$forceUpdate();
            },
            changeBg(menuList) {
                menuList.forEach(menuItem => {
                    if (menuItem.children && menuItem.children.length != 0) {
                        this.changeBg(menuItem.children);
                    } else {
                        menuItem.active = false;
                    }
                });
            }
        },
        watch: {
            // $route(to, from) {
            //     console.log(to);
            //     this.changeBg(this.menuList, to);
            // }
        }
    }

</script>

<style scoped>

    .menuBox {
        background: #36414f;
        overflow: scroll;
        border-bottom: 1px solid #3d4956;
        padding-bottom: 50px;
    }

    .menuItem {
        display: block;
        font-size: 14px;
        line-height: 40px;
        background: #28303a;
        padding-left: 36px;
        color: #a3abb7;
        border-top: 1px solid #3d4956;
        cursor: pointer;
        text-decoration: none;
    }

    .el-icon-arrow-down {
        line-height: 40px;
        float: right;
        padding-right: 20px;
    }

    .menuItem.firstChild.active, .menuItem.firstChild:hover {
        border-top: 1px solid #48c6d1;
    }

    .menuItem.active, .menuItem:hover {
        background: #48c6d1;
        color: white;
    }

    .menuChildrenItem {
        display: block;
        font-size: 14px;
        padding-left: 36px;
        line-height: 30px;
        margin: 6px;
        color: #a3abb7;
        background: #36414f;
        text-decoration: none;
        cursor: pointer;
    }

    /*.menuChildrenItem:last-child {*/
    /*    border-bottom: 1px solid #3d4956;*/
    /*}*/

    .menuChildrenItem.active, .menuChildrenItem:hover {
        background: #48c6d1;
        color: white;
    }


</style>
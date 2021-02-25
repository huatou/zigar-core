import Vue from 'vue'
import Router from 'vue-router'
import Index from "./views/index/Index";
import Login from "./views/login/Login";
import Register from "./views/login/Register";
import LoginMain from "./views/login/LoginMain";
import User from "./views/index/user/User";
import UserLoginLog from "./views/index/userLoginLog/UserLoginLog";
import Module from "./views/index/module/Module";
import File from "./views/index/file/File";
import Welcome from "./views/index/Welcome";

Vue.use(Router);
const router = new Router({
    mode: 'history',
    base: "/zigarcore",
    routes: [
        {path: '/zigarcore', redirect: '/zigarcore/index'},
        {
            path: '/zigarcore/index',
            name: 'index',
            component: Index,
            children: [
                {path: '/zigarcore/index', redirect: '/zigarcore/index/welcome'},
                {
                    path: '/zigarcore/index/welcome',
                    name: 'index-welcome',
                    component: Welcome
                },
                {
                    path: '/zigarcore/index/user',
                    name: 'index-user',
                    component: User
                },
                {
                    path: '/zigarcore/index/user-login-log',
                    name: 'index-userLoginLog',
                    component: UserLoginLog
                },
                {
                    path: '/zigarcore/index/module',
                    name: 'index-module',
                    component: Module
                },
                {
                    path: '/zigarcore/index/file',
                    name: 'index-file',
                    component: File
                },
            ]
        },
        {
            path: '/zigarcore/login-main',
            name: 'loginMain',
            component: LoginMain,
            children: [
                {
                    path: '/zigarcore/login-main/login',
                    name: 'login',
                    component: Login,
                    meta: {
                        needLogin: false
                    }
                },
                {
                    path: '/zigarcore/login-main/register',
                    name: 'register',
                    component: Register,
                    meta: {
                        needLogin: false
                    }
                }
            ]
        }
    ]
});
export default router;

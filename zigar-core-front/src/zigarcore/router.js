import Index from "./views/index/Index";
import Login from "./views/login/Login";
import Register from "./views/login/Register";
import LoginMain from "./views/login/LoginMain";
import User from "./views/index/user/User";
import UserLoginLog from "./views/index/userLoginLog/UserLoginLog";
import Module from "./views/index/module/Module";
import File from "./views/index/file/File";
import Welcome from "./views/index/Welcome";

const router = [
    {path: '*', redirect: '/index'},
    {path: '/', redirect: '/index'},
    {
        path: '/index',
        name: 'index',
        component: Index,
        children: [
            {path: '/index', redirect: '/index/welcome'},
            {
                path: '/index/welcome',
                name: 'index-welcome',
                component: Welcome
            },
            {
                path: '/index/user',
                name: 'index-user',
                component: User
            },
            {
                path: '/index/user-login-log',
                name: 'index-userLoginLog',
                component: UserLoginLog
            },
            {
                path: '/index/module',
                name: 'index-module',
                component: Module
            },
            {
                path: '/index/file',
                name: 'index-file',
                component: File
            },
        ]
    },
    {
        path: '/login-main',
        name: 'loginMain',
        component: LoginMain,
        children: [
            {
                path: '/login-main/login',
                name: 'login',
                component: Login,
                meta: {
                    needLogin: false
                }
            },
            {
                path: '/login-main/register',
                name: 'register',
                component: Register,
                meta: {
                    needLogin: false
                }
            }
        ]
    }
];
export default router;

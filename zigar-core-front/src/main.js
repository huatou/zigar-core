import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import 'font-awesome/css/font-awesome.css'
import axios from 'axios'
import jquery from 'jquery';

import App from './App.vue'
import store from './store'
import router from './router'
import global from './global'
import request from './request'
import tokenUtil from './tokenUtil'
import config from "./config";
import dialogConfirm from './components/Dialog/dialogConfirmIndex.js'


Vue.use(ElementUI);
Vue.config.productionTip = false
Vue.prototype.global = global
Vue.prototype.request = request
Vue.prototype.$axios = axios
Vue.prototype.tokenUtil = tokenUtil
Vue.prototype.config = config
Vue.prototype.jquery = jquery
Vue.prototype.dialogConfirm = dialogConfirm

new Vue({
    store,
    router,
    render: function (h) {
        return h(App)
    }
}).$mount('#app')

// function toMenu(menuList, to, next) {
//
//     if (!menuList) {
//         return;
//     }
//
//     menuList.forEach(menuItem => {
//         if (!menuItem.children || menuItem.children.length == 0) {
//             if (menuItem.url == to.path) {
//                 next();
//                 return;
//             }
//         } else {
//             toMenu(menuItem.children, to, next);
//         }
//     });
// }
//
// router.beforeEach((to, from, next) => {
//     if (to.meta.needLogin === false) {
//         next()
//     } else {
//         const menuList = store.getters.getCurrentUser.menuList;
//         if (menuList == null || menuList.length == 0) {
//             next();
//         }
//         toMenu(menuList, to, next)
//     }
// });

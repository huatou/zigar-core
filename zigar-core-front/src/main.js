import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min'
import 'font-awesome/css/font-awesome.css'
import axios from 'axios'
import jquery from 'jquery';

import App from './App.vue'
import zigarStore from './zigarcore/store'
import zigarRequest from './zigarcore/request'
import tokenUtil from './zigarcore/utils/tokenUtil'

import config from './config'
import request from './request'

import dialogConfirm from './zigarcore/components/zigarcore/Dialog/dialogConfirmIndex.js'

import router from './router'
import store from './store'


Vue.use(ElementUI);
Vue.prototype.config = config
Vue.prototype.zigarRequest = zigarRequest
Vue.prototype.tokenUtil = tokenUtil

Vue.prototype.request = request
Vue.prototype.$axios = axios
Vue.prototype.jquery = jquery
Vue.prototype.dialogConfirm = dialogConfirm

new Vue({
    router,
    store,
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

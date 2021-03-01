import Vue from 'vue'
import Router from 'vue-router'
import config from "@/config";
import jquery from 'jquery';

import Leave from "@/views/Leave";
import zigarRouter from "@/zigarcore/router";

Vue.use(Router);
const router = new Router({
    mode: 'history',
    base: '/view/',
    routes: zigarRouter.concat([{
        path: '/view/leave',
        name: 'leave',
        component: Leave
    }])
});
export default router;

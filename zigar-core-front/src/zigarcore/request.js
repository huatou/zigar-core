import requestUtil from "@/zigarcore/utils/requestUtil";

const LOGIN = "/zigarcore/login";
const LOGOUT = "/zigarcore/logout";
const REGISTER = "/zigarcore/user/register";

const USER = "/zigarcore/user";
const CHANGE_PWD = "/zigarcore/user/change-pwd";
const USER_AUTH = "/zigarcore/user/auth";

const MODULE = "/zigarcore/module";
const MENU = "/zigarcore/menu";


export default {

    //登录，用户
    axiosLogin(vue, param, onSuccess, onError) {
        requestUtil.axiosPostParam(vue, LOGIN, param, onSuccess, onError);
    },
    axiosRegister(vue, model, onSuccess, onError) {
        requestUtil.axiosPostData(vue, REGISTER, model, onSuccess, onError);
    },
    axiosLogout(vue, onSuccess, onError) {
        requestUtil.axiosPostData(vue, LOGOUT, null, onSuccess, onError);
    },
    axiosGetCurrentUser(vue, onSuccess, onError) {
        requestUtil.axiosGetData(vue, USER, {userId: 'current'}, onSuccess, onError);
    },
    axiosGetUserList(vue, params, onSuccess, onError) {
        requestUtil.axiosGetData(vue, USER, params, onSuccess, onError);
    },
    axiosGetUser(vue, params, onSuccess, onError) {
        requestUtil.axiosGetData(vue, USER, params, onSuccess, onError);
    },
    axiosInsertUser(vue, data, onSuccess, onError) {
        requestUtil.axiosPostData(vue, USER, data, onSuccess, onError);
    },
    axiosUpdateUser(vue, data, onSuccess, onError) {
        requestUtil.axiosPutData(vue, USER, data, onSuccess, onError);
    },
    axiosDeleteUser(vue, data, onSuccess, onError) {
        requestUtil.axiosDeleteData(vue, USER, data, onSuccess, onError);
    },
    axiosChangePwd(vue, module, onSuccess, onError) {
        requestUtil.axiosPostData(vue, CHANGE_PWD, module, onSuccess, onError);
    },
    axiosUserAuthPost(vue, module, onSuccess, onError) {
        requestUtil.axiosPostData(vue, USER_AUTH, module, onSuccess, onError);
    },

    //模块，操作项
    axiosGetModuleList(vue, params, onSuccess, onError) {
        requestUtil.axiosGetData(vue, MODULE, params, onSuccess, onError);
    },
    axiosInsertModule(vue, data, onSuccess, onError) {
        requestUtil.axiosPostData(vue, MODULE, data, onSuccess, onError);
    },
    axiosUpdateModule(vue, data, onSuccess, onError) {
        requestUtil.axiosPutData(vue, MODULE, data, onSuccess, onError);
    },
    axiosDeleteModule(vue, data, onSuccess, onError) {
        requestUtil.axiosDeleteData(vue, MODULE, data, onSuccess, onError);
    },
    axiosGetMenu(vue, params, onSuccess, onError) {
        requestUtil.axiosGetData(vue, MENU, params, onSuccess, onError);
    },

}




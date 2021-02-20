function config(vue, params, data, headers) {
    return {
        validateStatus(status) {
            if (status == 403) {
                vue.$store.commit('setToken', null);
                vue.$router.push("/login-main/login");
            }
            return status < 500; // 状态码在大于或等于500时才会 reject
        },
        headers: vue.jquery.extend({
            "Authorization": "Bearer " + vue.$store.getters.getToken
        }, headers),
        params: params,
        data: data
        // ,
        // proxyTable:
        //     vue.config.IS_DEV ?
        //         {
        //             '/': {
        //                 target: vue.global.HOST_PATH,//设置你调用的接口域名和端口号 需要加加http
        //                 changeOrigin: true,
        //                 pathRewrite: {
        //                     '^/': ''//这里理解成用‘/request’代替target里面的地址，后面组件中我们掉接口时直接用request代替 比如我要调用'http://22.00.100.100:3002/user/add'，在请求的时候接口直接写‘/request/user/add’即可
        //                 }
        //             }
        //         } : {}
    }
}


function beforeRequest(vue) {
    let token = vue.$store.getters.getToken;
    if (!token) {
        vue.$router.push("/login-main/login");
    }
}

function axiosGetData(vue, url, params, onSuccess, onError) {
    beforeRequest(vue);
    vue.$axios.get(vue.global.API_PATH + url, config(vue, params)).then((response) => {
        if (response.data.success) {
            onSuccess && onSuccess(response.data.data);
        } else {
            vue.$message.error(response.data.message);
            onError && onError(response);
        }
    }).catch((error) => {
        console.log(error);
        onError && onError(error);
    });
}

function axiosPostParam(vue, url, param, onSuccess, onError) {
    beforeRequest(vue);
    const headers = {'Content-Type': 'application/x-www-form-urlencoded'};
    vue.$axios.post(vue.global.API_PATH + url, param, config(vue, param, param, headers)).then((response) => {
        if (response.data.success) {
            onSuccess && onSuccess(response.data.data);
        } else {
            vue.$message.error(response.data.message);
            onError && onError(response);
        }
    }).catch((error) => {
        console.log(error);
        onError && onError(error);
    });
}

function axiosPostData(vue, url, model, onSuccess, onError) {
    beforeRequest(vue);
    vue.$axios.post(vue.global.API_PATH + url, model, config(vue)).then((response) => {
        if (response.data.success) {
            onSuccess && onSuccess(response.data.data);
        } else {
            vue.$message.error(response.data.message);
            onError && onError(response);
        }
    }).catch((error) => {
        console.log(error);
        onError && onError(error);
    });
}

function axiosPutData(vue, url, model, onSuccess, onError) {
    beforeRequest(vue);
    vue.$axios.put(vue.global.API_PATH + url, model, config(vue)).then((response) => {
        if (response.data.success) {
            onSuccess && onSuccess(response.data.data);
        } else {
            vue.$message.error(response.data.message);
            onError && onError(response);
        }
    }).catch((error) => {
        console.log(error);
        onError && onError(error);
    });
}

function axiosDeleteData(vue, url, data, onSuccess, onError) {
    beforeRequest(vue);
    vue.$axios.delete(vue.global.API_PATH + url, config(vue, null, data)).then((response) => {
        if (response.data.success) {
            onSuccess && onSuccess(response.data.data);
        } else {
            vue.$message.error(response.data.message);
            onError && onError(response);
        }
    }).catch((error) => {
        console.log(error);
        onError && onError(error);
    });
}


const LOGIN = "/login";
const LOGOUT = "/logout";
const REGISTER = "/user/register";

const USER = "/user";
const CHANGE_PWD = "/user/change-pwd";
const USER_AUTH = "/user/auth";
const CUSTOMER_COLLECT_SWITCH_UPLOAD = "/user/switch-permit-upload";
const CUSTOMER_COLLECT_SWITCH_PAY = "/user/switch-permit-pay";
const CUSTOMER_COLLECT_EDIT_INFO = "/user/edit-info";

const MODULE = "/module";
const MENU = "/menu";


//---------------业务相关---------------

//卡券类型
const TICKET_TYPE = "/ticket-type";

//卡券
const TICKET = "/ticket";
const TICKET_UN_STORAGE = TICKET + "/un-storage";
const TICKET_IN_STORAGE = TICKET + "/in-storage";
const TICKET_EXPIRE_TIME = TICKET + "/expire-time";
const TICKET_PROTECT_TIME = TICKET + "/protect-time";
const TICKET_CHECK = TICKET + "/check";
const TICKET_PAY = TICKET + "/pay";
const TICKET_TRANSFER_SELL = TICKET + "/transfer-sell";
const TICKET_ERROR = TICKET + "/error";
const TICKET_ERROR_CANCEL = TICKET + "/error-cancel";
const TICKET_FIRST_SELL = TICKET + "/first-sell";
const TICKET_INSERT_LIST = TICKET + "/insert-list";
const TICKET_SET_FAIL = TICKET + "/set-fail";
const TICKET_DELETE = TICKET + "/ticket-delete";

//轮播图
const PIC_SETTINGS = "/pic-settings";

//通知公告
const NOTICE = "/notice";

//活动线报
const ACTIVITY = "/activity";


//销售端----------------
//销售端订单
const ORDER_SELL = "/order-sell";

//公共
const CUSTOMER = "/customer";


export default {


    // 基础请求
    axiosGetData(vue, url, params, onSuccess, onError) {
        axiosGetData(vue, url, params, onSuccess, onError);
    },
    axiosPostData(vue, url, params, onSuccess, onError) {
        axiosPostData(vue, url, params, onSuccess, onError);
    },
    axiosPutData(vue, url, params, onSuccess, onError) {
        axiosPutData(vue, url, params, onSuccess, onError);
    },
    axiosDeleteData(vue, url, data, onSuccess, onError) {
        axiosDeleteData(vue, url, data, onSuccess, onError);
    },


    //登录，用户
    axiosLogin(vue, param, onSuccess, onError) {
        axiosPostParam(vue, LOGIN, param, onSuccess, onError);
    },
    axiosRegister(vue, model, onSuccess, onError) {
        axiosPostData(vue, REGISTER, model, onSuccess, onError);
    },
    axiosLogout(vue, onSuccess, onError) {
        axiosPostData(vue, LOGOUT, null, onSuccess, onError);
    },
    axiosGetCurrentUser(vue, onSuccess, onError) {
        axiosGetData(vue, USER, {userId: 'current'}, onSuccess, onError);
    },
    axiosGetUserList(vue, params, onSuccess, onError) {
        axiosGetData(vue, USER, params, onSuccess, onError);
    },
    axiosGetUser(vue, params, onSuccess, onError) {
        axiosGetData(vue, USER, params, onSuccess, onError);
    },
    axiosInsertUser(vue, data, onSuccess, onError) {
        axiosPostData(vue, USER, data, onSuccess, onError);
    },
    axiosUpdateUser(vue, data, onSuccess, onError) {
        axiosPutData(vue, USER, data, onSuccess, onError);
    },
    axiosDeleteUser(vue, data, onSuccess, onError) {
        axiosDeleteData(vue, USER, data, onSuccess, onError);
    },
    axiosChangePwd(vue, module, onSuccess, onError) {
        axiosPostData(vue, CHANGE_PWD, module, onSuccess, onError);
    },
    axiosUserAuthPost(vue, module, onSuccess, onError) {
        axiosPostData(vue, USER_AUTH, module, onSuccess, onError);
    },
    axiosCustomerCollectSwitchPermitUpload(vue, data, onSuccess, onError) {
        axiosPostData(vue, CUSTOMER_COLLECT_SWITCH_UPLOAD, data, onSuccess, onError);
    },
    axiosCustomerCollectSwitchPermitPay(vue, data, onSuccess, onError) {
        axiosPostData(vue, CUSTOMER_COLLECT_SWITCH_PAY, data, onSuccess, onError);
    },
    axiosUpdateUserInfo(vue, data, onSuccess, onError) {
        axiosPostData(vue, CUSTOMER_COLLECT_EDIT_INFO, data, onSuccess, onError);
    },


    //模块，操作项
    axiosGetModuleList(vue, params, onSuccess, onError) {
        axiosGetData(vue, MODULE, params, onSuccess, onError);
    },
    axiosInsertModule(vue, data, onSuccess, onError) {
        axiosPostData(vue, MODULE, data, onSuccess, onError);
    },
    axiosUpdateModule(vue, data, onSuccess, onError) {
        axiosPutData(vue, MODULE, data, onSuccess, onError);
    },
    axiosDeleteModule(vue, data, onSuccess, onError) {
        axiosDeleteData(vue, MODULE, data, onSuccess, onError);
    },
    axiosGetMenu(vue, params, onSuccess, onError) {
        axiosGetData(vue, MENU, params, onSuccess, onError);
    },


    //卡券类型
    axiosGetTicketTypeList(vue, params, onSuccess, onError) {
        axiosGetData(vue, TICKET_TYPE, params, onSuccess, onError);
    },
    axiosInsertTicketType(vue, data, onSuccess, onError) {
        axiosPostData(vue, TICKET_TYPE, data, onSuccess, onError);
    },
    axiosUpdateTicketType(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_TYPE, data, onSuccess, onError);
    },
    axiosDeleteTicketType(vue, data, onSuccess, onError) {
        axiosDeleteData(vue, TICKET_TYPE, data, onSuccess, onError);
    },


    //卡券
    //获取所有卡券
    axiosGetTicketList(vue, params, onSuccess, onError) {
        axiosGetData(vue, TICKET, params, onSuccess, onError);
    },
    axiosGetTicketUnStorageList(vue, params, onSuccess, onError) {
        axiosGetData(vue, TICKET_UN_STORAGE, params, onSuccess, onError);
    },
    //获取状态为未入库之前的卡券
    axiosGetTicketInStorageList(vue, params, onSuccess, onError) {
        axiosGetData(vue, TICKET_IN_STORAGE, params, onSuccess, onError);
    },
    axiosUpdateTicketExpired(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_EXPIRE_TIME, data, onSuccess, onError);
    },
    axiosTicketCheck(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_CHECK, data, onSuccess, onError);
    },
    axiosPayTicket(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_PAY, data, onSuccess, onError);
    },
    axiosFirstSell(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_FIRST_SELL, data, onSuccess, onError);
    },
    axiosTransferSell(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_TRANSFER_SELL, data, onSuccess, onError);
    },
    axiosTicketError(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_ERROR, data, onSuccess, onError);
    },
    axiosTicketErrorCancel(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_ERROR_CANCEL, data, onSuccess, onError);
    },
    axiosUpdateTicketProtectTime(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_PROTECT_TIME, data, onSuccess, onError);
    },
    axiosTicketInsertList(vue, data, onSuccess, onError) {
        axiosPostData(vue, TICKET_INSERT_LIST, data, onSuccess, onError);
    },
    axiosSetFail(vue, data, onSuccess, onError) {
        axiosPutData(vue, TICKET_SET_FAIL, data, onSuccess, onError);
    },
    axiosTicketDelete(vue, data, onSuccess, onError) {
        axiosPostData(vue, TICKET_DELETE, data, onSuccess, onError);
    },

    //轮播图
    axiosGetPicSettings(vue, params, onSuccess, onError) {
        axiosGetData(vue, PIC_SETTINGS, params, onSuccess, onError);
    },
    axiosInsertPicSettings(vue, params, onSuccess, onError) {
        axiosPostData(vue, PIC_SETTINGS, params, onSuccess, onError);
    },
    axiosUpdatePicSettings(vue, params, onSuccess, onError) {
        axiosPutData(vue, PIC_SETTINGS, params, onSuccess, onError);
    },
    axiosDeletePicSettings(vue, params, onSuccess, onError) {
        axiosDeleteData(vue, PIC_SETTINGS, params, onSuccess, onError);
    },

    //通知公告
    axiosGetNoticeList(vue, params, onSuccess, onError) {
        axiosGetData(vue, NOTICE, params, onSuccess, onError);
    },
    axiosInsertNotice(vue, params, onSuccess, onError) {
        axiosPostData(vue, NOTICE, params, onSuccess, onError);
    },
    axiosUpdateNotice(vue, params, onSuccess, onError) {
        axiosPutData(vue, NOTICE, params, onSuccess, onError);
    },
    axiosDeleteNotice(vue, params, onSuccess, onError) {
        axiosDeleteData(vue, NOTICE, params, onSuccess, onError);
    },

    //活动线报
    axiosGetActivityList(vue, params, onSuccess, onError) {
        axiosGetData(vue, ACTIVITY, params, onSuccess, onError);
    },
    axiosInsertActivity(vue, params, onSuccess, onError) {
        axiosPostData(vue, ACTIVITY, params, onSuccess, onError);
    },
    axiosUpdateActivity(vue, params, onSuccess, onError) {
        axiosPutData(vue, ACTIVITY, params, onSuccess, onError);
    },
    axiosDeleteActivity(vue, params, onSuccess, onError) {
        axiosDeleteData(vue, ACTIVITY, params, onSuccess, onError);
    },


    //销售端----------------------------------------


    //销售端订单
    axiosGetOrderSellList(vue, params, onSuccess, onError) {
        axiosGetData(vue, ORDER_SELL, params, onSuccess, onError);
    },

    //公共----------------------------------------

    axiosGetCustomer(vue, onSuccess, onError) {
        axiosGetData(vue, CUSTOMER, null, onSuccess, onError);
    },
    axiosPostCustomer(vue, data, onSuccess, onError) {
        axiosPostData(vue, CUSTOMER, data, onSuccess, onError);
    },
}




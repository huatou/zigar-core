import requestUtil from "@/zigarcore/utils/requestUtil";

const LEAVE = "/zigaroa/leave";

export default {

    //登录，用户
    axiosGetLeaveList(vue, param, onSuccess, onError) {
        requestUtil.axiosGetData(vue, LEAVE, param, onSuccess, onError);
    },
    axiosPostLeaveList(vue, model, onSuccess, onError) {
        requestUtil.axiosPostData(vue, LEAVE, model, onSuccess, onError);
    },
    axiosDeleteLeaveList(vue, onSuccess, onError) {
        requestUtil.axiosDeleteData(vue, LEAVE, null, onSuccess, onError);
    }

}




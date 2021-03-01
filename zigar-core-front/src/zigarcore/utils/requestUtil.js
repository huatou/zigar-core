function config(vue, params, data, headers) {
    return {
        validateStatus(status) {
            if (status == 403) {
                vue.$store.commit('setToken', null);
                vue.$router.push("/view/login-main/login");
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
        vue.$router.push("/view/login-main/login");
    }
}

function axiosGetData(vue, url, params, onSuccess, onError) {
    beforeRequest(vue);
    vue.$axios.get(vue.config.API_PATH + url, config(vue, params)).then((response) => {
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
    vue.$axios.post(vue.config.API_PATH + url, param, config(vue, param, param, headers)).then((response) => {
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
    vue.$axios.post(vue.config.API_PATH + url, model, config(vue)).then((response) => {
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
    vue.$axios.put(vue.config.API_PATH + url, model, config(vue)).then((response) => {
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
    vue.$axios.delete(vue.config.API_PATH + url, config(vue, null, data)).then((response) => {
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


export default {
    // 基础请求
    axiosGetData,
    axiosPostData,
    axiosPutData,
    axiosDeleteData,
    axiosPostParam,
}




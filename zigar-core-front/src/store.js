// import Vue from 'vue'
// import Vuex from 'vuex'
// import zigarStore from "@/zigarcore/store";
//
// import jquery from 'jquery';
//
// Vue.use(Vuex)
//
// const storeOption = jquery.extend(true, zigarStore, {
//     state: {
//         model: {}
//     },
//     mutations: {
//         setModel(state, currentUser) {
//             this.state.model = currentUser;
//         }
//     },
//     getters: {
//         getModel: state => {
//             return state.model;
//         }
//     },
//     actions: {}
// });
// console.log("storeOption", storeOption);
// const store = new Vuex.Store(storeOption)
//
// export default store;


import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        currentUser: {},
        token: null
    },
    mutations: {
        setCurrentUser(state, currentUser) {
            this.state.currentUser = currentUser;
        },
        setToken(state, token) {
            this.state.token = token;
            localStorage.setItem('token', token);
        }
    },
    getters: {
        getCurrentUser: state => {
            return state.currentUser;
        },
        getToken: state => {
            if (state.token) {
                return state.token;
            }
            return localStorage.getItem('token');
        }
    },
    actions: {}
})

export default store;

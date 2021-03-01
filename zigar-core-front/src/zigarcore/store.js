const zigarStore = {
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
}

export default zigarStore;

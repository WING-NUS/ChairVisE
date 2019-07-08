import axios from 'axios'

export default {
  state: {
    isApiError: false,
    apiErrorMsg: '',
    isLogin: false,
    loginUrl: '',
    logoutUrl: '',
    userEmail: '',
    userNickname: '',
  },
  mutations: {
    setAuthInfoApiRequestFail(state, payload) {
      state.isApiError = true;
      state.apiErrorMsg = payload;
    },

    setAuthInfo(state, payload) {
      state.isLogin = payload.isLogin;
      state.loginUrl = payload.loginUrl;
      state.logoutUrl = payload.logoutUrl;

      if (payload.isLogin) {
        state.userEmail = payload.userInfo.userEmail;
        state.userNickname = payload.userInfo.userNickname;
      }
    }
  },
  actions: {
    async getAuthInfo({commit}) {
      commit('setPageLoadingStatus', true);
      const urlToGetBack = encodeURI(window.location.href);
      axios.get('/api/auth?redirectUrl=' + urlToGetBack)
        .then(response => {
          commit('setAuthInfo', response.data)
        })
        .catch(e => {
          commit('setAuthInfoApiRequestFail', e.toString());
        })
        .finally(() => {
          commit('setPageLoadingStatus', false)
        })
    }
  }
};
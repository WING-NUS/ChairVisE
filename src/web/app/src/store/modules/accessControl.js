import axios from "axios";

export default {
  state: {
    accessControlList: [],
    accessControlListStatus: {
      isLoading: false,
      isApiError: false,
      apiErrorMsg: '',
    },
    accessControlForm: {
      id: '',
      userIdentifier: '',
      accessLevel: '',
    },
    accessControlFormStatus: {
      isLoading: false,
      isApiError: false,
      apiErrorMsg: '',
    },
  },

  mutations: {
    setAccessControlListLoading(state, isLoading) {
      if (isLoading) {
        state.accessControlListStatus.isApiError = false;
      }
      state.accessControlListStatus.isLoading = isLoading;
    },

    setAccessControlListApiError(state, msg) {
      state.accessControlListStatus.isApiError = true;
      state.accessControlListStatus.apiErrorMsg = msg;
    },

    setAccessControlFormLoading(state, isLoading) {
      if (isLoading) {
        state.accessControlFormStatus.isApiError = false;
      }
      state.accessControlFormStatus.isLoading = isLoading;
    },

    setAccessControlFormApiError(state, msg) {
      state.accessControlFormStatus.isApiError = true;
      state.accessControlFormStatus.apiErrorMsg = msg;
    },

    clearAccessControlList(state) {
      state.accessControlList = []
    },

    setAccessControlFormField(state, {field, value}) {
      state.accessControlForm[field] = value
    },

    addAccessControl(state, payload) {
      state.accessControlList.push(payload);
    },

    deleteAccessControl(state, payload) {
      let index = state.accessControlList.findIndex(s => s.id === payload);
      state.accessControlList.splice(index, 1)
    },

    updateAccessControl(state, {id, accessLevel}) {
      let accessControl = state.accessControlList.find(element => element.id === id);
      accessControl.accessLevel = accessLevel;
    }
  },

  actions: {
    async fetchAccessControlList({commit}, presentationId) {
      commit('setAccessControlListLoading', true);

      await axios.get(`/api/presentations/${presentationId}/accessControl`)
        .then(response => {
          commit('clearAccessControlList');
          response.data.forEach(ele => {
            commit('addAccessControl', ele)
          });
        })
        .catch(e => {
          commit('setAccessControlListApiError', e.toString())
        })
        .finally(() => {
          commit('setAccessControlListLoading', false);
        })
    },

    async addAccessControl({commit}, {presentationId, userIdentifier, accessLevel}) {
      commit('setAccessControlFormLoading', true);
      await axios.post(`/api/presentations/${presentationId}/accessControl`, {
        userIdentifier,
        accessLevel
      })
        .then(response => {
          commit('addAccessControl', response.data);
        })
        .catch(e => {
          commit('setAccessControlFormApiError', e.toString());
        })
        .finally(() => {
          commit('setAccessControlFormLoading', false);
        })
    },

    async deleteAccessControl({commit}, {id, presentationId}) {
      commit('setAccessControlListLoading', true);
      await axios.delete(`/api/presentations/${presentationId}/accessControl/${id}`)
        .then(() => {
          commit('deleteAccessControl', id);
        })
        .catch(e => {
          commit('setAccessControlListApiError', e.toString());
        })
        .finally(() => {
          commit('setAccessControlListLoading', false);
        })
    },

    async updateAccessControl({commit}, {id, presentationId, accessLevel}) {
      commit('setAccessControlListLoading', true);
      await axios.put(`/api/presentations/${presentationId}/accessControl/${id}`, {
        accessLevel
      })
        .then(response => {
          let accessControl = response.data;
          commit('updateAccessControl', {
            id: accessControl.id,
            accessLevel: accessControl.accessLevel
          });
        })
        .catch(e => {
          commit('setAccessControlListApiError', e.toString());
        })
        .finally(() => {
          commit('setAccessControlListLoading', false);
        })
    },
  }
}
import axios from 'axios'
import {deepCopy} from "@/common/utility"

export default {
    state: {
        conferenceList: [],
        conferenceForm: {
            id: '',
            name: '',
            description: '',
            date: '',
            creatorIdentifier: '',
        },
        conferenceListStatus: {
            isLoading: true,
            isApiError: false,
            apiErrorMsg: '',
        },
        conferenceFormStatus: {
            isLoading: false,
            isApiError: false,
            apiErrorMsg: '',
        },
        isSaveSuccess: false,
    },
    mutations: {
        setConferenceListLoading(state, payload) {
            if (payload) {
                state.conferenceListStatus.isApiError = false;
            }
            state.conferenceListStatus.isLoading = payload;
        },

        setConferenceListApiError(state, payload) {
            state.conferenceListStatus.isApiError = true;
            state.conferenceListStatus.apiErrorMsg = payload;
        },

        setConferenceFormLoading(state, isLoading) {
            state.conferenceFormStatus.isLoading = isLoading;
        },

        setConferenceFormApiError(state, msg) {
            state.conferenceFormStatus.isApiError = true;
            state.conferenceFormStatus.apiErrorMsg = msg;
        },

        setConferenceList(state, payload) {
            state.conferenceList = payload;
        },

        addToConferenceList(state, payload) {
            state.conferenceList.push(payload);
        },

        deleteFromConferenceList(state, payload) {
            const index = state.conferenceList.findIndex(conference => conference.id === payload);
            state.conferenceList.splice(index, 1)
        },

        updateConferenceListWith(state, payload) {
            state.conferenceList = state.conferenceList.map(conference => {
                if (conference.id === payload.id) {
                    return payload
                }
                return conference
            });
        },
        setConferenceForm(state, payload) {
            state.conferenceForm = payload;
        },

        resetConferenceForm(state) {
            state.conferenceForm.id = '';
            state.conferenceForm.name = '';
            state.conferenceForm.description = '';
            state.conferenceForm.date = '';
            state.conferenceFormStatus.isLoading = false;
            state.conferenceFormStatus.isApiError = false;
            state.conferenceFormStatus.apiErrorMsg = '';
        },
        setSaveSuccess(state, success) {
          state.isSaveSuccess = success;
        },
        setConferenceFormField(state, {field, value}) {
            state.conferenceForm[field] = value
        },
    },

    actions: {
        async getConferenceList({commit}) {
            axios.get('/api/conferences')
                .then(response => {
                    commit('setConferenceList', response.data)
                })
                .catch(e => {
                    commit('setConferenceListApiError', e.toString());
                })
                .finally(() => {
                    commit('setConferenceListLoading', false);
                })
        },
        async getConference({commit}, conferenceId) {
            commit('setConferenceFormLoading', true);
            await axios.get(`/api/conferences/${conferenceId}`)
                .then(response => {
                    commit('setConferenceForm', response.data)
                })
                .catch(e => {
                    commit('setConferenceFormApiError', e.toString());
                })
                .finally(() => {
                    commit('setConferenceFormLoading', false);
                });
        },

        async updateConference({commit, state}) {
            commit('setConferenceFormLoading', true);
            await axios.put('/api/conferences/' + state.conferenceForm.id, state.conferenceForm)
                .then(response => {
                    commit('updateConferenceListWith', response.data)
                })
                .catch(e => {
                    commit('setConferenceFormApiError', e.toString());
                })
                .finally(() => {
                    commit('setConferenceFormLoading', false);
                })
        },

        async saveConference({commit, state}) {
            commit('setConferenceFormLoading', true);
            await axios.post('/api/conferences', state.conferenceForm)
                .then(response => {
                    commit('addToConferenceList', deepCopy(response.data));
                    commit('setConferenceForm', deepCopy(response.data))
                    commit("setSaveSuccess", true);
                })
                .catch(e => {
                    commit('setConferenceFormApiError', e.toString());
                    commit("setSaveSuccess", false);
                })
                .finally(() => {
                    commit('setConferenceFormLoading', false);
                })
        },

        async deleteConference({commit}, payload) {
            commit('setConferenceFormLoading', true);
            await axios.delete('/api/conferences/' + payload)
                .then(() => {
                    commit('deleteFromConferenceList', payload);
                    commit('resetConferenceForm')
                })
                .catch(e => {
                    commit('setConferenceFormApiError', e.toString());
                })
                .finally(() => {
                    commit('setConferenceFormLoading', false);
                })
        }
    }
};
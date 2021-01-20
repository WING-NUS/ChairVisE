// import { deepCopy } from '@/utils/utility'
import Axios from 'axios'
import router from '@/router'
import { getUserEmail } from '@/utils/authUtils'

export default {
  state: {
    presentationId: 0,
    presentationSummary: {
      id: 0,
      name: '',
      author: '',
      description: '',
      isPublic: false
    },
    conferenceId: 0,
    editable: false,
    sectionList: []
  },
  mutations: {
    setPresentationSummary (state, payload) {
      state.presentationSummary.name = payload.name
      state.presentationSummary.description = payload.description
    },
    updatePresentation (state, payload) {
      // after getting from db
      // console.log(payload)
      state.presentationId = payload.id
      state.presentationSummary = {
        id: payload.id,
        name: payload.name,
        author: payload.chair.name,
        description: payload.description,
        isPublic: payload.public
      }
      state.conferenceId = payload.conferenceId
      state.editable = payload.chair.email === getUserEmail()
      state.sectionList = payload.sectionList ? JSON.parse(payload.sectionList) : []
    },
    clearSectionList (state) {
      state.sectionList = []
    },
    addSection (state, payload) {
      state.sectionList.push(payload)
    },
    deleteSection (state, num) {
      state.sectionList.splice(num, 1)
    },
    updateChartData (state, { id, result }) {
      for (const k in result) {
        state.sectionList[id][k] = result[k]
      }
    },
    clearPresentation (state) {
      state = {
        presentationId: 0,
        presentationSummary: {
          id: 0,
          name: '',
          author: '',
          description: '',
          isPublic: false
        },
        conferenceId: 0,
        editable: false,
        sectionList: []
      }
    },
    setPresentationPublic (state, payload) {
      state.presentationSummary.isPublic = payload.isPublic
    }
  },
  actions: {
    async getPresentation ({ state, commit }, id) {
      await Axios.get('/api/presentation/' + id).then(res => {
        commit('updatePresentation', res.data)
      }).catch(err => {
        console.log(err)
      })
    },
    async updatePresentationSummary ({ state, commit }, payload) {
      await Axios.patch('/api/presentation', null, { params: payload }).then(res => {
        commit('updatePresentation', res.data)
      }).catch(err => {
        console.log(err)
      })
    },
    async deletePresentation ({ state, commit }, payload) {
      await Axios.delete('/api/presentation', payload).then(res => {
        commit('clearPresentation')
        router.push('ConferenceList')
      }).catch(err => {
        console.log(err)
      })
    },
    async updatePresentationSection ({ state, commit }) {
      await Axios.post('/api/presentation', {
        id: state.presentationId,
        sectionList: JSON.stringify(state.sectionList)
      }).catch(err => [
        console.log(err)
      ])
    },
    async setPresentationPublic ({ state, commit }, payload) {
      await Axios.post('/api/chairhub', null, { params: payload }).then(res => {
        commit('setPresentationPublic', payload.isPublic)
      }).catch(err => [
        console.log(err)
      ])
    }
  }
}

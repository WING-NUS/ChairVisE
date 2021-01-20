import { deepCopy } from '@/utils/utility'
import importDataUtils from '@/utils/importDataUtils'
import Axios from 'axios'
import router from '@/router/index'

export default {
  state: {
    conferenceList: [
      //   {
      //   id: 10,
      //   name: 'NLPC',
      //   year: 2020,
      //   description: 'demo conference',
      //   presentations: [],
      //   records: {
      //     author: null,
      //     review: null,
      //     submission: null
      //   }
      // }
    ]
  },
  mutations: {
    setConferenceList (state, payload) {
      state.conferenceList = deepCopy(payload)
    },
    addConference (state, payload) {
      state.conferenceList.push(deepCopy(payload))
    },
    setConference (state, payload) {
      state.conferenceList.forEach(c => {
        if (c.id === payload.id) {
          c = deepCopy(payload)
        }
      })
    },
    addPresentationToConference (state, payload) {
      state.conferenceList.forEach(c => {
        if (c.id === payload.conferenceId) {
          if (c.presentations) {
            c.presentations.push(payload)
          } else {
            c.presentations = [payload]
          }
        }
      })
    },
    deleteConference (state, payload) {
      for (const i in state.conferenceList) {
        if (state.conferenceList[i].id === payload.id) {
          state.conferenceList.splice(i, 1)
          return
        }
      }
    }
  },
  actions: {
    async getConferenceList ({ commit }) {
      await Axios.get('/api/conference', { headers: { Authorization: window.sessionStorage.getItem('token') } }).then(res => {
        const tmpList = []
        for (const i in res.data) {
          tmpList.push({
            id: res.data[i].id,
            name: res.data[i].name,
            year: res.data[i].year,
            description: res.data[i].description,
            presentations: res.data[i].presentations.map(p => {
              return {
                id: p.id,
                name: p.name
              }
            }),
            chair: res.data[i].chair
          })
          commit('setConferenceList', tmpList)
          const record = JSON.parse(res.data[i].records)
          console.log(record)
          const fileType = record.fileType
          const dbName = res.data[i].id
          if (record.author) {
            const tmpAuthor = importDataUtils.dataMappingHandler(record.author, fileType, 0)
            importDataUtils.saveIndexedDB(tmpAuthor, 0, dbName)
          }
          if (record.review) {
            const tmpReview = importDataUtils.dataMappingHandler(record.review, fileType, 1)
            importDataUtils.saveIndexedDB(tmpReview, 1, dbName)
          }
          if (record.submission) {
            const tmpSubmission = importDataUtils.dataMappingHandler(record.submission, fileType, 2)
            importDataUtils.saveIndexedDB(tmpSubmission, 2, dbName)
          }
        }
      })
    },
    async addConference ({ commit, state }, payload) {
      await Axios.put('/api/conference', payload, {
        headers:
          {
            Authorization: window.sessionStorage.getItem('token')
          }
      })
        .then(response => {
          commit('addConference', response.data)
        }).catch(err => {
          console.log(err)
        })
    },
    async updateConference ({ commit, state }, payload) {
      await Axios.patch('/api/conference', payload).then(res => {
        commit('setConference', deepCopy(res.data))
      })
    },
    async addPresentation ({ commit, state }, payload) {
      await Axios.put('/api/presentation', {
        name: payload.presentationName,
        description: payload.description,
        conferenceId: payload.conferenceId
      }).then(res => {
        commit('addPresentationToConference', res.data)
        router.push({
          name: 'PresentationDetail',
          params: res.data
        })
      }).catch(err => {
        console.log(err)
      })
    },
    async deleteConference ({ commit, state }, payload) {
      if (payload.id) {
        await Axios.delete('/api/conference', { params: { id: payload.id } }).then(res => {
          // router.push(0)
          commit('deleteConference', { id: payload.id })
        }).catch(err => {
          console.log(err)
        })
      }
    }
  }
}

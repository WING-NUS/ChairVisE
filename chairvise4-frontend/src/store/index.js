import Vue from 'vue'
import Vuex from 'vuex'
import presentation from './presentation'
import conference from './conference'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    presentation,
    conference
  }
})

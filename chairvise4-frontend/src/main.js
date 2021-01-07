import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/global.css'
import './plugins/element.js'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/en'
import Axios from 'axios'

Vue.config.productionTip = false
Vue.use(ElementUI, { locale })

Axios.defaults.baseURL = 'http://localhost:8000'

Axios.interceptors.request.use(config => {
  console.log(config)
  config.headers.common.Authorization = window.sessionStorage.getItem('token')
  return config
}, function (error) {
  console.info('error: ')
  console.info(error)
  return Promise.reject(error)
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

import Vue from 'vue'
import Router from 'vue-router'
import {ID_NEW_PRESENTATION} from "@/common/const";

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/home',
      name: 'home',
      meta: {
        title: 'Home Page'
      },
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "home" */ './views/Home.vue')
    },
    {
      path: '/analyze',
      redirect: '/analyze/' + ID_NEW_PRESENTATION,
      meta: {
        title: 'New Analyze Page'
      },
    },
    {
      path: '/analyze/:id',
      name: 'analyze',
      meta: {
        title: 'Analyze Detail Page'
      },
      component: () => import(/* webpackChunkName: "analyze" */ './views/Analyze.vue'),
      props: true
    },
    {
      path: '/importData',
      name: 'importData',
      meta: {
        title: 'Import Data Page'
      },
      component: () => import(/* webpackChunkName: "importData" */ './views/ImportData.vue')
    },
    {
      path: '/',
      redirect: '/home'
    }
  ]
})

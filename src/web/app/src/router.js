import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/home',
      name: 'home',
      meta: {
        title: 'Home Page',
      },
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "home" */ './views/Home.vue')
    },
    {
      path: '/userGuide',
      name: 'userGuide',
      meta: {
        title: 'User Guide',
      },
      component: () => import(/* webpackChunkName: "userGuide" */ './views/UserGuide.vue')
    },
    {
      path: '/analyze/create',
      meta: {
        title: 'Create Presentation Page',
      },
      component: () => import(/* webpackChunkName: "analyze" */ './views/NewPresentation.vue'),
      props: true
    },
    {
      path: '/analyze',
      name: 'analyze',
      meta: {
        title: 'My Created Presentations',
      },
      component: () => import(/* webpackChunkName: "analyze" */ './views/Analyze.vue'),
      props: true
    },
    {
      path: '/analyze/:id',
      name: 'section',
      meta: {
        title: 'Section Page',
      },
      component: () => import(/* webpackChunkName: "analyze" */ './views/PresentationSection.vue'),
      props: true
    },
    {
      path: '/conference/add',
      meta: {
        title: 'Add Conference Page',
      },
      component: () => import(/* webpackChunkName: "conference" */ './views/NewConference.vue'),
      props: true
    },
    {
      path: '/conference',
      name: 'conference',
      meta: {
        title: 'My Conferences',
      },
      component: () => import(/* webpackChunkName: "analyze" */ './views/ViewConferences.vue'),
      props: true
    },
    {
      path: '/conference/:id',
      name: 'details',
      meta: {
        title: 'Conference Details Page',
      },
      component: () => import(/* webpackChunkName: "analyze" */ './views/ConferenceSection.vue'),
      props: true
    },
    {
      path: '/importData',
      name: 'importData',
      meta: {
        title: 'Import Data Page',
      },
      component: () => import(/* webpackChunkName: "importData" */ './views/ImportData.vue')
    },
    {
      path: '/',
      redirect: '/home'
    }
  ],
  scrollBehavior () {
    return { x: 0, y: 0 }
  }
})

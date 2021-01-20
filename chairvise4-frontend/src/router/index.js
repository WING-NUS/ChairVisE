import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import ConferenceList from '@/views/ConferenceList'
import WordCloudDemo from '@/components/presentations/PresentationDetails/WorldCloudDemo'
import ImportData from '@/views/ImportData'
import BarChartDemo from '@/components/presentations/PresentationDetails/BarChartDemo'
import PieChartDemo from '@/components/presentations/PresentationDetails/PieChartDemo'
import PresentationDetail from '@/views/PresentationDetail'
import SectionListEdit from '@/components/presentations/SectionListEdit'
import NewPresentation from '@/views/NewPresentation'
import ChairHub from '@/views/ChairHub'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/home',
    redirect: '/'
  },
  {
    path: '/ConferenceList',
    name: 'ConferenceList',
    component: ConferenceList
  },
  {
    path: '/import',
    name: 'ImportData',
    component: ImportData
  },
  {
    path: '/import/:id',
    name: 'ImportData',
    component: ImportData
  },
  {
    path: '/WordCloudDemo',
    name: 'WordCloudDemo',
    component: WordCloudDemo
  },
  {
    path: '/BarChartDemo',
    name: 'BarChartDemo',
    component: BarChartDemo
  },
  {
    path: '/PirChartDemo',
    name: 'PieChartDemo',
    component: PieChartDemo
  },
  {
    path: '/presentation/:id',
    name: 'PresentationDetail',
    component: PresentationDetail
  },
  {
    path: '/NewPresentation',
    name: 'NewPresentation',
    component: NewPresentation
  },
  {
    path: '/ChairHub',
    name: 'ChairHub',
    component: ChairHub
  },
  {
    path: '/testsection',
    name: 'SectionListEdit',
    component: SectionListEdit
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/') {
    return next()
  }
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) {
    return next('/')
  }
  next()
})

export default router

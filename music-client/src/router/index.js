import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/pages/Home'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/login',
      component: resolve => require(['../pages/login.vue'],resolve)
    },
    {
      path: '/registered',
      component: resolve => require(['../pages/registered.vue'],resolve)
    },
    {
      path: '/songList',
      component: resolve => require(['../pages/songListPage.vue'],resolve)
    },
    {
      path: '/singer',
      component: resolve => require(['../pages/singerPage.vue'],resolve)
    },
    {
      path: '/myMusic',
      component: resolve => require(['../pages/myMusicPage.vue'],resolve)
    },
    {
      path: '/search',
      component: resolve => require(['../pages/searchPage.vue'],resolve)
    },
    {
      path: '/detail',
      component: resolve => require(['../pages/detailPage.vue'],resolve)
    },
    {
      path: '/setting',
      component: resolve => require(['../pages/userSetting.vue'],resolve)
    },
    {
      path: '/singerDetail',
      component: resolve => require(['../pages/singerDetail.vue'],resolve)
    },
    {
      path: '/songlistDetail',
      component: resolve => require(['../pages/songlistDetail.vue'],resolve)
    },
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})

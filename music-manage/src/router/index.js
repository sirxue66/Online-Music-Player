import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: resolve => require(['../pages/Login.vue'], resolve)
    },
    {
      path: '/home',
      component: resolve => require(['../components/Home.vue'], resolve),
      children: [
        {
          path: '/manage',
          component: resolve => require(['../pages/managePage.vue'], resolve)
        },
        {
          path: '/consumer',
          component: resolve => require(['../pages/consumerPage.vue'], resolve)
        },
        {
          path: '/singer',
          component: resolve => require(['../pages/singerPage.vue'], resolve)
        },
        {
          path: '/songlist',
          component: resolve => require(['../pages/songListPage.vue'], resolve)
        },
        {
          path: '/songs',
          component: resolve => require(['../pages/songPage.vue'], resolve)
        },
        {
          path: '/listsong',
          component: resolve => require(['../pages/listSongPage.vue'], resolve)
        },
        {
          path: '/comment',
          component: resolve => require(['../pages/comment.vue'], resolve)
        }
      ]
    }

  ]
})

import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/oauth2/kakao',
      name: 'oauth2-kakao',
      component: () => import('../views/Oauth2KakaoView.vue')
    },
    {
      path: '/oauth2/google',
      name: 'oauth2-google',
      component: () => import('../views/Oauth2GoogleView.vue')
    },
    {
      path: '/oauth2/naver',
      name: 'oauth2-naver',
      component: () => import('../views/Oauth2NaverView.vue')
    }
  ]
})

export default router

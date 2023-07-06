import Vue from 'vue'
import VueRouter from 'vue-router'
import DashBoard from '@/views/DashBoard.vue'
import Login from '@/views/Login.vue'
import store from '../store/index.js';


Vue.use(VueRouter)

const requireAuth = (to, from, next) => {
  if (!store.state.authenticated) {
    console.log("teste1")
    next('/login');
  } else {
    console.log("ola")
    next();
  }
};

const routes = [
  {
    path: '/',
    name: 'Despesas',
    component: DashBoard,
    beforeEnter: requireAuth,
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
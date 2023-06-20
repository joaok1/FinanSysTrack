import Vue from 'vue'
import VueRouter from 'vue-router'
import DashBoard from '@/views/DashBoard.vue'
import Relatorio from '@/views/relatorio.vue'
import Financiamento from '@/views/Financiamento.vue'
import FinanciamentoDashBoard from '@/views/FinanciamentoDashBoard.vue'
import Quitacao from '@/views/Quitacao.vue'
import Financiamentos from '@/views/Financiamentos.vue'
import Despesas from '@/views/Despesas.vue'
import Categorias from '@/views/Tipo.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Despesas',
    component: Despesas,
    children: [
      {
        path: '/',
        name: 'Despesas',
        component: DashBoard,
      },
      {
        path: '/Relatorio',
        name: 'Relatorio',
        component: Relatorio,
      },
      {
        path: '/RegistroDespesas',
        name: 'RegistroDespesas',
        component: Categorias,
      }
    ]
  },

  {
    path: '',
    name: 'financiamento',
    component: Financiamento,
      children: [
        {
          path: '/financiamento',
          name: 'financiamento',
          component: FinanciamentoDashBoard
        },
        {
          path: '/Quitacao',
          name: 'quitacao',
          component: Quitacao
        },
        {
          path: '/Registrofinanciamentos',
          name: 'Registrofinanciamentos',
          component: Financiamentos,
        }
      ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
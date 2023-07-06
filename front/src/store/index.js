import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router'
import router from '@/router';
import axios from 'axios';
import jwtDecode from 'jwt-decode';

Vue.use(VueRouter)
Vue.use(Vuex);

const API_URL = 'http://localhost:1081/api/usuarios/auth';

export default new Vuex.Store({
  state: {
    authenticated: false,
  },
  mutations: {
    setAuthenticated(state, authenticated) {
      state.authenticated = authenticated;
    },
  },
  actions: {
    async login({ commit }, credentials) {
      try {
        const response = await axios.post(`${API_URL}`, credentials);
        console.log(response)
        const token = response.data.token;
        const user = jwtDecode(token);
        // Armazene o token e as informações do usuário no armazenamento local (local storage ou cookies)
        localStorage.setItem('token', token);
        localStorage.setItem('user', JSON.stringify(user));
        console.log("localStorege", localStorage);
        commit('setAuthenticated', true);
        router.push({
          name:'Despesas'
        })
      } catch (error) {
        console.log(error);
        // Lidar com erros de autenticação, exibir mensagens de erro etc.
      }
    },
    logout({ commit }) {
      // Coloque aqui a lógica de logout e atualize o estado 'authenticated' para false em caso de sucesso
      commit('setAuthenticated', false);
    },
  },
});
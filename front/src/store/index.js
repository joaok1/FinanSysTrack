import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router'
import router from '@/router';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
import createPersistedState from 'vuex-persistedstate';
import Cookies from 'js-cookie';

Vue.use(VueRouter)
Vue.use(Vuex);

const API_URL = 'http://localhost:1081/api/usuarios/auth';
const API_URL_VERIFY = 'http://localhost:1081/api/usuarios/validatorUser';

const TOKEN_COOKIE_KEY = 'token';
const USER_COOKIE_KEY = 'user';

export default new Vuex.Store({
  state: {
    authenticated: false,
  },
  mutations: {
    setAuthenticated(state, authenticated) {
      state.authenticated = authenticated;
    },
    restoreAuthentication(state) {
      const token = Cookies.get(TOKEN_COOKIE_KEY);
      const user = JSON.parse(Cookies.get(USER_COOKIE_KEY));
      
      if (token && user) {
        state.authenticated = true;
        // Faça qualquer manipulação adicional necessária com o token ou informações do usuário
      }
    },
    clearAuthentication(state) {
      Cookies.remove(TOKEN_COOKIE_KEY);
      Cookies.remove(USER_COOKIE_KEY);
      state.authenticated = false;
    },
  },
  actions: {
    async login({ commit }, credentials) {
      try {
        const response = await axios.post(`${API_URL}`, credentials);
        const token = response.data.token;
        const user = jwtDecode(token);
        console.log("", user);
        // Armazene o token e as informações do usuário no cookie
        Cookies.set(TOKEN_COOKIE_KEY, token, { expires: 1, secure: true });
        Cookies.set(USER_COOKIE_KEY, JSON.stringify(user), { expires: 1, secure: true });
        commit('setAuthenticated', true);
        router.push({ name: 'Despesas' });
        return user;
      } catch (error) {
        console.log('ola')
        console.log(error);
        // Lidar com erros de autenticação, exibir mensagens de erro etc.
      }
    },
    async logout({ commit }) {
      Cookies.remove(TOKEN_COOKIE_KEY);
      Cookies.remove(USER_COOKIE_KEY);
      router.push({ name: 'Login' });
      commit('clearAuthentication');
    },
  },
  plugins: [
    createPersistedState({
      key: 'your-key-name',
      storage: {
        getItem: (key) => Cookies.get(key),
        setItem: (key, value) => Cookies.set(key, value),
        removeItem: (key) => Cookies.remove(key),
      },
      reducer: (state) => ({
        authenticated: state.authenticated,
      }),
    }),

   async function verifyUserExpired({ commit },to, from, next) {
      const response = await axios.get(`${API_URL_VERIFY}/${Cookies.get(TOKEN_COOKIE_KEY)}`)
      if(response.data === false) {
        Cookies.remove(TOKEN_COOKIE_KEY);
        Cookies.remove(USER_COOKIE_KEY);
        commit('clearAuthentication');
        next({ name: 'Login' });
      }

    }
  ],
});

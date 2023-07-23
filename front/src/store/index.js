import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router'
import router from '@/router';
import axios from 'axios';
import jwtDecode from 'jwt-decode';
import createPersistedState from 'vuex-persistedstate';
import Cookies from 'js-cookie';
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en'

Vue.use(Element, { locale })
Vue.use(VueRouter)
Vue.use(Vuex);

const API_URL = 'http://localhost:1081/api/usuarios/auth';
const API_URL_VERIFY = 'http://localhost:1081/api/usuarios/validatorUser';

const TOKEN_COOKIE_KEY = 'token';
const USER_COOKIE_KEY = 'user';
const DADOS_USUARIO = 'dados_usuario';


  function showLoading() {
    const loadingInstance = Element.Loading.service({
      lock:true,
      fullscreen: true, // Exibir o "loading" em tela cheia
      text: 'Carregando...', // Texto opcional do "loading",
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 1.7)',
    });
    return loadingInstance;
    }
  function hideLoading() {
    // eslint-disable-next-line no-undef
    const loadingInstance = showLoading()
    loadingInstance.close();
    }
  function messageError() {
    Element.Notification.error({
      message: "Usuario inválido!",
    })
  }
  function messageSucess() {
    Element.Notification.success({
      message: "Seja bem-vindo!",
    })
  }
  

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
        const response = await axios.post(`${API_URL}`, credentials)
        const token = response.data.token;
        const user = jwtDecode(token);
        // Armazene o token e as informações do usuário no cookie
        Cookies.set(TOKEN_COOKIE_KEY, token, { expires: 1, secure: true });
        Cookies.set(USER_COOKIE_KEY, JSON.stringify(user), { expires: 1, secure: true });
        commit('setAuthenticated', true);
        const getUserCookie = Cookies.get('user');
        console.log(getUserCookie);
        const userCookie = JSON.parse(getUserCookie);
        const usuario = userCookie.sub;
        Cookies.set(DADOS_USUARIO, usuario, { expires: 1, secure: true });
        // eslint-disable-next-line no-undef
        showLoading(),
        setTimeout(() => {
          // eslint-disable-next-line no-undef
          hideLoading(),
          messageSucess()
          router.push({ name: 'Despesas' });
        },2000)
        return user;
      } catch (error) {
        messageError()
        console.error(error);
      }
    },
    async logout({ commit }) {
      Cookies.remove(TOKEN_COOKIE_KEY);
      Cookies.remove(USER_COOKIE_KEY);
      router.push({ name: 'Login' });
      commit('clearAuthentication');
    }
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

  async function verifyUserExpired({ commit }) {
      const response = await axios.get(`${API_URL_VERIFY}/${Cookies.get(TOKEN_COOKIE_KEY)}`)
      if(response.data === false) {
        Cookies.remove(TOKEN_COOKIE_KEY);
        Cookies.remove(USER_COOKIE_KEY);
        Cookies.remove(DADOS_USUARIO);
        commit('clearAuthentication');
      }
    }
  ],
});

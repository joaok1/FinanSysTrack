 /* eslint-disable */
import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import './plugins/element.js'
import vBlur from 'v-blur'
import VueApexCharts from 'vue-apexcharts'
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import VueMask from 'v-mask';
import 'vue-material/dist/theme/default.css'
import vuetify from './plugins/vuetify'

// import 'vue-material/dist/theme/default.css'
Vue.use(VueApexCharts)
Vue.use(vBlur)
Vue.use(VueMaterial)
Vue.use(VueMask)
Vue.component('apexchart', VueApexCharts)
Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')

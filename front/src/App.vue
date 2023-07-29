<template lang="pug">
div
  aside
    v-app
      v-main
        <router-view />
  footer
    Footer(style="position:fixed; bottom:0; width:100%;")
</template>
  
  <script>
  import { mapActions } from 'vuex'
  import DashBoard from './views/DashBoard.vue'
  import Footer from "./components/Footer.vue"
  
  export default {
    name: 'app',
    components: {
      DashBoard,
      Footer
      },
      data() {
        return {
        isActive: false,
        listCard: {
          saldo: null,
          entrada: null,
          total: null,
          mes: null
        },
        list: [],
        dialogVisible: false,
        id: null,
        dolar: null,
        cotacao: null,
        findAll: [],
        financeiro: {
          mes: null,
          mei: null,
          bancoDigio: null,
          bancoBradesco: null,
          bancoBv: null,
          padaria: null,
          agua: null,
        }
        }
      },
    methods: {
      ...mapActions({
      salvarConta: 'dashboard/dadosSeries'
      }),
      async verCotacao() {
        const axios = require('axios').default
        this.dolar = await axios.get('https://economia.awesomeapi.com.br/json/last/USD-BRL,BTC-BRL')
        this.cotacao = this.dolar.data;
      }
    },
  
    async mounted() {
        await this.verCotacao();
      }
    }
  </script>
  
<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
  }
  
  
  .labelCard{
    font-weight: bolder;
    color: #fff;
  }
  
  .widthCard {
    margin-top: 2px;
    width: 200px;
    font-size: 16px;
    align-items: center;
    text-align: center;
    color: #fff;
  }



  </style>
  
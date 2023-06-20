<template lang="pug">
div
  div(style="display:flex;")
    el-col(:span="2")
      h2(style="color:#fff; font-weight:bolder; margin-top:2.5rem; text-align:center;")
        <span style ="color:red">Miranda</span><span style ="color:#fff">Dev</span>
      div.navbar
        el-row
          div(@click='despesas()' :class="{ active: $route.path === '/Despesas' || $route.path ===  '/relatorio' || $route.path ===  '/RegistroDespesas' || $route.path ===  '/Categorias' }").rotate
            el-button(type='text')
              i(style="font-weight:bold; color:#fff; font-size:14px;",class='el-icon-s-data' )
              span(style="font-weight:bold; color:#fff; font-size:14px;") {{' ' + 'Home.Expenses'}}
        el-row
          div(@click='financiamento' :class="{ active: $route.path === '/financiamento' || $route.path === '/Quitacao' || $route.path === '/Registrofinanciamentos'}").rotate
            el-button(type='text')
              i(style="font-weight:bold; color:#fff; font-size:14px;",class='el-icon-wallet')
              span(style="font-weight:bold; color:#fff; font-size:14px;") {{' ' + 'Home.Financing '}}
    el-col(:span="22")
      div.dashboard
        <router-view />

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
      dollar() {
        this.$notify.info({
          title: 'Cotação Dolar',
          message: this.cotacao.USDBRL.code + '/' + this.cotacao.USDBRL.codein + ': ' + this.cotacao.USDBRL.ask
      })},
      btc() {
        this.$notify.info({
          title: 'Cotação BitCoin',
          message: this.cotacao.BTCBRL.code + '/' + this.cotacao.BTCBRL.codein + ': ' + this.cotacao.BTCBRL.ask
      })},
      async verCotacao() {
        const axios = require('axios').default
        this.dolar = await axios.get('https://economia.awesomeapi.com.br/json/last/USD-BRL,BTC-BRL')
        this.cotacao = this.dolar.data;
      },
      relatorio() {
        this.$router.push({
        name: 'despesas'
        })
      },
      dadosEmpresa() {
        this.$router.push({
          name: 'empresa'
        })
      },
      registroDespesas() {
        this.$router.push({
          name:'registroDespesas'
        })
      },
      despesas() {
        this.$router.push({
          name:'Despesas',
        })
      },
      financiamento() {
        this.$router.push({
          name:'financiamento'
        })
      },
      dashBoard() {
        this.$router.push({
          name: 'DashBoard'
        })
      },
      async listar() {
        const find = await this.axios.get(`http://localhost:1081/financeiro/listar/${1}`);
        this.findAll = find.data;
      },
      async botaoAtivo() {
      return this.findAll.length > 0;
      },
      async salvar() {
        this.axios.post('http://localhost:1081/financeiro/adicionar', this.financeiro );
      }
    },
  
    async mounted() {
        await this.verCotacao();
        await this.listar();
        await this.botaoAtivo()
      }
    }
  </script>
  
  <style>
    .navbar{
      margin-top: 10px;
      height: 100%; 
      width: 94%;
      margin-left: 10px;
      text-align:start;
    }

    .dashboard {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    justify-content: center;
    width: 99%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    align-items:center;
    position: relative;
    padding-right: 10px;
    margin-top: 2rem;
    padding: 5px;
    border-radius: 20px;
  }
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    margin-top: 40px;
  }
  header{
    justify-content: flex-start;
    display: flex;
    height: 40px;
    align-items: center;
    text-align: center;
    color: #fff;
  }
  header el-col{
      color: aliceblue;
      align-items: center;
  }
  .margin{
    box-shadow: rgba(50, 50, 93, 0.25) 0px 13px 27px -5px, rgba(0, 0, 0, 0.3) 0px 8px 16px -8px;
    margin-right: 10px;
    margin-bottom: 10px;
    margin-left: 10px;
    border-radius: 20px;
    justify-content: space-between;
  }
  .card {
    justify-content: center;
    align-items: center;
    display: flex;
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
  .justify {
      justify-content: space-between;
      display: flex;
  }


  .rotate{
    margin-top: 10px;
    padding: 10px;
    margin-bottom: 10px;
    font-size: 15px;
    cursor: pointer;
  }
  .active{
    background-color: rgba(0, 0, 0, 0.5);
    color: #fff;
    font-weight: bolder;
    font-size: 16px;
    border-radius: 10px 0 0 10px;
  }
  .activeRouter{
    color: #fff;
    font-weight: bolder;
    font-size: 16px;
  }

  </style>
  
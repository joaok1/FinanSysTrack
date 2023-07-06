<template lang="pug">
div
  v-app
    v-main
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
      despesas() {
        this.$router.push({
          name:'Despesas',
        })
      },
      relatorio() {
        this.$router.push({
        name: 'Relatorio'
        })
      },
      dadosEmpresa() {
        this.$router.push({
          name: 'empresa'
        })
      },
      registroDespesas() {
        this.$router.push({
          name:'RegistroDespesas'
        })
      },
      dashBoard() {
        this.$router.push({
          name: 'DashBoard'
        })
      },
      financiamento() {
        this.$router.push({
          name: 'financiamento'
        })
      },
      quitacao() {
        this.$router.push({
          name: 'quitacao'
        })
      },
      registrofinanciamentos() {
        this.$router.push({
          name: 'Registrofinanciamentos'
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
  
<template lang="pug">
div
  div(style="display:flex;")
    el-col(:span="2")
      div.navbar
        h2(style="color:#fff; font-weight:bolder; margin-top:2.5rem; text-align:center;")
          <span style ="color:red">Miranda</span><span style ="color:#fff">Dev</span>
          el-row
            div(@click='despesas()' :class="{ active: $route.path === '/' || $route.path ===  '/Relatorio' || $route.path ===  '/RegistroDespesas' || $route.path ===  '/Categorias' }").rotate
              el-button(type='text')
                i(style="font-weight:bold; color:#fff; font-size:18px; justify-content:center; position:relative; display:flex; margin-bottom:5px;",class='el-icon-s-data' )
                span(style="color:#fff; font-size:18px;") {{' ' + 'Home.Expenses'}}
          el-row
            div(@click='financiamento' :class="{ active: $route.path === '/financiamento' || $route.path === '/Quitacao' || $route.path === '/Registrofinanciamentos'}").rotate
              el-button(type='text')
                i(style="font-weight:bold; color:#fff; font-size:18px; justify-content:center; position:relative; display:flex; margin-bottom:5px;",class='el-icon-wallet')
                span(style="color:#fff; font-size:18px;") {{' ' + 'Home.Financing '}}
    //el-col(:span="2")
      div.navbartwo
        div(style="justify-content:center; position:relative; align-items:center; text-align:center;" v-show="$route.path === '/' || $route.path ===  '/Relatorio' || $route.path ===  '/RegistroDespesas'")
          div(style="padding:10px" :class="{ active: $route.path === '/'}")
            el-button(type='text' @click="despesas()")
                i(style="font-weight:bold; color:#fff; font-size:18px; justify-content:center; position:relative; display:flex; margin-bottom:5px;",class='el-icon-s-data' )
                span(style=" color:#fff; font-size:18px;") {{' ' + 'DashBoard'}}
          div(style="padding:10px" :class="{ active: $route.path === '/Relatorio'}")
            el-button(type='text' @click="relatorio()")
              i(style="font-weight:bold; color:#fff; font-size:18px; justify-content:center; position:relative; display:flex; margin-bottom:5px;",class="el-icon-edit" )
              span(style=" color:#fff; font-size:18px;") {{' ' + 'Relatorio'}}
          div(style="padding:10px" :class="{ active: $route.path === '/RegistroDespesas'}")
            el-button(type='text' @click="registroDespesas()")
              i(style="font-weight:bold; color:#fff; font-size:18px; justify-content:center; position:relative; display:flex; margin-bottom:5px;",class="el-icon-document-add" )
              span(style=" color:#fff; font-size:18px;") {{' ' + 'Cadastro'}}

        div(style="justify-content:center; position:relative; align-items:center; text-align:center;" v-show="$route.path === '/financiamento' || $route.path ===  '/Quitacao' || $route.path ===  '/Registrofinanciamentos'")
          div(style="padding:10px" :class="{ active: $route.path === '/financiamento'}")
            el-button(type='text' @click="financiamento()")
                i(style="font-weight:bold; color:#fff; font-size:18px; justify-content:center; position:relative; display:flex; margin-bottom:5px;",class='el-icon-s-data' )
                span(style="color:#fff; font-size:18px;") {{' ' + 'DashBoard'}}
          div(style="padding:10px" :class="{ active: $route.path === '/Quitacao'}")
            el-button(type='text' @click="quitacao()")
              i(style="font-weight:bold; color:#fff; font-size:18px; justify-content:center; position:relative; display:flex; margin-bottom:5px;",class="el-icon-edit" )
              span(style="color:#fff; font-size:18px;") {{' ' + 'Regist.Baixas'}}
          div(style="padding:10px" :class="{ active: $route.path === '/Registrofinanciamentos'}")
            el-button(type='text' @click="registrofinanciamentos()")
              i(style="font-weight:bold; color:#fff; font-size:18px; justify-content:center; position:relative; display:flex; margin-bottom:5px;",class="el-icon-document-add" )
              span(style=" color:#fff; font-size:18px;") {{' ' + 'Cadastro'}}
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
  .navbartwo{
      background-color: rgba(0, 0, 0, 0.5);
      margin-top: 10px;
      height: 98%; 
      width: 10rem;
      margin-left: 10px;
      margin-right: 10px;
      text-align:start;
      border-radius: 10px;
      position: fixed;
  }

    .navbar{
      background-color: rgba(0, 0, 0, 0.5);
      margin-top: 10px;
      height: 98%; 
      width: 10rem;
      margin-left: 5px;
      margin-right: 5px;
      text-align:start;
      border-radius: 10px;
      position: fixed;
    }

    .dashboard {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
    justify-content: center;
    max-width: 100%;
    min-height: 910px;
    background-color: rgba(0, 0, 0, 0.5);
    margin-top: 0.5rem;
    margin-left: 20px;
    margin-right: 20px;
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
    color: rgb(0, 0, 0);
  }
  header el-col{
      color: rgb(0, 0, 0);
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

    font-size: 16px;
    border-radius: 10px;
  }
  .activeRouter{
    color: #fff;
    font-size: 16px;
  }

  </style>
  
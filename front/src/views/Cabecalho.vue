eslint-disable vue/multi-word-component-names
<template lang="pug">
div
    div.dashBoard
      el-card(style="margin: 10px 10px; border-radius:10px; background-color:#409EFF;")
        div(style="display:flex; position:relative; flex-wrap:wrap; align-items:center;")
          el-col(:span="22")
            div(style="display:flex;  justify-content:flex-end;")
              el-button( plain @click="abrirModalDespesa()" icon="el-icon-s-finance" style="color:#000;") Registrar despesas
              el-button( plain icon="el-icon-tickets"  @click="dialogRelatorio = true"  style="color:#000;") Relatorio
              <el-dropdown style="margin-left:10px;">
                <el-button  plain >
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu style="width:200px; align-items:center; text-align:center;">
                  <el-button type="text" @click="centerDialog = true"  style="color:#000;">Cadastrar Despesa</el-button>
                  <el-button type="text" @click="centerDialogVisibleTable = true"  style="color:#000;">Visualizar tabela categoria</el-button>
                </el-dropdown-menu>
              </el-dropdown>
          el-col(:span="2" style="padding-left:10px")
            div(style="justify-content:flex-end; padding:10px;")
                div(style="position:relative; align-items:center; text-align:center; justify-content:center;")
                  div
                    <el-avatar :size="75"></el-avatar>
                  div
                    el-button(type="text" style="font-size:18px; color:#fff; font-weight:900;" @click="logout()") Sair
      //Inserção das categorias//
      el-dialog(title="Incluir despesa." :visible.sync="centerDialog" width="30%" center @before-close="handleClose")
            div(style="padding:10px")
                label(style="font-size:14px; margin-right:10px; font-weight:bold;") Digite o nome da despesa:
                el-input(v-model="despesasCategory.name", size="small" placeholder="Digite um nome" style="width:215px")
            div(style="padding:10px")
                label(style="font-size:14px; margin-right:10px;font-weight:bold;") Selecione a categoria da despesa:
                el-select(v-model="despesasCategory.tipo",clearable,filterable ,style="width:200px", size="small")
                    el-option(
                        v-for="select in listaTipo"
                        :key="select.id"
                        :label="select.name"
                        :value="select.id"
                    )
            
            <span slot="footer" class="dialog-footer">
                el-button(@click="centerDialog = false") Cancelar
                el-button(type="primary" @click="despesasCategoryByTipo()") Confirmar
            </span>
      //Edição
      el-dialog(title="Editar depesa", :visible.sync="centerDialogVisible", width="30%", center,:before-close="handleClose")
          div(style="padding:10px")
              label(style="font-size:14px; margin-right:10px; font-weight:bold;") Digite o nome da despesa:
              el-input(v-model="despesasCategoryEdit.name", size="small" placeholder="Digite um nome" style="width:215px")
          div(style="padding:10px")
              label(style="font-size:14px; margin-right:10px;font-weight:bold;") Selecione a categoria da despesa:
              el-select(v-model="despesasCategoryEdit.tipo",clearable,filterable ,style="width:200px", size="small")
                  el-option(
                      v-for="select in listaTipo"
                      :key="select.id"
                      :label="select.name"
                      :value="select.id"
                  )
          <span slot="footer" class="dialog-footer">
              <el-button @click="centerDialogVisible = false">Cancelar</el-button>
              <el-button type="primary" @click="despesasEdit()">Confirmar</el-button>
          </span>
      //Tabela de edição categoria
      el-dialog(title="Despesas cadastradas", :visible.sync="centerDialogVisibleTable", width="50%", center)
        div(style="display:flex; justify-content: space-between; position:relative; align-items:center;")
            div
                label(style='font-size: 20px; text-align:center; display:flex; margin-left:20px; font-weight:bolder') Listagem
            div
            el-button(type="primary" @click="centerDialog = true") Registrar despesas
        <br>
        div
          data-table(
              :pageable='pageable'
              :data="listaData.content",
              :columns='columns',
              @atualizarTabela='atualizarTabela',
              :acoes='acoes',
              @editar="editar",
              @excluir="excluirDespesasCategoria"
          )
      //Registro de despesas
      el-dialog(:visible.sync="centerDialogResgistroDespesas", width="50%", center,title="Inserir despesa",:before-close="handleClose")
        div(style="margin-right:10px; padding:10px;")
          el-button(type="text" @click="centerDialogVisibleTable = true") Visualizar tabela categoria
          div(style="display:flex; position:relative; justify-content:space-around; align-items:center; padding:10px")
            div
              span Insira a data:
              el-date-picker( type="date", placeholder="Insira a data da despesa", v-model="despesas.calendar", size="small", style="width: auto")
            div(style="width:50%")
              div(id="chart")
                apexchart(type="radialBar" :options="chartOptions" :series="series")
            div
              span Insira o valor recebido:
              el-input-number(v-model="despesas.entrada" :min="0" size="small" style="width: auto")
          div(style="display:flex; position:relative; justify-content:space-around; align-items:center;")
            div(style="padding:10px; text-align:center;")
              div(style="display:flex; position:relative; justify-content:center;")
                span(style="font-size:18px;") Valor de entrada
              div
                span(style="font-size:18px;") {{ this.despesas.entrada }}
            div(style="padding:10px; text-align:center;")
              div(style="display:flex; position:relative; justify-content:center;")
                span(style="font-size:18px;") Valor total dos gastos
              div
                span(style="font-size:18px;") {{ saida ? saida : 0 }}
            div(style="padding:10px; text-align:center;")
              div(style="display:flex; position:relative; justify-content:center;")
                span(style="font-size:18px;") Saldo
              div
              span(style="font-size:18px;") {{ saldo ? saldo : 0 }}
          div(style="display:flex; align-items:center;")
            div(style="padding:10px")
              label(style="font-size:14px; margin-right:10px;font-weight:bold;") Selecione a categoria da despesa:
              el-select(v-model="despesaId",clearable,filterable ,style="width:100%", size="small")
                el-option(
                    v-for="selectCategoria in listaCategoria"
                      :key="selectCategoria.id"
                      :label="selectCategoria.name"
                      :value="selectCategoria.id"
                )
            div(style="padding:10px")
                label(style="font-size:14px; margin-right:10px;font-weight:bold;") Insira o valor da despesa
                el-input-number(v-model="valorDespesa"  :min="0" size="small" style="width:100%")
            div(style="padding:10px; align-items:center; margin-top:0.5rem;")
                el-button(type="primary", @click="mountedDespesas()") Inserir
          div
            data-table(
              :data="arrayDespesa",
              :columns='columnsDesp',
              :acoes='acoesDespesas',
              @excluir="excluir"
            )
          div(style="position:relative; display:flex; align-items:end; justify-content:flex-end; padding-top:10px;")
              el-button(type="primary", @click="inserirDespesas()") Salvar
      //Relatorio
      el-dialog(title="Listagem", :visible.sync="dialogRelatorio", width="50%", center)
        div
            div(style='margin: 10px 20px 0px 20px; border-radius: 10px; background-color: #fff;')
                div(style='display:flex; justify-content:space-between; margin-bottom: 10px; align-items:center;')
                    div
                        label(style='font-size: 20px; text-align:center; display:flex; margin-left:20px; font-weight:bolder') Registro de entrada

                data-table(
                    :pageable='pageableListagemDespesa'
                    :data="listaDataListagemDespesas.content",
                    :columns='columnsListagemDespesas',
                    @atualizarTabela='atualizarTabelaListagemDespesa',
                    :acoes='acoesListagemDespesas',
                    @editar="editarListagemDespesas"
                    @excluir="excluirListagemDespesa",
                    @visualizar='visualizar',
                )
      el-dialog(title="Visualização das despesas", :visible.sync="visualizarDialogRelatorio", width="30%", center)
          div(style="display:flex; margin-bottom:1.5em align-items:center;")
            el-col(:span="14")
              el-switch(
                v-model="graphic"
                active-text="Modo avançado"
                style="margin-bottom:1em;")

            el-col(:span="12")
              div(style="margin-right:2em; margin-left:2em; display:flex; justify-content:end;" )
                el-button(type='primary' icon='el-icon-printer' size='small' @click='downloadPDF()', style='font-weight: bold; margin-right:20px') PDF
          div(style="margin-right:2em; margin-left:2em; margin-top:2em;" v-if="graphic")
            div(style="display:flex; align-items:center; margin-bottom:1em;")
              el-tooltip(content="Alimentação" placement="left")
                i(style="font-size:2em;").el-icon-refrigerator  
              el-progress(:text-inside="true" :stroke-width="26" :percentage="this.alimentacao ? this.alimentacao.toFixed(2) : 0"  style="width:100%;" text-color="#000" color="#005353") 
            div(style="display:flex;  align-items:center; margin-bottom:1em;")
              el-tooltip(content="Refeição" placement="left")
                i(style="font-size:2em").el-icon-burger
              el-progress(:text-inside="true" :stroke-width="26" :percentage="this.refeicao ? this.refeicao.toFixed(2) : 0"  style="width:100%" color="#140091")
            div(style="display:flex; align-items:center; margin-bottom:1em;")
              el-tooltip(content="Mobilidade" placement="left")
                i(style="font-size:2em").el-icon-truck
              el-progress(:text-inside="true" :stroke-width="26" :percentage="this.mobilidade ? this.mobilidade.toFixed(2) : 0" style="width:100%" color="#947302")
            div(style="display:flex; align-items:center; margin-bottom:1em;")
              el-tooltip(content="Farmácia" placement="left" )
                i(style="font-size:2em").el-icon-first-aid-kit
              el-progress(:text-inside="true" :stroke-width="26" :percentage="this.farmacia ? this.farmacia.toFixed(2) : 0" style="width:100%" color="#BA0C0C")
            div(style="display:flex; align-items:center; margin-bottom:1em;")
              el-tooltip(content="Diversos" placement="left")
                i(style="font-size:2em").el-icon-shopping-cart-1
              el-progress(:text-inside="true" :stroke-width="26" :percentage="this.diversos ? this.diversos.toFixed(2) : 0"  style="width:100%" color="#404280")
          data-table(
            v-if="!graphic",
            :data="visualizarRegistroDespesa.listagemDespesas",
            :columns='columnsVisualizarListagemDespesas',
          )
      el-row
        el-col(:span="6")
          div(style="display:flex; align-items:center;")
            span(style="margin-left:20px") Selecione o ano:
            el-select(v-model="ano",clearable,filterable ,style="width:200px; margin-left:10px;", size="small", v-on:change="dadosDashBoardBar()" )
              el-option(
                  v-for="select in this.listAnos"
                  :key="select"
                  :label="select"
                  :value="select"
              )
        el-col(:span="24")
          el-col(:span="18")
            <div id="chart">
              <apexchart type="bar" height="280" :options="chartOptionsDashBoard" :series="seriesDashBoard"></apexchart>
            </div>
          el-col(:span="6")
            <div id="chart">
              <apexchart type="pie" width="450" :options="chartOptionsPie" :series="seriesPie"></apexchart>
            </div>
      el-row
        el-col(:span="8")
          <div id="chart">
            <apexchart type="radar" height="260" width="100%" :options="chartOptionsRadar" :series="seriesRadar"></apexchart>
          </div>
        el-col(:span="16")
          <div id="chart">
            <apexchart type="line" height="250" :options="chartOptionsDashBoard" :series="seriesDashBoard"></apexchart>
          </div>
    </template>
<script>
  import DataTable from '@/components/DataTable.vue'
  import panel from '@/components/Panel.vue'
  import Cookies from 'js-cookie';
  import actions from '@/methods/funções'
  import axios from 'axios';

      
  export default {
    // eslint-disable-next-line vue/multi-word-component-names
    name: 'cabecalho',
    components: {
    DataTable,
    panel
  },
    data() {
      return {
        ano:null,
        listAnos:null,
        seriesRadar: [{
            name: 'Entrada',
            data: [],
          },
          {
            name: 'Saída',
            data: [],
          }],
        chartOptionsRadar: {
            colors: ['#1E00FF','#FF0000'],
            chart: {
              height: 350,
              width: '100%',
              type: 'radar',
            },
            title: {
              text: 'Relatorio Anual de entrada e saida'
            },
            xaxis: {
              categories: []
            }
          },
        seriesPie: [],
        chartOptionsPie: {
            chart: {
              width: 380,
              type: 'pie',
            },
            labels: [],
            responsive: [{
              breakpoint: 480,
              options: {
                chart: {
                  width: 350
                },
                legend: {
                  position: 'bottom'
                }
              }
            }]
          },
        seriesDashBoard: [{
            name: 'Entrada',
            data: []
            
          },{
            name: 'Saida',
            data: [],
            
          }],
          chartOptionsDashBoard: {
            annotations: {
              points: [{
                x: 'Bananas',
                seriesIndex: 0,
                label: {
                  borderColor: '#775DD0',
                  offsetY: 0,
                  style: {
                    color: '#fff',
                    background: '#775DD0',
                  },
                  text: 'Bananas are good',
                }
              }]
            },
            chart: {
              height: 350,
              type: 'bar',
            },
            plotOptions: {
              bar: {
                borderRadius: 10,
                columnWidth: '100%',
              }
            },
            dataLabels: {
              enabled: false
            },
            stroke: {
              width: 2
            },
            
            grid: {
              row: {
                colors: ['#fff', '#f2f2f2']
              }
            },
            colors: ['#1E00FF','#FF0000'],
            xaxis: {
              labels: {
                rotate: -45
              },
              categories: ["JANEIRO","FEVEREIRO","MARÇO","ABRIL","MAIO","JUNHO","JULHO","AGOSTO","SETEMBRO","OUTUBRO","NOVEMBRO","DEZEMBRO"],
              tickPlacement: 'on'
            },
            yaxis: {
              title: {
                text: 'valores',
              },
            },
            fill: {
              type: 'gradient',
              gradient: {
                shade: 'light',
                type: "horizontal",
                shadeIntensity: 0.25,
                gradientToColors: undefined,
                inverseColors: true,
                opacityFrom: 0.85,
                opacityTo: 0.85,
                stops: [50, 0, 100]
              },
            }
          },
          
        graphic:false,
        alimentacao:null,
        refeicao:null,
        mobilidade:null,
        farmacia:null,
        diversos:null,
        listCardDespesa:{
          total:null,
          saldo:null,
          entrada:null
        },
        token:Cookies.get('token'),
        user:Cookies.get('dados_usuario'),
        acoesListagemDespesas: [
          {
              text: 'Visualizar',
              codigo: 'VISUALIZAR',
              icon: 'el-icon-view'
          },
          {
              text: 'Editar',
              codigo: 'EDITAR',
              icon: 'el-icon-edit'
          },
          {
              text: 'Excluir',
              codigo: 'EXCLUIR',
              icon: 'el-icon-delete'
          }
        ],
        columnsVisualizarListagemDespesas:[
          {
            label: 'Categoria despesa',
            prop: 'despesasCategory.name'
          },
          {
            label: 'Tipo',
            prop: 'despesasCategory.tipo.name'
          },
          {
            label: 'Valor',
            prop: 'valor',
            formatter: ({ valor }) => valor.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
          }
        ],
        columnsListagemDespesas: [
          {
              label: 'Data',
              prop: 'calendar',
              formatter: ({ calendar }) => calendar || '-'
          },
          {
              label: 'Valor recebido',
              prop: 'entrada',
              formatter: ({ entrada }) => entrada.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'}) || '-'
          },
          {
              label: 'Total gastos',
              prop: 'total',
              formatter: ({ total }) => total.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'}) || '-'
          },
          {
              label: 'Saldo do mês',
              prop: 'saldo',
              formatter: ({ saldo }) => saldo.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'}) || '-'
          },
        ],
        dialogRelatorio:false,
        visualizarDialogRelatorio:false,
        valorDespesa:null,
        arrayDespesa:[],
        despesaId:null,
        listCard:{
          entrada:null,
          total:null,
          saldo:null,
          mes:null
        },
        fileList: [{
                name: 'food.jpeg',
                url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }, {
            name: 'food2.jpeg',
            url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }],
        series: [0],
        chartOptions: {
            chart: {
                type: 'radialBar',
                offsetY: -20,
                sparkline: {
                    enabled: true
                }
            },
            plotOptions: {
                max:null,
                radialBar: {
                    startAngle: -90,
                    endAngle: 90,
                    track: {
                        background: "#e7e7e7",
                        strokeWidth: '97%',
                        margin: 5, // margin is in pixels
                        dropShadow: {
                            enabled: true,
                            top: 2,
                            left: 0,
                            color: '#999',
                            opacity: 1,
                            blur: 2
                        }
                    },
                    dataLabels: {
                        name: {
                            show: false,
                        },
                        value: {
                            offsetY: -2,
                            fontSize: '22px',
                            formatter: (value) => value.toFixed(2)
                        }
                    }
                }
            },
            grid: {
                padding: {
                    top: -10
                }
            },
            fill: {
                type: 'gradient',
                gradient: {
                    shade: 'light',
                    shadeIntensity: 0.4,
                    inverseColors: false,
                    opacityFrom: 1,
                    opacityTo: 1,
                    stops: [0, 50, 53, 91]
                },
            },
            labels: ['Average Results'],
        },
        centerDialogResgistroDespesas:false,
        despesas:{
          listagemDespesas:[
              {
                despesasCategory:{
                  id:null
                },
                valor:null,
                despesas:null
              }
            ],
          calendar:null,
          mes:null,
          total:0,
          login:null,
          entrada:0,
          saldo:0,
          usuario:null
        },
        centerDialogVisibleTable:false,
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        saida:null,
        saldo:null,
        listaDataById:{
          name:null,
          tipo:{
              id:null,
              name:null
          }
        },
        centerDialog:false,
        data:null,
        pageListagemDespesas:null,
        listaDataListagemDespesas:{
          content:[]
        },
        despesasCategoryEdit:{
          id:null,
          name:null,
          tipo:null
        },
        visualizarRegistroDespesa:{},
        centerDialogVisible:false,
        bootbox:null,
        dashboardBar:[],
        despesasCategory:{
          name:null,
          tipo:{},
          usuario:null
        },
        listaData:{},
        pageable: {},
        getGraficoArea:{},
        getGraficoPie:{},
        pageableListagemDespesa:{},
        listaTipo:{},
        page:null,
        acoesDespesas: [
          {
            text: 'Excluir',
            codigo: 'EXCLUIR',
            icon: 'el-icon-trash'
          },
          {
          }
        ],
        acoes: [
          {
            text: 'Editar',
            codigo: 'EDITAR',
            icon: 'el-icon-edit',
          },
          {
            text: 'Excluir',
            codigo: 'EXCLUIR',
            icon: 'el-icon-delete'
          }
        ],
        listaCategoria:null,
        columns: [
          {
            label:'Nome',
            prop: 'name',
          },
          {
            label:'Tipo',
            prop: 'tipo.name',
          },
          {
            label:'Utilizado',
            prop: 'usage',
            // o 0 quer dizer que nao esta sendo utilizado
            formatter: ({ usage }) => usage === 1 ? <i class={'el-icon-check'}></i> : <i class={'el-icon-close'}></i>
          }
        ],
        columnsDesp: [
          {
            label:'Nome',
            prop: 'despesasCategory.name',
          },
          {
            label:'Valor',
            prop: 'valor',
          }
        ]
      }
  },
    async mounted() {
      await this.dadosDashBoardBar();
      await this.getAno();

      window.dispatchEvent(new Event('resize'));
    },
    watch: {
      propriedadeObservada: {
        immediate: true,
        async handler() {
          if (this.user) {
            await actions.dadosLogin();
          }
        },
      },
      propriedades: {
        immediate: true,
        async handler() {
          if (this.token) {
            await this.despesasByCategory();
            await this.tipo();

            await this.loader();

            window.dispatchEvent(new Event('resize'));
          }
          
        },
      },
    },
    methods: {
    handleClose(done) {
      this.$confirm('Deseja fechar o modal?')
        .then(confirm =>{
          this.despesas = {
            listagemDespesas:[
              {
                despesasCategory:{
                  id:null
                },
                valor:null,
                despesas:null
              }
            ],
            calendar: null,
            mes: null,
            total: 0,
            entrada:0,
            saldo: 0,
            usuario: null
          }
          done(confirm);
        })
        .catch(cancel =>{
          cancel
        });
    },
    atualizarTabela(newPage) {
        this.page = this.listaData.pageable.pageNumber = newPage - 1;
        this.tipo();
    },
    async logout(){
      await this.$store.dispatch('logout');
    },
    async tipo() {
      try {
        const listaData = await actions.getCategoria(this.page);
        this.listaData = listaData.data;
        const { empty, number, numberOfElements, pageable, totalElements } = this.listaData;
        this.pageable = {
          empty,
          number,
          numberOfElements,
          pageable,
          totalElements
        };
        const listaTipo = await actions.getTipo();
        this.listaTipo = listaTipo.data;
      } catch (error) {
        console.error(error);
}
},
    async despesasCategoryByTipo() {
        this.despesasCategory.usuario = this.user;
        await actions.despesasCategoryByTipo(this.despesasCategory).then(async response => {
          if(response.status === 200) {
              this.centerDialog = false
              this.despesasCategory = {};
              await this.despesasByCategory();
              this.$notify({
                title: 'Sucesso!',
                message: 'Registro salvo!',
                type: 'success'
              })
          }
        }).catch(error => {
            if(error.response.data.message === "Categoria já registrada!") {
              this.$notify.error({
                title: 'Erro!',
                message: 'Despesa já registrada!',
              })
            } else {
              this.$notify.error({
                title: 'Erro!',
                message: 'Erro ao salvar despesa',
              })
            }
        })
        await this.tipo();
    },
    async excluirDespesasCategoria(data){
      await actions.excluirDespesasCategoria(data.id).then(async () => {
        this.loader();
        this.despesasByCategory();

        this.$notify({
          title: 'Sucesso!',
          message: 'Registro Deletado!',
          type: 'success'
        });
        await this.tipo();
        }).catch(data => {
          this.$notify.error({
            title: 'Erro!',
            message: data.response.data.message
          })
        })
    },
    async editar(data){
      this.despesasCategoryEdit = {
        id: '',
        name: '',
        tipo: {
          id:null
        }
      };
      this.data = data.id;
      const listaData = await actions.getByCategoria(this.data);
      this.listaDataById = listaData.data;
      this.despesasCategoryEdit.id = this.data;
      this.despesasCategoryEdit.name = this.listaDataById.name;
      this.despesasCategoryEdit.tipo = this.listaDataById.tipo.id;
      this.centerDialogVisible = true;
      await this.tipo();
    },
    async despesasEdit(){
      await actions.editarByCategoria(this.despesasCategoryEdit).then(response => {
        if(response.status === 200) {
            this.centerDialogVisible = false;
            this.despesasCategoryEdit = {
                id: '',
                name: '',
                tipo: {
                    id:null
                }
            };
            this.$notify({
                title: 'Sucesso!',
                message: 'Registro Editado!',
                type: 'success'
            });
          }
      }).catch(error => {
        if(error.response.data.message === "Este registro não pode ser editado pois já esta sendo utilizado.") {
          this.$notify.error({
                title: 'Erro!',
                message: error.response.data.message
            })
        } else {
            this.$notify.error({
                title: 'Erro!',
                message: 'Erro ao editar registro!',
            })
        }
      })
        await this.tipo();
    },
    //Inserção das despesas
    abrirModalDespesa(){
      this.despesas = {
        listagemDespesas:[
          {
            despesasCategory:{
              id:null
            },
            valor:null,
            despesas:null
          }
        ],
        calendar: null,
        mes: null,
        total: 0,
        entrada:0,
        saldo: 0,
        usuario: null
      }
      window.dispatchEvent(new Event('resize'));
      this.centerDialogResgistroDespesas = true
    },
    async despesasByCategory() {
        const lista = await actions.getByCategoriaTable()
        this.listaCategoria = lista.data;
    },
    mountedDespesas() {
      this.listaCategoria.forEach(x => {
        if (this.despesaId === x.id) {
          var names = x.name;
          var desp = {
            despesasCategory: {
              id: this.despesaId,
              name: names
            },
            valor: this.valorDespesa
          };
          this.arrayDespesa.push(desp);
          this.saida = 0;
          this.saldo = 0;
          this.arrayDespesa.forEach(item => {
            this.saida += item.valor
            this.saldo = this.despesas.entrada - this.saida
            this.despesas.saldo = this.saldo;
            this.despesas.total = this.saida;
          });
        }
      });
      this.series.splice(0,1);
      let final = (this.saida * 100) / this.despesas.entrada
      this.series.push(final);
    },
    excluir(val) {
      const index = this.arrayDespesa.indexOf(val);
      this.arrayDespesa.splice(index, 1);
      this.saida = 0;
      for (let ind = 0; ind < this.arrayDespesa.length; ind++) {
        this.saida += this.arrayDespesa[ind].valor;
      }
      this.saldo = this.despesas.entrada - this.saida;
      this.despesas.saldo = this.saldo;
      this.despesas.total = this.saida;
      let final = (this.saida * 100) / this.despesas.entrada
      this.series.splice(0,1);
      this.series.push(final);
    },
    async inserirDespesas() {
      this.arrayDespesa.map(objec => {
        this.despesas.listagemDespesas.push(objec);
      })
      this.despesas.listagemDespesas.splice(0,1);
      this.despesas.usuario = this.user;
      await actions.inserirDespesas(this.despesas).then(response => {
        if(response.status === 200) {
          this.despesas = {
            listagemDespesas:[
              {
                despesasCategory:{
                  id:null
                },
                valor:null,
                despesas:null
              }
            ],
            calendar: null,
            mes: null,
            total: 0,
            entrada:0,
            saldo: 0,
            usuario: null
          }
          this.$notify({
            title: 'Sucesso!',
            message: 'Despesa registrada!',
            type: 'success'
          })
          this.loader()
          this.getAno();
          this.dadosDashBoardBar();
          this.centerDialogResgistroDespesas = false
        }
      }).catch(response => {
        if(response.status !== 200) {
          this.$notify.error({
              title: 'Erro!',
              message: 'Erro ao salvar registro!',
          })
        }
      })
    },
    //Listagem das despesas
    async loader() {
      const listaData = await actions.getListagemDespesas(this.pageListagemDespesas);
      this.listaDataListagemDespesas = listaData.data;
      const { empty, number, numberOfElements, pageable, totalElements } = this.listaDataListagemDespesas;
      this.pageableListagemDespesa = {
          empty,
          number,
          numberOfElements,
          pageable,
          totalElements
      };
      await this.graficoArea();
      window.dispatchEvent(new Event('resize'));
      this.$emit('excluir')
    },
    atualizarTabelaListagemDespesa(newPage) {
      this.pageListagemDespesas = this.listaDataListagemDespesas.pageable.pageNumber = newPage - 1;
      this.loader();
      this.tipo();
    },
    editarListagemDespesas(data) {
      this.data = data.id;
      this.$router.push({
          name: 'despesas',
          params: {data}
      })
    },
    async visualizar(data){
      this.idDespesa = data.id;
      this.visualizarDialogRelatorio = true;
      const dataDespesa = await actions.visualizarDespesas(data.id)
      this.visualizarRegistroDespesa = dataDespesa.data
      this.mobilidade = this.calcularPorcentagem(this.visualizarRegistroDespesa.valorMobilidade, this.visualizarRegistroDespesa.entrada)
      this.alimentacao = this.calcularPorcentagem(this.visualizarRegistroDespesa.valorAlimentacao, this.visualizarRegistroDespesa.entrada)
      this.refeicao = this.calcularPorcentagem(this.visualizarRegistroDespesa.valorRefeicao, this.visualizarRegistroDespesa.entrada)
      this.farmacia = this.calcularPorcentagem(this.visualizarRegistroDespesa.valorFarmacia, this.visualizarRegistroDespesa.entrada)
      this.diversos = this.calcularPorcentagem(this.visualizarRegistroDespesa.valorDiversos, this.visualizarRegistroDespesa.entrada)

      this.listCardDespesa = dataDespesa.data
      this.listCardDespesa.entrada = dataDespesa.data.entrada;
      this.listCardDespesa.total = dataDespesa.data.total;
      this.listCardDespesa.saldo = dataDespesa.data.saldo;
    },
    calcularPorcentagem(valor, total) {
      return (valor / total) * 100;
    },
    async downloadPDF() {
      try {
      const response = await axios({
        url: `http://localhost:1081/api/despesas/relatorioDownload/${this.idDespesa}`,
        method: 'GET',
        headers: {
          Authorization: `Bearer ${Cookies.get('token')}`
        },
        responseType: 'blob',
      });

    // Cria uma URL válida para o arquivo blob
        const url = URL.createObjectURL(new Blob([response.data], { type: 'application/pdf' }));

        // Abre o arquivo em uma nova guia
        window.open(url, '_blank');
      } catch (error) {
        console.log(error);
      }
    },   
    excluirListagemDespesa(data) {
        this.$confirm("Deseja deletar este registro?")
        .then(confirm => {
          window.dispatchEvent(new Event('resize'));
          this.idDeleteAcoes = data.id;
          this.deleteId(this.idDeleteAcoes)
          data(confirm);
        })
        .catch(cancel =>{
            cancel
        });
    },
    async deleteId(idDeleteAcoes) {
        this.centerDialogVisible = false;
        const loading = this.$loading({
          lock: true,
          text: 'Deletando registro',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 1.7)'
        });
        setTimeout(() => {
          loading.close();
          actions.deleteDespesas(idDeleteAcoes).then(async response => {
            this.loader();
            this.getAno();
            this.dadosDashBoardBar();
            if(response.status === 200) {
              this.$notify({
                title: 'Sucesso!',
                message: 'Registro deletado!',
                type: 'success'
              })
            }
        }).catch(response => {
          this.$notify.error({
          title: 'Erro!',
          message: response,
            })
        })
      }, 1000);
    },
    async dadosDashBoardBar() {
      if (this.ano === null) {
        this.ano = new Date().getFullYear();
      }
      const dados =  await actions.visualizarDespesasDashBoard(this.ano);
      await this.graficoPie(this.ano);
      this.dashboardBar = dados.data
      
      this.seriesDashBoard[0].data = [
        this.dashboardBar[0] ? this.dashboardBar[0].entrada : 0,
        this.dashboardBar[1] ? this.dashboardBar[1].entrada : 0,
        this.dashboardBar[2] ? this.dashboardBar[2].entrada : 0,
        this.dashboardBar[3] ? this.dashboardBar[3].entrada : 0,
        this.dashboardBar[4] ? this.dashboardBar[4].entrada : 0,
        this.dashboardBar[5] ? this.dashboardBar[5].entrada : 0,
        this.dashboardBar[6] ? this.dashboardBar[6].entrada : 0,
        this.dashboardBar[7] ? this.dashboardBar[7].entrada : 0,
        this.dashboardBar[8] ? this.dashboardBar[8].entrada : 0,
        this.dashboardBar[9] ? this.dashboardBar[9].entrada : 0,
        this.dashboardBar[10] ? this.dashboardBar[10].entrada : 0,
        this.dashboardBar[11] ? this.dashboardBar[11].entrada : 0,
      ];
      this.seriesDashBoard[1].data = [
      this.dashboardBar[0] ? this.dashboardBar[0].saida : 0,
        this.dashboardBar[1] ? this.dashboardBar[1].saida : 0,
        this.dashboardBar[2] ? this.dashboardBar[2].saida : 0,
        this.dashboardBar[3] ? this.dashboardBar[3].saida : 0,
        this.dashboardBar[4] ? this.dashboardBar[4].saida : 0,
        this.dashboardBar[5] ? this.dashboardBar[5].saida : 0,
        this.dashboardBar[6] ? this.dashboardBar[6].saida : 0,
        this.dashboardBar[7] ? this.dashboardBar[7].saida : 0,
        this.dashboardBar[8] ? this.dashboardBar[8].saida : 0,
        this.dashboardBar[9] ? this.dashboardBar[9].saida : 0,
        this.dashboardBar[10] ? this.dashboardBar[10].saida : 0,
        this.dashboardBar[11] ? this.dashboardBar[11].saida : 0,
      ];
        window.dispatchEvent(new Event('resize'));
    },
    async getAno(){
      const dados = await actions.getAnos();
      this.listAnos = dados.data;
    },
    async graficoPie(ano) {
      const dados = await actions.graficoPie(ano);
      this.getGraficoPie = dados.data;
      const tipo = this.getGraficoPie.map((area) => { return area});
      this.chartOptionsPie = {
        labels : []
      };
      this.seriesPie = []
      for (let index = 0; index < tipo.length; index++) {
        this.chartOptionsPie.labels.push(tipo[index].name);
        this.seriesPie.push(tipo[index].saida);
      }
    },
    async graficoArea() {
      const dados = await actions.graficoArea();
      this.getGraficoArea = dados.data;
      const tipo = this.getGraficoArea.map((area) => { return area});
      this.chartOptionsRadar = {
        xaxis: {
          categories: []
        }
      }
      this.seriesRadar = [{
        data: []
      },
      {
        data:[]
      }]

      for (let index = 0; index < tipo.length; index++) {
        this.chartOptionsRadar.xaxis.categories.push(tipo[index].ano)
        this.seriesRadar[0].data.push(tipo[index].entrada);
        this.seriesRadar[1].data.push(tipo[index].saida);

      }
    }
  }
}
</script>
<style scoped>
  .card1{
    margin-right: 10px;
    margin-bottom: 10px;
    padding: 10px;
    background-image: linear-gradient(to right, #33001b, #ff0084); 
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

  .card2{
    margin-right: 10px;
    margin-bottom: 10px;
    padding: 10px;
    background-image: linear-gradient(to right, #8360c3, #2ebf91);
  }

  .card3{
    margin-right: 10px;
    margin-bottom: 10px;
    padding: 10px;
    background-image: radial-gradient( circle farthest-corner at 22.4% 21.7%, rgba(4,189,228,1) 0%, rgba(2,83,185,1) 100.2% );
  }

  .elCard{
    width: 99%;
    margin: 10px 10px;
  }

  @media (max-width: 1200px)
  {
      .elCard{
        max-height: 70%;
        position: fixed;
        width: 100%;
      }

    }

  .card4{
    margin-right: 10px;
    margin-bottom: 10px;
    padding: 10px;
    background-image: linear-gradient(to right, #2c3e50, #4ca1af);
  }

  .widthCard {
    margin-top: 2px;
    width: 200px;
    font-size: 16px;
    align-items: center;
    text-align: center;
    color: #fff;
  }
  .card {
    justify-content: center;
    align-items: center;
    display: flex;
  }
</style>

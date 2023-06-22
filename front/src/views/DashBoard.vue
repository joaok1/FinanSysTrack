<template lang="pug">
div
  div(style="display:flex; align-items:center;")
    el-col(:span="11")
      div(style="position:relative; display:flex; padding-left:20px; padding-top:10px; flex-wrap:wrap;")
        el-row(style="margin-top: 1rem")
          div.card
            div.widthCard
                el-card(style='border-radius:20px').card1
                  el-row
                    label.labelCard Entrada
                  el-row
                    label.labelCard {{this.listCard && this.listCard.entrada ? this.listCard.entrada.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'}) : '0' }}
            div.widthCard
                el-card(style='border-radius:20px').card2
                  el-row
                    label.labelCard Gastos
                  el-row
                    label.labelCard {{this.listCard && this.listCard.total ? this.listCard.total.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'}) : '0'}}
            div.widthCard
              el-card(style='border-radius:20px').card3
                el-row
                  label.labelCard Saldo
                el-row
                  label.labelCard {{this.listCard && this.listCard.saldo ? this.listCard.saldo.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'}) : '0'}}
            div.widthCard
                el-card(style='border-radius:20px').card4
                  el-row
                    label.labelCard Mês referência
                  el-row
                    label.labelCard {{this.listCard && this.listCard.mes ? this.listCard.mes : '-'}}
    el-col(:span="11")
      div(style="position:relative; display:flex; align-items:end; justify-content:flex-end; padding-top:10px; flex-wrap:wrap;")
        el-button(type="primary" @click="centerDialogResgistroDespesas = true") Registrar despesas
        el-button(type="primary" @click="centerDialog = true") Relatorio
        el-dropdown(style="margin-left:10px;")
          el-button(type="primary")
            Dropdown Categoria<i class="el-icon-arrow-down el-icon--right"></i>
          el-dropdown-menu(style="width:200px; align-items:center; text-align:center;")
            el-button(type="text" @click="centerDialog = true") Cadastrar Despesa
            el-button(type="text" @click="centerDialogVisibleTable = true") Visualizar tabela categoria
  //Inserção das categorias//
  el-dialog(title="Incluir depesa.", :visible.sync="centerDialog", width="30%", center,:before-close="handleClose")
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
  el-dialog(title="Editar depesa", :visible.sync="centerDialogVisibleTable", width="50%", center,:before-close="handleClose")
    el-card
      div(style="display:flex; justify-content: space-between; position:relative; align-items:center;")
          div
              label(style='font-size: 20px; text-align:center; display:flex; margin-left:20px; font-weight:bolder') Listagem das despesas
          div
          el-button(type="primary" @click="centerDialog = true") Registrar despesas
      data-table(
          :pageable='pageable'
          :data="listaData.content",
          :columns='columns',
          @atualizarTabela='atualizarTabela',
          :acoes='acoes',
          @editar="editar"
      )
  //Registro de despesas
  el-dialog(:visible.sync="centerDialogResgistroDespesas", width="50%", center,title="Inserir despesa",:before-close="handleClose")
    el-card(style="margin-right:10px; padding:10px;")
      div(style="display:flex; padding:10px;")
        div
          span Insira a data:
          el-date-picker( type="date", placeholder="Insira a data da despesa", v-model="despesas.calendar",size="small", style="width: 100%;")
        div
          div(id="chart")
            apexchart(type="radialBar" :options="chartOptions" :series="series")
        div
          span Insira o valor recebido:
          el-input-number(v-model="despesas.entrada" :min="0" size="small" style="width:100%")
      div(style="display:flex; position:relative; justify-content:center;")
        div(style="padding:10px;")
          div(style="display:flex; position:relative; justify-content:center;")
            span Valor de entrada
          div
            el-input-number(v-model="this.despesas.entrada" disabled="true")
        div(style="padding:10px;")
          div(style="display:flex; position:relative; justify-content:center;")
            span Valor total dos gastos
          div
            el-input-number(v-model="saida" disabled="true")
        div(style="padding:10px")
          div(style="display:flex; position:relative; justify-content:center;")
            span Saldo
          div
            el-input-number(v-model="saldo" disabled="true")
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
</template>
<script>
  import DataTable from '@/components/DataTable.vue'
  import panel from '@/components/Panel.vue'

  export default {
    name: 'DashBoard',
    components: {
    DataTable,
    panel
    },
    data() {
      return {
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
                            show: false
                        },
                        value: {
                            offsetY: -2,
                            fontSize: '22px'
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
        despesasCategoryEdit:{
          id:null,
          name:null,
          tipo:null
        },
        centerDialogVisible:false,
        bootbox:null,
        despesasCategory:{
          name:null,
          tipo:{},
          usuario:null
        },
        listaData:{},
        pageable: {},
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
            icon: 'el-icon-edit'
          },
          {
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
        this.despesasByCategory();
        this.tipo();
    },
    methods: {
      handleClose(done) {
        this.$confirm('Deseja fechar o modal?')
          .then(confirm =>{
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
      async tipo() {
          const listaData = await this.axios.get(`http://localhost:1081/registroCategoriaDespesas/listar/page/${1}?size=10&page=${this.page}&sort=data_cadastro,desc`);
          this.listaData = listaData.data;
          const {empty, number,numberOfElements,pageable,totalElements} = this.listaData;
          this.pageable = {
              empty,
              number,
              numberOfElements,
              pageable,
              totalElements
          }
          const listaTipo = await this.axios.get(`http://localhost:1081/despesasTipo/listar`);
          this.listaTipo = listaTipo.data;
      },
      async despesasCategoryByTipo() {
          this.despesasCategory.usuario = 1;
          await this.axios.post('http://localhost:1081/registroCategoriaDespesas/inserir', this.despesasCategory ).then(response => {
              if(response.status === 200) {
                  this.centerDialog = false
                  this.despesasCategory = {};
                  this.$notify({
                          title: 'Sucesso!',
                          message: 'Registro salvo!',
                          type: 'success'
                      })
              }
          }).catch(response => {
              if(response.status !== 200) {
                  this.$notify.error({
                      title: 'Erro!',
                      message: 'Erro ao salvar registro!',
                  })
              }
          })
          await this.tipo();
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
          const listaData = await this.axios.get(`http://localhost:1081/registroCategoriaDespesas/listarFindById/${this.data}`);
          this.listaDataById = listaData.data;
          this.despesasCategoryEdit.id = this.data;
          this.despesasCategoryEdit.name = this.listaDataById.name;
          this.despesasCategoryEdit.tipo = this.listaDataById.tipo.id;
          this.centerDialogVisible = true;
          await this.tipo();
      },
      async despesasEdit(){
          await this.axios.put('http://localhost:1081/registroCategoriaDespesas/edit', this.despesasCategoryEdit ).then(response => {
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
          }).catch(response => {
              if(response.status !== 200) {
                  this.$notify.error({
                      title: 'Erro!',
                      message: 'Erro ao editar registro!',
                  })
              }
          })
          await this.tipo();
      },
      //Inserção das despesas
      async despesasByCategory() {
          this.usuario = 1;
          console.log(this.usuario)
          const lista = await this.axios.get(`http://localhost:1081/registroCategoriaDespesas/listar/${this.usuario}`);
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
            console.log(this.arrayDespesa.length);
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
        console.log(this.despesas.calendar)
        this.arrayDespesa.map(objec => {
          this.despesas.listagemDespesas.push(objec);
        })
        this.despesas.listagemDespesas.splice(0,1);

        this.despesas.usuario = 1;
        await this.axios.post('http://localhost:1081/despesas/adicionar', this.despesas ).then(response => {
            if(response.status === 200) {
                this.centerDialogResgistroDespesas = false
                this.despesas = {};
                this.$notify({
                        title: 'Sucesso!',
                        message: 'Despesa registrada!',
                        type: 'success'
                    })
            }
        }).catch(response => {
            if(response.status !== 200) {
                this.$notify.error({
                    title: 'Erro!',
                    message: 'Erro ao salvar registro!',
                })
            }
        })
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
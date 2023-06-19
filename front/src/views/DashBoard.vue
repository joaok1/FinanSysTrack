<template lang="pug">
div
  div
      el-dialog.border(title="" :visible.sync="dialogVisible" width="30%")
          span.span Deseja excluir este registro ?
          span(slot="footer" class="dialog-footer")
            div.justify
              div
                el-button.bottomDialog(@click="closeModal") Cancelar
              div
                el-button.bottomDialog(type="primary" @click="deleteId"  style='color: #fff') Confirmar
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
      el-row.row
        el-card.flex
          el-select(v-model="date" clearable placeholder="Selecione o ano" size='mini')
            el-option(
              v-for="listAno in ano"
              :key="listAno.value"
              :label="listAno.label"
              :value="listAno.value")
          el-button.justifyBottom(type="primary" icon="el-icon-edit" @click='graphic' size='mini' ) Pesquisar
          el-col(:span='24')
            apexchart(type="bar" height="120%" width="100%" :options="chartOptions" :series="series")
        div
            el-col(:span='12')
              el-card.flex
                apexchart.marginRadar(type="radar" height="230%"  width="100%" :options="chartOptionsRadar" :series="seriesradar")
            el-col(:span='12')
              el-card.flex
                apexchart(type="donut" width="100%" height="140%" :options="DonutOptions" :series="seriesDonut")
            el-col(:span='12')
              el-card.flex
                apexchart(type="area" height="100%" width="100%" :options="chartOptionsline" :series="series")
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
        seriesradar:[
        {
            name: 'Entrada',
            data: [0]
        },
          {
            name: 'Saída',
            data: [1]
          }],
          chartOptionsRadar: {
            colors : ['#00FF00','#ff0230'],
            chart: {
              foreColor: '#fff',
              background: {
                color: '#fff'
              },
              
              height: 350,
              type: 'radar',
              colors: '#fff',
              dropShadow: {
                enabled: true,
                blur: 1,
                left: 1,
                top: 1
              },
            },
            title: {
              text: 'Relatório anual',
              style :{
                color: '#fff',
                fontSize:  '14px',
                fontWeight:  'bold'
              }
            },
            stroke: {
              width: 2,
              colors: ['#00FF00','#ff0230']
            },
            fill: {
              opacity: 0.1,
              colors: ['#fff']
            },
            markers: {
              size: 0
            },
            xaxis: {
              categories : []
            }
          },
        selectAno: [],
        date: null,
        ano : [
        {
          label: 2023,
          value: 2023
        }
        ],
        chartOptionsline: {
          title: {
              text: 'Relatório Mensal',
              style :{
                color: '#fff',
                fontSize:  '14px',
                fontWeight:  'bold'
              }
            },
          colors : ['#00FF00','#ff0230'],
          chart: {
            fontFamily: 'Bolder, Arial, sans-serif',
            foreColor: '#fff',
            toolbar: {
                    show: true
                },
            grid: {
              row: {
                colors: ['#f3f3f3', 'transparent'],
                opacity: 0.2
              },
            },
            height: 350,
            type: 'line',
            zoom: {
              enabled: true
            }
          },
          xaxis: {
            categories: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul','Ago','Set','Out','Nov','Dez'],
          },
          yaxis: {
                labels: {
                show: true,
                align: 'right',
                minWidth: 0,
                maxWidth: 100,
                style: {
                    colors: [],
                    fontSize: '10px',
                    fontFamily: 'Helvetica, Arial, sans-serif',
                    fontWeight: 400,
                    cssClass: 'apexcharts-yaxis-label',
                },
                offsetX: 0,
                offsetY: 0,
                rotate: 0,
                formatter: (value) => { 
                  return value.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'}) 
                }
              }
          },
          tooltip: {
                y: {
                  formatter: function(value) {
                    return value.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
                  }
                }
              },
          dataLabels: {
              enabled: false
            }
        },
        dash: [],
        page: null,
        filtro: {
          pagina: 0
        },
        pageable: {},
        listaData: {},
        donut: [],
        editarFinanca: null,
        acoes: [
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
        columns: [
          {
            label: 'Data',
            prop: 'calendar',
            width: '120px',
            formatter: ({ calendar }) => calendar || '-'
          },
          {
            label: 'Bc Digio',
            prop: 'bancoDigio',
            width: '120px',
            formatter: ({ bancoDigio }) => bancoDigio || '-'
          },
          {
            label: 'Bc Bradesco',
            prop: 'bancoBradesco',
            width: '120px',
            formatter: ({ bancoBradesco }) => bancoBradesco || '-'
          },
          {
            label: 'Bc Bv',
            width: '120px',
            prop: 'bancoBv',
            formatter: ({ bancoBv }) => bancoBv || '-'
          },
          {
            label: 'Padaria',
            width: '120px',
            prop: 'padaria',
            formatter: ({ padaria }) => padaria || '-'
          },
          {
            label: 'Água',
            prop: 'agua',
            width: '120px',
            formatter: ({ agua }) => agua || '-'
          },
          {
            label: 'Bc Brasil',
            width: '110px',
            prop: 'bancoBrasil',
            formatter: ({ bancoBrasil }) => bancoBrasil || '-'
          },
          {
            label: 'Mei',
            prop: 'mei',
            width: '120px',
            formatter: ({ mei }) => mei || '-'
          },
          {
            label: 'Faculdade',
            width: '100px',
            prop: 'faculdade',
            formatter: ({ faculdade }) => faculdade || '-'
          },
          {
            label: 'Vivo',
            prop: 'vivo',
            width: '100px',
            formatter: ({ vivo }) => vivo || '-'
          },
          {
            label: 'Carro',
            prop: 'carro',
            width: '100px',
            formatter: ({ carro }) => carro || '-'
          },
          {
            label: 'Internet',
            prop: 'internet',
            width: '100px',
            formatter: ({ internet }) => internet || '-'
          },
          {
            label: 'Entrada',
            width: '120px',
            prop: 'entrada',
            formatter: ({ entrada }) => entrada || '-'
          },
          {
            label: 'Total/Saída',
            width: '120px',
            prop: 'total',
            formatter: ({ total }) => total || '-'
          },
          {
            label: 'Mês referência',
            prop: 'mes',
            width: '140px',
            formatter: ({ mes }) => mes || '-'
          },
        ],
        listCard: {
          saldo: null,
          entrada: null,
          total: null,
          mes: null
        },
        delete: [],
        idDeleteAcoes: null,
        dialogVisible: false,
        desserts: [],
        lista: [],
        series: [{
            name: 'Entrada',
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
          },
          {
            name: 'Saída',
            data: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
          }],
          chartOptions: {
            title: {
              text: 'Relatório Mensal',
              style :{
                color: '#fff',
                fontSize:  '14px',
                fontWeight:  'bold'
              }
            },
            colors : ['#00FF00','#ff0230'],
            chart: {
              fontFamily: 'Bolder, Arial, sans-serif',
              type: 'bar',
              height: 350,
              foreColor: '#fff'          
            },
            plotOptions: {
              bar: {
                borderRadius: 10,
                horizontal: false,
                columnWidth: '100%',
                endingShape: 'rounded'
              },
            },
            dataLabels: {
              enabled: false,
              style: {
                color: '#fff'
              }
            },
            stroke: {
              show: true,
              width: 2,
              colors: ['transparent']
            },
            xaxis: {
              categories: ['Janeiro','Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro','Novembro','Dezembro'],
            },
            tooltip: {
              y: {
                formatter: function(value) {
                  return value.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
                }
              }
            },
            yaxis: {
              labels: {
              show: true,
              align: 'right',
              minWidth: 0,
              maxWidth: 100,
              style: {
                  colors: [],
                  fontSize: '10px',
                  fontFamily: 'Helvetica, Arial, sans-serif',
                  fontWeight: 400,
                  cssClass: 'apexcharts-yaxis-label',
              },
              offsetX: 0,
              offsetY: 0,
              rotate: 0,
              formatter: (value) => { 
                return value.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'}) 
              }
            }
            },
            fill: {
              opacity: 1
            }
            },
        seriesDonut: [],
            DonutOptions: {
              title: {
                text: 'Ultimo mês',
                style :{
                  color: '#fff',
                  fontSize:  '12px',
                  fontWeight:  'bold'
                }
              },
              labels:['Faculdade','Internet','Carro','Vivo','Agua','Bradesco','Brasil','Bv','Digio','Mei','Padaria'],
              chart: {
                fontFamily: 'Helvetica, Arial, sans-serif',
                type: 'donut',
                foreColor: '#fff',
              },
              plotOptions: {
                pie: {
                  height:'90%',
                  startAngle: -100,
                  endAngle: 290,
                  donut: {
                    size: '70px',
                      labels: {
                        show:true,
                          total: {
                            show:true,
                            label: 'Total',
                            formatter: function (w) {
                              return w.globals.seriesTotals.reduce((a, b) => {
                                return a + b
                              }, 0).toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
                            }
                        }
                    }
                  }
                }
              },
              dataLabels: {
                enabled: true
              },
              fill: {
                type: 'gradient',
              },
              legend: {
                formatter: function(val, opts) {
                  const series = opts.w.globals.series[opts.seriesIndex];
                  if (series === undefined) {
                    return val;
                  }
                  return val + " : " + series.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
                }
              },
              responsive: [{
                breakpoint: 480,
                options: {
                  chart: {
                    width: 200,
                    color: '#fff'
                  },
                  legend: {
                    position: 'bottom'
                  }
                }
              }]
            }
      }
    },
    async mounted() {
      this.Loader();
      this.graphic();
      window.dispatchEvent(new Event('resize'));
    },
    methods: {
      atualizarTabela(newPage) {
        this.page = this.listaData.pageable.pageNumber = newPage - 1;
        this.Loader();
      },
  
      async graphic() {
        if (this.date === null) {
          const date = new Date();
          this.date = date.getFullYear();
        }
        //Gravando os dados no grafico de radar
        const listaDashArea = await this.axios.get(`http://localhost:1081/despesasMensal/listarAno/${1}`);
        this.seriesradar[0].data = listaDashArea.data.map(item => item.entrada);
        this.seriesradar[1].data = listaDashArea.data.map(item => item.total);
        listaDashArea.data.forEach(item => 
          this.chartOptionsRadar.xaxis.categories.push(item.ano)
        );
        //Gravando os dados no grafico de barras e no grafico de linha
        const listaDashBoard = await this.axios.get(`http://localhost:1081/despesasMensal/listar/${1}/${this.date}`);
        const listaDashBoardSaida = await this.axios.get(`http://localhost:1081/despesasMensal/listar/${1}/${this.date}`);

        this.entrada = listaDashBoard.data;
        this.saida = listaDashBoardSaida.data;

        this.series[0].data = [
        this.entrada[0] = this.entrada[0] ? this.entrada[0].entrada : 0,
        this.entrada[1] = this.entrada[1] ? this.entrada[1].entrada : 0,
        this.entrada[2] = this.entrada[2] ? this.entrada[2].entrada : 0,
        this.entrada[3] = this.entrada[3] ? this.entrada[3].entrada : 0,
        this.entrada[4] = this.entrada[4] ? this.entrada[4].entrada : 0,
        this.entrada[5] = this.entrada[5] ? this.entrada[5].entrada : 0,
        this.entrada[6] = this.entrada[6] ? this.entrada[6].entrada : 0,
        this.entrada[7] = this.entrada[7] ? this.entrada[7].entrada : 0,
        this.entrada[8] = this.entrada[8] ? this.entrada[8].entrada : 0,
        this.entrada[9] = this.entrada[9] ? this.entrada[9].entrada : 0,
        this.entrada[10] = this.entrada[10] ? this.entrada[10].entrada : 0,
        this.entrada[11] = this.entrada[11] ? this.entrada[11].entrada : 0,
      ];
      this.series[1].data = [
        this.saida[0] = this.saida[0] ? this.saida[0].total : 0,
        this.saida[1] = this.saida[1] ? this.saida[1].total : 0,
        this.saida[2] = this.saida[2] ? this.saida[2].total : 0,
        this.saida[3] = this.saida[3] ? this.saida[3].total : 0,
        this.saida[4] = this.saida[4] ? this.saida[4].total : 0,
        this.saida[5] = this.saida[5] ? this.saida[5].total : 0,
        this.saida[6] = this.saida[6] ? this.saida[6].total : 0,
        this.saida[7] = this.saida[7] ? this.saida[7].total : 0,
        this.saida[8] = this.saida[8] ? this.saida[8].total : 0,
        this.saida[9] = this.saida[9] ? this.saida[9].total : 0,
        this.saida[10] = this.saida[10] ? this.saida[10].total : 0,
        this.saida[11] = this.saida[11] ? this.saida[11].total : 0,
      ];
        window.dispatchEvent(new Event('resize'));
      },
  
      async Loader() {
        const lista = await this.axios.get('http://localhost:1081/financeiro/listar');
        const listaData = await this.axios.get(`http://localhost:1081/financeiro/listarData/${1}?size=10&page=${this.page}&sort=calendar,desc`);
        this.listaData = listaData.data;
        const {empty, number,numberOfElements,pageable,totalElements} = this.listaData;
        this.pageable = {
          empty,
          number,
          numberOfElements,
          pageable,
          totalElements
        }
        this.lista = lista.data;
        this.list = lista.data;
        this.seriesDonut = [this.list[this.list.length - 1].faculdade, this.list[this.list.length - 1].internet, this.list[this.list.length - 1].carro, this.list[this.list.length - 1].vivo, this.list[this.list.length - 1].agua, this.list[this.list.length - 1].bancoBradesco, this.list[this.list.length - 1].bancoBrasil, this.list[this.list.length - 1].bancoBv,
        this.list[this.list.length - 1].bancoDigio, this.list[this.list.length - 1].mei,
        this.list[this.list.length - 1].padaria ];
        this.listCard = this.list[this.list.length - 1];
        this.listaData.content.map(row => {
          if (row) {
            row.agua =  row.agua.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.bancoBradesco =  row.bancoBradesco.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.vivo =  row.vivo.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.internet =  row.internet.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.carro =  row.carro.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.bancoBrasil =  row.bancoBrasil.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.bancoBv =  row.bancoBv.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.bancoDigio =  row.bancoDigio.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.faculdade =  row.faculdade.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.entrada =  row.entrada.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.mei =  row.mei.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.padaria =  row.padaria.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.total =  row.total.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
            row.saldo =  row.saldo.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
          }
        })
        window.dispatchEvent(new Event('resize'));
        this.$emit('excluir')
      },
      search() {
        this.mounted()
        this.Loader();
        this.deleteId();
        window.dispatchEvent(new Event('resize'));
      },
      closeModal() {
        this.dialogVisible = false;
      },
      excluir(data) {
        this.dialogVisible = true;
        this.idDeleteAcoes = data.id;
      },
      editar(data) {
        this.data = data.id;
        this.$router.push({
          name: 'despesas',
          params: {data}
        })
      },

  async downloadPDF() {
    try {
      const response = await this.axios({
        url: `http://localhost:1081/financeiro/relatorioDownload/${1}`,
        method: 'GET',
        responseType: 'blob',
      });
      const url = URL.createObjectURL(response.data);
      const link = document.createElement('a');
      link.href = url;
      link.download = 'Relatorio Despesas.pdf';

      link.click();
    } catch (error) {
      console.log(error);
    }
},


      async deleteId() {
        this.dialogVisible = false;
        const loading = this.$loading({
              lock: true,
              text: 'Deletando registro',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 1.7)'
          });
          setTimeout(() => {
              loading.close();
              this.axios.delete(`http://localhost:1081/financeiro/delete/${this.idDeleteAcoes}`).then(response => {
              if(response.status === 200) {
                  this.$notify({
                    title: 'Sucesso!',
                    message: 'Registro deletado!',
                    type: 'success'
              })

                  this.teste = response.data;
                  this.Loader();
                  this.graphic();
                }
              }).catch(response => {
                  if(response.status !== 200) {
                    this.$notify.error({
                      title: 'Erro!',
                      message: 'Erro ao deletar registro!',
                    })
                  }
              })
          }, 1000);
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
  
  h3 {
    margin: 40px 0 0;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  .bar {
  display: flex;
  }
  
  .justifyBottom {
    justify-content:flex-end;
  }
  .justify {
    justify-content: space-between;
    display: flex;
  }
  .flexTable {
  background: #fff;
  margin: 5px;
  }
  .flex {
  margin: 5px;
  background-color: rgba(0, 0, 0, 0.5);
  border-width: 1px;
  border-image: linear-gradient(to right, rgb(0rgb(255, 255, 255) 0), rgb(10, 0, 0));
  border-image-slice: 1;
  box-shadow: rgba(50, 50, 93, 0.25) 0px 13px 27px -5px, rgba(0, 0, 0, 0.3) 0px 8px 16px -8px;
  border-radius: 10px;
  }
  .grap {
    margin-left: 10px;
  }
  .select {
    margin-bottom: 2px;
    margin-left: 5px;
  }
  .font {
    font-size: 14px;
    display: inline-flex;
    }
  .span {
    font-style: bolder;
    font-size: 18px;
    color: black;
  }
  .footer{
    display: flex;
    justify-content: space-between;
    margin-bottom: -1.5rem;
  }
  .panelTable {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 20px;
  }
  .row {
  height: 100%;
  }
  
  .colTable {
    margin-top: 15px;
  }
  .marginRadar {
    padding-top: 10px;
    margin-bottom: 10px;
  }
  </style>
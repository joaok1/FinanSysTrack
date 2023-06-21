<template lang="pug">
div(style="padding:10px;")
    el-col(:span="13")
        el-card(style="margin-right:10px; padding:10px; min-height:750px;")
            div(style="display:flex;")
                div
                    span Insira a data:
                    el-date-picker( type="date" placeholder="Insira a data da despesa" v-model="form.date1" style="width: 100%;")
                div
                    div(id="chart")
                        apexchart(type="radialBar" :options="chartOptions" :series="series")
            div(style="display:flex; padding:10px;")
                div
                    span Insira o valor recebido:
                    el-input-number(v-model="num" @change="handleChange" :min="0" size="small" style="width:100%")
                div
                    span {{  }}
                div
                    span {{  }}
            div(style="display:flex; align-items:center;")
                div(style="padding:10px")
                    label(style="font-size:14px; margin-right:10px;font-weight:bold;") Selecione a categoria da despesa:
                    el-select(v-model="despesasCategory.tipo",clearable,filterable ,style="width:100%", size="small")
                        el-option(
                            v-for="select in listaTipo"
                            :key="select.id"
                            :label="select.name"
                            :value="select.id"
                        )
                div(style="padding:10px")
                    label(style="font-size:14px; margin-right:10px;font-weight:bold;") Insira o valor da despesa
                    el-input-number(v-model="num" @change="handleChange" :min="0" size="small" style="width:100%")
                div(style="padding:10px; align-items:center; margin-top:0.5rem;")
                    el-button(type="primary") Inserir
            div
                el-upload(
                    class="upload-demo"
                    action="https://jsonplaceholder.typicode.com/posts/"
                    :on-change="handleChange"
                    :file-list="fileList")
            div(style="position:relative; display:flex; align-items:end; justify-content:flex-end; padding-top:10px;")
                el-button(type="primary") Salvar
    el-col(:span="11")
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
            //Adição
    el-dialog(title="Incluir depesa.", :visible.sync="centerDialog", width="30%", center)
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
    el-dialog(title="Editar depesa", :visible.sync="centerDialogVisible", width="30%", center)
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
</template>
<script>
import DataTable from '@/components/DataTableFin.vue'

export default{
    // eslint-disable-next-line vue/multi-word-component-names
    name: 'Tipo',
    components: {
        DataTable
    },
    data() {
        return{
            fileList: [{
                name: 'food.jpeg',
                url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
            }, {
                name: 'food2.jpeg',
                url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
            }],
            series: [76],
            chartOptions: {
                chart: {
                    type: 'radialBar',
                    offsetY: -20,
                    sparkline: {
                        enabled: true
                    }
                },
                plotOptions: {
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
            acoes: [
                {
                    text: 'Editar',
                    codigo: 'EDITAR',
                    icon: 'el-icon-edit'
                },
                {
                }
            ],
            columns: [
                {
                    label:'Nome',
                    prop: 'name',
                },
                {
                    label:'Tipo',
                    prop: 'tipo.name',
                }
            ]
        }
    },
    async mounted() {
        this.tipo();
    },
    methods: {
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
        }
        
    }
}
</script>
<style scoped>
    .justify {
    justify-content: space-between;
    display: flex;
    }
</style>
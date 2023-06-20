<template lang="pug">
div
        //el-col(:span="14")
            el-card(style="margin-right:10px")
                <el-form ref="form" :model="form" label-width="120px">
                    <el-form-item label="Activity name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
                    <el-form-item label="Activity zone">
                        <el-select v-model="form.region" placeholder="please select your zone">
                        <el-option label="Zone one" value="shanghai"></el-option>
                        <el-option label="Zone two" value="beijing"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Activity time">
                        <el-col :span="11">
                        <el-date-picker type="date" placeholder="Pick a date" v-model="form.date1" style="width: 100%;"></el-date-picker>
                        </el-col>
                        <el-col class="line" :span="2">-</el-col>
                        <el-col :span="11">
                        <el-time-picker placeholder="Pick a time" v-model="form.date2" style="width: 100%;"></el-time-picker>
                        </el-col>
                    </el-form-item>
                    <el-form-item label="Instant delivery">
                        <el-switch v-model="form.delivery"></el-switch>
                    </el-form-item>
                    <el-form-item label="Activity type">
                        <el-checkbox-group v-model="form.type">
                        <el-checkbox label="Online activities" name="type"></el-checkbox>
                        <el-checkbox label="Promotion activities" name="type"></el-checkbox>
                        <el-checkbox label="Offline activities" name="type"></el-checkbox>
                        <el-checkbox label="Simple brand exposure" name="type"></el-checkbox>
                        </el-checkbox-group>
                    </el-form-item>
                    <el-form-item label="Resources">
                        <el-radio-group v-model="form.resource">
                        <el-radio label="Sponsor"></el-radio>
                        <el-radio label="Venue"></el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="Activity form">
                        <el-input type="textarea" v-model="form.desc"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit">Create</el-button>
                        <el-button>Cancel</el-button>
                    </el-form-item>
                </el-form>
        el-col(:span="10")
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
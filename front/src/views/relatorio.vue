<template lang="pug">
div
    div
        div(style='margin: 10px 20px 0px 20px; border-radius: 10px; background-color: #fff;')
            div(style='display:flex; justify-content:space-between; margin-bottom: 10px; align-items:center;')
                div
                    label(style='font-size: 20px; text-align:center; display:flex; margin-left:20px; font-weight:bolder') Registro de entrada
                div
                    el-button(type='primary' icon='el-icon-printer' size='small' @click='downloadPDF()', style='font-weight: bold; margin-right:20px') PDF
            data-table(
                :pageable='pageable'
                :data="listaData.content",
                :columns='columns',
                @atualizarTabela='atualizarTabela',
                :acoes='acoes',
                @editar="editar"
                @excluir="excluir",
            )

</template>
<script>
    import DataTable from '@/components/DataTable.vue'
    import Cabecalho from './Cabecalho.vue';
    import Cookies from 'js-cookie';
    import axios from 'axios'

    export default{
        // eslint-disable-next-line vue/multi-word-component-names
        name: 'relatorio',
        components: {
        DataTable,
        Cabecalho
        },
        data() {
            return{
                centerDialogVisible:false,
                idDeleteAcoes: null,
                listaData:{},
                pageable: {},
                acoes: [
                    {
                        text: 'Visualizar',
                        codigo: 'EDITAR',
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
                columns: [
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
            }
        },
        async mounted() {
            await this.dadosLogin();
            await this.loader();
            window.dispatchEvent(new Event('resize'));
        },
        methods: {
            excluir(data) {
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
            handleClose(done) {
                this.$confirm('Deseja fechar o modal?')
                .then(confirm =>{
                    window.dispatchEvent(new Event('resize'));
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
            async dadosLogin() {
                //configuração do usuario
                const user = JSON.parse(localStorage.getItem('user'));
                //Configuração do Token
                const token = Cookies.get('token');
                this.config = {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                };
                const userData = await axios.get(`http://localhost:1081/api/usuarios/findByLogin/${user.sub}`)
                this.usuario = userData.data.id;  
                console.log(this.usuario)              
            },
            atualizarTabela(newPage) {
                this.page = this.listaData.pageable.pageNumber = newPage - 1;
                this.loader();
            },
            async loader() {
                console.log(this.page)
                const listaData = await axios.get(`http://localhost:1081/api/despesas/pageLista/${this.usuario}?size=8&page=${this.page}&sort=calendar,desc`,this.config);
                console.log(listaData)
                this.listaData = listaData.data;
                const { empty, number, numberOfElements, pageable, totalElements } = this.listaData;
                this.pageable = {
                    empty,
                    number,
                    numberOfElements,
                    pageable,
                    totalElements
                };
                window.dispatchEvent(new Event('resize'));
                this.$emit('excluir')
            },
            closeModal() {
                this.centerDialogVisible = false;
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
                    axios.delete(`http://localhost:1081/api/despesas/delete/${idDeleteAcoes}`,this.config).then(response => {
                        this.loader();
                        if(response.status === 200) {
                            this.$notify({
                                title: 'Sucesso!',
                                message: 'Registro deletado!',
                                type: 'success'
                            })
                        }
                    }).catch(response => {
                            console.log(response)
                            this.$notify.error({
                            title: 'Erro!',
                            message: response,
                            })
                    })
                }, 1000);
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
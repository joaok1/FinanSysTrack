<template lang="pug">
div
    el-dialog.border(title="" :visible.sync="dialogVisible" width="30%")
            span.span Deseja excluir este registro ?
            span(slot="footer" class="dialog-footer")
                div.justify
                    div
                        el-button.bottomDialog(@click="closeModal") Cancelar
                    div
                        el-button.bottomDialog(type="primary" @click="deleteId"  style='color: #fff') Confirmar
    el-card(style='margin: 10px 20px 0px 20px; border-radius: 10px; background-color: #fff;')
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
    export default{
        // eslint-disable-next-line vue/multi-word-component-names
        name: 'relatorio',
        components: {
        DataTable
        },
        data() {
            return{
                idDeleteAcoes: null,
                dialogVisible: false,
                listaData:{},
                pageable: {},
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
                    }
                ],
            }
        },
        async mounted() {
            this.Loader();
            window.dispatchEvent(new Event('resize'));
        },
        methods: {
            atualizarTabela(newPage) {
                this.page = this.listaData.pageable.pageNumber = newPage - 1;
                this.Loader();
            },
            async Loader() {
                const listaData = await this.axios.get(`http://localhost:1081/financeiro/listarData/${1}?size=12&page=${this.page}&sort=calendar,desc`);
                this.listaData = listaData.data;
                const {empty, number,numberOfElements,pageable,totalElements} = this.listaData;
                this.pageable = {
                    empty,
                    number,
                    numberOfElements,
                    pageable,
                    totalElements
                }
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
                        this.Loader();
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
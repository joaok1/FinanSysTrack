<template lang="pug">
div.display(style="padding-top:2.5rem")
    div(style="display:flex; position:relative; align-items:center; justify-content:center")
        el-card.doc( v-show="!this.calculo.totalFinanciamento")
            el-form(ref="form" :model="form" size="small", v-show="!this.calculo.totalFinanciamento")
                el-form-item
                    div(style="display:flex; justify-content:center;")
                        div(style="display:flex;")
                            div
                                div
                                    span.font Digite o nome do financiamento: 
                                div
                                    el-input(placeholder="Digite um nome" v-model="financiamento.nome")
                                div
                                    span.font Selecione o banco: 
                                div
                                    el-select(v-model="calculoFinanciamento.bancos",clearable,filterable ,style="width:600px" )
                                        el-option(
                                            v-for="select in selectBanco"
                                            :key="select.id"
                                            :label="select.banco"
                                            :value="select.id"
                                        )
                    div(style="display:flex; align-items:center; justify-content:space-around; position:relative;")
                        div(style="margin-right:50px")
                            div
                                label.font Valor do financiamento:
                            div
                                el-input-number(size="small"  :min='0' style="width:200px", v-model="calculoFinanciamento.valorFinanciamento")
                        div(style="margin-left:50px")
                            div
                                label.font Selecione a data:
                            div
                                el-date-picker(
                                    v-model="calculoFinanciamento.date"
                                    type="date"
                                    placeholder="Data"
                                    )
                    div(style="display:flex; align-items:center; justify-content:space-around; position:relative;")
                        div(style="margin-right:50px")
                            div
                                label.font Taxa de Juros:
                            div
                                el-input-number(size="small"  :min='0' style="width:200px",v-model="calculoFinanciamento.taxJuros")
                        div(style="margin-left:50px")
                            div
                                label.font Qtd de Prestações:
                            div
                                el-input-number(size="small"  :min='0' style="width:200px",v-model="calculoFinanciamento.qtdPrestacoes")
                div(style="display:flex; position:relative; margin-left:36rem;")
                    div
                        el-button(type="success",@click="calcularPrestacao()", :disabled="!financiamento.nome") Calcular
        div(v-show="this.calculo.totalFinanciamento")
            el-card.doc
                div(style="justify-content:center; position:relative; display:flex;")
                    span(style="font-size:24px; font-weight:bold") Pré-Visualização
                div.doc
                    div.title(style="font-size:22px")
                        span(style="font-weight:bolder") Dados pessoais 
                    div(style="display:flex; justify-content:space-between; position:relative;")
                        div.grid
                            div
                                el-row
                                    span(style="font-weight:bolder") Nome: 
                                el-row
                                    span.space {{ calculo.pessoa.nome }}
                            div
                                el-row
                                    span(style="font-weight:bolder") Sobrenome: 
                                el-row
                                    span.space {{ calculo.pessoa.sobrenome }}
                            div
                                el-row
                                    label(style="font-weight:bolder") Cpf:
                                el-row
                                    span.space {{ calculo.pessoa.cpf }}
                    div(style="display:flex; justify-content:space-between; position:relative;")
                        div.grid
                            div
                                el-row
                                    span(style="font-weight:bolder") Rg: 
                                el-row
                                    span.space {{ calculo.pessoa.rg }}
                            div
                                el-row
                                    label(style="font-weight:bolder") Nascimento:
                                el-row
                                    span.space {{ calculo.pessoa.data_nascimento }}
                            div
                                el-row
                                    label(style="font-weight:bolder") Telefone:
                                el-row
                                    span.space {{ calculo.pessoa.telefone }}
                    div.title(style="font-size:22px")
                        span(style="font-weight:bolder") Endereço
                    div(style="display:flex; justify-content:space-between; position:relative;")
                        div.grid
                            div
                                el-row
                                    span(style="font-weight:bolder") Email: 
                                el-row
                                    span.space {{ calculo.pessoa.email }}
                            div
                                el-row
                                    label(style="font-weight:bolder") cep:
                                el-row
                                    span.space {{ calculo.pessoa.cep }}
                            div
                                el-row
                                    label(style="font-weight:bolder") Estado:
                                el-row
                                    span.space {{ calculo.pessoa.estado }}
                    div(style="display:flex; justify-content:space-between; position:relative;")
                        div.grid
                            div
                                el-row
                                    span(style="font-weight:bolder") Cidade: 
                                el-row
                                    span.space {{ calculo.pessoa.cidade }}
                            div
                                el-row
                                    label(style="font-weight:bolder") Endereco:
                                el-row
                                    span.space {{ calculo.pessoa.endereco }}
                    div.title(style="font-size:22px")
                        span(style="font-weight:bolder") Banco
                    div(style="display:flex; justify-content:space-between; position:relative;")
                        div.grid
                            div
                                el-row
                                    label(style="font-weight:bolder") Cod:
                                el-row
                                    span.space {{ calculo.bancos.cod }}
                            div
                                el-row
                                    label(style="font-weight:bolder") Nome:
                                el-row
                                    span.space {{ calculo.bancos.banco }}
                    div.title(style="font-size:22px")
                        span(style="font-weight:bolder") Dados do Financiamento
                    div(style="display:flex; justify-content:space-between; position:relative;")
                        div.grid
                            div
                                el-row
                                    label(style="font-weight:bolder; margin-right:10px;") Valor Solicitado:
                                el-row
                                    span.space {{ valorFinanciamento}}
                            div
                                el-row
                                    label(style="font-weight:bolder") Juros a pagar:
                                el-row
                                    span.space {{ totalJurosAPagar }}
                            div
                                el-row
                                    label(style="font-weight:bolder; margin-right:10px;") Valor final do financiamento:
                                el-row
                                    span.space {{ totalFinanciamento}}

                    span.space(style="font-weight:bolder") Segue abaixo as prestações
                    data-table(
                        :pageable='pageable'
                        :data="this.calculo.parcelas.content",
                        :columns='columns',
                        @atualizarTabela='atualizarTabela'
                    )
                div(style="display:flex; position:relative; justify-content:space-between; padding-top:20px")
                    div
                        el-button(type="danger", @click="cancelar()") Cancelar
                    div
                        el-button(type="success", @click="salvarPrestacao()") Concluir

</template>
<script>
import vMask from 'v-mask'
import DataTable from '@/components/DataTableFin.vue'
export default{
    name: 'financiamentos',
    components: {
        DataTable,
        vMask
    },
    data() {
        return {
            valorFinanciamento:null,
            totalFinanciamento:null,
            totalJurosAPagar:null,
            parcelaList:[],
            paginacao: null,
            pageable: {},
            columns: [
                {
                    label: 'Nº prestação',
                    prop: 'sequencia'
                },
                {
                    label: 'Valor da prestação',
                    prop: 'valorPrestacao'
                },
                {
                    label: 'Data Vencimento',
                    prop: 'data'
                }
            ],
            financiamento : {
                bancos:{},
                date:null,
                pessoa:{},
                usuario:{},
                prestacoes:null,
                nome:null,
                taxJuros:null,
                totalJurosAPagar:null,
                totalFinanciamento:null,
                valorFinanciamento:null
            },
            calculo : {
                prestacoes:null,
                totalJurosAPagar:null,
                totalFinanciamento:null,
                bancos:{},
                pessoa:{},
                parcelas:{
                    content:[]
                }
            },
            calculoFinanciamento: {
                "date": null,
                "arquivo": null,
                "valorFinanciamento": null,
                "taxJuros": null,
                "qtdPrestacoes": null,
                "bancos": null
            },
            selectBanco:[],
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: ''
            }
        }
    },
    async mounted() {
        this.select();
    },
    methods:{
        async select() {
            const cargaSelect = await this.axios.get('http://localhost:1081/banco/listar');
            this.selectBanco = cargaSelect.data;
        },
        atualizarTabela(newPage) {
                this.page = this.calculo.parcelas.pageable.pageNumber = newPage - 1;
                this.calcularPrestacao();
            },
        async calcularPrestacao() {
            if (!this.financiamento.nome || !this.calculoFinanciamento.bancos || !this.calculoFinanciamento.date ) {
                console.log(this.financiamento.nome , this.calculoFinanciamento.bancos,this.calculoFinanciamento.date  )
                this.$notify.error({
                        title: 'Erro!',
                        message: 'Preencha todos os campos!',
                        })
            } else {
                const calcular = await this.axios.post(`http://localhost:1081/cadastroFinanciamento/calculoFinanciamento/${1}?size=5&page=${this.page}&sort=valorFinanciamento,desc`, this.calculoFinanciamento);
                this.calculo = calcular.data;
                var totalFinanciamento = this.calculo.totalFinanciamento;
                var totalJurosAPagar = this.calculo.totalJurosAPagar;
                var valorFinanciamento = this.calculo.valorFinanciamento;
                this.totalFinanciamento = totalFinanciamento.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
                this.totalJurosAPagar = totalJurosAPagar.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
                this.valorFinanciamento = valorFinanciamento.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
                this.calculo.parcelas.content.map(element => {
                    element.valorPrestacao = element.valorPrestacao.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})
                })
                const {empty, number,numberOfElements,pageable,totalElements} = this.calculo.parcelas;
                    this.pageable = {
                        empty,
                        number,
                        numberOfElements,
                        pageable,
                        totalElements
                    }
                return this.calculo
            }
        },
        async salvarPrestacao(){
            this.salvarDoc =  await this.calcularPrestacao()
            this.financiamento.bancos = this.salvarDoc.bancos;
            this.financiamento.date = this.salvarDoc.date;
            this.financiamento.pessoa = this.salvarDoc.pessoa;
            this.financiamento.usuario = this.salvarDoc.usuario;
            this.financiamento.prestacoes = this.salvarDoc.prestacoes
            this.financiamento.taxJuros = this.salvarDoc.taxJuros;
            this.financiamento.totalFinanciamento = this.salvarDoc.totalFinanciamento;
            this.financiamento.totalJurosAPagar = this.salvarDoc.totalJurosAPagar;
            this.financiamento.valorFinanciamento = this.salvarDoc.valorFinanciamento;
            await this.axios.post(`http://localhost:1081/cadastroFinanciamento/save/${1}`, this.financiamento).then(response =>{
                if(response.data.statusCodeValue === 200) {
                    this.$notify({
                                        title: 'Sucesso!',
                                        message: 'Registro salvo!',
                                        type: 'success'
                                    })
                    this.$router.push({name: 'financiamento'})  
                }
            }).catch(response => {
                    if(response.data.statusCodeValue !== 200) {
                        this.$notify.error({
                        title: 'Erro!',
                        message: 'Erro ao salvar Financiamento!',
                        })
                    }
                })
        },
        cancelar() {
            this.calculo = {
                    totalJurosAPagar:null,
                    totalFinanciamento:null,
                    bancos:{},
                    pessoa:{},
                    parcelas:[]
            }
            this.page = 0;
        },
    }
}
</script>
<style scoped>
.identificador{
    margin-right: 10px;
}

.doc{
    font-size: 16px;
    border-radius: 20px;
    margin-right: 1rem;
    color: #000;
    margin-top: 20px;
    position: relative;
    justify-content: center;
}



.font{
    font-size: 18px;
    font-weight: bolder;
    color: #000;
}

.grid{
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    justify-content: space-between;
    position: relative;
    width: 100%;
    padding-bottom: 5px;
}
.grid2{
    display: flex;
    justify-content: center;
    position: relative;
    width: 100%;

}
.title{
    padding-bottom: 10px;
    padding-top: 10px;
}
.space{
    padding-top: 10px;

    font-size: 16px;
}

</style>
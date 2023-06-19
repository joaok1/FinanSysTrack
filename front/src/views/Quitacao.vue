<template lang="pug">
div
    div
        div
            span.font Selecione o Financiamento/emprestimo: 
        div
            el-select(clearable,filterable ,style="width:600px" )
                el-option(
                    v-for="select in selectFinanciamento"
                    :key="select.id"
                    :label="select.nome"
                    :value="select.id"
                )
</template>
<script>
import DataTable from '@/components/DataTable.vue'
export default {
    name: 'quitacao',
    components: {
        DataTable
    },
    data() {
        return {
            selectFinanciamento:[]
        }
    },
    async mounted(){
        await this.select();
    },
    methods:{
        async select() {
            const cargaSelect = await this.axios.get(`http://localhost:1081/cadastroFinanciamento/findAllCadastroFinanciamento/${1}`);
            this.selectFinanciamento = cargaSelect.data;
        }

    }
}
</script>
<style  scoped>
    .font{
        color: #fff;
        font-size: 18px;
        font-weight: bold;
    }
</style>
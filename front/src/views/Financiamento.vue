<template lang="pug">
div   
    <el-tabs v-model="activeName">
        <el-tab-pane >
            span(slot="label")
                i(class="el-icon-date" style="color:#fff; font-size:16px; margin-left:10px;") DashBoard
        </el-tab-pane>
        <el-tab-pane label="Config"> 
            span(slot="label") 
                i(class="el-icon-edit"  style="color:#fff; font-size:16px;") Registro de baixas
        </el-tab-pane>
        <el-tab-pane label="Role">
            span(slot="label") 
                i(class="el-icon-document-add"  style="color:#fff; font-size:16px;") Cadastro
        </el-tab-pane>
    </el-tabs>
        <router-view />
</template>
<script>
import FinanciamentoDashBoard from '@/views/FinanciamentoDashBoard.vue';
import Quitacao from '@/views/Quitacao.vue';
import Financiamentos from '@/views/Financiamentos.vue';

export default {
    components: {
        FinanciamentoDashBoard,
        Quitacao,
        Financiamentos
    },
    data() {
        return {
            activeName: ''
        }
    },
    watch: {
        activeName(newName) {
            if(this.$route.path !== '/financiamento'){
                if (newName === "0") {
                    this.$router.push('/financiamento');
                }
            }
            if (newName === "1") {
                this.$router.push('/Quitacao');
            }
            if (newName === "2") {
                this.$router.push('/Registrofinanciamentos');
            }
        }
    },
    methods:{
    }
}
</script>
<style scoped>
.active{
    padding: 10px;
    font-size: 1em;
    border-radius: 10px;
    background-color:#05214e;
    color: #fff;
    font-weight: bolder;
}
.activeRouter{
    padding: 10px;
    font-size: 1em;
    color: #fafafa;
    font-weight: bolder;
    font-size: 1em;
}

.card{
    background-color: #434D67;
}
.el-icon-s-data,.el-icon-money,.el-icon-s-order{
    font-size: 3em;
}

</style>
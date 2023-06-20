<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
div   
    //<el-tabs v-model="activeName">
        <el-tab-pane label="DashBoard">
            span(slot="label")
                i(class="el-icon-date" style="color:#fff; font-size:16px; margin-left:10px;") DashBoard
        </el-tab-pane>
        <el-tab-pane label="Relatorio"> 
            span(slot="label") 
                i(class="el-icon-edit"  style="color:#fff; font-size:16px;") Relatorio
        </el-tab-pane>
        <el-tab-pane label="Role">
            span(slot="label") 
                i(class="el-icon-document-add"  style="color:#fff; font-size:16px;") Cadastro
        </el-tab-pane>
        <el-tab-pane label="Categorias">
            span(slot="label") 
                i(class="el-icon-document-add"  style="color:#fff; font-size:16px;") Cadastro Tipos Despesas
        </el-tab-pane>
    </el-tabs>
        div
            <router-view />
</template>
<script>

export default {
    components: {

    },
    data() {
        return {
            activeName: ''
        }
    },
    watch: {
        activeName(newName) {
            if(this.$route.path !== '/Despesas'){
                if (newName === "0") {
                    this.$router.push('/Despesas');
                }
            }
            if (newName === "1") {
                this.$router.push('/relatorio');
            }
            if (newName === "2") {
                this.$router.push('/RegistroDespesas');
            }
            if (newName === "3") {
                this.$router.push('/Categorias');
            }
        }
    },

    mounted(){
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
<template lang="pug">
div
    div.title
        label.title Registro de despesas
    div(style="justify-content:center; display:flex; margin-top:20px; width:100%; ")
        div
            el-col(:span="8")
                div
                    div.card
                        label Bc Bradesco
                        el-input-number(size="small" v-model="financeiro.bancoBradesco" :min='0' style="width:300px")

                    div.card
                        label   Bc Digio
                        el-input-number.input(size="small" v-model="financeiro.bancoDigio" :min='0' style="width:300px")
                    div.card
                        label Faculdade
                        el-input-number.input(size="small" v-model="financeiro.faculdade" :min='0' style="width:300px")
                    div.card
                        label Agua
                        el-input-number.input(size="small" v-model="financeiro.agua" :min='0' style="width:300px")
                    div.card
                        label Mei
                        el-input-number.input(size="small" v-model="financeiro.mei" :min='0' style="width:300px")
                    div.card
                        label Vivo
                        el-input-number.input(size="small" v-model="financeiro.vivo" :min='0' style="width:300px")
            el-col(:span="8")
                div
                    div.card
                        label Bc Bv
                        el-input-number(size="small" v-model="financeiro.bancoBv" :min='0' style="width:300px")
                    div.card
                        label Bc Brasil
                        el-input-number.input(size="small" v-model="financeiro.bancoBrasil" :min='0' style="width:300px")
                    div.card
                        label Internet
                        el-input-number.input(size="small" v-model="financeiro.internet" :min='0' style="width:300px")
                    div.card
                        label Padaria
                        el-input-number.input(size="small" v-model="financeiro.padaria" :min='0' style="width:300px")
                    div.card
                        label Carro
                        el-input-number.input(size="small" v-model="financeiro.carro" :min='0' style="width:300px")
                    div.card
                        label Entrada
                        el-input-number.input(size="small" v-model="financeiro.entrada" :min='0' style="width:300px")
            el-col(:span="6")
                div(style="margin-top: 5px;")
                    div(style="width: 200px;")
                        el-radio-group(v-model="financeiro.mes")
                            el-radio.margin(label="JANEIRO") JANEIRO
                            el-radio.margin(label="FEVEREIRO") FEVEREIRO
                            el-radio.margin(label="MARÇO") MARÇO
                            el-radio.margin(label="ABRIL") ABRIL
                            el-radio.margin(label="MAIO") MAIO
                            el-radio.margin(label="JUNHO") JUNHO
                            el-radio.margin(label="JULHO") JULHO
                            el-radio.margin(label="AGOSTO") AGOSTO
                            el-radio.margin(label='SETEMBRO') SETEMBRO
                            el-radio.margin(label="OUTUBRO") OUTUBRO
                            el-radio.margin(label="NOVEMBRO") NOVEMBRO
                            el-radio.margin(label="DEZEMBRO") DEZEMBRO
                        div
                            el-button(type='primary' @click='salvar' v-model='financeiro' size="small" v-loading.fullscreen.lock="fullscreenLoading" icon='el-icon-finished', v-if='!this.idEditar' style='font-weight: bold; width:200px') Salvar registro
                            el-button(type='primary' @click='editarRegistro' v-model='financeiro' size="small" v-loading.fullscreen.lock="fullscreenLoading" icon='el-icon-finished', v-if='this.idEditar' style='font-weight: bold; width:200px') Concluir edição

    </template>
    
    <script>
    export default {
        name: 'RegistroDespesas',
        data() {
            return {
                idEditar: {
                    row: {
                        id: null
                    }
                },
                fullscreenLoading: false,
                financeiro: {
                    mes: null,
                    mei: null,
                    bancoDigio: null,
                    bancoBradesco: null,
                    bancoBv: null,
                    padaria: null,
                    agua: null,
                    faculdade: null,
                    internet: null,
                    carro: null,
                    vivo: null,
                    bancoBrasil: null,
                    entrada: null,
                    saldo: null,
                    usuario: null
            }
            }
        },
        mounted() {
        this.idEditar = this.$route.params.data;
        this.buscarId();
        },
        methods: {
            async buscarId() {
                if (this.$route.params.data) {
                    const editar = this.idEditar
                    const listaEditar = await this.axios.get(`http://localhost:1081/financeiro/consultar/${editar.id}`);
                    this.financeiro.mes = listaEditar.data.mes;
                    this.financeiro.mei = listaEditar.data.mei;
                    this.financeiro.bancoDigio = listaEditar.data.bancoDigio;
                    this.financeiro.bancoBradesco = listaEditar.data.bancoBradesco;
                    this.financeiro.bancoBv = listaEditar.data.bancoBv;
                    this.financeiro.padaria = listaEditar.data.padaria;
                    this.financeiro.agua = listaEditar.data.agua;
                    this.financeiro.bancoBrasil = listaEditar.data.bancoBrasil;
                    this.financeiro.entrada = listaEditar.data.entrada;
                    this.financeiro.vivo = listaEditar.data.vivo;
                    this.financeiro.carro = listaEditar.data.carro;
                    this.financeiro.internet = listaEditar.data.internet;
                    this.financeiro.faculdade = listaEditar.data.faculdade;
                }
            },
            async salvar() {
                if (this.financeiro.mes === null) {
                    this.$message.error({
                        title: 'Erro!',
                        message: 'O campo referente ao mês e obrigatório!',
                    })
                }else{
                const loading = this.$loading({
                    lock: true,
                    text: 'Salvando registro',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 1.7)',
                });
                setTimeout(() => {
                    this.financeiro.usuario = 1;
                        this.axios.post('http://localhost:1081/financeiro/adicionar', this.financeiro ).then(response => {
                            if(response.status === 200) {
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
                        setTimeout(() => {
                            loading.close(); 
                            this.$router.push({
                                name: 'DashBoard'
                            })
                        },10)
                    }, 1000);
                }
            },
            async editarRegistro() {
                const loading = this.$loading({
                    lock: true,
                    text: 'Salvando registro',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 1.7)',
                });
                setTimeout(() => {
                    this.axios.put(`http://localhost:1081/financeiro/editar/${this.idEditar.id}`,this.financeiro ).then(response => {
                        if(response.status === 200) {
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
                    setTimeout(() => {
                        loading.close(); 
                        this.$router.push({
                            name: 'DashBoard'
                        })
                    },10)
                }, 1000);
            }
        }
    }
    </script>
    
    <style scoped>



.card {
    margin-bottom: 20px;
    display: block;
    font-size: 18px;
    text-align: center;
    font-weight: bolder;
}

    .flex {
    display: flex;
    margin: 0 auto;
    margin-top: 5px;

    border: #2B2D3E;
    align-content: center;
    color: #fff;

    }
    label {
        color: #fff;
    }
    .justify {
       display: flex;
        align-items: center;
        align-content: center;
        text-align: center;
    }
    
    .title {
        font-size: 28px;
        justify-content: center;
        text-align: center;
        align-items: center;
        margin: 0 auto;
        margin-top: 20px;
        margin-bottom: 50px;
    }
    .marginTop{
        color: #fff; font-weight:bolder;
        align-items: center;
        text-align: center;
        display: flex;
    }
    .salvar {
        margin-top: 50px;
    }
    .margin {
        padding-bottom: 0.31rem;
        color: #fff; font-weight:bolder;
    }
    </style>
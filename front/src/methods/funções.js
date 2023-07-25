import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router'
import axios from 'axios';
import Cookies from 'js-cookie';
Vue.use(VueRouter)
Vue.use(Vuex);


const actions = {

    //Configurações globais do usuario
    //Resgata o usuario logado


    // Busca o id do usuario
    async dadosLogin() {
        const usuario =  axios.get(`http://localhost:1081/api/usuarios/findByLogin/${Cookies.get('dados_usuario')}`)
        return (await usuario).data.id
    },

    //Resgata o Token do usuario
    // eslint-disable-next-line no-unused-vars



    //Resgata o id do usuario


    //
    //Inicio dos Metodos
    //

    //Metodo para Adicionar as depesas do mes
    async  inserirDespesas(despesas) {
        const data = {
            method: "post",
            url: "http://localhost:1081/api/despesas/adicionar",
            data: despesas,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data)
    },

    //Metodo para deletar as depesas do mes
    async  deleteDespesas(idDeleteAcoes) {
        const data = {
            method: 'delete',
            url: `http://localhost:1081/api/despesas/delete/${idDeleteAcoes}`,
            data: idDeleteAcoes,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        };
        return axios(data);
    },

    //Metodo para buscar as listagem das despesas (Geral)
    async  getListagemDespesas(page) {
        const data ={
            method: "get",
            url: `http://localhost:1081/api/despesas/pageLista/${Cookies.get('dados_usuario')}?size=8&page=${page}&sort=calendar,desc`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data);
    },

    //Busca os Categoria de despesas
    async  getCategoria(page){
        const data = {
            method: "get",
            url: `http://localhost:1081/api/registroCategoriaDespesas/listar/page/${Cookies.get('dados_usuario')}?size=10&page=${page}&sort=data_cadastro,desc`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data)
    },

    //Busca os tipos de despesas, metodo global(tipos registrados para todos os usuarios)
    async  getTipo(){
        const data = {
            method: "get",
            url: "http://localhost:1081/api/despesasTipo/listar" ,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data);
    },

    //Metodo para inserir as Categorias das despesas
    async  despesasCategoryByTipo(despesasCategory){
        const data = {
            method: "post",
            url: "http://localhost:1081/api/registroCategoriaDespesas/inserir",
            data: despesasCategory,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data)
    },

    //Metodo para pegar as Categorias das despesas no select
    async  getByCategoria(id){
        const data = {
            method : "get",
            url: `http://localhost:1081/api/registroCategoriaDespesas/listarFindById/${id}`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data);
    },

    //Metodo para editar as Categorias das despesas
    async  editarByCategoria(despesasCategoryEdit){
        const data = {
            method: "put",
            url: "http://localhost:1081/api/registroCategoriaDespesas/edit",
            data: despesasCategoryEdit,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data);
    },

    //Metodo para pegar a listagem e colocar na tabela
    async  getByCategoriaTable(){
        const data = {
            method: "get",
            url: `http://localhost:1081/api/registroCategoriaDespesas/listar/${Cookies.get('dados_usuario')}`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data);
    },
    //Metodo para excluir
    async  excluirDespesasCategoria(id){
            const data = {
            method: "delete",
            url: `http://localhost:1081/api/registroCategoriaDespesas/delete/${id}`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return axios(data);
    },
    //Metodo para visualizar as despesas
    async  visualizarDespesas(id){
        const data = {
        method: "get",
        url: `http://localhost:1081/api/despesas/visualizarDespesas/${id}`,
        headers: {
            Authorization: `Bearer ${Cookies.get('token')}`
        }
    }
    return axios(data);
}

}
export default actions;
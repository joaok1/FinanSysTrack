import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router'
import axios from 'axios';
import Cookies from 'js-cookie';
Vue.use(VueRouter)
Vue.use(Vuex);


//Configurações globais do usuario

//Resgata o usuario logado
const user = getUserCookie();
//Resgata o Token do usuario
const token = Cookies.get('token');
//Cria a Authorization do usuario
const config = {
    headers: {
        Authorization: `Bearer ${token}`
    }
};
function getUserCookie() {
    const userCookie = Cookies.get('user');
    if (userCookie) {
        const user = JSON.parse(userCookie);
        return user.sub
    } else {
        return null; // ou qualquer valor padrão que faça sentido para o seu caso
    }
}

// Busca o id do usuario
export async function dadosLogin() {
    const userData = await axios.get(`http://localhost:1081/api/usuarios/findByLogin/${user}`)
    return userData.data.id
}
//Resgata o id do usuario
const USER_ID = dadosLogin();

//
//Inicio dos Metodos
//

//Metodo para Adicionar as depesas do mes
export async function inserirDespesas(despesas) {
    return axios.post('http://localhost:1081/api/despesas/adicionar', despesas, config )
}

//Metodo para deletar as depesas do mes
export async function deleteDespesas(idDeleteAcoes) {
    const data = {
        method: 'delete',
        url: `http://localhost:1081/api/despesas/delete/${idDeleteAcoes}`,
        data: idDeleteAcoes,
        ...config
    };
    return axios(data);
}

//Metodo para buscar as listagem das despesas (Geral)
export async function getListagemDespesas(page) {
    const data ={
        method: "get",
        url: `http://localhost:1081/api/despesas/pageLista/${await USER_ID}?size=8&page=${page}&sort=calendar,desc`,
        ...config
    }
    return axios(data);
}

//Busca os Categoria de despesas
export async function getCategoria(page){
    const data = {
        method: "get",
        url: `http://localhost:1081/api/registroCategoriaDespesas/listar/page/${await USER_ID}?size=10&page=${page}&sort=data_cadastro,desc`,
        ...config
    }
    return axios(data)
}

//Busca os tipos de despesas, metodo global(tipos registrados para todos os usuarios)
export async function getTipo(){
    const data = {
        method: "get",
        url: "http://localhost:1081/api/despesasTipo/listar" ,
        ...config
    }
    return axios(data);
}

//Metodo para inserir as Categorias das despesas
export async function despesasCategoryByTipo(despesasCategory){
    const data = {
        method: "post",
        url: "http://localhost:1081/api/registroCategoriaDespesas/inserir",
        data: despesasCategory,
        ...config
    }
    return axios(data)
}

//Metodo para pegar as Categorias das despesas no select
export async function getByCategoria(id){
    const data = {
        method : "get",
        url: `http://localhost:1081/api/registroCategoriaDespesas/listarFindById/${id}`,
        ...config
    }
    return axios(data);
}

//Metodo para editar as Categorias das despesas
export async function editarByCategoria(despesasCategoryEdit){
    const data = {
        method: "put",
        url: "http://localhost:1081/api/registroCategoriaDespesas/edit",
        data: despesasCategoryEdit,
        ...config
    }
    return axios(data);
}

//Metodo para pegar a listagem e colocar na tabela
export async function getByCategoriaTable(){
    const data = {
        method: "get",
        url: `http://localhost:1081/api/registroCategoriaDespesas/listar/${await USER_ID}`,
        ...config
    }
    return axios(data);
}

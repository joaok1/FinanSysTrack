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
    await getListagemDespesas(await USER_ID,1);
    return axios.post('http://localhost:1081/api/despesas/adicionar', despesas, config )
}

//Metodo para buscar as listagem das despesas (Geral)
export async function getListagemDespesas(page) {
    return axios.get(`http://localhost:1081/api/despesas/pageLista/${await USER_ID}?size=8&page=${page}&sort=calendar,desc`,config);
}

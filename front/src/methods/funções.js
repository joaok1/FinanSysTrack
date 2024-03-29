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

    async Login() {
        const usuario =  axios.get(`http://localhost:1081/api/usuarios/findByLogin/${Cookies.get('dados_usuario')}`)
        return await usuario
    },

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
            url: `http://localhost:1081/api/despesas/pageLista/${Cookies.get('dados_usuario')}?size=5&page=${page}&sort=calendar,desc`,
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
            url: `http://localhost:1081/api/registroCategoriaDespesas/listar/page/${Cookies.get('dados_usuario')}?size=5&page=${page}&sort=data_cadastro,desc`,
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
    },
        //Metodo para editar as despesas
        async  editarDespesas(despesas){
            const data = {
                method: "put",
                url: `http://localhost:1081/api/despesas/edititionFindById`,
                data:despesas,
                headers: {
                    Authorization: `Bearer ${Cookies.get('token')}`
                }
            }
            return axios(data);
        },
    //Metodo de dados do dashBoar - barra e linha
    async  visualizarDespesasDashBoard(ano){
        const data = {
            method: "get",
            url: `http://localhost:1081/api/despesas/grafico/${Cookies.get('dados_usuario')}/${ano}`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return  axios(data);
    },
    //Metodo para pegar os anos 
    async  getAnos(){
        const data = {
            method: "get",
            url: `http://localhost:1081/api/despesas/grafico/${Cookies.get('dados_usuario')}`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return  axios(data);
    },
    //Metodo para pegar os dados do grafico area
    async  graficoPie(ano){
        const data = {
            method: "get",
            url: `http://localhost:1081/api/despesasTipo/graficoPie/${Cookies.get('dados_usuario')}/${ano}`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return  axios(data);
    },
    async  graficoArea(){
        const data = {
            method: "get",
            url: `http://localhost:1081/api/despesas/graficoArea/${Cookies.get('dados_usuario')}`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            }
        }
        return  axios(data);
    },
    async fetchImage(url) {
        const response = await axios({
            method: "post",
            url: `http://localhost:1081/api/arquivos/image`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`,
                "Content-Type": "application/json"
            },
            data: url,
            responseType: "blob"
        });
        return response.data;
    },
    async Pessoa() {
        const response = await axios({
            method: "get",
            url: `http://localhost:1081/api/pessoa/findByUser/${Cookies.get('dados_usuario')}`,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            },
        });
        return response.data;
    },
    async UploadTemp(file) {
        const formData = new FormData();
        formData.append('file', file); 
        const response = await axios({
            method: "post",
            url: `http://localhost:1081/api/arquivos/upload`,
            data: formData,
            headers: {
                Authorization: `Bearer ${Cookies.get('token')}`
            },
        });
        return response.data;
    },
    async cadastrar(pessoa) {
        const response = await axios({
            method: "post",
            url: `http://localhost:1081/api/pessoa/adicionar`,
            data: pessoa
        });
        return response.data;
    },
}
export default actions;
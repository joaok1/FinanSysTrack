<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <v-container>
      <v-row align="center" justify="center" >
          <v-col cols="12" sm="10">
            <v-card class="elevation-6 mt-10"  >
              <v-window v-model="step">
                <v-window-item :value="1">
                  <v-row>
                    <v-col cols="12" md="6">
                      <v-card-text class="mt-12">
                        <h4
                          class="text-center"
                        >Entre na sua conta</h4>
                        <h6
                          class="text-center  grey--text "
                        >Faça login em sua conta para continuar criando <br>e editando seus fluxos de integração</h6>
                        <v-row align="center" justify="center">
                          <v-col cols="12" sm="8">
                          <v-text-field
                            label="Cpf"
                            outlined
                            v-mask="'###.###.###-##'"
                            dense
                            v-model="formLogin.login"
                            color="blue"
                            autocomplete="false"
                            class="mt-16"
                          />
                          <v-text-field
                            label="Senha"
                            outlined
                            v-model="formLogin.senha"
                            dense
                            color="blue"
                            autocomplete="false"
                            type="password"
                          
                          />
                          <v-btn @click="logar()" color="blue" dark block tile>Entrar</v-btn>
                          </v-col>
                        </v-row>  
                      </v-card-text>
                    </v-col>
                    <v-col cols="12" md="6" class="blue rounded-bl-xl" >
                    <div style="  text-align: center; padding: 180px 0;">
                      <v-card-text class="white--text" >
                        <h3 class="text-center ">Não tem uma conta ainda?</h3>
                        <h6
                          class="text-center"
                        >Vamos preparar tudo para que você possa começar a criar sua primeira<br> experiência de integração</h6>
                      </v-card-text>
                      <div class="text-center">
                        <v-btn tile outlined dark @click="step++">INSCREVER-SE</v-btn>
                      </div>
                      </div>
                    </v-col>
                  </v-row>
                </v-window-item>
                <v-window-item :value="2">
                  <v-row >
                    <v-col cols="12" md="6" class="blue rounded-br-xl">
                    <div style="  text-align: center; padding: 180px 0;">
                      <v-card-text class="white--text" >
                        <h3 class="text-center ">Possui uma conta?</h3>
                        <h6
                          class="text-center"
                        >Faça login em sua conta para continuar criando e<br> editando seus fluxos de integração</h6>
                      </v-card-text>
                      <div class="text-center">
                        <v-btn tile outlined dark @click="step--">ENTRAR</v-btn>
                      </div>
                      </div>
                    </v-col>

                    <v-col cols="12" md="6">
                      <v-card-text class="mt-12">
                        <h4
                          class="text-center"
                        >Crie uma conta</h4>
                        <h6
                          class="text-center  grey--text "
                        >Vamos configurar tudo para que você possa começar a criar seu <br>
                        primeira experiência de integração</h6>
                        <v-row align="center" justify="center">
                          <v-col cols="12" sm="8">
                            <el-upload
                              ref="upload"
                              action="http://localhost:1081/api/arquivos/upload"
                              list-type="picture-card"
                              :on-preview="handlePictureCardPreview"
                              :accept="'image/png'"
                              :on-success="handleUploadSuccess"
                              :limit="1"
                              >
                              <i class="el-icon-plus"></i>
                            </el-upload>
                            <el-dialog :visible.sync="dialogVisible">
                              <img width="100%" :src="dialogImageUrl" alt="">
                            </el-dialog>
                           <v-row>
                           <v-col cols="12" sm="6">
                            <v-text-field
                            label="Primeiro nome"
                            outlined
                            dense
                            color="blue"
                            autocomplete="false"
                           class="mt-4"
                           v-model="pessoa.nome"
                          />
                           </v-col>
                           <v-col cols="12" sm="6">
                            <v-text-field
                            label="Sobrenome"
                            outlined
                            dense
                            color="blue"
                            autocomplete="false"
                           class="mt-4"
                           v-model="pessoa.sobrenome"
                          />
                           </v-col>
                           </v-row>
                          <v-text-field
                            label="CPF"
                            v-mask="'###.###.###-##'"
                            outlined
                            dense
                            color="blue"
                            autocomplete="false"
                            v-model="pessoa.cpf"
                          />
                          <v-text-field
                            label="Email"
                            outlined
                            dense
                            color="blue"
                            autocomplete="false"
                            v-model="pessoa.email"
                          />
                          <v-text-field
                            label="Password"
                            outlined
                            dense
                            color="blue"
                            autocomplete="false"
                            type="password"
                            v-model="pessoa.usuario.senha"
                          
                          />
                            <v-row>
                              <br>
                              <br>
                            </v-row>
                          <v-btn color="blue" dark block tile @click="cadastrar()">Sign up</v-btn>
                          </v-col>
                        </v-row>  
                      </v-card-text>
                    </v-col>
                  </v-row>
                </v-window-item>
              </v-window>
            </v-card>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
import store from '../store/index.js';
import actions from '@/methods/funções'
  export default {
    data() {
      return {
        uploadComponent: null ,
        fileList: {},
        dialogImageUrl: '',
        dialogVisible: false,
        step: 1,
        length: 0, 
        formLogin: {
          login:null,
          senha:null
        },
        pessoa:{
          nome:null,
          sobrenome:null,
          cpf:null,
          email:null,
          usuario:{
            senha:null
          },
          file:{
            key:null
          }
        },
        imageTemp:null
      }
  },
  mounted(){
    this.uploadComponent = this.$refs.upload;
  },
  methods:{
    async handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    sendFormattedCPF(dados) {
      const cpfWithoutFormat = dados.replace(/[^\d]/g, "");
      return this.formLogin.login = cpfWithoutFormat;
    },
    handleUploadSuccess(response,fileList){
      this.fileList = fileList
     this.pessoa.file.key = response;
    },

    async logar() {
      this.formLogin.login = this.sendFormattedCPF(this.formLogin.login);
      await store.dispatch('login', this.formLogin)
    },
    async cadastrar() {
      this.pessoa.cpf = this.sendFormattedCPF(this.pessoa.cpf);
      if (this.pessoa.file.key === null) {
        this.$notify.error({
          title: 'Erro!',
          message: "Insira uma foto!"
        })
      }
      actions.cadastrar(this.pessoa).then(response => {
        this.pessoa={
          nome:null,
          sobrenome:null,
          cpf:null,
          email:null,
          usuario:{
            senha:null
          },
          file:{
            key:null
          }
        },
        this.step = 1;
        this.$refs.upload.clearFiles();
        this.$notify({
            title: 'Sucesso!',
            message: response,
            type: 'success'
          })
      }).catch(error =>{
        this.$refs.upload.clearFiles();
        this.$notify.error({
            title: 'Erro!',
            message: error.response.data.message,
          })
      })

    
    }
  } 
}

    
    
</script>
<style scoped>
.v-application .rounded-bl-xl {
    border-bottom-left-radius: 300px !important;
}
.v-application .rounded-br-xl {
    border-bottom-right-radius: 300px !important;
}
</style>

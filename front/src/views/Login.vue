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
                            <v-row>
                              <v-col cols="12" sm="7">
                                <v-checkbox
                        label="Remember Me"
                        class="mt-n1"
                        color="blue"
                      > </v-checkbox>
                              </v-col>
                              <v-col cols="12" sm="5">
                                <span class="caption blue--text">Esqueceu a senha?</span>
                              </v-col>
                            </v-row>
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
                           <v-row>
                           <v-col cols="12" sm="6">
                            <v-text-field
                            label="First Name"
                            outlined
                            dense
                            color="blue"
                            autocomplete="false"
                           class="mt-4"
                          />
                           </v-col>
                           <v-col cols="12" sm="6">
                            <v-text-field
                            label="Last Name"
                            outlined
                            dense
                            color="blue"
                            autocomplete="false"
                           class="mt-4"
                          />
                           </v-col>
                           </v-row>
                          <v-text-field
                            label="Email"
                            outlined
                            dense
                            color="blue"
                            autocomplete="false"
                          />
                          <v-text-field
                            label="Password"
                            outlined
                            dense
                            color="blue"
                          autocomplete="false"
                           type="password"
                          
                          />
                            <v-row>
                              <br>
                              <br>
                            </v-row>
                          <v-btn color="blue" dark block tile>Sign up</v-btn>
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

  export default {
    data() {
      return {
        step: 1,
        length: 0, 
        formLogin: {
          login:null,
          senha:null
        }
      }
  },
  methods:{
    async logar() {
      try {
        // Chame uma ação do Vuex para realizar o login
        await store.dispatch('login', this.formLogin);
        this.$notify.success({
          title: 'Sucesso!',
          message: 'Seja bem-vindo',
        })
        // Redirecione para a página de perfil ou qualquer outra página protegida
      } catch (error) {
        // Lidar com erros de login, exibir mensagens de erro etc.
      }
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

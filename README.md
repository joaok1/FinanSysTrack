# FinanSysTrack
Nome do Projeto: FinanSysTrack
## Descrição do Projeto
Este projeto foi desenvolvido para uso pessoal e estudos, tendo como principal função o registro das despesas mensais. Surgiu com base na minha necessidade de armazenar todos os registros de despesas do mês, possibilitando a visualização do fluxo de entrada e saída de todos os meses e anos através de gráficos.<h2 align="start">Tecnologias</h1>

<h5>Backend</h5>
Java Spring

<h5>Frontend</h5>
VueJs

<h5>Banco de dados</h5>
Mysql

<h2>Sistema</h2>
Este sistema foi desenvolvido no linux, caso possua um sistema operacional diferente, será necessário fazer ajustes nas pastas abaixo.

<h2>Manual de usabilidade</h2>

Onde possui um ícone apontado para baixo, é possível registrar uma despesa e adicionar um tipo a ela, dentre os quais estão: mobilidade, alimentação, refeição e saúde. Mais ao centro, é possível verificar todos os registros de entrada, permitindo a exclusão e edição dos mesmos, assim como gerar um pequeno relatório. Por fim, temos o botão para inserir as despesas. Ali, é possível verificar em um menu suspenso todas as despesas registradas. Ao selecionar uma despesa, à direita do menu, é possível inserir o valor e, por fim, salvar este registro.
* O sistema conta com login, sendo assim nenhum outro usuario tem acesso aos dados pessoais

<h2>Instalação</h2>

* criar uma pasta em /home chamada 'arquivos_financas'
* Criar um banco de dados chamado de financeiro na porta 3306 com usuario chamado root e senha = 'Brasileirao1@'
* Instalar node igual ou superior ao 8
* Instalar java igual ou superior ao 17
* comando para rodar o front = npm run serve / porta:8080
* comando para rodar o back = mvn spring-boot:run / porta:1081
* E necessário liberar tanto a pasta tmp e a arquivos_financas, rode um chmod nelas.
* Não é necessário se preocupar com as tabelas, o sistema está equipado com o Flyway. Sendo assim, quando for compilado, ele irá criar automaticamente as tabelas necessárias para o seu funcionamento.

<h2>Fotos</h2>

![Captura de tela de 2023-08-19 22-53-54](https://github.com/joaok1/FinanSysTrack/assets/137024088/eaa8e52d-4925-46e9-9a42-a98fd4d3a4c3)
![Captura de tela de 2023-08-19 22-54-27](https://github.com/joaok1/FinanSysTrack/assets/137024088/695e7a5d-848a-4d31-b50e-1cfe95ba5683)
![Captura de tela de 2023-08-19 22-54-39](https://github.com/joaok1/FinanSysTrack/assets/137024088/394a62f5-aaf9-4aef-92f3-4757f08185f2)
![Captura de tela de 2023-08-19 22-55-25](https://github.com/joaok1/FinanSysTrack/assets/137024088/90f6639b-984b-41d0-a262-dbc14b0f9bae)

# Cadastro de pessoas

Projeto para cadastro de pessoas.

## Demonstração

http://alexoshiro.com/

Usuário de acesso: admin

Senha do usuário: 123

A documentação da API(Swagger) estará disponível em:

http://alexoshiro.com:8080/swagger-ui.html



## Pré-requisitos

Java versão 11 ou compatíveis

Maven versão 3.6 ou compatíveis

Node versão 10 ou compatíveis

Docker versão 19 ou compatíveis

Yarn versão 1.19 ou compatíveis

## Executar com docker-compose

### 1° Opção:

Existem as versões release das imagens da aplicação hospedadas no repositório do docker hub:

Backend: 

https://hub.docker.com/r/alexoshiro/register-api

FrontEnd:

https://hub.docker.com/r/alexoshiro/register-ui

**Atenção**: a imagem da aplicação do frontend foi criada com a variável de ambiente configurada como  REACT_APP_API_URL=http://localhost:8080, ou seja, ela estará realizando consultas na API localmente.

Com isso basta navegar até /register-project e executar o comando:

```
docker-compose up -d
```

Esse comando fará com que o docker realize o pull das imagens do repositório e implantado os containers com as imagens mongodb, register-api e register-ui.



Caso queira que a aplicação se conecte a um banco já existente, há também a opção de executar a aplicação de backend se conectando a um banco externo:

Para isso configure a variável de ambiente MONGO_URI dentro de docker-compose-without-mongo.yml na pasta raiz do projeto alterando o valor para a URI de conexão com o seu banco de dados.

```
docker-compose -f docker-compose-without-mongo.yml up -d
```

Esse comando fará com que o docker realize o pull das imagens do repositório e implantado os containers com as imagens register-api e register-ui.

### 2° Opção:

Compilando e criando local docker image do back-end

Navegue até register-project/back-end/register-api e execute o seguinte comando:

```
mvn clean install
```

Compilando e criando local docker image do front-end

Navegue até register-project\front-end\register-ui 

Antes de fazer o build é necessário configurar o .env do projeto com a varável de ambiente REACT_APP_API_URL para indicar a url base de consulta com a API, exemplo: http://localhost:8080

Execute o seguinte comando:

```
docker build -t alexoshiro/register-ui .
```

Após executar com sucesso os dois comandos, navegue até /register-project.

Execute:

```
docker-compose up -d
```

Esse comando fará com que seja levantado os containers com as imagens mongodb, register-api e register-ui.



Caso queira que a aplicação se conecte a um banco já existente, há também a opção de executar a aplicação de backend se conectando a um banco externo:

Para isso configure a variável de ambiente MONGO_URI dentro de docker-compose-without-mongo.yml na pasta raiz do projeto alterando o valor para a URI de conexão com o seu banco de dados.

```
docker-compose -f docker-compose-without-mongo.yml up -d
```

Esse comando fará com que seja levantado os containers com as imagens register-api e register-ui.

-----------------------------------

O acesso a página do aplicativo está disponível em: 

[http://localhost](http://localhost)

e o acesso a api estará disponível em:

[http://localhost:8080](http://localhost:8080)

A documentação da API(Swagger) estará disponível em:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Ambiente de dev

A aplicação está preparada com configurações básicas para executar em um ambiente de desenvolvimento.

Executar banco de dados:

```
docker-compose -f docker-compose-dev.yml up -d
```

Esse docker compose executa apenas o container do mongodb.

Para executar a aplicação back-end, utilize o perfil **dev** e sua ide de preferência.

Para executar a aplicação front-end, utilize o comando:

```
yarn start
```

### Ambiente de produção

Perfil de build back-end: **prod**

Antes de implantar a aplicação em um ambiente de produção, é necessário configurar algumas variáveis de ambiente, são elas:

```
MONGO_URI = variável para indicar a URI do banco de dados mongo, exemplo: 
MONGO_URI=mongodb://localhost:27017
REACT_APP_API_URL= variável para indicar a URL na qual a API está exposta, exemplo:
REACT_APP_API_URL=http://localhost:8080
```

## API - Registros no banco de dados (migration)

A aplicação possui um sistema de *migration* ativo.

Os scripts podem ser visualizados no pacote: project.alexoshiro.registerapi.migration.changelogs

Quando a aplicação é implantada com qualquer perfil ativo, a *migration* cria um usuário padrão(usuário admin, senha 123) para acesso ao sistema(*ChangeSet id 1580358097*) na *collection user*.

Quando a aplicação é implantada com os perfis de **dev** ou **test** ativos, a *migration* gera 1000 registros de mock de pessoas na *collection person*(*ChangeSet id 1580409846*).

Adicionalmente quando a aplicação é implantada com o perfil de **test**, a *migration* gera 1 registro de mock de pessoa na *collection person*(*ChangeSet id 1580529304*) para a realização dos testes de integração.

## Possíveis erros e soluções

### Erro - Could not build image

Esse erro ocorre quando o plugin dockerfile-maven-plugin não consegue conexão com o docker da máquina host.

Solução: Expose daemon on tcp://localhost:2375 without TLS

**Atenção** - essa solução abre vulnerabilidades, use com cuidado.
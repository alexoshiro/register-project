# Cadastro de pessoas

Projeto para cadastro de pessoas.

## Demonstração

<link>

Usuário de acesso: ***

Senha do usuário: ***

A documentação da API(Swagger) estará disponível em:

<link_api>/swagger-ui.html



## Pré-requisitos

Java versão 11 ou compatíveis

Maven versão 3.6 ou compatíveis

Node versão 10 ou compatíveis

Docker versão 19 ou compatíveis

## Executar com docker-compose

Compilando e criando local docker image do back-end

Navegue até register-project/back-end/register-api e execute o seguinte comando:

```
mvn clean install
```

Compilando e criando local docker image do front-end

Navegue até - e execute o seguinte comando:

```
npm run build
```

Após executar com sucesso os dois comandos, navegue até /register-project.

Execute:

```
docker-compose up -d
```

Esse comando fará com que seja levantado os containers com as imagens do mongodb, register-api e register-ui.

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

Para executar a aplicação back-end, utilize o perfil **dev**.

Para executar a aplicação front-end, utilize o comando:

```
npm start
```

### Ambiente de produção

Perfil de build back-end: **prod**

Antes de implantar a aplicação em um ambiente de produção, é necessário configurar algumas variáveis de ambiente, são elas:

```
database_host = variável para indicar o host do banco de dados mongo
database_port = porta na qual o mongo está alocado
database_username = usuário de autenticação do mongo
database_password = senha do usuário de autenticação
```

### Possíveis erros e soluções

#### Erro - Could not build image

Esse erro ocorre quando o plugin dockerfile-maven-plugin não consegue conexão com o docker da máquina host.

Solução: Expose daemon on tcp://localhost:2375 without TLS

**Atenção** - essa solução abre vulnerabilidades, use com cuidado.
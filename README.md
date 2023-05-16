# cadastro-api

API para o gerenciamento de Pessoas, sendo possível incluir, alterar, consultar e excluir.

## Iniciando a aplicação

1. Java
2. Maven
3. IntelliJ IDEA
4. Docker
5. PostgreSQL
6. Postman

## Configurando o banco de dados

Usamos o Docker para executar o PostgreSQL da aplicação.

Acesse a pasta do projeto e execute:

    docker-compose up -d

Informações do banco de dados:

    spring.datasource.url=jdbc:postgresql://localhost:5432/dev  
    spring.datasource.username=dev  
    spring.datasource.password=dev

## Postman

Arquivo disponível do postman para GET, POST, PUT e DELETE

 - [Collection](https://github.com/alison-luiz/cadastro-api/blob/master/Cadastro.postman_collection.json)

## Endpoints

### Incluir Pessoa

#### Request

- URL: `/api/pessoas`
- Method: `POST`

#### Request Body

```json
{
    "nome": "Alison Luiz",
    "cpf": "339.458.740-84",
    "dataNascimento": "1998-06-24",
    "contatos": [
        {
            "nome": "Jose",
            "telefone": "44997267963",
            "email": "contato1@gmail.com"
        }
    ]
}
```

### Consultar Pessoa(s)

#### Request

- URL: `/api/pessoas/buscar`
- Method: `GET`
- **Parâmetros de consulta**: 
  - `nome`
  - `cpf`

#### Request

- **URL**: `/api/pessoas/{id}`
- **Method**: GET

### Editar Pessoa

#### Request

- URL: `/api/pessoas/{id}`
- Method: `PUT`

##### Request Body

```json
{
    "nome": "Alison Luiz",
    "cpf": "339.458.740-84",
    "dataNascimento": "1998-06-24",
    "contatos": [
        {
            "nome": "João",
            "telefone": "44997267963",
            "email": "contato1@gmail.com"
        }
    ]
}
```

### Deletar Pessoa

#### Request

- URL: `/api/pessoas/{id}`
- Method: `DELETE`

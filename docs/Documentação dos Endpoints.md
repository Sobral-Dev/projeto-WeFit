# Documentação dos Endpoints

Esta seção detalha os endpoints disponíveis na API desenvolvida, incluindo métodos HTTP, URLs, exemplos de corpo de requisição (para POST) e formatos de resposta (sucesso e erro).

A API estará rodando em `http://localhost:8080`.

## 1. Cadastrar Pessoa Física

- **Método:** `POST`
- **URL:** `/pessoas-fisicas`
- **Descrição:** Cadastra um novo usuário do tipo Pessoa Física.

- **Exemplo de Request Body:**
```json
    {
        "nome": "João da Silva",
        "celular": "11987654321",
        "telefone": "1132109876",
        "email": "joao.silva@example.com",
        "cpf": "71674004087",
        "endereco": {
            "cep": "01001000",
            "logradouro": "Rua da Consolação",
            "numero": "1000",
            "complemento": "Apto 51",
            "cidade": "São Paulo",
            "bairro": "Consolação",
            "estado": "SP"
        }
    }
```

- Exemplo de Resposta de Sucesso (201 Created):

```json
    Pessoa Física cadastrada com sucesso!
```
    
- Exemplos de Resposta de Erro:

  - 400 Bad Request (Erro de Validação):

    - Causa: CPF inválido, CEP inválido, campos obrigatórios em branco, formato de e-mail/celular inválido.

    - Exemplo:

      ```json
      {
          "status": "BAD_REQUEST",
          "message": "Erro de validação: cpf: CPF inválido.",
          "timestamp": "2025-07-03T22:00:00.000000"
      }
      ```
  - 409 Conflict (Usuário Existente):

    - **Causa:** CPF ou E-mail já cadastrado.

    - Exemplo:

    ```json
      {
          "status": "CONFLICT",
          "message": "Já existe uma Pessoa Física cadastrada com o CPF: 71674004087",
          "timestamp": "2025-07-03T22:00:00.000000"
      }
    ```

## 2. Cadastrar Pessoa Jurídica

- **Método:** `POST`
- **URL:** `/pessoas-juridicas`
- **Descrição:** Cadastra um novo usuário do tipo Pessoa Jurídica.

- **Exemplo de Request Body:**

```json
    {
        "nome": "Empresa Exemplo Ltda.",
        "celular": "11998877665",
        "telefone": "1155443322",
        "email": "contato@empresaexemplo.com",
        "cnpj": "88508547000143",
        "cpfResponsavel": "26780851043",
        "endereco": {
            "cep": "04547000",
            "logradouro": "Rua do Comércio",
            "numero": "500",
            "complemento": "Sala 10",
            "cidade": "São Paulo",
            "bairro": "Vila Olímpia",
            "estado": "SP"
        }
    }
```

- Exemplo de Resposta de Sucesso (201 Created):

```json
    Pessoa Jurídica cadastrada com sucesso!
```

- Exemplos de Resposta de Erro:

  - 400 Bad Request (Erro de Validação):

    - **Causa:** CNPJ inválido, CPF do responsável inválido, CEP inválido, campos obrigatórios em branco, formato de e-mail/celular inválido.

    - Exemplo:
      ```json
        {
            "status": "BAD_REQUEST",
            "message": "Erro de validação: cnpj: CNPJ inválido.",
            "timestamp": "2025-07-03T22:00:00.000000"
        }
      ```

  - 409 Conflict (Usuário Existente):

    - **Causa:** CNPJ ou E-mail já cadastrado.

    - Exemplo:

    ```json
        {
            "status": "CONFLICT",
            "message": "Já existe uma Pessoa Jurídica cadastrada com o CNPJ: 88508547000143",
            "timestamp": "2025-07-03T22:00:00.000000"
        }
    ```

## 3. Listar Todos os Usuários

- **Método:** `GET`
- **URL:** `/usuarios`
- **Descrição:** Retorna uma lista de todos os usuários cadastrados (Pessoas Físicas e Pessoas Jurídicas).

- Exemplo de Resposta de Sucesso (200 OK):

```json
    [
        {
            "id": 1,
            "nome": "João da Silva",
            "celular": "11987654321",
            "telefone": "1132109876",
            "email": "joao.silva@example.com",
            "cpf": "12345678909"
        },
        {
            "id": 2,
            "nome": "Empresa Exemplo Ltda.",
            "celular": "11998877665",
            "telefone": "1155443322",
            "email": "contato@empresaexemplo.com",
            "cnpj": "11222333000181",
            "cpfResponsavel": "97092109062"
        }
    ]
```

## 4. Listar Apenas Pessoas Físicas

- **Método:** `GET`
- **URL:** `/usuarios/pessoas-fisicas`
- **Descrição:** Retorna uma lista de todos os usuários do tipo Pessoa Física.

- Exemplo de Resposta de Sucesso (200 OK):
```json
    [
        {
            "id": 1,
            "nome": "João da Silva",
            "celular": "11987654321",
            "telefone": "1132109876",
            "email": "joao.silva@example.com",
            "cpf": "12345678909"
        }
    ]
```

## 5. Listar Apenas Pessoas Jurídicas

- **Método:** `GET`
- **URL:** `/usuarios/pessoas-juridicas`
- **Descrição:** Retorna uma lista de todos os usuários do tipo Pessoa Jurídica.

- Exemplo de Resposta de Sucesso (200 OK):
```json
    [
        {
            "id": 2,
            "nome": "Empresa Exemplo Ltda.",
            "celular": "11998877665",
            "telefone": "1155443322",
            "email": "contato@empresaexemplo.com",
            "cnpj": "11222333000181",
            "cpfResponsavel": "97092109062"
        }
    ]
```

## 6. Buscar Usuário por ID

- **Método:** `GET`
- **URL:** `/usuarios/{id}`
- **Descrição:** Retorna os detalhes de um usuário específico pelo seu ID.

- Exemplo de Resposta de Sucesso (200 OK):

```json
    {
        "id": 1,
        "nome": "João da Silva",
        "celular": "11987654321",
        "telefone": "1132109876",
        "email": "joao.silva@example.com",
        "cpf": "12345678909"
    }
```
    
- Exemplo de Resposta de Erro (404 Not Found):

  - **Causa:** Usuário com o ID especificado não foi encontrado.

  - Exemplo:
    ```json
    {
        "status": "NOT_FOUND",
        "message": "Usuário não encontrado com ID: 999",
        "timestamp": "2025-07-03T22:00:00.000000"
    }
    ```

## 7. Deletar Usuário por ID

- **Método:** `DELETE`
- **URL:** `/usuarios/{id}`
- **Descrição:** Deleta um usuário específico pelo seu ID.

- Exemplo de Resposta de Sucesso (200 OK):

```json
    Usuário deletado com sucesso!
```

- Exemplo de Resposta de Erro (404 Not Found):

  - **Causa:** Usuário com o ID especificado não foi encontrado.

  - Exemplo:

    ```json
    {
        "status": "NOT_FOUND",
        "message": "Usuário não encontrado com ID: 999",
        "timestamp": "2025-07-03T22:00:00.000000"
    }
    ```

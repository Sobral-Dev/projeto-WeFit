CREATE TABLE endereco (
    id BIGSERIAL PRIMARY KEY,
    cep VARCHAR(255) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(255) NOT NULL,
    complemento VARCHAR(255),
    cidade VARCHAR(255) NOT NULL,
    bairro VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL
);

CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    celular VARCHAR(255) NOT NULL,
    telefone VARCHAR(255),
    email VARCHAR(255) NOT NULL UNIQUE,
    endereco_id BIGINT UNIQUE,
    CONSTRAINT fk_endereco
        FOREIGN KEY (endereco_id)
        REFERENCES endereco (id)
        ON DELETE CASCADE
);

CREATE TABLE pessoa_fisica (
    id BIGINT PRIMARY KEY,
    cpf VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_usuario_pf FOREIGN KEY (id) REFERENCES usuario (id) ON DELETE CASCADE
);

CREATE TABLE pessoa_juridica (
    id BIGINT PRIMARY KEY,
    cnpj VARCHAR(255) NOT NULL UNIQUE,
    cpf_responsavel VARCHAR(255) NOT NULL,
    CONSTRAINT fk_usuario_pj FOREIGN KEY (id) REFERENCES usuario (id) ON DELETE CASCADE
);
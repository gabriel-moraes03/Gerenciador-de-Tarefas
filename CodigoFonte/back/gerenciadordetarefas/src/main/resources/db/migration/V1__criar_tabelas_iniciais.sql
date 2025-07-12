CREATE TABLE usuario (
    id_usuario UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE tarefa (
    id_tarefa UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    estado VARCHAR(50) NOT NULL,
    usuario_id UUID REFERENCES usuario(id_usuario) ON DELETE CASCADE
);
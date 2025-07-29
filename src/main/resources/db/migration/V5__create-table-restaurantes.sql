CREATE TABLE restaurantes
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco_id BIGINT NOT NULL,
    tipo_cozinha VARCHAR(100) NOT NULL,
    horario_abertura TIME NOT NULL,
    horario_fechamento TIME NOT NULL,
    dono_id BIGINT NOT NULL,
    ativo BIT NOT NULL,
    CONSTRAINT fk_restaurantes_enderecos FOREIGN KEY (endereco_id) REFERENCES enderecos(id),
    CONSTRAINT fk_restaurantes_usuarios FOREIGN KEY (dono_id) REFERENCES usuarios(id)
);
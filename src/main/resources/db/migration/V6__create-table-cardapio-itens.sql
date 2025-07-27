CREATE TABLE cardapios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurante_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    ativo BIT NOT NULL,
    CONSTRAINT fk_cardapio_restaurante_id FOREIGN KEY (restaurante_id) REFERENCES restaurantes(id)
);
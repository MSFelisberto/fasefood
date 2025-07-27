CREATE TABLE cardapio_itens (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cardapio_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    apenas_no_local BIT NOT NULL,
    caminho_foto VARCHAR(255) NOT NULL,
    ativo BIT NOT NULL,
    CONSTRAINT fk_item_cardapio_id FOREIGN KEY (cardapio_id) REFERENCES cardapios(id)
);
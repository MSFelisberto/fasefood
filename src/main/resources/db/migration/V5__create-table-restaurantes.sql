CREATE TABLE restaurantes (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      nome VARCHAR(100) NOT NULL,
      endereco_id BIGINT NOT NULL,
      tipo_cozinha VARCHAR(50) NOT NULL,
      horario_funcionamento DATETIME,
      usuario_id BIGINT NOT NULL,

      CONSTRAINT fk_restaurantes_enderecos FOREIGN KEY (endereco_id) REFERENCES enderecos(id),
      CONSTRAINT fk_restaurantes_usuarios FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

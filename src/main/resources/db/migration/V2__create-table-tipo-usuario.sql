CREATE TABLE tipo_usuario (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	nome varchar(255)
);

INSERT INTO tipo_usuario (id, nome) VALUES (1,'DONO_RESTAURANTE');
INSERT INTO tipo_usuario (id, nome) VALUES (2,'CLIENTE');
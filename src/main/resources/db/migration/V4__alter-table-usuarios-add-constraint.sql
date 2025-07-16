ALTER TABLE usuarios
ADD CONSTRAINT fk_tipo_usuario
FOREIGN KEY (tipo_usuario_id)
REFERENCES tipo_usuario(id);
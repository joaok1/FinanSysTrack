CREATE TABLE listagem_despesas (
  id SMALLINT NOT NULL AUTO_INCREMENT,
  usuario_id SMALLINT NOT NULL,
  registro_categoria_despesas_id SMALLINT NOT NULL,
  despesas SMALLINT NOT NULL,
  valor DOUBLE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (registro_categoria_despesas_id) REFERENCES registro_categoria_despesas(id),
  FOREIGN KEY (despesas) REFERENCES despesas(id)
);
CREATE TABLE listagem_despesas_seq (
  next_val BIGINT
);



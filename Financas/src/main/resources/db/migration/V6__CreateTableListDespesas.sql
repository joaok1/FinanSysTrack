CREATE TABLE  listagem_despesas(
  id SMALLINT NOT NULL AUTO_INCREMENT,
  despesas_category SMALLINT NOT NULL,
  usuario_id SMALLINT NOT NULL,
  despesas SMALLINT NOT NULL,
  valor DOUBLE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  FOREIGN KEY (despesas) REFERENCES despesas(id),
  FOREIGN KEY (despesas_category) REFERENCES registro_categoria_despesas(id)
);

CREATE TABLE listagem_despesas_seq (
  next_val BIGINT
);
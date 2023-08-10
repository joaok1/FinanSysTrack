CREATE TABLE  registro_tipo_despesas(
  id SMALLINT NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  usuario SMALLINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (usuario) REFERENCES usuario(id)
);

CREATE TABLE  registro_categoria_despesas(
  id SMALLINT NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  tipo SMALLINT NOT NULL,
  usuario SMALLINT NOT NULL,
  data_cadastro DATE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (usuario) REFERENCES usuario(id),
  FOREIGN KEY (tipo) REFERENCES registro_tipo_despesas(id)
);

CREATE TABLE registro_tipo_despesas_seq (
  next_val BIGINT
);
CREATE TABLE registro_categoria_despesas_seq (
  next_val BIGINT
);




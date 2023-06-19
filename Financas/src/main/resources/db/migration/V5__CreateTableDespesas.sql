CREATE TABLE  despesas(
  id SMALLINT NOT NULL AUTO_INCREMENT,
  calendar DATETIME,
  ano INT NOT NULL,
  mes VARCHAR(255),
  total DOUBLE,
  entrada DOUBLE NOT NULL,
  saldo DOUBLE,
  usuario SMALLINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (usuario) REFERENCES usuario(id)
);

CREATE TABLE despesas_seq (
  next_val BIGINT
);
CREATE TABLE arquivos (
  id SMALLINT NOT NULL AUTO_INCREMENT,
  usuario_id SMALLINT NOT NULL,
  nome VARCHAR(500),
  arquivos VARCHAR(1000) NOT NULL,

  PRIMARY KEY (id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);




CREATE TABLE roles (
  id SMALLINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE usuario
ADD COLUMN id_roles SMALLINT;

ALTER TABLE usuario
ADD FOREIGN KEY (id_roles) REFERENCES roles(id);


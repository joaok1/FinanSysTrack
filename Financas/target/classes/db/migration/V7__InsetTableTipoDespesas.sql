Alter table registro_tipo_despesas drop foreign key registro_tipo_despesas_ibfk_1;
Alter table registro_tipo_despesas drop column usuario;

INSERT into registro_tipo_despesas (name) VALUES ("Alimentação"),
    ("Refeição"),
    ("Mobilidade"),
    ("Farmácia"),
    ("Diversos")

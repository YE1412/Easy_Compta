-- Datas in Startup of Application

INSERT INTO tva (pourcentage, libelle) VALUES (0.2, "Tva 20%");
INSERT INTO tva (pourcentage, libelle) VALUES (0.02, "Tva 2%");
INSERT INTO tva (pourcentage, libelle) VALUES (0.05, "Tva 5%");

INSERT INTO devise (symbole) VALUES ('$');
INSERT INTO devise (symbole) VALUES ('�');
INSERT INTO devise (symbole) VALUES ('�');

INSERT INTO langue (nom, libelle, id_devise) VALUES ("US", "Anglais (Am�ricain)", 1);
INSERT INTO langue (nom, libelle, id_devise) VALUES ("FR", "Fran�ais", 2);
INSERT INTO langue (nom, libelle, id_devise) VALUES ("EN", "Anglais", 3);
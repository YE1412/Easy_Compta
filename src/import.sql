-- Datas in Startup of Application

INSERT INTO tva (pourcentage, libelle) VALUES (0.2, "20%");
INSERT INTO tva (pourcentage, libelle) VALUES (0.02, "2%");
INSERT INTO tva (pourcentage, libelle) VALUES (0.05, "5%");

INSERT INTO devise (symbole) VALUES ('$');
INSERT INTO devise (symbole) VALUES ('€');
INSERT INTO devise (symbole) VALUES ('£');

INSERT INTO langue (nom, libelle, id_devise) VALUES ("US", "English (US)", 1);
INSERT INTO langue (nom, libelle, id_devise) VALUES ("FR", "Français", 2);
INSERT INTO langue (nom, libelle, id_devise) VALUES ("EN", "English (EN)", 3);
SET search_path TO projet;


-- Supprimer toutes les données
DELETE FROM club;
DELETE FROM Personne;
DELETE FROM Utilisateur;
DELETE FROM Participant;
DELETE FROM Benevole;
DELETE FROM Administrateurs;
DELETE FROM Equipe;
DELETE FROM Parcours;
DELETE FROM Balise;
DELETE FROM Permis_de_Conduire;
DELETE FROM Poste;
DELETE FROM est_composee;
DELETE FROM a_poste;
DELETE FROM est_assignee;


------------------------------------------------------------
-- Table: Club
------------------------------------------------------------
INSERT INTO Club (Nom) VALUES 
  ('club nautique'),
  ('club equitation'),
  ('club aviation'),
  ('club noyade');


------------------------------------------------------------
-- Table: Personne
------------------------------------------------------------

INSERT INTO Personne (Nom,Prenom,DateNaissance,Tel,Mail,Adresse) VALUES 
  ('test1','testa',DATE '05-07-1999','0432587891','a@b.fr','aa'),
  ('test2','teste',DATE '09-25-2009','0423588791','c@d.fr','bb'),
  ('test3','testi',DATE '04-19-2000','0342578819','d@f.fr','cc'),
  ('test4','testo',DATE '01-30-1960','0342578819','e@f.fr','dd'),
  ('test5','tesu',DATE '05-18-1999','0324587819','f@f.fr','ee');


------------------------------------------------------------
-- Table: Utilisateur
------------------------------------------------------------
INSERT INTO Utilisateur (login,password,id) VALUES 
  ('machin','lol',1),
  ('truc','test',2),
  ('muche','tset',3),
  ('et','tet',4),
  ('personne','dt',5);
  
------------------------------------------------------------
-- Table: Participant
------------------------------------------------------------
INSERT INTO Participant (id,Autorisation_medicale,Autorisation_parentale,id_Equipe,Id_Club) VALUES 
  (1,FALSE,FALSE,1,3),
  (2,TRUE,FALSE,1,2);
  
------------------------------------------------------------
-- Table: Benevole
------------------------------------------------------------

INSERT INTO Benevole (id) VALUES 
	(3),
	(5);

------------------------------------------------------------
-- Table: Administrateurs
------------------------------------------------------------

INSERT INTO Administrateurs (id) VALUES 
 (4);

------------------------------------------------------------
-- Table: Parcours
------------------------------------------------------------

INSERT INTO Parcours(Date_depart) VALUES 
	(TIMESTAMP '2020-06-30 04:05:06');

------------------------------------------------------------
-- Table: Equipe
------------------------------------------------------------

INSERT INTO Equipe (Payement,Nbr_repas,Categorie,Id_Parcours,Id_Capitaine,Id_Equipier) VALUES
(TRUE,1,'HA',1,2,1);

------------------------------------------------------------
-- Table: Balise
------------------------------------------------------------

INSERT INTO Balise VALUES
(1,10.0,15.0),
(2,15.0,10.0);

------------------------------------------------------------
-- Table: Permis de Conduire
------------------------------------------------------------

--INSERT INTO Permis_de_Conduire VALUES;

------------------------------------------------------------
-- Table: Poste
------------------------------------------------------------

INSERT INTO Poste VALUES
('Signaleur','ME',37,TIMESTAMP '2020-06-30 08:30:00',TIMESTAMP '2020-06-30 13:30:00');

------------------------------------------------------------
-- Table: est composee
------------------------------------------------------------

INSERT INTO est_composee VALUES 
(1,1),
(2,1);

------------------------------------------------------------
-- Table: a poste
------------------------------------------------------------

INSERT INTO a_poste VALUES 
('Signaleur',3),
('Signaleur',5);

------------------------------------------------------------
-- Table: est assignée
------------------------------------------------------------

INSERT INTO est_assignee VALUES
(3,1),
(5,2);


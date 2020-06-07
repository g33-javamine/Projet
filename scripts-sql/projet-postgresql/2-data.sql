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
  ('Croustipon','Michel',DATE '1999-07-20','0432587891','michel.croustipon@gmail.com','51 Rue jean Mace, Bourg Envol'),
  ('Crottal','Didier',DATE '2009-09-25','0455241536','didier.crottal@gmail.com','15 Rue de la Boustifaille, Katrepat'),
  ('Pratier','Nathaniel',DATE '2000-04-19','0322885123','nathaniel.pratier@gmail.com','10 Place des Grands Hommes, Dijon'),
  ('Biloune','Mathilde',DATE '1960-01-30','0277352771','mathilde.biloune@gmail.com','51 Place de l"Eglise'),
  ('Plouf','Prin',DATE '1999-05-18','0923487425','prin.plouf@gmail.com','33 Boulevard Du Chapomy, Orléans'),
  ('Grasset','Jerome',DATE '1968-09-04','0475551854','jerome.grasset@gmail.com','3 Avenue du Saule, Valence'),
  ('Crapotier','Dominique',DATE '2001-11-25','0101588689','dominique.crapotier@gmail.com','8 Rue de la Maison Haute, Bouzolle'),
  ('Labatelle','Nadege',DATE '1985-04-05','0342578819','nadege.labatelle@gmail.com','5 Avenue Maraicher, Saharache'),
  ('Radebaz','Pedro',DATE '1989-02-05','0341177819','pedro.radebaz@gmail.com','58 Rue du Pugilat, Fallaise'),
  ('Toutcourt','Bob',DATE '1975-08-15','0595642214','bob.toutcourt@gmail.com','Envers de rue 23, San Dsudsou'),
  ('Amblard','Emmanuel',DATE '1995-12-26','0754815231','emmanuel.amblard@gmail.com','2 Boulevard Janze, Salapon les Beignets'),
  ('Koulouba','Houpouloum',DATE '1994-05-25','0488452530','houpouloum.koulouba@gmail.com','planete Mars'),
  ('Gloomy','Michael',DATE '1978-11-22','0124313124','michael.gloomy@gmail.com','67 Rue du Terreau, Boudlard'),
  ('Bonneau','Jean',DATE '1961-04-31','0627515234','jean.bonneau@gmail.com','33 Rue du not-ok, Terryland'),
  ('Desu','Kawaii',DATE '1978-08-21','0954813527','kawaii.desu@gmail.com','67 Rue du vieux chantier, Albertville'),
  ('Batent','Denis',DATE '1980-01-16','0948572163','denis.batent@gmail.com','21 Rue de la rue, Villeville'),
  ('Cabroul','Francis',DATE '1998-08-31','0866666601','francis.batent@gmail.com','1 rue du premier, Robbierotten'),
  ('de Montaigue','Philibert',DATE '2000-04-08','0889453612','philibert.demontaigue@gmail.com','666 Rue de l"Ange, Montaigue'),
  ('Aristobourge','Alphonse',DATE '1974-01-05','0326451324','alphonse.aristobourge@gmail.com','15 Rue de Versailles, Versailles'),
  ('Balouba','Bill',DATE '1987-06-13','0216548431','bill.balouba@gmail.com','Bunker de la Maison Blanche, Washinton'),
  ('Ketchum','Sacha',DATE '1999-07-20','0432587891','sacha.ketchum@gmail.com','12 place de la place, Plasse'),
  ('Ketch','Saas',DATE '2009-09-25','0455241536','saas.ketch@gmail.com','1 Rue de rien, Bourg Bourg'),
  ('Afoula','Barnabe',DATE '2000-04-19','0322885123','barnabe.afoula@gmail.com','42 Rue de la bonne réponse, Nigloland'),
  ('Tracassin','Jordan',DATE '1960-01-30','0277352771','jordan.tracassin@gmail.com','56 Rue des Champions, Koupdumonde'),
  ('Madame','Oui',DATE '1999-05-18','0923487425','oui.madame@gmail.com','Ananas dans la mer, Bikkini Bottom'),
  ('Monsieur','Non',DATE '1968-09-04','0475551854','non.monsieur@gmail.com','100 Avenue du Sang, Bonaugure'),
  ('Peche','Michel',DATE '2001-11-25','0101588689','michel.peche@gmail.com','7 Rue du Sel, Saltybeach'),
  ('Respire','Britnes',DATE '1985-04-05','0342578819','britness.respire@gmail.com','777 Rue du Casino, Last Vegas'),
  ('Marie','Marie',DATE '1989-02-05','0341177819','marie.marie@gmail.com','Porte gauche du batiment B, 12 Rue de la galère, Paris'),
  ('Balatielle','Romain',DATE '1975-08-15','0595642214','romain.balatielle@gmail.com','8 Rue du Ouite, Bruxelles'),
  ('Nouvoala','Marechal',DATE '1995-12-26','0754815231','marechal.nouvoala@gmail.com','41 Boulevard de la Vilette, Monopoly'),
  ('Liane','Francois',DATE '1994-05-25','0488452530','francois.liane@gmail.com','98 Rue de la premiere Etoile, Saint-Denis'),
  ('Merguez','Angela',DATE '1978-11-22','0124313124','angela.merguez@gmail.com','81 Rue du Saltimbanque, Macon'),
  ('Groland','Francois',DATE '1961-04-31','0627515234','francois.groland@gmail.com','1 Rue du Marais, Fort fort lointain'),
  ('Echtetete','Philippe',DATE '1978-08-21','0954813527','phillipe.echtetete@gmail.com','2 Rue du Greu, Fort Fort Proche'),
  ('Dadidou','Roi',DATE '1980-01-16','0948572163','roi.dadidou@gmail.com','54 Rue de la Pierre Philosophale, Laquoi'),
  ('En riz','Thierry',DATE '1998-08-31','0866666601','thierry.enriz@gmail.com','67 Rue du Pou qui Grimpe, Serretete'),
  ('Kong','Donkey',DATE '2000-04-08','0889453612','donkey.kong@gmail.com','41 Rue du caillou, Pieeeeeerre'),
  ('Klaxon','Michael',DATE '1974-01-05','0326451324','michale.klaxon@gmail.com','87 Avenue du Venu, Venue'),
  ('Maipazelle','Hilary',DATE '1987-06-13','0216548431','hilary.maipazelle@gmail.com','2 Boulevard du Boulevard, Inceptionville'),
  ('bol','air',DATE '1987-06-13','0216548431','adminboldair@gmail.com','rue du commerce');


------------------------------------------------------------
-- Table: Utilisateur
------------------------------------------------------------
INSERT INTO Utilisateur (login,password,id) VALUES 
  ('benevole','test',1),
  ('participant','test',21),
  ('bol','air',41);
  
------------------------------------------------------------
-- Table: Participant
------------------------------------------------------------
INSERT INTO Participant (id,Autorisation_medicale,Autorisation_parentale,id_Equipe,Id_Club) VALUES
  (21,FALSE,FALSE,1,NULL),
  (22,FALSE,FALSE,1,NULL),
  (23,FALSE,FALSE,2,NULL),
  (24,FALSE,FALSE,2,NULL),
  (25,FALSE,FALSE,3,NULL),
  (26,FALSE,FALSE,3,NULL),
  (27,FALSE,FALSE,4,NULL),
  (28,FALSE,FALSE,4,NULL),
  (29,FALSE,FALSE,5,NULL),
  (30,FALSE,FALSE,5,NULL),
  (31,FALSE,FALSE,6,NULL),
  (32,FALSE,FALSE,7,NULL),
  (33,FALSE,FALSE,6,NULL),
  (34,FALSE,FALSE,7,NULL),
  (35,FALSE,FALSE,10,NULL),
  (36,FALSE,FALSE,9,NULL),
  (37,FALSE,FALSE,9,NULL),
  (38,FALSE,FALSE,8,NULL),
  (39,FALSE,FALSE,10,NULL),
  (40,FALSE,FALSE,8,NULL);
  
------------------------------------------------------------
-- Table: Benevole
------------------------------------------------------------

INSERT INTO Benevole (id) VALUES 
	(1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20);

------------------------------------------------------------
-- Table: Administrateurs
------------------------------------------------------------

INSERT INTO Administrateurs (id) VALUES 
 (41);

------------------------------------------------------------
-- Table: Parcours
------------------------------------------------------------

INSERT INTO Parcours(Date_depart) VALUES 
	(TIMESTAMP '2020-06-30 04:05:06');

------------------------------------------------------------
-- Table: Equipe
------------------------------------------------------------

INSERT INTO Equipe (Paiement,Nbr_repas,Categorie,Id_Parcours,Id_Capitaine,Id_Equipier) VALUES
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

INSERT INTO Poste(nom_poste,Types_benevoles,nombre_benevole,debut_intervention,fin_intervention) VALUES
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


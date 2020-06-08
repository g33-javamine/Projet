------------------------------------------------------------
--        Script Postgre 
------------------------------------------------------------
DROP TABLE IF EXISTS est_assignee;
DROP TABLE IF EXISTS est_composee;
DROP TABLE IF EXISTS Balise;
DROP TABLE IF EXISTS a_poste;
DROP TABLE IF EXISTS Poste;
DROP TABLE IF EXISTS Permis_de_Conduire;
DROP TABLE IF EXISTS Utilisateur;
DROP TABLE IF EXISTS Equipe;
DROP TABLE IF EXISTS Administrateurs;
DROP TABLE IF EXISTS Participant;
DROP TABLE IF EXISTS Benevole;
DROP TABLE IF EXISTS club;
DROP TABLE IF EXISTS Personne;
DROP TABLE IF EXISTS Parcours;


SET search_path TO projet;

------------------------------------------------------------
-- Table: Club
------------------------------------------------------------
CREATE TABLE Club(
	Id    SERIAL NOT NULL ,
	Nom   VARCHAR (50) NOT NULL  ,
	CONSTRAINT Club_PK PRIMARY KEY (Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Personne
------------------------------------------------------------
CREATE TABLE Personne(
	id              SERIAL NOT NULL ,
	Nom             VARCHAR (50) NOT NULL ,
	Prenom          VARCHAR (50) NOT NULL ,
	DateNaissance   DATE  NOT NULL CHECK (DateNaissance > DATE '1919-06-30' ),
	Tel             VARCHAR (10) NOT NULL CHECK (Tel SIMILAR TO '(0|1|2|3|4|5|6|7|8|9){10}') ,
	Mail            VARCHAR (50) NOT NULL CHECK ( Mail LIKE '%@%.%') , 
	Adresse         VARCHAR (50) NOT NULL ,
	CONSTRAINT Personne_PK PRIMARY KEY (id)
)WITHOUT OIDS;

------------------------------------------------------------
-- Table: Utilisateur
------------------------------------------------------------
CREATE TABLE Utilisateur(
	login      VARCHAR (50) NOT NULL ,
	password   VARCHAR (50) NOT NULL ,
	id         INT  NOT NULL  ,
	CONSTRAINT Utilisateur_PK PRIMARY KEY (login),
	CONSTRAINT Utilisateur_Personne_FK FOREIGN KEY (id) REFERENCES Personne(id),
	CONSTRAINT Utilisateur_Personne_id UNIQUE (id),
	CONSTRAINT Utilisateur_password_unique UNIQUE (password)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Participant
------------------------------------------------------------
CREATE TABLE Participant(
	id                       INT  NOT NULL,
	Autorisation_medicale    BOOL  NOT NULL,
	Autorisation_parentale   BOOL  NOT NULL,
	id_Equipe                INT  NOT NULL,
	Id_Club                  INT,
	CONSTRAINT Participant_PK PRIMARY KEY (id) ,
	CONSTRAINT Participant_Personne_FK FOREIGN KEY (id) REFERENCES Personne(id),
	CONSTRAINT Participant_Club_FK FOREIGN KEY (Id_Club) REFERENCES Club(Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Benevole
------------------------------------------------------------
CREATE TABLE Benevole(
	id   INT  NOT NULL  ,
	CONSTRAINT Benevole_PK PRIMARY KEY (id),
	CONSTRAINT Benevole_Personne_FK FOREIGN KEY (id) REFERENCES Personne(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Administrateurs
------------------------------------------------------------
CREATE TABLE Administrateurs(
	id   INT  NOT NULL  ,
	CONSTRAINT Administrateurs_PK PRIMARY KEY (id),
	CONSTRAINT Administrateurs_Personne_FK FOREIGN KEY (id) REFERENCES Personne(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Parcours
------------------------------------------------------------
CREATE TABLE Parcours(
	Id            SERIAL NOT NULL ,
	Date_depart   TIMESTAMP  NOT NULL  ,
	CONSTRAINT Parcours_PK PRIMARY KEY (Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Equipe
------------------------------------------------------------
CREATE TABLE Equipe(
	Id                   SERIAL NOT NULL ,
	Paiement             BOOL  NOT NULL ,
	Nbr_repas            INT  NOT NULL CHECK (nbr_repas >= 0),
	Categorie            VARCHAR (2) NOT NULL CHECK (Categorie SIMILAR TO '(H|F|M)(A|N)'  ),
	Id_Parcours          INT  NOT NULL ,
	Id_Capitaine         INT  NOT NULL UNIQUE,
	Id_Equipier  		 INT NOT NULL UNIQUE,
	CONSTRAINT Equipe_PK PRIMARY KEY (Id),
	CONSTRAINT Equipe_Parcours_FK FOREIGN KEY (Id_Parcours) REFERENCES Parcours(Id),
	CONSTRAINT Equipe_Participant1_FK FOREIGN KEY (Id_Capitaine) REFERENCES Participant(id),
	CONSTRAINT Equipe_Participant2_FK FOREIGN KEY (Id_Equipier) REFERENCES Participant(id),
	CONSTRAINT ck_doublon_participant CHECK
  (
    (Id_Capitaine != Id_Equipier)AND(Id_Equipier != Id_Capitaine)
  )
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Balise
------------------------------------------------------------
CREATE TABLE Balise(
	Id          INT  NOT NULL ,
	Longitude   DOUBLE PRECISION  NOT NULL ,
	Latitude    DOUBLE PRECISION  NOT NULL  ,
	CONSTRAINT Balise_PK PRIMARY KEY (Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Permis de Conduire
------------------------------------------------------------
CREATE TABLE Permis_de_Conduire(
	numero                     VARCHAR (9) NOT NULL ,
	date_de_delivrance         DATE  NOT NULL ,
	prefecture_de_delivrance   VARCHAR (50) NOT NULL ,
	id                         INT  NOT NULL  ,
	CONSTRAINT Permis_de_Conduire_PK PRIMARY KEY (numero),
	CONSTRAINT Permis_de_Conduire_Benevole_FK FOREIGN KEY (id) REFERENCES Benevole(id),
	CONSTRAINT Permis_de_Conduire_Benevole_AK UNIQUE (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Poste
------------------------------------------------------------
CREATE TABLE Poste(
	nom_poste            VARCHAR (50) NOT NULL ,
	Types_benevoles      VARCHAR (2) NOT NULL CHECK (Types_benevoles SIMILAR TO '(M|N)(E|N)'),
	nombre_benevole      INT  NOT NULL ,
	debut_intervention   TIMESTAMP  NOT NULL ,
	fin_intervention     TIMESTAMP  NOT NULL,
	CONSTRAINT check_tmp_intervention_positif CHECK(debut_intervention<fin_intervention),
	CONSTRAINT Poste_PK PRIMARY KEY (nom_poste)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: est composee
------------------------------------------------------------
CREATE TABLE est_composee(
	Id_Balises            INT  NOT NULL ,
	Id_Parcours   INT  NOT NULL  ,
	CONSTRAINT est_composee_PK PRIMARY KEY (Id_Balises,Id_Parcours),
	CONSTRAINT est_composee_Balise_FK FOREIGN KEY (Id_Balises) REFERENCES Balise(Id),
	CONSTRAINT est_composee_Parcours0_FK FOREIGN KEY (Id_Parcours) REFERENCES Parcours(Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: a poste
------------------------------------------------------------
CREATE TABLE a_poste(
	id_poste   VARCHAR (50) NOT NULL ,
	id          INT  NOT NULL  ,
	CONSTRAINT a_poste_PK PRIMARY KEY (id_poste,id),
	CONSTRAINT a_poste_Poste_FK FOREIGN KEY (id_poste) REFERENCES Poste(nom_poste),
	CONSTRAINT a_poste_Benevole0_FK FOREIGN KEY (id) REFERENCES Benevole(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: est assignée
------------------------------------------------------------
CREATE TABLE est_assignee(
	id          INT  NOT NULL ,
	Id_Balise   INT  NOT NULL  ,
	CONSTRAINT est_assignee_PK PRIMARY KEY (id,Id_Balise),
	CONSTRAINT est_assignee_Benevole_FK FOREIGN KEY (id) REFERENCES Benevole(id),
	CONSTRAINT est_assignee_Balise0_FK FOREIGN KEY (Id_Balise) REFERENCES Balise(Id)
)WITHOUT OIDS;


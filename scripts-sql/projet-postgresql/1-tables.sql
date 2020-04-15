------------------------------------------------------------
--        Script Postgre 
------------------------------------------------------------



------------------------------------------------------------
-- Table: Personne
------------------------------------------------------------
CREATE TABLE public.Personne(
	id              SERIAL NOT NULL ,
	Nom             VARCHAR (50) NOT NULL ,
	Prenom          VARCHAR (50) NOT NULL ,
	DateNaissance   DATE  NOT NULL ,
	Tel             VARCHAR (10) NOT NULL ,
	Mail            VARCHAR (50) NOT NULL ,
	Adresse         VARCHAR (50) NOT NULL  ,
	CONSTRAINT Personne_PK PRIMARY KEY (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Club
------------------------------------------------------------
CREATE TABLE public.Club(
	Id    SERIAL NOT NULL ,
	Nom   VARCHAR (50) NOT NULL  ,
	CONSTRAINT Club_PK PRIMARY KEY (Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Participant
------------------------------------------------------------
CREATE TABLE public.Participant(
	id                       INT  NOT NULL ,
	Autorisation_medicale    BOOL  NOT NULL ,
	Autorisation_parentale   BOOL  NOT NULL ,
	id_Equipe                INT  NOT NULL ,
	Id_Club                  INT    ,
	CONSTRAINT Participant_PK PRIMARY KEY (id) ,
	CONSTRAINT Participant_AK UNIQUE (id_Equipe)

	,CONSTRAINT Participant_Personne_FK FOREIGN KEY (id) REFERENCES public.Personne(id)
	,CONSTRAINT Participant_Club0_FK FOREIGN KEY (Id_Club) REFERENCES public.Club(Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Benevole
------------------------------------------------------------
CREATE TABLE public.Benevole(
	id   INT  NOT NULL  ,
	CONSTRAINT Benevole_PK PRIMARY KEY (id)

	,CONSTRAINT Benevole_Personne_FK FOREIGN KEY (id) REFERENCES public.Personne(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Parcours
------------------------------------------------------------
CREATE TABLE public.Parcours(
	Id            SERIAL NOT NULL ,
	Date_depart   TIMESTAMP  NOT NULL  ,
	CONSTRAINT Parcours_PK PRIMARY KEY (Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Balise
------------------------------------------------------------
CREATE TABLE public.Balise(
	Id          INT  NOT NULL ,
	Longitude   DOUBLE PRECISION  NOT NULL ,
	Latitude    DOUBLE PRECISION  NOT NULL  ,
	CONSTRAINT Balise_PK PRIMARY KEY (Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Permis de Conduire
------------------------------------------------------------
CREATE TABLE public.Permis_de_Conduire(
	numero                     VARCHAR (9) NOT NULL ,
	date_de_delivrance         DATE  NOT NULL ,
	prefecture_de_delivrance   VARCHAR (50) NOT NULL ,
	id                         INT  NOT NULL  ,
	CONSTRAINT Permis_de_Conduire_PK PRIMARY KEY (numero)

	,CONSTRAINT Permis_de_Conduire_Benevole_FK FOREIGN KEY (id) REFERENCES public.Benevole(id)
	,CONSTRAINT Permis_de_Conduire_Benevole_AK UNIQUE (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Utilisateur
------------------------------------------------------------
CREATE TABLE public.Utilisateur(
	login      VARCHAR (50) NOT NULL ,
	password   VARCHAR (50) NOT NULL ,
	id         INT  NOT NULL  ,
	CONSTRAINT Utilisateur_PK PRIMARY KEY (login)

	,CONSTRAINT Utilisateur_Personne_FK FOREIGN KEY (id) REFERENCES public.Personne(id)
	,CONSTRAINT Utilisateur_Personne_AK UNIQUE (id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Administrateurs
------------------------------------------------------------
CREATE TABLE public.Administrateurs(
	id   INT  NOT NULL  ,
	CONSTRAINT Administrateurs_PK PRIMARY KEY (id)

	,CONSTRAINT Administrateurs_Personne_FK FOREIGN KEY (id) REFERENCES public.Personne(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Poste
------------------------------------------------------------
CREATE TABLE public.Poste(
	nom_poste            VARCHAR (50) NOT NULL ,
	Types_benevoles      VARCHAR (2) NOT NULL ,
	nombre_benevole      INT  NOT NULL ,
	debut_intervention   TIMESTAMP  NOT NULL ,
	fin_intervention     TIMESTAMP  NOT NULL  ,
	CONSTRAINT Poste_PK PRIMARY KEY (nom_poste)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: Equipe
------------------------------------------------------------
CREATE TABLE public.Equipe(
	Id                            SERIAL NOT NULL ,
	Payement                      BOOL  NOT NULL ,
	Nbr_repas                     INT  NOT NULL ,
	Categorie                     VARCHAR (2) NOT NULL ,
	Id_Parcours                   INT  NOT NULL ,
	id_Participant                INT  NOT NULL ,
	id_Participant_est_equipier   INT  NOT NULL  ,
	CONSTRAINT Equipe_PK PRIMARY KEY (Id)

	,CONSTRAINT Equipe_Parcours_FK FOREIGN KEY (Id_Parcours) REFERENCES public.Parcours(Id)
	,CONSTRAINT Equipe_Participant0_FK FOREIGN KEY (id_Participant) REFERENCES public.Participant(id)
	,CONSTRAINT Equipe_Participant1_FK FOREIGN KEY (id_Participant_est_equipier) REFERENCES public.Participant(id)
	,CONSTRAINT Equipe_Participant_AK UNIQUE (id_Participant)
	,CONSTRAINT Equipe_Participant0_AK UNIQUE (id_Participant_est_equipier)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: est composee
------------------------------------------------------------
CREATE TABLE public.est_composee(
	Id            INT  NOT NULL ,
	Id_Parcours   INT  NOT NULL  ,
	CONSTRAINT est_composee_PK PRIMARY KEY (Id,Id_Parcours)

	,CONSTRAINT est_composee_Balise_FK FOREIGN KEY (Id) REFERENCES public.Balise(Id)
	,CONSTRAINT est_composee_Parcours0_FK FOREIGN KEY (Id_Parcours) REFERENCES public.Parcours(Id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: a poste
------------------------------------------------------------
CREATE TABLE public.a_poste(
	nom_poste   VARCHAR (50) NOT NULL ,
	id          INT  NOT NULL  ,
	CONSTRAINT a_poste_PK PRIMARY KEY (nom_poste,id)

	,CONSTRAINT a_poste_Poste_FK FOREIGN KEY (nom_poste) REFERENCES public.Poste(nom_poste)
	,CONSTRAINT a_poste_Benevole0_FK FOREIGN KEY (id) REFERENCES public.Benevole(id)
)WITHOUT OIDS;


------------------------------------------------------------
-- Table: est assignée
------------------------------------------------------------
CREATE TABLE public.est_assignee(
	id          INT  NOT NULL ,
	Id_Balise   INT  NOT NULL  ,
	CONSTRAINT est_assignee_PK PRIMARY KEY (id,Id_Balise)

	,CONSTRAINT est_assignee_Benevole_FK FOREIGN KEY (id) REFERENCES public.Benevole(id)
	,CONSTRAINT est_assignee_Balise0_FK FOREIGN KEY (Id_Balise) REFERENCES public.Balise(Id)
)WITHOUT OIDS;


-- Vues

CREATE VIEW v_compte_avec_roles AS
	SELECT c.*, ARRAY_AGG( r.role ) AS roles
	FROM compte c 
	LEFT JOIN ROLE r ON c.idcompte = r.idcompte
	GROUP BY c.idcompte;


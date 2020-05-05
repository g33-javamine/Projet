package projet.view.systeme;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import jfox.commun.exception.ExceptionValidation;
import projet.dao.DaoCompte2;
import projet.data.Utilisateur;


public class ModelConnexion {
	
	// Logger
	public static final Logger logger = Logger.getLogger( ModelConnexion.class.getName() );
	
	
	// Données observables 
	
	// Vue connexion
	private final Utilisateur			courant = new Utilisateur();

	// Compte connecté
	private final Property<Utilisateur>	compteActif = new SimpleObjectProperty<>();

	
	// Autres champs
	@Inject
	private DaoCompte2	daoCompte;
	

	// Getters 
	
	public Utilisateur getCourant() {
		return courant;
	}
	
	public Property<Utilisateur> compteActifProperty() {
		return compteActif;
	}
	
	public Utilisateur getCompteActif() {
		return compteActif.getValue();
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		courant.setLogin( "geek" );
		courant.setPassword( "geek" );
	}
	
	
	// Actions


	public void ouvrirSessionUtilisateur() {

		Utilisateur compte = daoCompte.validerAuthentification(
					courant.loginProperty().getValue(), courant.passwordProperty().getValue() );
		
		if( compte == null ) {
			throw new ExceptionValidation( "Pseudo ou mot de passe invalide." );
		} else {
			Platform.runLater( () -> compteActif.setValue( compte ) );
		}
	}
	

	public void fermerSessionUtilisateur() {
		compteActif.setValue( null );
	}

}

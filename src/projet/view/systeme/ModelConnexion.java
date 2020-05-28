package projet.view.systeme;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import jfox.commun.exception.ExceptionValidation;
import projet.dao.DaoUtilisateur;
import projet.data.Utilisateur;


public class ModelConnexion {
	
	// Logger
	public static final Logger logger = Logger.getLogger( ModelConnexion.class.getName() );
	
	
	// Données observables 
	
	// Vue connexion
	private final Utilisateur			courant = new Utilisateur();

	// Utilisateur connecté
	private final Property<Utilisateur>	compteActif = new SimpleObjectProperty<>();

	
	// Autres champs
	@Inject
	private DaoUtilisateur	daoUtilisateur;
	

	// Getters 
	
	public Utilisateur getCourant() {
		return courant;
	}
	
	public Utilisateur getCompteActif()
	{
		return compteActif.getValue();
	}
	
	public Property<Utilisateur> compteActifProperty() {
		return compteActif;
	}
	
	public Utilisateur getUtilisateurActif() {
		return compteActif.getValue();
	}
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		courant.setLogin( "machin" );
		courant.setPassword( "lol" );
	}
	
	
	// Actions


	public void ouvrirSessionUtilisateur() {

		Utilisateur compte = daoUtilisateur.validerAuthentification(
					courant.loginProperty().getValue(), courant.passwordProperty().getValue() );
		
		if( compte == null ) {
			throw new ExceptionValidation( "Pseudo ou mot de passe invalide." );
		} else {
			Utilisateur.setCourant(compte);
			Platform.runLater( () -> compteActif.setValue( compte ) );
		}
	}
	

	public void fermerSessionUtilisateur() {
		compteActif.setValue( null );
	}

}

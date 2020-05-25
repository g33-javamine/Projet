package projet.view.participant;


import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.commun.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoParticipant;
import projet.dao.DaoPersonne;
import projet.data.Participant;
import projet.data.Utilisateur;


public class ModelParticipant {
	
	
	// Données observables 
	 
	
	private final Participant	courant = new Participant();
	
	
	// Autres champs
    @Inject
	private IMapper			mapper;
    @Inject
	private DaoParticipant		daoParticipant;
    @Inject
	private DaoPersonne		daoPersonne;
	
	// Getters
	


	public Participant getCourant() {
		return courant;
	}
	
	
	// Actualisations
	
	/*public void actualiserListe() {
		liste.setAll( daoCompte.listerTout() );
 	}*/
	
	
	// Actions
	
	public void preparerAjouter() {
		mapper.update( courant, new Participant() );
	}

	
	public void preparerModifier( Participant item ) {
		mapper.update( courant, daoParticipant.retrouver( item.getId() ) );
	}
	
	/*public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();
		
		if( courant.getPseudo() == null || courant.getPseudo().isEmpty() ) {
			message.append( "\nLe pseudo ne doit pas être vide." );
		} else 	if ( courant.getPseudo().length() < 3 ) {
			message.append( "\nLe pseudo est trop court : 3 mini." );
		} else  if ( courant.getPseudo().length()> 25 ) {
			message.append( "\nLe pseudo est trop long : 25 maxi." );
		} else 	if ( ! daoCompte.verifierUnicitePseudo( courant.getPseudo(), courant.getId() ) ) {
			message.append( "\nLe pseudo " + courant.getPseudo() + " est déjà utilisé." );
		}
		
		if( courant.getMotDePasse() == null || courant.getMotDePasse().isEmpty() ) {
			message.append( "\nLe mot de passe ne doit pas être vide." );
		} else  if ( courant.getMotDePasse().length()< 3 ) {
			message.append( "\nLe mot de passe est trop court : 3 mini." );
		} else  if ( courant.getMotDePasse().length()> 25 ) {
			message.append( "\nLe mot de passe est trop long : 25 maxi." );
		}
		
		if( courant.getEmail() == null || courant.getEmail().isEmpty() ) {
			message.append( "\nL'adresse e-mail ne doit pas être vide." );
		} else  if ( courant.getEmail().length()> 100 ) {
			message.append( "\nL'adresse e-mail est trop longue : 100 maxi." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			courant.setId( daoParticipant.inserer( courant ) );
		} else {
			// modficiation
			daoCompte.modifier( courant );
		}
	}*/
	
	
	public void supprimer( Participant item ) {
		daoPersonne.supprimer( item.getId() );
	}

}

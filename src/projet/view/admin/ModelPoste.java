package projet.view.admin;

import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoPoste;
import projet.data.Poste;

public class ModelPoste 
{
	// Données observables 
		
		private final Poste	courant = new Poste();
		
		
		// Autres champs
	    @Inject
		private IMapper			mapper;
	    @Inject
		private DaoPoste		daoPoste;
		
		
		// Getters
		
		public Poste getCourant() {
			return courant;
		}
		
		// Actions
		
		public void preparerAjouter() {
			mapper.update( courant, new Poste() );
		}

		
		public void preparerModifier( Poste item ) {
			mapper.update( courant, daoPoste.retrouver( item.getNomPoste() ) );
		}
		
		
		public void validerMiseAJour() {

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
				courant.setId( daoCompte.inserer( courant ) );
			} else {
				// modficiation
				daoCompte.modifier( courant );
			}
		}
}

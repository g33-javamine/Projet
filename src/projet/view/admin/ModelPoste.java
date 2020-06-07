package projet.view.admin;

import javax.inject.Inject;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import projet.commun.IMapper;
import projet.dao.DaoPoste;
import projet.data.Poste;

public class ModelPoste 
{
	// Données observables 
		
		private final Poste	courant = new Poste();
		private final StringProperty newNom = new SimpleStringProperty();
		private boolean creer;
		
		// Autres champs
	    @Inject
		private IMapper			mapper;
	    @Inject
		private DaoPoste		daoPoste;
		
		
		// Getters
		
		public Poste getCourant() {
			return courant;
		}
		
		public final StringProperty newNomProperty() {
			return this.newNom;
		}

		public final String getNewNom() {
			return this.newNomProperty().get();
		}

		public final void setNewNom(final String newNom) {
			this.newNomProperty().set(newNom);
		}
		
		// Actions
		
		public void preparerAjouter() {
			creer = true;
			newNom.setValue("");
			mapper.update( courant, new Poste() );
		}

		
		public void preparerModifier( Poste item ) {
			creer = false;
			newNom.setValue(item.getNomPoste());
			mapper.update( courant, daoPoste.retrouver( item.getNomPoste() ) );
		}
		
		
		public void validerMiseAJour() {

			// Vérifie la validité des données
			
			StringBuilder message = new StringBuilder();
			
			if( newNom.getValue() == null || newNom.getValue() == "" ) {
				message.append( "\nLe nom du poste ne doit pas être vide." );
			} else  if ( newNom.getValue().length()> 50 ) {
				message.append( "\nLe nom du poste est trop long : 50 maxi." );
			}
			
			if( courant.getDebutIntervention() == null  ) {
				message.append( "\nLa date de début d'intervention ne doit pas être vide." );
			}
			
			if( courant.getFinIntervention() == null  ) {
				message.append( "\nLa date de début d'intervention ne doit pas être vide." );
			}
			
			
			
			// Effectue la mise à jour
			
			if ( creer) 
			{
				// Insertion
				courant.setNomPoste(newNom.getValue());
				daoPoste.inserer( courant );
				creer = false;
			} else {
				// modficiation
				daoPoste.modifier( courant,newNom.getValue());
			}
		}
		
}

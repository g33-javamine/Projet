package projet.view.personne;

import javax.inject.Inject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.commun.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoCategorie;
import projet.dao.DaoMemo;
import projet.dao.DaoPersonne;
import projet.data.Club;


public class ModelCategorie  {
	
	
	// Données observables 
	
	private final ObservableList<Club> liste = FXCollections.observableArrayList(); 
	
	private final Club	courant = new Club();

	
	// Autres champs
    @Inject
	private IMapper			mapper;
    @Inject
	private DaoCategorie	daoCategorie;
    @Inject
    private DaoPersonne		daoPersonne;
    @Inject
    private DaoMemo			daoMemo;
	
	
	// Getters 
	
	public ObservableList<Club> getListe() {
		return liste;
	}
	
	public Club getCourant() {
		return courant;
	}
	
	
	// Actualisations
	
	public void actualiserListe() {
		liste.setAll( daoCategorie.listerTout() );
 	}


	// Actions
	
	public void preparerAjouter() {
		mapper.update( courant, new Club() );
	}
	
	public void preparerModifier( Club item ) {
		mapper.update( courant, daoCategorie.retrouver( item.getId() ) );
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getLibelle() == null || courant.getLibelle().isEmpty() ) {
			message.append( "\nLe libellé ne doit pas être vide." );
		} else  if ( courant.getLibelle().length()> 25 ) {
			message.append( "\nLe libellé est trop long : 25 maxi." );
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			courant.setId( daoCategorie.inserer( courant ) );
		} else {
			// modficiation
			daoCategorie.modifier( courant );
		}
	}
	
	
	public void supprimer( Club item ) {
		
		// Vérifie l'abence de personnes rattachées à la catégorie
		if ( daoPersonne.compterPourCategorie( item.getId() ) != 0 ) {
			throw new ExceptionValidation( "Des personnes sont rattachées à cette catégorie.." ) ;
		}
		
		// Vérifie l'abence de mémos rattaches à la catégorie
		if ( daoMemo.compterPourCategorie( item.getId() ) != 0 ) {
			throw new ExceptionValidation( "Des mémos sont rattachés à cette catégorie.." ) ;
		}
		
		daoCategorie.supprimer( item.getId() );
		mapper.update( courant, UtilFX.findNext( liste, item ) );
	}
	
}

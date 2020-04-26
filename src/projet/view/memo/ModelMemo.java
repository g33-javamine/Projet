package projet.view.memo;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jfox.commun.exception.ExceptionValidation;
import jfox.javafx.util.UtilFX;
import projet.commun.IMapper;
import projet.dao.DaoMemo;
import projet.dao.DaoPersonne;
import projet.data.Club;
import projet.data.Participant;
import projet.data.Personne;
import projet.view.personne.ModelCategorie;
import projet.view.systeme.ModelConfig;


public class ModelMemo  {
	
	
	// Données observables 
	
	private final ObservableList<Participant> liste = FXCollections.observableArrayList(); 
	
	private final Participant	courant = new Participant();
	
	private final ObservableList<Personne> personnesPourDialogAjout = FXCollections.observableArrayList();
	
	private final Property<Image>	schema = new SimpleObjectProperty<>();
	
	private boolean		flagModifSchema;

	
	// Autres champs
    @Inject
	private IMapper			mapper;
    @Inject
	private DaoMemo			daoMemo;
    @Inject
    private ModelCategorie	modelCategorie;
    @Inject
	private DaoPersonne		daoPersonne;
    @Inject
    private ModelConfig		modelConfig;
	
    
	// Initialisations
	
	@PostConstruct
	public void init() {
		schema.addListener( obs -> flagModifSchema = true );
	}
	
	
	// Getters 
	
	public ObservableList<Participant> getListe() {
		return liste;
	}
	
	public Participant getCourant() {
		return courant;
	}
	
	public ObservableList<Club> getCategories() {
		return modelCategorie.getListe();
	}
	
	public ObservableList<Personne> getPersonnesPourDialogAjout() {
		return personnesPourDialogAjout;
	} 
	
	public Property<Image> schemaProperty() {
		return schema;
	}
	
	
	// Actualisations
	
	public void actualiserListe() {
		liste.setAll( daoMemo.listerTout() );
 	}
	
	public void actualiserListePersonnesPourDialogAjout() {
		personnesPourDialogAjout.setAll( daoPersonne.listerTout() );
		personnesPourDialogAjout.removeAll( courant.getPersonnes() );
 	}


	// Actions
	
	public void preparerAjouter() {
		modelCategorie.actualiserListe();
		mapper.update( courant, new Participant() );
		schema.setValue(null);
		flagModifSchema = false;
	}
	
	public void preparerModifier( Participant item ) {
		modelCategorie.actualiserListe();
		mapper.update( courant, daoMemo.retrouver( item.getId() ) );
		File fichier = getFichierSchemaCourant();
		if ( fichier.exists() ) {
			schema.setValue( new Image( fichier.toURI().toString() ) );
		} else {
			schema.setValue( null );
		}
		flagModifSchema = false;
	}
	
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
		StringBuilder message = new StringBuilder();

		if( courant.getTitre() == null || courant.getTitre().isEmpty() ) {
			message.append( "\nLe titre ne doit pas être vide." );
		} else  if ( courant.getTitre().length()> 50 ) {
			message.append( "\nLe titre est trop long : 50 maxi." );
		}

		if( courant.getEffectif() != null ) {
			if ( courant.getEffectif() < 0  ) {
				message.append( "\nL'effectif ne peut pas être inféireur à zéro." );
			} else  if ( courant.getEffectif() > 1000 ) {
				message.append( "\nEffectif trop grand : 1000 maxi." );
			}
		}

		if( courant.getBudget() != null ) {
			if ( courant.getBudget().doubleValue() < 0  ) {
				message.append( "\nLe budget ne peut pas être inféireur à zéro." );
			} else  if ( courant.getBudget().doubleValue() > 1000000 ) {
				message.append( "\nBudget trop grand : 1 000 000 maxi." );
			}
		}
		if( courant.getEcheance() != null ) {
			if ( courant.getEcheance().isBefore( LocalDate.of( 2000, 1, 1) ) 
					|| courant.getEcheance().isAfter( LocalDate.of( 2099, 12, 31) )  ) {
				message.append( "\nLa date d'échéance doit être compirse entre la 01/01/2000 et le 31/12/2099." );
			}
		}
		
		if ( message.length() > 0 ) {
			throw new ExceptionValidation( message.toString().substring(1) );
		}
		
		
		// Effectue la mise à jour
		
		if ( courant.getId() == null ) {
			// Insertion
			courant.setId( daoMemo.inserer( courant ) );
		} else {
			// modficiation
			daoMemo.modifier( courant );
		}

		if ( flagModifSchema ) {
			if (schema.getValue() == null) {
				getFichierSchemaCourant().delete();
			} else {
				try {
					ImageIO.write(SwingFXUtils.fromFXImage(schema.getValue(), null), "JPG", getFichierSchemaCourant());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} 
		}
		
	}
	
	
	public void supprimer( Participant item ) {
		
		daoMemo.supprimer( item.getId() );
		mapper.update( courant, UtilFX.findNext( liste, item ) );
		
		getFichierSchemaCourant().delete();
	}

	
	public void supprimerPersonne( Personne item ) {
		courant.getPersonnes().remove(item);
	}
	
	public void ajouterPersonne( Personne item ) {
		courant.getPersonnes().add(item);
	}
	
	
	// Méthodes auxiliaires
	
	public File getFichierSchemaCourant() {
		String nomFichier = String.format( "%06d.jpg", courant.getId() );
		File dossierSchemas = modelConfig.getDossierSchemas();
		return new File( dossierSchemas, nomFichier );
	}
	
}

package projet.view.admin;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Equipe;
import projet.data.Participant;
import projet.data.Poste;
import projet.util.ConverterStringTimestamp;


public class ControllerPosteForm {
	
	
	// Composants de la vue
	
	@FXML
	private TextField			textFieldNomPoste;
	@FXML
	private CheckBox			checkBoxEnfant;
	@FXML
	private CheckBox			checkBoxAdulte;
	@FXML
	private Spinner<Integer>	spinnerNbrBenevoles;
	@FXML
	private TextField			textFieldDebut;
	@FXML
	private TextField			textFieldFin;

	
	
	// Autres champs

	private Poste 				courant;
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelPoste			modelPoste;

	
	// Initialisation du Controller
	
	@FXML
	private void initialize() {
		
		courant = modelPoste.getCourant();

		// Data binding
		courant = modelPoste.getCourant();
		textFieldNomPoste.textProperty().bindBidirectional( courant.nomPosteProperty());
		spinnerNbrBenevoles.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,30,0));
		spinnerNbrBenevoles.getValueFactory().valueProperty().bindBidirectional(courant.nbrBenevoleProperty());
		checkBoxEnfant.selectedProperty().set(courant.getTypeBenevole().charAt(1)=='E');
		checkBoxAdulte.selectedProperty().set(courant.getTypeBenevole().charAt(0)=='M');
		textFieldDebut.textProperty().bindBidirectional(courant.debutInterventionProperty(), new ConverterStringTimestamp());
		textFieldFin.textProperty().bindBidirectional(courant.debutInterventionProperty(), new ConverterStringTimestamp());
		
		// Configuration de l'objet ListView

		// Data binding

		itemRoles.clear();
    	for ( String role : Roles.getRoles()  ) {
    		itemRoleAjouter( role, false);
    	}
		actualiserListeItemRoles();    			

        listViewRoles.setItems( itemRoles );
    	
    	// De compteVue vers la liste
    	courant.getRoles().addListener(
        	(ListChangeListener<String>)	c -> {
    			c.next();
				for ( String role : c.getAddedSubList() ) {
					itemRoleChoisir(role, true );
				}
				for ( String role : c.getRemoved() ) {
					itemRoleChoisir(role, false );
				}
        });
    	
		
		// Affichage
        listViewRoles.setCellFactory( CheckBoxListCell.forListView(
        		(ItemRole item) -> item.choisiProperty()
   		) );
        
	}
	
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.PosteListe );
	}
	
	@FXML
	private void doValider() {
		modelPoste.validerMiseAJour();
		managerGui.showView( EnumView.PosteListe );
	}

    
    
    // Méthodes auxiliaires
    
    private void actualiserListeItemRoles() {
    	Collections.sort( itemRoles,
    			(Comparator<ItemRole>) (i1, i2) -> {
    				return i1.getRole().toUpperCase().compareTo( i2.getRole().toUpperCase() );
    	} );
		for( ItemRole item : itemRoles ) {
			item.setChoisi( courant.isInRole( item.getRole() ) );
		}
    }
	
    
    
    private ItemRole itemRoleRetrouver( String role ) {
    	if ( role != null ) {
    		for ( ItemRole item : itemRoles ) {
    			if ( item.getRole().equals( role ) ) {
    				return item;
    			}
    		}
    	}
		return null;
    }

    
    private void itemRoleAjouter( String role, boolean choisi ) {
		ItemRole item = itemRoleRetrouver(role);
		if ( item == null ) {
			itemRoles.add( new ItemRole(role, choisi) );
		}
    }
    
    private void itemRoleChoisir( String role, boolean choisi ) {
		ItemRole item = itemRoleRetrouver(role);
		if ( item != null ) {
			item.setChoisi(choisi);;
		}
    }
	
	
	// Classe auxiliaire

	private class ItemRole {

		// Champs
		
		private final String			role;
		private final BooleanProperty	choisi;
		

		// Constructeurs

		public ItemRole( String role, boolean present ) {
			this.role = role;
			this.choisi = new SimpleBooleanProperty( present );
        	// Binding de l'item vers compteVue 
    		choisi.addListener(
    			(ChangeListener<Boolean> ) ( obs, oldValue, newValue ) -> {
    				if ( newValue ) {
    					if( ! courant.getRoles().contains( role) ) {
        					courant.getRoles().add( role );
    					}
    				} else {
       					courant.getRoles().remove( role );
    				}
        		});
		}

		
		// Getters & Setters

		public String getRole() {
			return role;
		}
		
		public void setChoisi( boolean choisi ) {
			choisiProperty().set(choisi);
		}
		
		public BooleanProperty choisiProperty() {
			return choisi;
		}
		
		@Override
		public String toString() {
			return Roles.getLibellé( role );
		}
		
	}
}

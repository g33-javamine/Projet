package projet.view.participant;

import java.util.Collections;
import java.util.Comparator;

import javax.inject.Inject;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import jfox.javafx.util.ConverterStringInteger;
import jfox.javafx.view.IManagerGui;
import projet.commun.Roles;
import projet.data.Utilisateur;
import projet.view.EnumView;


public class ControllerCompteForm {

	
	// Composants de la vue
	
	@FXML
	private TextField			textFieldId;
	@FXML
	private TextField			textFieldPseudo;
	@FXML
	private TextField			textFieldMotDePasse;
	@FXML
	private TextField			textFieldEmail;
	@FXML
	private ListView<ItemRole>	listViewRoles;

	
	// Autres champs

	private Utilisateur 				courant;
	private final ObservableList<ItemRole> itemRoles = FXCollections.observableArrayList();
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelCompte			modelCompte;

	
	// Initialisation du Controller
	
	@FXML
	private void initialize() {
		
		courant = modelCompte.getCourant();

		// Data binding
		courant = modelCompte.getCourant();
		textFieldId.textProperty().bindBidirectional( courant.idProperty(), new ConverterStringInteger());
		textFieldPseudo.textProperty().bindBidirectional( courant.pseudoProperty() );
		textFieldMotDePasse.textProperty().bindBidirectional( courant.motDePasseProperty() );
		textFieldEmail.textProperty().bindBidirectional( courant.emailProperty() );

		
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
		managerGui.showView( EnumView.CompteListe );
	}
	
	@FXML
	private void doValider() {
		modelCompte.validerMiseAJour();
		managerGui.showView( EnumView.CompteListe );
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

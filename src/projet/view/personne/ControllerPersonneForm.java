package projet.view.personne;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jfox.javafx.control.EditingCell;
import jfox.javafx.util.ConverterStringInteger;
import jfox.javafx.view.IManagerGui;
import projet.data.Club;
import projet.data.Personne;
import projet.data.Administrateurs;
import projet.view.EnumView;


public class ControllerPersonneForm  {
	
	
	// Composants de la vue
	
	@FXML
	private TextField			textFieldId;
	@FXML
	private TextField			textFieldNom;
	@FXML	
	private TextField			textFieldPrenom;
    @FXML
    private ComboBox<Club>	comboBoxCategorie;
	@FXML
	private TableView<Administrateurs>	tableViewTelphones;
	@FXML
	private TableColumn<Administrateurs, Integer> columnId;
	@FXML
	private TableColumn<Administrateurs, String> columnLibelle;
	@FXML
	private TableColumn<Administrateurs, String> columnNumero;

	
	// Autres champs
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelPersonne		modelPersonne;
    
	
	// Initialisation du controller
	
	public void initialize() {

		Personne courant = modelPersonne.getCourant();
		
		// Champs simples
		textFieldId.textProperty().bindBidirectional( courant.idProperty(), new ConverterStringInteger() );
		textFieldNom.textProperty().bindBidirectional( courant.nomProperty() );
		textFieldPrenom.textProperty().bindBidirectional( courant.prenomProperty() );

        
		// Configuration de la combo box

		// Data binding
		comboBoxCategorie.setItems(  modelPersonne.getCategories());
        comboBoxCategorie.valueProperty().bindBidirectional( courant.categorieProperty() );
 		
		
		// Configuration du TableView

		// Data binding
		tableViewTelphones.setItems(  courant.getTelephones() );
		
		columnId.setCellValueFactory( t -> t.getValue().idProperty() );
		columnLibelle.setCellValueFactory( t -> t.getValue().libelleProperty() );
		columnNumero.setCellValueFactory( t -> t.getValue().numeroProperty() );

		columnLibelle.setCellFactory(  p -> new EditingCell<>() );
		columnNumero.setCellFactory(  p -> new EditingCell<>() );		
	
	}
	
	
	// Actions
	
	@FXML
	private void doValider() {
		modelPersonne.validerMiseAJour();
		managerGui.showView( EnumView.PersonneListe );
	}
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.PersonneListe );
	}
	
	@FXML
	private void doAjouterTelephone() {
		modelPersonne.ajouterTelephone();
	}
	
	
	@FXML
	private void doiSupprimerTelephone() {
		Administrateurs telephone = tableViewTelphones.getSelectionModel().getSelectedItem();
		modelPersonne.supprimerTelephone(telephone);
	}
    
}

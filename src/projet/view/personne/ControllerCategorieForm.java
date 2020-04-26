package projet.view.personne;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.converter.IntegerStringConverter;
import jfox.javafx.view.IManagerGui;
import projet.data.Club;
import projet.view.EnumView;


public class ControllerCategorieForm {

	
	// Composants de la vue
	
	@FXML
	private TextField			textFieldId;
	@FXML
	private TextField			textFieldLibelle;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelCategorie		modelCategorie;


	// Initialisation du Controller

	@FXML
	private void initialize() {

		// Data binding
		
		Club courant = modelCategorie.getCourant();
		textFieldId.textProperty().bindBidirectional( courant.idProperty(), new IntegerStringConverter()  );
		textFieldLibelle.textProperty().bindBidirectional( courant.libelleProperty()  );
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.CategorieListe );
	}
	
	@FXML
	private void doValider() {
		modelCategorie.validerMiseAJour();
		managerGui.showView( EnumView.CategorieListe );
	}

}

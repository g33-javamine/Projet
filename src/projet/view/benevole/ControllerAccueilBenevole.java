package projet.view.benevole;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Benevole;
import projet.view.EnumView;


public class ControllerAccueilBenevole {
	
	
	// Composants de la vue
	@FXML
	private Button				buttonCompte;
	@FXML
	private Button				buttonParcours;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelBenevole		modelBenevole;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		// Data binding


		// Configuration des bouton
	}

	
	// Actions
	
	@FXML
	private void doCompte() {
		managerGui.showView( EnumView.CompteBenevole);
	}



	
	
	// Gestion des évènements

	// Clic sur la lise

	
	// Méthodes auxiliaires
	

}

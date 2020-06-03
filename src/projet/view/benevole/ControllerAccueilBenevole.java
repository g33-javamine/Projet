package projet.view.benevole;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import jfox.javafx.view.IManagerGui;
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

	@FXML
	private void doParcours() {
		managerGui.showView( EnumView.Parcours );
	}

	
	
	// Gestion des évènements

	// Clic sur la lise

	
	// Méthodes auxiliaires
	

}

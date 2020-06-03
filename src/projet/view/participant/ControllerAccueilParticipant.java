package projet.view.participant;

import javax.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import jfox.javafx.view.IManagerGui;
import projet.view.EnumView;


public class ControllerAccueilParticipant  {
	
	
	// Composants de la vue

	@FXML
	private Button				buttonCompte;
	
	@FXML
	private Button				buttonParcours;

	
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	
	
	// Actions

	@FXML
	private void doCompte() {
		managerGui.showView( EnumView.CompteParticipant );
	}
	@FXML
	private void doParcours() {
		managerGui.showView( EnumView.Parcours );
	}



}

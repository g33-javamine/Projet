package projet.view.admin;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import jfox.javafx.view.IManagerGui;
import projet.view.EnumView;


public class ControllerGestion {
	
	
	// Composants de la vue

	@FXML
	private Button				buttonModifier;
	@FXML
	private Button				buttonSupprimer;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() 
	{

	}
	
	@FXML
	private void doGestionParticipant() 
	{
		managerGui.showView(EnumView.GestionParticipant);
	}
	
	@FXML
	private void doGestionBenevole() 
	{
		managerGui.showView(EnumView.GestionBenevole);
	}
}

package projet.view.participant;

import javax.inject.Inject;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Utilisateur;
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
	@Inject
	private ModelParticipant			modelParticipant;
	
	
	// Actions

	@FXML
	private void doCompte() {
		managerGui.showView( EnumView.CompteParticipant );
	}




}

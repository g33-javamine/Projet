package projet.view.admin;



import javax.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import jfox.javafx.view.IManagerGui;
import projet.view.EnumView;


public class ControllerAccueilAdmin {

	
	// Composants de la vue
	
	@FXML
	private Button			buttonGestion;
	@FXML
	private Button			buttonParcours;
	

	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;

	
	

	// Initialisation du Controller

	@FXML
	private void initialize() 
	{
		
	}
	
	

	
	
	// Actions
	
	@FXML
	private void doGestion() 
	{
		managerGui.showView(EnumView.Gestion);
	}
	
	@FXML
	private void doParcours() 
	{
		managerGui.showView(EnumView.Parcours);
	}

}

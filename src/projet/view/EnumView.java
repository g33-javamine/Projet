package projet.view;

import javafx.scene.Scene;
import jfox.javafx.view.IEnumView;


public enum EnumView implements IEnumView {

	
	// Valeurs
	Parcours			("parcours/ViewParcours.fxml"),
	Gestion 			("admin/ViewGestion.fxml"),
	GestionBenevole		("admin/ViewGestionBenevole.fxml"),
	GestionParticipant	("admin/ViewGestionParticipant.fxml"),
	AccueilParticipant 	("participant/ViewAccueilParticipant.fxml"),
	AccueilBenevole 	("benevole/ViewAccueilBenevole.fxml"),
	AccueilAdmin 		("admin/ViewAccueilAdmin.fxml"),
	Connexion			( "systeme/ViewConnexion.fxml" ),
	CompteParticipant 	("participant/ViewCompteParticipant.fxml"), 
	CompteBenevole		("benevole/ViewCompteBenevole.fxml"),
	Autorisations		("participant/ViewAutoParticipant.fxml"),	
	Permis				("benevole/ViewPermisBenevole.fxml"),
	;

	
	// Champs
	
	private String		path;
	private Object		controller;
	private Scene		scene;

	
	// Constructeur 
	
	EnumView( String path ) {
		this.path = path;
	}

	
	// Getters & setters

	@Override
	public String getPath() {
		return path;
	}
	
	@Override
	public Object getController() {
		return controller;
	}

	@Override
	public void setController(Object controller) {
		this.controller = controller;
	}
	
	@Override
	public Scene getScene() {
		return scene;
	}
	
	@Override
	public void setScene(Scene scene) {
		this.scene = scene;
	}

}

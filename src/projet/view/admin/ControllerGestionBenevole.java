package projet.view.admin;

import javax.inject.Inject;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Benevole;
import projet.data.Poste;

public class ControllerGestionBenevole {
	
	
	// Composants de la vue

	@FXML
	private ListView<Poste>		listViewPoste;
	@FXML
	private ListView<Benevole>	listViewBenevole;
	@FXML
	private Button				buttonModifier;
	@FXML
	private Button				buttonAssimiler;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGestionBenevole			modelGestionBenevole;
	
	
	@FXML
	private void initialize() 
	{
		modelGestionBenevole.actualiserListePoste();
		ObservableList<Poste> test = modelGestionBenevole.getPosteList();
		System.out.println(test);
		listViewPoste.setItems(test);
		listViewPoste.setCellFactory(  UtilFX.cellFactory( item -> item.getNomPoste() ));
		listViewBenevole.setCellFactory(  UtilFX.cellFactory( item -> item.getPrenom()+" "+item.getNom()));
	}
	
	public void refreshPoste() {
		modelGestionBenevole.actualiserListeBenevole();
		UtilFX.selectInListView( listViewPoste, modelGestionBenevole.getPosteCourant() );
		listViewPoste.requestFocus();
	}
	
	public void refreshBenevole() {
		modelGestionBenevole.setPosteCourant(listViewPoste.getSelectionModel().getSelectedItem());
		modelGestionBenevole.actualiserListeBenevole();
		listViewBenevole.setItems(modelGestionBenevole.getBenevoleList());
	}
	
	// Actions
	
	@FXML
	private void doModifier() 
	{

	}
	
	@FXML
	private void doAssimiler() 
	{

	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) 
		{
			if ( listViewPoste.getSelectionModel().getSelectedIndex() == -1 ) {
				managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
			} else {
				refreshBenevole();
			}
		}
	}

}

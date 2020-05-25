package projet.view.admin;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Equipe;
import projet.data.Participant;


public class ControllerGestionParticipant {
	
	
	// Composants de la vue

	@FXML
	private ListView<Equipe>		listViewEquipe;
	@FXML
	private ListView<Participant>	listViewParticipant;
	@FXML
	private Button				buttonValidites;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGestionParticipant			modelGestionParticipant;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() 
	{
		modelGestionParticipant.actualiserListePersonnes();
		listViewEquipe.setItems(modelGestionParticipant.getEquipeList());
		listViewEquipe.setCellFactory(  UtilFX.cellFactory( item -> ""+item.getId() ));
		listViewParticipant.setCellFactory(  UtilFX.cellFactory( item -> item.getPrenom()+" "+item.getNom()));
		// Configuraiton des boutons
		listViewEquipe.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
				});
		configurerBoutons();

	}
	
	public void refreshEquipe() {
		modelGestionParticipant.actualiserListeParticipant();
		UtilFX.selectInListView( listViewEquipe, modelGestionParticipant.getEquipeCourant() );
		listViewEquipe.requestFocus();
	}
	
	public void refreshParticipant() {
		modelGestionParticipant.setEquipeCourant(listViewEquipe.getSelectionModel().getSelectedItem());
		modelGestionParticipant.actualiserListeParticipant();
		listViewParticipant.setItems(modelGestionParticipant.getParticipantList());
	}
	
	// Actions
	
	@FXML
	private void doValidites() 
	{

	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( listViewEquipe.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					refreshParticipant();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
		
    	if( listViewEquipe.getSelectionModel().getSelectedItems().isEmpty() ) {
    		buttonValidites.setDisable(true);
		} else {
			buttonValidites.setDisable(false);
		}
	}

}

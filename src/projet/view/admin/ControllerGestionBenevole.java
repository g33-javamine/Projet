package projet.view.admin;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Participant;
import projet.view.EnumView;


public class ControllerGestionBenevole {
	
	
	// Composants de la vue

	@FXML
	private ListView<Participant>		listView;
	private Button				buttonModifier;
	@FXML
	private Button				buttonSupprimer;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGestionBenevole			modelGestionBenevole;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() 
	{


	}
	
	public void refresh() {
		modelGestionBenevole.actualiserListe();
		UtilFX.selectInListView( listView, modelGestionBenevole.getCourant() );
		listView.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelGestionBenevole.preparerAjouter();;
		managerGui.showView( EnumView.GestionBenevoleForm );
	}

	@FXML
	private void doModifier() {
		Participant item = listView.getSelectionModel().getSelectedItem();
		if ( item == null ) {
			managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
		} else {
			modelGestionBenevole.preparerModifier(item);
			managerGui.showView( EnumView.GestionBenevoleForm );
		}
	}

	@FXML
	private void doSupprimer() {
		Participant item = listView.getSelectionModel().getSelectedItem();
		if ( item == null ) {
			managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
		} else {
			boolean reponse = managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" );
			if ( reponse ) {
				modelGestionBenevole.supprimer( item );
				refresh();
			}
		}
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( listView.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
		
    	if( listView.getSelectionModel().getSelectedItems().isEmpty() ) {
			buttonModifier.setDisable(true);
			buttonSupprimer.setDisable(true);
		} else {
			buttonModifier.setDisable(false);
			buttonSupprimer.setDisable(false);
		}
	}

}

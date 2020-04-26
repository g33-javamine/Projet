package projet.view.personne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Club;
import projet.report.EnumReport;
import projet.report.ManagerReport;


public class ControllerEtatPersonnesParCategorie2 {
	
	
	// Composants de la vue

	@FXML
	private ListView<Club>	listView;
	@FXML
	private Button				buttonEtat;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ManagerReport		managerReport;
	@Inject
	private ModelCategorie		modelCategorie;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		// Data binding
		listView.setItems( modelCategorie.getListe() );

		listView.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
	}
	
	public void refresh() {
		modelCategorie.actualiserListe();
		UtilFX.selectInListView( listView, modelCategorie.getCourant() );
		listView.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doFermer() {
		managerGui.closeStage();
	}

	@FXML
	private void doEtat() {
		Map<String, Object> params = new HashMap<>();
		List<Integer> liste = new ArrayList<>();
		for ( Club c : listView.getSelectionModel().getSelectedItems() ) {
			liste.add( c.getId() );
		}
		params.put( "idCategories", liste );
		managerReport.showViewer( EnumReport.PersonnesParCategorie2, params);
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
					doEtat();
				}
			}
		}
	}
	
	// Appui d'une touche sur la liste
	@FXML
	private void gererToucheSurListe( KeyEvent event ) {
		// Si échap ferme la boîte de dialogue 
		if ( event.getCharacter().charAt(0) == 27 )  {
			doFermer();;
		}
	}

}

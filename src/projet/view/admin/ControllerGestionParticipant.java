package projet.view.admin;


import javax.inject.Inject;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
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
	private boolean validite =false;
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGestionParticipant			modelGestionParticipant;
	
	
	// Initialisation du Contrvoller

	@FXML
	private void initialize() 
	{
		modelGestionParticipant.actualiserListePersonnes();
		ObservableList<Equipe> test = modelGestionParticipant.getEquipeList();
		System.out.println(test);
		listViewEquipe.setItems(test);
		listViewEquipe.setCellFactory(UtilFX.cellFactory(item -> "équipe numéro "+item.getId()) );
		listViewParticipant.setCellFactory(UtilFX.cellFactory( item -> item.getPrenom()+" "+item.getNom()));
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
		if(validite)
		{
			listViewEquipe.setCellFactory(UtilFX.cellFactory(item -> "équipe numéro "+item.getId()) );
			listViewParticipant.setCellFactory(UtilFX.cellFactory( item -> item.getPrenom()+" "+item.getNom()));
		}
		else
		{
			listViewEquipe.setCellFactory(lv-> new EquipeListCell());
			listViewParticipant.setCellFactory(lv -> new ParticipantListCell());
		}
		validite = !validite;
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) 
		{
			if ( listViewEquipe.getSelectionModel().getSelectedIndex() == -1 ) {
				managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
			} else {
				refreshParticipant();
			}
		}
	}
	
	public class EquipeListCell extends ListCell<Equipe> { 
		  
	    private final GridPane gridPane = new GridPane(); 
	    private final Label labelId = new Label(); 
	    private final Label labelRepas = new Label();
	    private final CheckBox checkBoxPayement = new CheckBox();
	    private final CheckBox checkBoxComplet = new CheckBox();
	    private final AnchorPane content = new AnchorPane(); 
	  
	    public EquipeListCell() { 
	    	GridPane.setConstraints(labelId, 0, 0);
	        // 
	        GridPane.setConstraints(labelRepas, 0, 1); 
	        // 
	        checkBoxPayement.setText("payement effectuée");
	        checkBoxPayement.setDisable(true);
	        GridPane.setConstraints(checkBoxPayement, 0, 2); 
	        //
	        checkBoxComplet.setText("préparation compléte");
	        checkBoxComplet.setDisable(true);
	        GridPane.setConstraints(checkBoxComplet, 0, 3); 
	        // 
	        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true)); 
	        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.LEFT, true)); 
	        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true)); 
	        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true)); 
	        gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true)); 
	        gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true)); 
	        gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true)); 
	        gridPane.setHgap(6); 
	        gridPane.setVgap(6); 
	        gridPane.getChildren().setAll(labelId, labelRepas, checkBoxPayement, checkBoxComplet); 
	        AnchorPane.setTopAnchor(gridPane, 0d); 
	        AnchorPane.setLeftAnchor(gridPane, 0d); 
	        AnchorPane.setBottomAnchor(gridPane, 0d); 
	        AnchorPane.setRightAnchor(gridPane, 0d); 
	        content.getChildren().add(gridPane); 
	    } 
	  
	  
	    @Override 
	    protected void updateItem(Equipe item, boolean empty) { 
	        super.updateItem(item, empty); 
	        setGraphic(null); 
	        setText(null); 
	        setContentDisplay(ContentDisplay.LEFT); 
	        if (!empty && item != null) { 
	        	labelId.setText(String.format("équipe numéro %d", item.getId()));
	        	labelRepas.setText(String.format("l'équipe a commandé %d repas",item.getNbrRepas()));
	        	checkBoxPayement.setSelected(item.getPaiement());
	        	checkBoxComplet.setSelected(item.getPaiement() && 
	        			item.getIdCapitaine().getAutoMedicale() && item.getIdCapitaine().getAutoParentale() &&
	        			item.getIdEquipier().getAutoMedicale() && item.getIdEquipier().getAutoParentale());
	            setText(null); 
	            setGraphic(content); 
	            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
	        } 
	    }
	}
	
	public class ParticipantListCell extends ListCell<Participant> { 
		  
	    private final GridPane gridPane = new GridPane();
	    private final Label labelNom = new Label(); 
	    private final Label labelPrenom = new Label();
	    private final CheckBox checkBoxMedical = new CheckBox();
	    private final CheckBox checkBoxParental = new CheckBox();
	    private final AnchorPane content = new AnchorPane(); 
	    
	    public ParticipantListCell() {
	    	GridPane.setConstraints(labelNom, 1, 0);
	        // 
	        GridPane.setConstraints(labelPrenom, 0, 0); 
	        // 
	        checkBoxMedical.setText("Autorisation médicale");
	        checkBoxMedical.setDisable(true);
	        GridPane.setConstraints(checkBoxMedical, 0, 1,2,1); 
	        //
	        checkBoxParental.setText("Autorisation Parentale");
	        checkBoxParental.setDisable(true);
	        GridPane.setConstraints(checkBoxParental, 0, 2,2,1); 
	        // 
	        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true)); 
	        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, HPos.LEFT, true)); 
	        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true)); 
	        gridPane.getColumnConstraints().add(new ColumnConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, HPos.LEFT, true)); 
	        gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true)); 
	        gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.NEVER, VPos.CENTER, true)); 
	        gridPane.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE, Priority.ALWAYS, VPos.CENTER, true)); 
	        gridPane.setHgap(6); 
	        gridPane.setVgap(6); 
	        gridPane.getChildren().setAll(labelNom, labelPrenom, checkBoxMedical, checkBoxParental); 
	        AnchorPane.setTopAnchor(gridPane, 0d); 
	        AnchorPane.setLeftAnchor(gridPane, 0d); 
	        AnchorPane.setBottomAnchor(gridPane, 0d); 
	        AnchorPane.setRightAnchor(gridPane, 0d); 
	        content.getChildren().add(gridPane); 
	    } 
	  
	  
	    @Override 
	    protected void updateItem(Participant item, boolean empty) { 
	        super.updateItem(item, empty); 
	        setGraphic(null); 
	        setText(null); 
	        setContentDisplay(ContentDisplay.LEFT); 
	        if (!empty && item != null) { 
	        	
	        	labelNom.setText(item.getNom());
	        	labelPrenom.setText(item.getPrenom());
	        	checkBoxMedical.setSelected(item.getAutoMedicale());
	        	checkBoxParental.setSelected(item.getAutoParentale());
	            setText(null); 
	            setGraphic(content); 
	            setContentDisplay(ContentDisplay.GRAPHIC_ONLY); 
	        } 
	    } 
	}
}

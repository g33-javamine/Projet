package projet.view.admin;

import javax.inject.Inject;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Benevole;
import projet.data.Poste;
import projet.view.EnumView;

public class ControllerGestionBenevole {
	
	
	// Composants de la vue

	@FXML
	private ListView<Poste>		listViewPoste;
	@FXML
	private ListView<Benevole>	listViewBenevole;
	@FXML
	private Button				buttonAjouterPoste;
	@FXML
	private Button				buttonSupprimerPoste;
	@FXML
	private Button				buttonModifierPoste;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGestionBenevole			modelGestionBenevole;
	@Inject
	private ModelPoste						modelPoste;
	
	
	@FXML
	private void initialize() 
	{
		modelGestionBenevole.actualiserListePoste();
		ObservableList<Poste> test = modelGestionBenevole.getPosteList();
		System.out.println(test);
		listViewPoste.setItems(test);
		listViewPoste.setCellFactory(  lv -> new PosteListCell());	
		listViewBenevole.setCellFactory(  UtilFX.cellFactory( item -> item.getPrenom()+" "+item.getNom()));
		//Drag and Drop
		listViewBenevole.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				modelGestionBenevole.setBenevoleCourant(listViewBenevole.getSelectionModel().getSelectedItem());
				
				System.out.println("Drag and drop");
				
				Dragboard dragBoard = listViewBenevole.startDragAndDrop(TransferMode.MOVE);
				 
				ClipboardContent content = new ClipboardContent();
				
				content.putString(listViewBenevole.getSelectionModel().getSelectedItem().getId()+"");
				
				dragBoard.setContent(content);
			}});
		
		listViewBenevole.setOnDragDone(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				modelGestionBenevole.setBenevoleCourant(null);
				System.out.println("drag and drop");
			}});
		
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
	private void doAjouter() 
	{
		modelPoste.preparerAjouter();
		managerGui.showView( EnumView.PosteForm );
	}
	
	@FXML
	private void doSupprimer() 
	{
		modelGestionBenevole.supprimer();
	}
	
	@FXML
	private void doModifier() 
	{
		Poste courant = modelGestionBenevole.getPosteCourant();
		if(courant == null || courant.getNomPoste() == "Sans poste")
			return;
		modelPoste.preparerModifier(courant);
		managerGui.showView( EnumView.PosteForm );
	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) 
		{
			if ( listViewPoste.getSelectionModel().getSelectedIndex() == -1 ) {
				
			} else {
				refreshBenevole();
			}
		}
	}
	
	//drag and drop
	
	public class PosteListCell extends ListCell<Poste> { 
		 
		private Poste item;
		
		public PosteListCell()
		{
			super();
			
			this.setOnDragEntered(new EventHandler<DragEvent>(){
				@Override
				public void handle(DragEvent dragEvent){
					System.out.println("setOnDragEntered");
					listViewPoste.setBlendMode(BlendMode.DARKEN);
				}});
			 
			this.setOnDragExited(new EventHandler<DragEvent>(){
				@Override
			 	public void handle(DragEvent dragEvent)
			 	{
			 		listViewPoste.setBlendMode(null);
			 	}});
			 
			this.setOnDragOver(new EventHandler<DragEvent>(){
				@Override
				public void handle(DragEvent dragEvent){
					dragEvent.acceptTransferModes(TransferMode.MOVE);
				}});
			 
			this.setOnDragDropped(new EventHandler<DragEvent>(){
				@Override
				public void handle(DragEvent dragEvent){
					System.out.println("from "+modelGestionBenevole.getPosteCourant().getNomPoste() +" to "+item.getNomPoste()); 
					modelGestionBenevole.getPosteCourant().getBenevoles().remove(modelGestionBenevole.getBenevoleCourant());
					item.getBenevoles().add(modelGestionBenevole.getBenevoleCourant());
					modelGestionBenevole.actualiserDragAndDrop(modelGestionBenevole.getPosteCourant(), item);
					dragEvent.setDropCompleted(true);
					refreshBenevole();
				}});
			
		}
		
	    @Override 
	    protected void updateItem(Poste item, boolean empty) { 
	        super.updateItem(item, empty); 
	        this.item = item;
	        setText(null); 
	        if (!empty && item != null) { 
	            final String text = String.format("%s %d/%d", item.getNomPoste(), item.getBenevoles().size(),item.getNbrBenevole()); 
	            setText(text); 
	        } 
	    } 
	}
}

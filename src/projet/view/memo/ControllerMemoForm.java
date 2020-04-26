package projet.view.memo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import jfox.javafx.util.ConverterStringDouble;
import jfox.javafx.util.ConverterStringInteger;
import jfox.javafx.util.ConverterStringLocalDate;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.IManagerGui;
import projet.data.Club;
import projet.data.Participant;
import projet.data.Personne;
import projet.view.EnumView;


public class ControllerMemoForm {

	
	// Composants de la vue
	
	@FXML
	private TextField		textFieldId;
	@FXML
	private TextField		textFieldTitre;
	@FXML
	private TextArea		textAreaDescription;
	@FXML
	private CheckBox		checkBoxUrgent;
	
	@FXML
	private ToggleGroup		toggleGroupStatut;
	@FXML
	private TextField		textFieldEffectif;
	@FXML
	private TextField		textFieldBudget;
	@FXML
	private DatePicker		datePicherEcheance;
	
	@FXML
	private ComboBox<Club>	comboBoxCategorie;
	@FXML
	private ListView<Personne>	listViewPersonnes;
	
	@FXML
	private ImageView		imageViewSchema;
	@FXML
	private Button			buttonOuvrirSchema;
	@FXML
	private Button			buttonSupprimerSchema;
	

	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelMemo		modelMemo;

	
	

	// Initialisation du Controller

	@FXML
	private void initialize() {

		// Data binding
		
		Participant courant = modelMemo.getCourant();
		
		// id
		textFieldId.textProperty().bindBidirectional( courant.idProperty(), new ConverterStringInteger()  );
		
		// Titre
		textFieldTitre.textProperty().bindBidirectional( courant.titreProperty() );
		
		// Description
		textAreaDescription.textProperty().bindBidirectional( courant.descriptionProperty() );
		
		// FlagUrgent
		checkBoxUrgent.selectedProperty().bindBidirectional( courant.flagUrgentProperty() );
	
		// Statut
		toggleGroupStatut.selectedToggleProperty().addListener( obs -> actualiserStatutDansModele() ) ; 
		courant.statutProperty().addListener(  obs -> actualiserStatutDansVue() );
		actualiserStatutDansVue();
	
		// Effectif
//		textFieldEffectif.textProperty().bindBidirectional( courant.effectifProperty(), new ConverterStringInteger() );
//		textFieldEffectif.focusedProperty().addListener( new ListenerFocusValidation( courant.effectifProperty() ));
		UtilFX.bindBidirectional( textFieldEffectif, courant.effectifProperty(), new ConverterStringInteger() );

		// Budget
//		textFieldBudget.textProperty().bindBidirectional( courant.budgetProperty(), new ConverterStringDouble( "#,##0.00" ) );
//		textFieldBudget.focusedProperty().addListener( new ListenerFocusValidation( courant.budgetProperty(), "Valeur incorrecte pour le budget."  ));
		UtilFX.bindBidirectional( textFieldBudget, courant.budgetProperty(), new ConverterStringDouble( "#,##0.00" ), "Valeur incorrecte pour le budget."  );

		// Echéance
//		datePicherEcheance.getEditor().textProperty().bindBidirectional( courant.echeanceProperty(), new ConverterStringLocalDate() );
//		datePicherEcheance.getEditor().focusedProperty().addListener(new ListenerFocusValidation( courant.echeanceProperty())  );
		UtilFX.bindBidirectional( datePicherEcheance.getEditor(), courant.echeanceProperty(), new ConverterStringLocalDate() );
		
		// Catégorie
		comboBoxCategorie.setItems( modelMemo.getCategories() );
		comboBoxCategorie.valueProperty().bindBidirectional( courant.categorieProperty() );
		
		// Personnes
		listViewPersonnes.setItems( courant.getPersonnes() );
		listViewPersonnes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		
		// Schéma
		imageViewSchema.imageProperty().bindBidirectional( modelMemo.schemaProperty() );
		
		buttonSupprimerSchema.disableProperty().bind( Bindings.isNull( imageViewSchema.imageProperty() ) );
	}
	
	
	public void refresh() {
		if ( modelMemo.getFichierSchemaCourant().exists() ) {
			buttonOuvrirSchema.setDisable( false );
		} else {
			buttonOuvrirSchema.setDisable( true );
		}
	}
	
	
	// Actions
	
	@FXML
	private void doSupprimerCategorie() {
		comboBoxCategorie.setValue( null );
	}
	
	@FXML
	private void doSupprimerPersonnes() {
		ObservableList<Personne> selectedItems = listViewPersonnes.getSelectionModel().getSelectedItems();
		for ( int i = selectedItems.size() - 1; i >= 0; --i ) {
			modelMemo.supprimerPersonne( selectedItems.get(i) );
		}
	}
	
	@FXML
	private void doAjouterPersonnes() {
		managerGui.showDialog( EnumView.MemoAjoutPersonnes );
	}
	
	@FXML
	private void doChoisirSchema() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisissez un fichier image");
		File fichier = fileChooser.showOpenDialog( managerGui.getStage() );
		if ( fichier != null ) {
			imageViewSchema.setImage( new Image( fichier.toURI().toString() ) );
		}
	}
	
	@FXML
	private void doOuvrirSchema() {
		try {
			Desktop.getDesktop().open( modelMemo.getFichierSchemaCourant() );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@FXML
	private void doSupprimerSchema() {
		imageViewSchema.setImage(null);
	}
	
	@FXML
	private void doAnnuler() {
		verifierValiditeSaisie();
		managerGui.showView( EnumView.MemoListe );
	}
	
	@FXML
	private void doValider() {
		verifierValiditeSaisie();
		modelMemo.validerMiseAJour();
		managerGui.showView( EnumView.MemoListe );
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurSchema( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( ! buttonOuvrirSchema.isDisable()  ) {
					doOuvrirSchema();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void actualiserStatutDansModele() {
		// Modifie le statut en fonction du bouton radio sélectionné 
		Toggle bouton = toggleGroupStatut.getSelectedToggle();
		int statut = toggleGroupStatut.getToggles().indexOf( bouton  );
		modelMemo.getCourant().setStatut( statut );
	}
	
	private void actualiserStatutDansVue() {
		// Sélectionne le bouton radio correspondant au statut
		int statut = modelMemo.getCourant().getStatut();
		Toggle bouton = toggleGroupStatut.getToggles().get( statut );
		toggleGroupStatut.selectToggle(  bouton );
	}
	
	
	private void verifierValiditeSaisie() {
		Participant courant = modelMemo.getCourant();
		UtilFX.checkParseError( textFieldEffectif, courant.effectifProperty() );
		UtilFX.checkParseError( textFieldBudget, courant.budgetProperty() );
		UtilFX.checkParseError( datePicherEcheance.getEditor(), courant.echeanceProperty() );
	}
}

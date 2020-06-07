package projet.view.participant;


import javax.inject.Inject;


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterStringLocalDate;
import jfox.javafx.util.ListenerFocusValidation;
import jfox.javafx.view.IManagerGui;
import projet.data.Participant;
import projet.util.ConverterStringDate;
import projet.view.EnumView;

public class ControllerCompteParticipant {

	// Composants de la vue

	@FXML
	private TextField textFieldNom;
	@FXML
	private TextField textFieldPrenom;
	@FXML
	private TextField textFieldTel;
	@FXML
	private TextField textFieldMail;
	@FXML
	private TextField textFieldAdresse;
	@FXML
	private DatePicker datePickerDateNaissance;
	@FXML
	private TextField textFieldCoequipier;
	@FXML
	private CheckBox checkBoxMedicale;
	@FXML
	private CheckBox checkBoxParentale;

	// Autres champs

	private Participant courant;

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ModelParticipant modelParticipant;

	// Initialisation du Controller

	@FXML
	private void initialize() {

		courant = modelParticipant.getCourant();

		// Data binding
		courant = modelParticipant.getCourant();
		textFieldNom.textProperty().bindBidirectional(courant.nomProperty());
		textFieldPrenom.textProperty().bindBidirectional(courant.prenomProperty());
		textFieldTel.textProperty().bindBidirectional(courant.telProperty());
		textFieldMail.textProperty().bindBidirectional(courant.mailProperty());
		textFieldAdresse.textProperty().bindBidirectional(courant.adresseProperty());
		datePickerDateNaissance.getEditor().textProperty().bindBidirectional(courant.dateNaissanceProperty(), new ConverterStringDate());
		datePickerDateNaissance.getEditor().focusedProperty().addListener(new ListenerFocusValidation(courant.dateNaissanceProperty()));
		datePickerDateNaissance.setConverter(new ConverterStringLocalDate("yyyy-MM-dd"));
		textFieldCoequipier.textProperty().setValue(courant.getCoequipier().getPrenom() + " "+ courant.getCoequipier().getNom());
		textFieldCoequipier.setEditable(false);
		checkBoxMedicale.selectedProperty().bindBidirectional(courant.autoMedicaleProperty());
		checkBoxParentale.selectedProperty().bindBidirectional(courant.autoParentaleProperty());
		checkBoxMedicale.setDisable(true);
		checkBoxParentale.setDisable(true);
	}

	// Actions

	@FXML
	private void doSauvegarder() {
		modelParticipant.Sauvegarder();
		managerGui.showView(EnumView.CompteParticipant);
	}

	@FXML
	private void doAjouterMedicale() {      // A Créer une méthode dans modèle pour ajouter autorisation
		//modelParticipant.validerMiseAJour();
		managerGui.showView(EnumView.Autorisations);
	}
	@FXML
	private void doAjouterParentale() {
		//modelParticipant.validerMiseAJour(); // A Créer une méthode dans modèle pour ajouter autorisation
		managerGui.showView(EnumView.Autorisations);
	}
	

}

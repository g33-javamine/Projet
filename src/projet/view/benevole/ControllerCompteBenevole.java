package projet.view.benevole;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterStringLocalDate;
import jfox.javafx.util.ListenerFocusValidation;
import jfox.javafx.view.IManagerGui;
import projet.data.Benevole;
import projet.util.ConverterStringDate;
import projet.view.EnumView;

public class ControllerCompteBenevole {

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
	private Button boutonAjouter;
	@FXML
	private Button boutonSauvegarder;
	@FXML
	private CheckBox checkBoxPermis;

	// Autres champs

	@Inject
	private IManagerGui managerGui;
	@Inject
	private ModelBenevole modelBenevole;

	// Initialisation du Controller

	@FXML
	private void initialize() {

		// Data binding

		Benevole courant = modelBenevole.getCourant();
		textFieldNom.textProperty().bindBidirectional(courant.nomProperty());
		textFieldPrenom.textProperty().bindBidirectional(courant.prenomProperty());
		textFieldTel.textProperty().bindBidirectional(courant.telProperty());
		textFieldMail.textProperty().bindBidirectional(courant.mailProperty());
		textFieldAdresse.textProperty().bindBidirectional(courant.adresseProperty());
		datePickerDateNaissance.getEditor().textProperty().bindBidirectional(courant.dateNaissanceProperty(), new ConverterStringDate());
		datePickerDateNaissance.getEditor().focusedProperty().addListener(new ListenerFocusValidation(courant.dateNaissanceProperty()));
		datePickerDateNaissance.setConverter(new ConverterStringLocalDate("uuuu-MM-dd"));
		//checkBoxPermis.selectedProperty().bindBidirectional( courant.permisProperty());
	}

	// Actions

	@FXML
	private void doSauvegarder() {
		modelBenevole.validerMiseAJour();
		managerGui.showView(EnumView.CompteBenevole);
	}

	@FXML
	private void doAjouter() {

		managerGui.showView(EnumView.CompteBenevole);

	}
}

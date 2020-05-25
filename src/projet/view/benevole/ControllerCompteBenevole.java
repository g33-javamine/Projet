package projet.view.benevole;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.IntegerStringConverter;
import jfox.javafx.util.ConverterStringInteger;
import jfox.javafx.util.ConverterStringLocalDate;
import jfox.javafx.util.ListenerFocusValidation;
import jfox.javafx.view.IManagerGui;
import projet.data.Benevole;
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
		textFieldPrenom.textProperty().bindBidirectional(courant.nomProperty());
		textFieldTel.textProperty().bindBidirectional(courant.nomProperty());
		textFieldMail.textProperty().bindBidirectional(courant.nomProperty());
		textFieldAdresse.textProperty().bindBidirectional(courant.nomProperty());
		// datePickerDateNaissance.getEditor().textProperty().bindBidirectional(courant.dateNaissanceProperty(),
		// new ConverterStringLocalDate());
		datePickerDateNaissance.getEditor().focusedProperty()
				.addListener(new ListenerFocusValidation(courant.dateNaissanceProperty()));

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

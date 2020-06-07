package projet.view.admin;

import javax.inject.Inject;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import jfox.javafx.view.IManagerGui;
import projet.data.Poste;
import projet.util.ConverterStringTimestamp;
import projet.view.EnumView;


public class ControllerPosteForm {
	
	
	// Composants de la vue
	
	@FXML
	private TextField			textFieldNomPoste;
	@FXML
	private CheckBox			checkBoxEnfant;
	@FXML
	private CheckBox			checkBoxAdulte;
	@FXML
	private Spinner<Integer>	spinnerNbrBenevoles;
	@FXML
	private TextField			textFieldDebut;
	@FXML
	private TextField			textFieldFin;

	
	
	// Autres champs

	private Poste 					courant;
	
	@Inject
	private IManagerGui				managerGui;
	@Inject
	private ModelPoste				modelPoste;
	@Inject
	private ModelGestionBenevole	modelGestionBenevole;

	
	// Initialisation du Controller
	
	@FXML
	private void initialize() 
	{
		
		courant = modelPoste.getCourant();

		// Data binding
		courant = modelPoste.getCourant();
		textFieldNomPoste.textProperty().bindBidirectional(modelPoste.newNomProperty());
		spinnerNbrBenevoles.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0));
		spinnerNbrBenevoles.getValueFactory().valueProperty().bindBidirectional(courant.nbrBenevoleProperty());
		
		checkBoxEnfant.selectedProperty().set(courant.getTypeBenevole().charAt(1)=='E');
		checkBoxEnfant.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) {
				char typeBenevoleAdulte = courant.getTypeBenevole().charAt(0);
				if(newValue)
					courant.setTypeBenevole(typeBenevoleAdulte+"E");
				else
					courant.setTypeBenevole(typeBenevoleAdulte+"N");
				System.out.println(courant.getTypeBenevole());
			}
		});
		
		checkBoxAdulte.selectedProperty().set(courant.getTypeBenevole().charAt(0)=='M');
		checkBoxAdulte.selectedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> value, Boolean oldValue, Boolean newValue) {
				char typeBenevoleEnfant = courant.getTypeBenevole().charAt(1);
				if(newValue)
					courant.setTypeBenevole("M"+typeBenevoleEnfant);
				else
					courant.setTypeBenevole("N"+typeBenevoleEnfant);
				System.out.println(courant.getTypeBenevole());
			}
		});
		
		textFieldDebut.textProperty().bindBidirectional(courant.debutInterventionProperty(), new ConverterStringTimestamp());
		textFieldFin.textProperty().bindBidirectional(courant.finInterventionProperty(), new ConverterStringTimestamp());
	}
	
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.GestionBenevole );
	}
	
	@FXML
	private void doValider() {
		modelPoste.validerMiseAJour();
		modelGestionBenevole.actualiserListePoste();
		managerGui.showView( EnumView.GestionBenevole );
	}

    
    
    // Méthodes auxiliaires	

}

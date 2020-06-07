package projet.view.participant;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import jfox.javafx.view.IManagerGui;
import projet.data.Participant;
import projet.view.EnumView;

public class ControllerAutorisationsParticipant {
	@FXML
	private ImageView imageViewAutoMedicale;
	@FXML
	private ImageView imageViewAutoParentale;

	@Inject
	private ModelParticipant modelParticipant;
	private Participant courant;
	@Inject
	private IManagerGui managerGui;

	@FXML
	private void initialize() {
		courant = modelParticipant.getCourant();
		modelParticipant.preparerModifierImageAutorisations(courant);
		imageViewAutoMedicale.imageProperty().bindBidirectional(modelParticipant.autoMedicaleProperty());
		imageViewAutoParentale.imageProperty().bindBidirectional(modelParticipant.autoParentaleProperty());
	}

	@FXML
	private void doChoisirAutoMedicale() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisissez un fichier image");
		File fichier = fileChooser.showOpenDialog(managerGui.getStage());
		if (fichier != null) {
			imageViewAutoMedicale.setImage(new Image(fichier.toURI().toString()));
		}
	}
	
	@FXML
	private void doOuvrirAutoMedicale() {
		try {
			Desktop.getDesktop().open(modelParticipant.getFichierSchemaCourantMedicale());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void doSupprimerAutoMedicale() {
		imageViewAutoMedicale.setImage(null);
	}
	
	
	@FXML
	private void doChoisirAutoParentale() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisissez un fichier image");
		File fichier = fileChooser.showOpenDialog(managerGui.getStage());
		if (fichier != null) {
			imageViewAutoParentale.setImage(new Image(fichier.toURI().toString()));
		}
	}
	
	@FXML
	private void doOuvrirAutoParentale() {
		try {
			Desktop.getDesktop().open(modelParticipant.getFichierSchemaCourantParentale());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void doSupprimerAutoParentale() {
		imageViewAutoParentale.setImage(null);
	}
	
	
	@FXML
	private void doSauvegarder(){
		modelParticipant.sauvegarderImageAutorisations();
		managerGui.showView(EnumView.CompteParticipant);
	}

}

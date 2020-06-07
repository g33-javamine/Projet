package projet.view.benevole;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import jfox.javafx.view.IManagerGui;
import projet.data.Benevole;
import projet.data.Participant;
import projet.view.EnumView;

public class ControllerPermisBenevole {
	@FXML
	private ImageView imageViewPermis;


	@Inject
	private ModelBenevole modelBenevole;
	private Benevole courant;
	@Inject
	private IManagerGui managerGui;

	@FXML
	private void initialize() {
		courant = modelBenevole.getCourant();
		modelBenevole.preparerModifierImagePermis(courant);
		imageViewPermis.imageProperty().bindBidirectional(modelBenevole.permisProperty());
	}

	@FXML
	private void doChoisirPermis() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisissez un fichier image");
		File fichier = fileChooser.showOpenDialog(managerGui.getStage());
		if (fichier != null) {
			imageViewPermis.setImage(new Image(fichier.toURI().toString()));
		}
	}
	
	@FXML
	private void doOuvrirPermis() {
		try {
			Desktop.getDesktop().open(modelBenevole.getFichierSchemaCourantPermis());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@FXML
	private void doSupprimerPermis() {
		imageViewPermis.setImage(null);
	}
	
	
	
	@FXML
	private void doSauvegarder(){
		modelBenevole.sauvegarderImagePermis();
		managerGui.showView(EnumView.CompteBenevole);
	}

}

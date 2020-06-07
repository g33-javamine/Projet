package projet.view.participant;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jfox.commun.exception.ExceptionValidation;
import projet.commun.IMapper;
import projet.dao.DaoParticipant;
import projet.dao.DaoPersonne;
import projet.data.Participant;
import projet.data.Utilisateur;
import projet.view.systeme.ModelConfig;
import projet.view.systeme.ModelConfigParentale;

public class ModelParticipant {

	// Données observables

	private final Participant courant = (Participant) Utilisateur.getCourant().getUtilisateur();
	private final Property<Image> autoMedicale = new SimpleObjectProperty<>();
	private final Property<Image> autoParentale = new SimpleObjectProperty<>();

	// Autres champs
	@Inject
	private IMapper mapper;
	@Inject
	private DaoParticipant daoParticipant;
	@Inject
	private DaoPersonne daoPersonne;
	@Inject
	private ModelConfig modelConfig;
	@Inject
	private ModelConfigParentale modelConfigParentale;

	// Getters

	public Participant getCourant() {
		return courant;
	}

	public Property<Image> autoMedicaleProperty() {
		return autoMedicale;
	}

	public Property<Image> autoParentaleProperty() {
		return autoParentale;
	}

	// Actualisations

	// Actions

	public void preparerAjouter() {
		mapper.update(courant, new Participant());
		autoMedicale.setValue(null);
	}

	public void preparerModifier(Participant item) {
		mapper.update(courant, daoParticipant.retrouver(item.getId()));

	}

	public void preparerModifierImageAutorisations(Participant item) {
		File fichier1 = getFichierSchemaCourantMedicale();
		if (fichier1.exists()) {
			autoMedicale.setValue(new Image(fichier1.toURI().toString()));
		} else {
			autoMedicale.setValue(null);
		}
		File fichier2 = getFichierSchemaCourantParentale();
		if (fichier2.exists()) {
			autoParentale.setValue(new Image(fichier2.toURI().toString()));
		} else {
			autoParentale.setValue(null);
		}
	}


	public void Sauvegarder() {

		// Vérifie la validité des données

		StringBuilder message = new StringBuilder();

		if (courant.getNom() == null || courant.getNom().isEmpty()) {
			message.append("\nLe nom ne doit pas être vide.");
		} else if (courant.getNom().length() > 50) {
			message.append("\nLe nom est trop long : 50 caractères maxi.");
		}
		if (courant.getPrenom() == null || courant.getPrenom().isEmpty()) {
			message.append("\nLe prénom ne doit pas être vide.");
		} else if (courant.getPrenom().length() > 50) {
			message.append("\nLe prénom est trop long : 50 caractères maxi.");
		}
		if (courant.getTel() == null || courant.getTel().isEmpty()) {
			message.append("\nLe numéro de téléphone ne doit pas être vide.");
		} else if (courant.getTel().length() != 10) {
			message.append("\nLe numéro de téléphone doit contenir exactement 10 chiffres.");
		} else if (!courant.getTel().matches("[1234567890]{10}")) {
			message.append("\nLe numéro de téléphone est incorrect.");
			message.append("\nVeuillez entrer que des chiffres et sans espace.");
		}
		if (courant.getMail() == null || courant.getMail().isEmpty()) {
			message.append("\nL'adresse e-mail ne doit pas être vide.");
		} else if (courant.getMail().length() > 50) {
			message.append("\nL'adresse e-mail est trop longue : 50 maxi.");
		}else if (courant.getMail().indexOf("@") >= courant.getMail().lastIndexOf(".")) {
			message.append("\nL'adresse e-mail rentrer n'est pas reconnue comme adresse mail");
		}
		
		if (courant.getAdresse() == null || courant.getAdresse().isEmpty()) {
			message.append("\nL'adresse ne doit pas être vide.");
		} else if (courant.getAdresse().length() > 50) {
			message.append("\nL'adresse est trop longue : 50 maxi.");
		}
		if (message.length() > 0) {
			throw new ExceptionValidation(message.toString().substring(1));
		}

		// Effectue la mise à jour

		if (courant.getId() == null) {
			// Insertion
			courant.setId(daoPersonne.inserer(courant));
		} else {
			// modficiation
			daoPersonne.modifier(courant);
		}
	}

	public void sauvegarderImageAutorisations() {
		if (autoMedicale.getValue() == null) {
			getFichierSchemaCourantMedicale().delete();
		} else {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(autoMedicale.getValue(), null), "JPG",
						getFichierSchemaCourantMedicale());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (autoParentale.getValue() == null) {
			getFichierSchemaCourantParentale().delete();
		} else {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(autoParentale.getValue(), null), "JPG",
						getFichierSchemaCourantParentale());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	// Méthodes auxiliaires
	public File getFichierSchemaCourantMedicale() {
		String nomFichier = String.format("%06d.jpg", courant.getId());
		File dossierSchemas = modelConfig.getDossierSchemas();
		return new File(dossierSchemas, nomFichier);
	}

	public File getFichierSchemaCourantParentale() {
		String nomFichier = String.format("%06d.jpg", courant.getId());
		File dossierSchemas = modelConfigParentale.getDossierSchemas();
		return new File(dossierSchemas, nomFichier);
	}
}

package projet.view.benevole;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jfox.commun.exception.ExceptionValidation;
import projet.commun.IMapper;
import projet.dao.DaoBenevole;
import projet.dao.DaoPermisDeConduire;
import projet.dao.DaoPersonne;
import projet.data.Benevole;
import projet.data.Utilisateur;
import projet.view.systeme.ModelConfigPermis;

public class ModelBenevole {

	// Données observables

	private final ObservableList<Benevole> liste = FXCollections.observableArrayList();

	private final Benevole courant = (Benevole) Utilisateur.getCourant().getUtilisateur();
	private final Property<Image> permis = new SimpleObjectProperty<>();
	// Autres champs
	@Inject
	private IMapper mapper;
	@Inject
	private DaoBenevole daoBenevole;
	@Inject
	private DaoPersonne daoPersonne;
	@Inject
	private DaoPermisDeConduire daoPermis;
	@Inject
	private ModelConfigPermis modelConfigPermis;
	// Getters

	public Benevole getCourant() {
		return courant;
	}

	public Property<Image> permisProperty() {
		return permis;
	}

	// Actualisations

	// Actions

	public void preparerAjouter() {
		mapper.update(courant, new Benevole());
		permis.setValue(null);
	}

	public void preparerModifier(Benevole item) {
		mapper.update(courant, daoBenevole.retrouver(item.getId()));
	}

	public void preparerModifierImagePermis(Benevole item) {
		File fichier = getFichierSchemaCourantPermis();
		if (fichier.exists()) {
			permis.setValue(new Image(fichier.toURI().toString()));
		} else {
			permis.setValue(null);
		}

	}

	public void sauvegarder() {

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
		}else if (courant.getMail().indexOf("@") > courant.getMail().lastIndexOf(".")) {
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

	public void sauvegarderImagePermis() {
		if (permis.getValue() == null) {
			getFichierSchemaCourantPermis().delete();
		} else {
			try {
				ImageIO.write(SwingFXUtils.fromFXImage(permis.getValue(), null), "JPG",
						getFichierSchemaCourantPermis());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void reinitialisationPermis()
	{
		daoPermis.supprimer(courant.getPermis().getId());
		courant.setPermis(null);
	}
	
	// Méthodes auxiliaires
	public File getFichierSchemaCourantPermis() {
		String nomFichier = String.format("%06d.jpg", courant.getId());
		File dossierSchemas = modelConfigPermis.getDossierSchemas();
		return new File(dossierSchemas, nomFichier);
	}

}

package projet.view.participant;
import javax.inject.Inject;

import jfox.commun.exception.ExceptionValidation;
import projet.commun.IMapper;
import projet.dao.DaoParticipant;
import projet.dao.DaoPersonne;
import projet.data.Participant;
import projet.data.Utilisateur;

public class ModelParticipant {

	// Données observables

	private final Participant courant = (Participant) Utilisateur.getCourant().getUtilisateur();

	// Autres champs
	@Inject
	private IMapper mapper;
	@Inject
	private DaoParticipant daoParticipant;
	@Inject
	private DaoPersonne daoPersonne;

	// Getters

	public Participant getCourant() {
		return courant;
	}

	// Actualisations

	/*
	 * public void actualiserListe() { liste.setAll( daoCompte.listerTout() ); }
	 */

	// Actions

	public void preparerAjouter() {
		mapper.update(courant, new Participant());
	}

	public void preparerModifier(Participant item) {
		mapper.update(courant, daoParticipant.retrouver(item.getId()));
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

	public void supprimer(Participant item) {
		daoPersonne.supprimer(item.getId());
	}

}

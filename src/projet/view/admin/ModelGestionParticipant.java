package projet.view.admin;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import projet.dao.DaoEquipe;
import projet.data.Equipe;
import projet.data.Participant;


public class ModelGestionParticipant  {
	
	
	// Données observables 
	
	private final ObservableList<Participant> participantList = FXCollections.observableArrayList(); 
	
	private Equipe		equipeCourant = new Equipe();
	private Participant	participantCourant = new Participant();
	
	private final ObservableList<Equipe> equipeList = FXCollections.observableArrayList();
	
	private final Property<Image>	schema = new SimpleObjectProperty<>();
	

	
	// Autres champs
    @Inject
	private DaoEquipe	daoEquipe;
    

    
	// Initialisations
	
	@PostConstruct
	public void init() 
	{
		
	}
	
	
	// Getters 
	
	public ObservableList<Participant> getParticipantList() {
		return participantList;
	}
	
	public void setEquipeCourant(Equipe equipeCourant) {
		this.equipeCourant = equipeCourant;
	}


	public void setParticipantCourant(Participant participantCourant) {
		this.participantCourant = participantCourant;
	}


	public Equipe getEquipeCourant() {
		return equipeCourant;
	}
	

	public Participant getParticipantCourant() {
		return participantCourant;
	}


	public ObservableList<Equipe> getEquipeList() 
	{
		return equipeList;
	}


	public Property<Image> schemaProperty() {
		return schema;
	}
	
	
	// Actualisations
	
	public void actualiserListeParticipant() 
	{
		participantList.clear();
		participantList.add(equipeCourant.getIdEquipier());
		participantList.add(equipeCourant.getIdCapitaine());
 	}
	
	public void actualiserListePersonnes() 
	{
		equipeList.clear();
		equipeList.addAll(daoEquipe.listerTout());
 	}

}

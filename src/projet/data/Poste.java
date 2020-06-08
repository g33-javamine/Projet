package projet.data;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Poste {
	
	
	// Champs
	
	private final StringProperty		nomPoste			= new SimpleStringProperty();
	private final StringProperty		typeBenevole			= new SimpleStringProperty("NN");
	private final SimpleObjectProperty<Integer> nbrBenevole = new SimpleObjectProperty<>(0);
	private final SimpleObjectProperty<Timestamp> debutIntervention =  new SimpleObjectProperty<>(Timestamp.from(Instant.EPOCH)) ;
	private final SimpleObjectProperty<Timestamp> finIntervention = new SimpleObjectProperty<>(Timestamp.from(Instant.now()));
	private final ObservableList<Benevole> benevoles = FXCollections.observableArrayList();
	
	
	
	// Getters & setters
	public final StringProperty nomPosteProperty() {
		return this.nomPoste;
	}
	
	public ObservableList<Benevole> getBenevoles() {
		return benevoles;
	}
	

	public void setBenevoles(Collection<Benevole> listBenevoles) {
		benevoles.clear();
		benevoles.addAll(listBenevoles);
	}
	
	public final String getNomPoste() {
		return this.nomPosteProperty().get();
	}
	
	public final void setNomPoste(final String nomPoste) {
		this.nomPosteProperty().set(nomPoste);
	}
	
	public final StringProperty typeBenevoleProperty() {
		return this.typeBenevole;
	}
	
	public final String getTypeBenevole() {
		return this.typeBenevoleProperty().get();
	}
	
	public final void setTypeBenevole(final String typeBenevole) {
		this.typeBenevoleProperty().set(typeBenevole);
	}
	
	public final SimpleObjectProperty<Integer> nbrBenevoleProperty() {
		return this.nbrBenevole;
	}
	
	public final Integer getNbrBenevole() {
		return this.nbrBenevoleProperty().get();
	}
	
	public final void setNbrBenevole(final Integer nbrBenevole) {
		this.nbrBenevoleProperty().set(nbrBenevole);
	}

	public final SimpleObjectProperty<Timestamp> debutInterventionProperty() {
		return this.debutIntervention;
	}
	

	public final Timestamp getDebutIntervention() {
		return this.debutInterventionProperty().get();
	}
	

	public final void setDebutIntervention(final Timestamp debutIntervention) {
		this.debutInterventionProperty().set(debutIntervention);
	}
	

	public final SimpleObjectProperty<Timestamp> finInterventionProperty() {
		return this.finIntervention;
	}
	

	public final Timestamp getFinIntervention() {
		return this.finInterventionProperty().get();
	}
	

	public final void setFinIntervention(final Timestamp finIntervention) {
		this.finInterventionProperty().set(finIntervention);
	}

	
	


	
	

	
	
	
}

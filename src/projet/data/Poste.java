package projet.data;

import java.util.Objects;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Poste {
	
	
	// Champs
	
	private final StringProperty		nomPoste			= new SimpleStringProperty();
	private final StringProperty		typeBenevole			= new SimpleStringProperty();
	private final SimpleObjectProperty<Integer> nbrBenevole = new SimpleObjectProperty<>();
	//private final      ...      debutIntervention =  ... ;
	//private final      ...     finIntervention =  ... ;
	
	
	
	
	// Getters & setters
	public final StringProperty nomPosteProperty() {
		return this.nomPoste;
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


	
	

	
	
	
}

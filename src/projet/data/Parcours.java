package projet.data;

import java.sql.Timestamp;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Parcours {
	private final Property<Integer> id   = new SimpleObjectProperty<>();
	private final SimpleObjectProperty<Timestamp> finIntervention = new SimpleObjectProperty<>();
	private final ObservableList<Balise> balises = FXCollections.emptyObservableList();
	
	
	Parcours(){
		
	}

	// Getters & Setters
	public final Property<Integer> idProperty() {
		return this.id;
	}
	

	public final Integer getId() {
		return this.idProperty().getValue();
	}
	

	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
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

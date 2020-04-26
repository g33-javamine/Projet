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


public class Benevole {
	
	
	// Champs
	
	private final Property<Integer>		id				= new SimpleObjectProperty<>();
	private final ObservableList<Personne> idPersonne = FXCollections.observableArrayList();

	
	// Getters & setters
	
	public final Property<Integer> idProperty() {
		return this.id;
	}
	

	public final Integer getId() {
		return this.idProperty().getValue();
	}
	

	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}
	
	public ObservableList<Personne> getPersonne() {
		return idPersonne;
	}
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(id.getValue());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Benevole other = (Benevole) obj;
		return Objects.equals(id.getValue(), other.id.getValue());
	}

	
	

	
	
	
}

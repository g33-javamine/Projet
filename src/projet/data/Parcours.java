package projet.data;

import java.sql.Timestamp;
import java.util.Collection;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Parcours {
	private final Property<Integer> id   = new SimpleObjectProperty<>();
	private final SimpleObjectProperty<Timestamp> dateDepart = new SimpleObjectProperty<>();
	private final ObservableList<Balise> balises = FXCollections.observableArrayList();
	
	
	public Parcours(){
		
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

	public final SimpleObjectProperty<Timestamp> dateDepartProperty() {
		return this.dateDepart;
	}
	

	public final Timestamp getDateDepart() {
		return this.dateDepartProperty().get();
	}
	

	public final void setDateDepart(final Timestamp finIntervention) {
		this.dateDepartProperty().set(finIntervention);
	}

	public ObservableList<Balise> getBalises() {
		return balises;
	}
	
	public void setBalises(Collection<Balise> listBalises) {
		balises.clear();
		balises.addAll(listBalises);
	}
	
	public void addBalise(Balise balise)
	{
		balises.add(balise);
	}

}

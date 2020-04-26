package projet.data;

import java.time.LocalDate;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Participant {
	
	
	// Champs
	
	private final Property<Integer>		id			= new SimpleObjectProperty<>();
	private final Property<Boolean>		autoMedicale	= new SimpleObjectProperty<>( false );
	private final Property<Boolean>		autoParentale	= new SimpleObjectProperty<>( false );
	private final ObservableList<Equipe> idEquipe = FXCollections.observableArrayList();
	private final ObservableList<Club> idClub = FXCollections.observableArrayList();

	
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
	


	public final Property<Boolean> autoMedicaleProperty() {
		return this.autoMedicale;
	}
	


	public final Boolean getAutoMedicale() {
		return this.autoMedicaleProperty().getValue();
	}
	


	public final void setAutoMedicale(final Boolean autoMedicale) {
		this.autoMedicaleProperty().setValue(autoMedicale);
	}
	


	public final Property<Boolean> autoParentaleProperty() {
		return this.autoParentale;
	}
	


	public final Boolean getAutoParentale() {
		return this.autoParentaleProperty().getValue();
	}
	


	public final void setAutoParentale(final Boolean autoParentale) {
		this.autoParentaleProperty().setValue(autoParentale);
	}
	
	public ObservableList<Equipe> getEquipe() {
		return idEquipe;
	}
	public ObservableList<Club> getClub() {
		return idClub;
	}
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash( id.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		return Objects.equals( id.getValue(), other.id.getValue() );
	}


	

}

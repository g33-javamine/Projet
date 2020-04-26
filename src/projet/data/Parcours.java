package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Parcours {
	private final Property<Integer> id   = new SimpleObjectProperty<>();
	//private final Property<Double> longitude   = new SimpleObjectProperty<>();
	
	
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

//	public final Property<Double> latitudeProperty() {
//		return this.latitude;
//	}
//
//	public final Double getLatitude() {
//		return this.latitudeProperty().getValue();
//	}
//
//	public final void setLatitude(final Double latitude) {
//		this.latitudeProperty().setValue(latitude);
//	}
	
	// hashCode() & equals()

		@Override
		public int hashCode() {
			return Objects.hash(id.getValue() );
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Parcours other = (Parcours) obj;
			return Objects.equals(id.getValue(), other.id.getValue() );
		}



}

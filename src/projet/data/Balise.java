package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

public class Balise {
	
	private final Property<Integer> id   = new SimpleObjectProperty<>();
	private final Property<Double> longitude   = new SimpleObjectProperty<>();
	private final Property<Double> latitude = new SimpleObjectProperty<>();
	
	public Balise(){
		
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
	

	public final Property<Double> longitudeProperty() {
		return this.longitude;
	}
	

	public final Double getLongitude() {
		return this.longitudeProperty().getValue();
	}
	

	public final void setLongitude(final Double longitude) {
		this.longitudeProperty().setValue(longitude);
	}
	

	public final Property<Double> latitudeProperty() {
		return this.latitude;
	}
	

	public final Double getLatitude() {
		return this.latitudeProperty().getValue();
	}
	

	public final void setLatitude(final Double latitude) {
		this.latitudeProperty().setValue(latitude);
	}
	
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
			Balise other = (Balise) obj;
			return Objects.equals(id.getValue(), other.id.getValue() );
		}



}

package projet.data;

import java.sql.Date;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class PermisDeConduire {
	
	
	// Champs
	
	private final Property<Integer>	id			= new SimpleObjectProperty<>();
	private final Property<Date>	dateDeliv	= new SimpleObjectProperty<>();
	private final StringProperty	prefectureDeliv	= new SimpleStringProperty();
	private final StringProperty    numero = new SimpleStringProperty();
	
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
	

	public final StringProperty prefectureDelivProperty() {
		return this.prefectureDeliv;
	}
	



	public final String getPrefectureDeliv() {
		return this.prefectureDelivProperty().get();
	}
	



	public final void setPrefectureDeliv(final String prefectureDeliv) {
		this.prefectureDelivProperty().set(prefectureDeliv);
	}
	



	public final StringProperty numeroProperty() {
		return this.numero;
	}
	



	public final String getNumero() {
		return this.numeroProperty().get();
	}
	



	public final void setNumero(final String numero) {
		this.numeroProperty().set(numero);
	}
	
	public final Property<Date> dateDelivProperty() {
		return this.dateDeliv;
	}
	



	public final Date getDateDeliv() {
		return this.dateDelivProperty().getValue();
	}
	



	public final void setDateDeliv(final Date dateDeliv) {
		this.dateDelivProperty().setValue(dateDeliv);
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
		PermisDeConduire other = (PermisDeConduire) obj;
		return Objects.equals( id.getValue(), other.id.getValue() );
	}
	



	
	


	

}

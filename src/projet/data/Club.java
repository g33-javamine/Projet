package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Club  {
	

	// Données observables
	
	private final Property<Integer>	id		= new SimpleObjectProperty<>();
	private final StringProperty	nom	= new SimpleStringProperty();
	
	
	// Constructeurs
	
	public Club() {
	}

	public Club( final int id, final String nom ) {
		setId(id);
		setNom(nom);
	}
	
	
	// Getters et Setters
	public final Property<Integer> idProperty() {
		return this.id;
	}
	

	public final Integer getId() {
		return this.idProperty().getValue();
	}
	

	public final void setId(final Integer id) {
		this.idProperty().setValue(id);
	}
	

	public final StringProperty nomProperty() {
		return this.nom;
	}
	

	public final String getNom() {
		return this.nomProperty().get();
	}
	

	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	
	// toString()
	
	@Override
	public String toString() {
		return getNom();
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
		Club other = (Club) obj;
		return Objects.equals(id.getValue(), other.id.getValue() );
	}

	
	
	
}


package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Equipe {
	private final Property<Integer> id   = new SimpleObjectProperty<>();
	private final Property<Boolean> paiement  = new SimpleObjectProperty<>( false );
	private final Property<Integer> nbrRepas = new SimpleObjectProperty<>();
	private final StringProperty	categorie	= new SimpleStringProperty();
	
//	private final ObservableList<Parcours> idParcours = FXCollections.observableArrayList();
//	private final ObservableList<Capitaine> idCapitaine = FXCollections.observableArrayList();
//	private final ObservableList<Equipier> idEquipier = FXCollections.observableArrayList();
	
	
	
	
	Equipe(){
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
	
	public final Property<Boolean> paiementProperty() {
		return this.paiement;
	}
	
	public final Boolean getPaiement() {
		return this.paiementProperty().getValue();
	}
	
	public final void setPaiement(final Boolean paiement) {
		this.paiementProperty().setValue(paiement);
	}
	
	public final Property<Integer> nbrRepasProperty() {
		return this.nbrRepas;
	}
	
	public final Integer getNbrRepas() {
		return this.nbrRepasProperty().getValue();
	}
	
	public final void setNbrRepas(final Integer nbrRepas) {
		this.nbrRepasProperty().setValue(nbrRepas);
	}
	
	public final StringProperty categorieProperty() {
		return this.categorie;
	}
	
	public final String getCategorie() {
		return this.categorieProperty().get();
	}
	
	public final void setCategorie(final String categorie) {
		this.categorieProperty().set(categorie);
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
			Equipe other = (Equipe) obj;
			return Objects.equals(id.getValue(), other.id.getValue() );
		}

}
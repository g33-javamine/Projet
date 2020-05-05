package projet.data;


import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Equipe {
	private final Property<Integer> id   = new SimpleObjectProperty<>();
	private final Property<Boolean> paiement  = new SimpleObjectProperty<>( false );
	private final Property<Integer> nbrRepas = new SimpleObjectProperty<>();
	private final StringProperty	categorie	= new SimpleStringProperty();
	private final Property<Parcours> idParcours = new SimpleObjectProperty<>();
	private final Property<Participant> idCapitaine = new SimpleObjectProperty<>();
	private final Property<Participant> idEquipier = new SimpleObjectProperty<>();
	
	
	
	
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


	public final Property<Parcours> idParcoursProperty() {
		return this.idParcours;
	}
	


	public final Parcours getIdParcours() {
		return this.idParcoursProperty().getValue();
	}
	


	public final void setIdParcours(final Parcours idParcours) {
		this.idParcoursProperty().setValue(idParcours);
	}
	


	public final Property<Participant> idCapitaineProperty() {
		return this.idCapitaine;
	}
	


	public final Participant getIdCapitaine() {
		return this.idCapitaineProperty().getValue();
	}
	


	public final void setIdCapitaine(final Participant idCapitaine) {
		this.idCapitaineProperty().setValue(idCapitaine);
	}
	


	public final Property<Participant> idEquipierProperty() {
		return this.idEquipier;
	}
	


	public final Participant getIdEquipier() {
		return this.idEquipierProperty().getValue();
	}
	


	public final void setIdEquipier(final Participant idEquipier) {
		this.idEquipierProperty().setValue(idEquipier);
	}
	


}
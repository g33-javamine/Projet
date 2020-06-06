package projet.data;



import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;


public class Participant extends Personne{
	
	
	// Champs
	
	protected final Property<Boolean>		autoMedicale	= new SimpleObjectProperty<>( false );
	protected final Property<Boolean>		autoParentale	= new SimpleObjectProperty<>( false );
	protected final Property<Equipe> 		idEquipe = new SimpleObjectProperty<>();
	protected final Property<Club> 			idClub = new SimpleObjectProperty<>();

	
	
	
	
	
	// Getters & setters


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

	public final Property<Equipe> idEquipeProperty() {
		return this.idEquipe;
	}
	



	public final Equipe getIdEquipe() {
		return this.idEquipeProperty().getValue();
	}
	



	public final void setIdEquipe(final Equipe idEquipe) {
		this.idEquipeProperty().setValue(idEquipe);
	}
	



	public final Property<Club> idClubProperty() {
		return this.idClub;
	}
	



	public final Club getIdClub() {
		return this.idClubProperty().getValue();
	}
	



	public final void setIdClub(final Club idClub) {
		this.idClubProperty().setValue(idClub);
	}
	
	public final Property<Participant> coequipierProperty()
	{
		if(idEquipe.getValue() != null )
		{
			Equipe equipe = idEquipe.getValue();
			if(this == equipe.getIdCapitaine())
			{
				return equipe.idEquipierProperty();
			}
			else
			{
				return equipe.idCapitaineProperty();
			}
		}
		return null;
	}
	
	public final Participant getCoequipier()
	{
		Property<Participant> coequipier = coequipierProperty();
		if(coequipier == null)
		{
			return null;
		}
		return coequipier.getValue();
	}

	

}

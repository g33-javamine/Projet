package projet.data;

import java.util.Collection;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Benevole extends Personne
{
	// Champs
	
		protected final Property<PermisDeConduire> permis = new SimpleObjectProperty<>();
		protected final Property<Poste> posteAssignee = new SimpleObjectProperty<>();
		protected final ObservableList<Balise> baliseAssignee = FXCollections.observableArrayList();
		
		
		
		public final Property<PermisDeConduire> permisProperty() {
			return this.permis;
		}
		
		public final PermisDeConduire getPermis() {
			return this.permisProperty().getValue();
		}
		
		public final void setPermis(final PermisDeConduire permis) {
			this.permisProperty().setValue(permis);
		}
		
		public final Property<Poste> posteAssigneeProperty() {
			return this.posteAssignee;
		}
		
		public final Poste getPosteAssignee() {
			return this.posteAssigneeProperty().getValue();
		}
		
		public final void setPosteAssignee(final Poste posteAssignee) {
			this.posteAssigneeProperty().setValue(posteAssignee);
		}
		

		public ObservableList<Balise> getBalises() {
			return baliseAssignee;
		}
		
		public void setBalises(Collection<Balise> listBalises) {
			baliseAssignee.clear();
			baliseAssignee.addAll(listBalises);
		}
		
		public void addBalise(Balise balise)
		{
			baliseAssignee.add(balise);
		}
}

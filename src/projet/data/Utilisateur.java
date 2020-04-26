package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Utilisateur  {

	
	// Données observables
	
	private final Property<Integer>	id			= new SimpleObjectProperty<>();
	private final StringProperty	login		= new SimpleStringProperty();
	private final StringProperty	password	= new SimpleStringProperty();

	// Constructeurs
	
	public Utilisateur() {
	}

	public Utilisateur( int id, String login, String password) {
		setId(id);
		setLogin(login);
		setPassword(password);
		
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

	public final StringProperty loginProperty() {
		return this.login;
	}

	public final String getLogin() {
		return this.loginProperty().getValue();
	}

	public final void setLogin(final String login) {
		this.loginProperty().setValue(login);
	}

	public final StringProperty passwordProperty() {
		return this.password;
	}

	public final String getPassword() {
		return this.passwordProperty().getValue();
	}

	public final void setPassword(final String password) {
		this.passwordProperty().setValue(password);
	}

	
	

	
	// toString()
	
	@Override
	public String toString() {
		return getLogin();
	}
	
	
	// hashCode() & equals()

	@Override
	public int hashCode() {
		return Objects.hash(login.getValue() );
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(login.getValue(), other.login.getValue() );
	}
	
}

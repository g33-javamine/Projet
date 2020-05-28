package projet.data;

import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import projet.commun.Roles;


public class Utilisateur  {

	
	// Données observables
	
	private final Property<Personne>	utilisateur	= new SimpleObjectProperty<>();
	private final StringProperty		login		= new SimpleStringProperty();
	private final StringProperty		password	= new SimpleStringProperty();
	private static Property<Utilisateur>courant		= new SimpleObjectProperty<>();
	// Constructeurs
	
	public Utilisateur() {
	}

	public Utilisateur( Personne utilisateur, String login, String password) {
		setLogin(login);
		setPassword(password);
		setUtilisateur(utilisateur);
		
	}
	
	
	// Getters et Setters
	
	
	public final Property<Personne> utilisateurProperty() {
		return this.utilisateur;
	}
	
	public final Personne getUtilisateur() {
		return this.utilisateurProperty().getValue();
	}
	

	public final void setUtilisateur(Personne utilisateur) {
		this.utilisateurProperty().setValue(utilisateur);
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
	
	public String getRole()
	{
		if(utilisateur.getValue() instanceof Administrateurs)
			return Roles.ADMINISTRATEUR;
		else if(utilisateur.getValue() instanceof Benevole)
			return Roles.BENEVOLE;
		else//(utilisateur.getValue() instanceof Participant)
			return Roles.PARTICIPANT;
	}

	public static final Property<Utilisateur> courantProperty() {
		return courant;
	}
	

	public static final Utilisateur getCourant() {
		return courantProperty().getValue();
	}
	

	public static final void setCourant(final Utilisateur courant) {
		courantProperty().setValue(courant);
	}
	

}

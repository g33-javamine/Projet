package projet.data;

import java.sql.Date;
import java.util.Objects;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Personne {


	// Données observables
	
	protected final Property<Integer>	id				= new SimpleObjectProperty<>();
	protected final StringProperty		nom	 			= new SimpleStringProperty();
	protected final StringProperty		prenom			= new SimpleStringProperty();
	protected final Property<Date>	dateNaissance	= new SimpleObjectProperty<>();
	protected final StringProperty		tel				= new SimpleStringProperty();
	protected final StringProperty		mail	 		= new SimpleStringProperty();
	protected final StringProperty		adresse			= new SimpleStringProperty();
	
	
	// Constructeurs
	
	public Personne() 
	{
		
	}
	
	public Personne( int id, String nom, String prenom, Date dateNaissance, String tel, String mail, String adresse ) {
		setId(id);
		setNom(nom);
		setPrenom(prenom);
		setDateNaissance(dateNaissance);
		setTel(tel)	;	
		setMail(mail);	 		
		setAdresse(adresse);		
		
	}
	
	
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
	

	public final StringProperty nomProperty() {
		return this.nom;
	}
	

	public final String getNom() {
		return this.nomProperty().get();
	}
	

	public final void setNom(final String nom) {
		this.nomProperty().set(nom);
	}
	

	public final StringProperty prenomProperty() {
		return this.prenom;
	}
	

	public final String getPrenom() {
		return this.prenomProperty().get();
	}
	

	public final void setPrenom(final String prenom) {
		this.prenomProperty().set(prenom);
	}
	

	public final Property<Date> dateNaissanceProperty() {
		return this.dateNaissance;
	}
	

	public final Date getDateNaissance() {
		return this.dateNaissanceProperty().getValue();
	}
	

	public final void setDateNaissance(final Date dateNaissance) {
		this.dateNaissanceProperty().setValue(dateNaissance);
	}
	

	public final StringProperty telProperty() {
		return this.tel;
	}
	

	public final String getTel() {
		return this.telProperty().get();
	}
	

	public final void setTel(final String tel) {
		this.telProperty().set(tel);
	}
	

	public final StringProperty mailProperty() {
		return this.mail;
	}
	

	public final String getMail() {
		return this.mailProperty().get();
	}
	

	public final void setMail(final String mail) {
		this.mailProperty().set(mail);
	}
	

	public final StringProperty adresseProperty() {
		return this.adresse;
	}
	

	public final String getAdresse() {
		return this.adresseProperty().get();
	}
	

	public final void setAdresse(final String adresse) {
		this.adresseProperty().set(adresse);
	}
	
	
	// toString()
	
	@Override
	public String toString() {
		return getNom() + " " + getPrenom();
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
		Personne other = (Personne) obj;
		return Objects.equals(id.getValue(), other.id.getValue() );
	}

	
	
}

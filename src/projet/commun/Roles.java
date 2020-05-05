package projet.commun;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class Roles {
	
	// Champs statiques
	
	public static final String ADMINISTRATEUR	= "ADMINISTRATEUR";
	public static final String PARTICIPANT		= "PARTICIPANT";
	public static final String BENEVOLE		= "BENEVOLE";
	
	
	private static final List<String>	roles = Collections.unmodifiableList( Arrays.asList( 
			ADMINISTRATEUR,			
			PARTICIPANT,
			BENEVOLE
	) );

	private static final String[]	 	libellés = new String[] {
			"Administrateur",
			"Participant",
			"Benevole"
	};
	
	
	// Constructeur privé qui empêche l'instanciation de la classe
	private Roles() {
	}
	
	
	// Actions

	public static List<String> getRoles() {
		return roles;
	}
	
	public static String getLibellé( String role ) {
		int index = roles.indexOf( role );
		if ( index == -1 ) {
			throw new IllegalArgumentException();
		} 
		return libellés[index];
	}

}

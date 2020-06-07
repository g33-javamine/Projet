package projet.view.systeme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import jfox.commun.exception.ExceptionAnomaly;


public class ModelConfigPermis {
	
	
	// Champs
	
	private File		dossierSchemas;
	
	
	// Initialisations
	
	@PostConstruct
	public void init() {
		
		
		// Lecture du fichier de configuration
		Properties props = new Properties();
		String chemin = "META-INF/configPermis.properties";
		InputStream in =  ClassLoader.getSystemResourceAsStream( chemin );
		if ( in == null ) {
			throw new ExceptionAnomaly( "Impossible de charger le fichier de configuration :\n" + chemin );
		}
		try {
			props.load( in );
			in.close();
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
		
		
		String valeur = props.getProperty( "dossier.schemas" );
		if ( valeur == null ) {
			throw new ExceptionAnomaly( 
				"Paramètre absent dans le fichier de configuration :\n"
				+ "dossier.schemas" );
		}
		dossierSchemas = new File( valeur );
		if ( ! dossierSchemas.exists() ) {
			throw new ExceptionAnomaly( 
					"Le dossier des schémas n'existe pas :\n"
					+ dossierSchemas.toString() );
		}
		
	}
	
	
	// Getters
	
	public File getDossierSchemas() {
		return dossierSchemas;
	}

}

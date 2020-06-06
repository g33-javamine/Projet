package projet.util;


import java.sql.Timestamp;

import javafx.util.StringConverter;


public class ConverterStringTimestamp extends StringConverter<Timestamp> {

	
	// Champs

	private boolean hasParseError = false;
	
	
	// Constructeur
	
	// getters
	
	public boolean hasParseError(){
		return hasParseError;
	}	
	
	
	// Actons
	
	@Override
	public String toString(Timestamp object) {
		if ( object == null ) {
			return null;
		}
		String moment = object.toString();
		return moment.substring(0, moment.indexOf('.'));
	}

	@Override
	public Timestamp fromString(String string) {
		if ( string == null ) {
			return null;
		}
		try {
			hasParseError = false;
			string.replace('/', '-');
			return Timestamp.valueOf( string ) ;
		} catch (Exception e) {
			hasParseError = true;
			return null;
		}
	}

}


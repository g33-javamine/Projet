package projet.util;


import java.sql.Date;

import javafx.util.StringConverter;


public class ConverterStringDate extends StringConverter<Date> {

	
	// Champs

	private boolean hasParseError = false;

	
	
	// Constructeur


	
	// getters
	
	public boolean hasParseError(){
		return hasParseError;
	}	
	
	
	// Actons
	
	@Override
	public String toString(Date object) {
		if ( object == null ) {
			return null;
		}
		return object.toString();
	}

	@Override
	public Date fromString(String string) {
		if ( string == null ) {
			return null;
		}
		try {
			hasParseError = false;
			return Date.valueOf( string ) ;
		} catch (Exception e) {
			hasParseError = true;
			return null;
		}
	}

}


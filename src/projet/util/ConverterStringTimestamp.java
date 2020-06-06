package projet.util;


import java.sql.Timestamp;

import javafx.util.StringConverter;


public class ConverterStringTimestamp extends StringConverter<Timestamp> {

	
	// Champs

	private boolean hasParseError = false;
	private boolean full = true;
	
	// Constructeur
	
	public ConverterStringTimestamp()
	{
		
	}
	
	public ConverterStringTimestamp(boolean full)
	{
		this.full = full;
	}
	
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
		if(full)
			return moment.substring(0, moment.indexOf('.'));
		else
			return moment.substring(0, moment.indexOf(' '));
	}

	@Override
	public Timestamp fromString(String string) {
		if ( string == null ) {
			return null;
		}
		try {
			hasParseError = false;
			if(full)
				return Timestamp.valueOf( string ) ;
			else
				return Timestamp.valueOf( string + " 00:00:00" );
		} catch (Exception e) {
			hasParseError = true;
			return null;
		}
	}

}


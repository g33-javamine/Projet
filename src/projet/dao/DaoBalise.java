package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.Balise;


public class DaoBalise {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public int inserer( Balise balise ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO Balise ( Id,Longitude,Latitude ) VALUES( ?,?,?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, balise.getId());
			stmt.setObject( 2, balise.getLongitude());
			stmt.setObject( 3, balise.getLatitude());
			stmt.executeUpdate();


			return balise.getId();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public void modifier( Balise balise ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE Balise SET Longitude = ?, Latitude = ? WHERE Id =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, balise.getLongitude() );
			stmt.setObject( 2, balise.getLatitude() );
			stmt.setObject( 3, balise.getId() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( int idBalise ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM Balise WHERE Id = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idBalise );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM est_composee WHERE Id_Balises = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idBalise );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM est_assignee WHERE Id_Balise = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idBalise );
			stmt.executeUpdate();
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Balise retrouver( int idBalise ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Balise WHERE Id = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idBalise);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireBalise( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Balise> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Balise ORDER BY Id";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Balise> balises = new LinkedList<>();
			while (rs.next()) {
				balises.add( construireBalise( rs ) );
			}
			return balises;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Balise> listerBalisesParcours(int idParcours) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Balise INNER JOIN est_composee ON est_composee.Id_Balises = Balise.Id WHERE est_composee.Id_Parcours = ? ORDER BY Id";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idParcours);
			rs = stmt.executeQuery();

			List<Balise> balises = new LinkedList<>();
			while (rs.next()) {
				balises.add( construireBalise( rs ) );
			}
			return balises;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	private Balise construireBalise( ResultSet rs ) throws SQLException {
		Balise balise = new Balise();
		balise.setId( rs.getObject( "Id", Integer.class ) );
		balise.setLatitude( rs.getObject( "Latitude", Double.class ) );
		balise.setLatitude( rs.getObject( "Longitude", Double.class ) );
		return balise;
	}

}

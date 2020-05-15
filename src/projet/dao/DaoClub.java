package projet.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.Club;


public class DaoClub {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public int inserer( Club club )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO Club ( Nom ) VALUES ( ? ) ";
			stmt = cn.prepareCall( sql ); 
			stmt.setObject( 1, club.getNom() );
			stmt.executeUpdate();
			
			rs = stmt.getGeneratedKeys();
			rs.next();
			// Récupère l'identifiant généré par le SGBD
			club.setId( rs.getObject( 1, Integer.class) );
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
		
		// Retourne l'identifiant
		return club.getId();
	}
	

	public void modifier( Club club )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "UPDATE Club SET Nom = ? WHERE Id =  ?";
			stmt = cn.prepareCall( sql );
			stmt.setObject( 1, club.getNom() );
			stmt.setObject( 2, club.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

	public void supprimer( int idClub )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le compte
			sql = "DELETE FROM Club WHERE Id = ?  ";
			stmt = cn.prepareCall( sql );
			stmt.setObject( 1, idClub );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

	public Club retrouver( int idClub )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Club WHERE Id = ? ";
            stmt = cn.prepareCall( sql );
            stmt.setObject( 1, idClub );
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireClub( rs );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	

	public List<Club> listerTout()   {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Club ORDER BY Id";
			stmt = cn.prepareCall( sql );
			rs = stmt.executeQuery();

			List<Club> clubs = new ArrayList<>();
			while ( rs.next() ) {
				clubs.add( construireClub(rs) );
			}
			return clubs;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	// Méthodes auxiliaires
	
	private Club construireClub( ResultSet rs ) throws SQLException {
		Club club = new Club();
		club.setId( rs.getObject( "Id", Integer.class ) );
		club.setNom( rs.getObject( "Nom", String.class ) );
		return club;
	}
	
}

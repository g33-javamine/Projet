package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.Administrateurs;


public class DaoAdministrateurs {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public void inserer(Administrateurs administrateurs)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le administrateurs
			sql = "INSERT INTO Administrateurs ( id) VALUES ( ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setInt(	1, administrateurs.getId() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			administrateurs.setId( rs.getObject( 1, Integer.class ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public void modifier(Administrateurs administrateurs)  
	{
		return;
	}

	
	public void supprimer(int idAdministrateurs)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le administrateurs
			sql = "DELETE FROM Administrateurs WHERE id = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idAdministrateurs );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Administrateurs retrouver(int idAdministrateurs)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Administrateurs WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idAdministrateurs);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireAdministrateurs(rs);
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	private Administrateurs construireAdministrateurs( ResultSet rs ) throws SQLException 
	{	
		Administrateurs administrateurs = new Administrateurs();
		administrateurs.setId(rs.getObject( "id", Integer.class ));
		return administrateurs;
	}
	
}

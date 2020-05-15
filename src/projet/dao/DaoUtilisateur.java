package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.Utilisateur;


public class DaoUtilisateur {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoPersonne	    daoPersonne;

	
	// Actions

	public void inserer( Utilisateur compte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;
		try {
			daoPersonne.inserer(compte.getUtilisateur());
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO compte ( login, password, id ) VALUES ( ?, ?, ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS ); 
			stmt.setObject( 1, compte.getLogin() );
			stmt.setObject( 2, compte.getPassword() );
			stmt.setObject( 3, compte.getUtilisateur().getId());
			stmt.executeUpdate();
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

	public void modifier( Utilisateur compte )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			daoPersonne.modifier(compte.getUtilisateur());
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "UPDATE compte SET password = ?, id = ? WHERE login = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, compte.getPassword() );
			stmt.setObject( 2, compte.getUtilisateur().getId() );
			stmt.setObject( 3, compte.getLogin() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

	public void supprimer( String login )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		// Supprime les rôles

		try {
			cn = dataSource.getConnection();

			// Supprime le compte
			sql = "DELETE FROM compte WHERE login = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, login );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

public Utilisateur retrouver( String login )  {
	
	Connection			cn		= null;
	PreparedStatement	stmt	= null;
	ResultSet 			rs 		= null;
	String				sql;

	try {
		cn = dataSource.getConnection();
		sql = "SELECT * FROM compte WHERE login = ?";
		stmt = cn.prepareStatement( sql );
        stmt.setObject( 1, login );
        rs = stmt.executeQuery();

        if ( rs.next() ) 
        {
        	return construireCompte( rs );
        } 
        else 
        {
        	return null;
        }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	

public List<Utilisateur> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM compte ORDER BY pseudo";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Utilisateur> comptes = new ArrayList<>();
			while ( rs.next() ) {
				comptes.add( construireCompte(rs) );
			}
			return comptes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public Utilisateur validerAuthentification( String pseudo, String motDePasse ) 
	{	
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM compte WHERE pseudo = ? AND motdepasse = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, pseudo );
			stmt.setObject( 2, motDePasse );
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireCompte( rs );			
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
	
	private Utilisateur construireCompte( ResultSet rs ) throws SQLException {
		Utilisateur compte = new Utilisateur();
		compte.setLogin( rs.getObject( "login", String.class ) );
		compte.setPassword( rs.getObject( "password", String.class ) );
		compte.setUtilisateur(daoPersonne.retrouver(rs.getObject("id",Integer.class)));
		return compte;
	}
	
}

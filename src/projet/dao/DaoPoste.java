package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.Poste;


public class DaoPoste {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public void inserer( Poste poste ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO Poste(nom_poste,Types_benevoles,nombre_benevole,debut_intervention,fin_intervention) VALUES (?,?,?,?,?);";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, poste.getNomPoste());
			stmt.setObject( 2, poste.getTypeBenevole());
			stmt.setObject( 3, poste.getNbrBenevole());
			stmt.setObject( 4, poste.getDebutIntervention());
			stmt.setObject( 5, poste.getFinIntervention());
			stmt.executeUpdate();


	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public void modifier( Poste poste ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE Poste SET nom_poste = ?,Types_benevoles = ?,nombre_benevole = ?,debut_intervention = ?,fin_intervention = ? WHERE nom_poste =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, poste.getNomPoste() );
			stmt.setObject( 2, poste.getTypeBenevole() );
			stmt.setObject( 3, poste.getNbrBenevole() );
			stmt.setObject( 4, poste.getDebutIntervention() );
			stmt.setObject( 5, poste.getFinIntervention() );
			stmt.setObject( 6, poste.getNomPoste() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( String nom_poste ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM Poste WHERE nom_poste = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, nom_poste );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM a_poste WHERE id_poste = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, nom_poste );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Poste retrouver( String nom_poste ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Poste WHERE nom_poste = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, nom_poste);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construirePoste( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Poste> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Poste ORDER BY Id";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Poste> postes = new LinkedList<>();
			while (rs.next()) {
				postes.add( construirePoste( rs ) );
			}
			return postes;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	private Poste construirePoste( ResultSet rs ) throws SQLException {
		Poste poste = new Poste();
		poste.setNomPoste(rs.getObject( "nom_poste", String.class ));
		poste.setTypeBenevole(rs.getObject( "Types_benevoles", String.class ));
		poste.setNbrBenevole(rs.getObject( "nombre_benevole", Integer.class ));
		poste.setDebutIntervention(rs.getObject( "debut_intervention", Timestamp.class ));
		poste.setFinIntervention(rs.getObject( "fin_intervention", Timestamp.class ));
		return poste;
	}

}

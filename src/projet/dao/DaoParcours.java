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
import projet.data.Balise;
import projet.data.Parcours;


public class DaoParcours {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoBalise       daoBalise;

	
	// Actions

	public int inserer( Parcours parcours ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO Parcours ( Date_depart ) VALUES( ?) ";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, parcours.getDateDepart());
			stmt.executeUpdate();
			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			parcours.setId( rs.getObject( 1, Integer.class) );
			stmt.close();
			
			for(Balise balise : parcours.getBalises())
			{
				sql = "INSERT INTO est_composee ( Id_Parcours,Id_Balises ) VALUES( ?,?) ";
				stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
				stmt.setObject( 1, parcours.getId());
				stmt.setObject( 2, balise.getId());
				stmt.executeUpdate();
				stmt.close();
			}
			
			
			return parcours.getId();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public void modifier( Parcours parcours ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE Parcours SET  Date_depart = ? WHERE Id =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, parcours.getDateDepart() );
			stmt.setObject( 2, parcours.getId() );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM est_composee WHERE Id_Parcours = ?";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, parcours.getId());
			stmt.executeUpdate();
			stmt.close();
			for(Balise balise : parcours.getBalises())
			{
				sql = "INSERT INTO est_composee ( Id_Parcours,Id_Balises ) VALUES( ?,?) ";
				stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
				stmt.setObject( 1, parcours.getId());
				stmt.setObject( 2, balise.getId());
				stmt.executeUpdate();
				stmt.close();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( int idParcours ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM Parcours WHERE Id = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idParcours );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM est_composee WHERE Id_Parcours = ?";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, idParcours);
			stmt.executeUpdate();
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Parcours retrouver( int idParcours ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Parcours WHERE Id = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idParcours);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireParcours( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Parcours> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Parcours ORDER BY Id";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Parcours> parcours = new LinkedList<>();
			while (rs.next()) {
				parcours.add( construireParcours( rs ) );
			}
			return parcours;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void generateBalises(Parcours parcours)
	{

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT Id_Balises FROM est_composee ORDER BY Id_Balises WHERE Id_Parcours = ?";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();
			parcours.getBalises().clear();
			while (rs.next()) 
			{
				parcours.addBalise( daoBalise.retrouver(rs.getObject("Id_Balises",Integer.class)));
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	// Méthodes auxiliaires
	
	private Parcours construireParcours( ResultSet rs ) throws SQLException {
		Parcours parcours = new Parcours();
		parcours.setId( rs.getObject( "Id", Integer.class ) );
		parcours.setDateDepart( rs.getObject( "Date_depart", Timestamp.class ) );
		parcours.setBalises(daoBalise.listerBalisesParcours(parcours.getId()));
		return parcours;
	}
	
	
}

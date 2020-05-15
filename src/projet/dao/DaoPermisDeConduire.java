package projet.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.PermisDeConduire;


public class DaoPermisDeConduire {

	
	// Champs

	@Inject
	private DataSource		dataSource;

	
	// Actions

	public void inserer( PermisDeConduire permis ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO Permis_de_Conduire(numero,date_de_delivrance,prefecture_de_delivrance,id) VALUES (?,?,?,?);";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, permis.getNumero());
			stmt.setObject( 2, permis.getDateDeliv());
			stmt.setObject( 3, permis.getPrefectureDeliv());
			stmt.setObject( 4, permis.getId());
			stmt.executeUpdate();

	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	public void supprimer( int idPermisDeConduire ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM Permis_de_Conduire WHERE id = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, idPermisDeConduire );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public PermisDeConduire retrouver( int idPermis ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Permis_de_Conduire WHERE id = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, idPermis);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construirePermisDeConduire( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<PermisDeConduire> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Permis_de_Conduire ORDER BY Id";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<PermisDeConduire> permiss = new LinkedList<>();
			while (rs.next()) {
				permiss.add( construirePermisDeConduire( rs ) );
			}
			return permiss;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	private PermisDeConduire construirePermisDeConduire( ResultSet rs ) throws SQLException {
		PermisDeConduire permis = new PermisDeConduire();
		permis.setId( rs.getObject( "id", Integer.class ) );
		permis.setDateDeliv(rs.getObject( "date_de_delivrance", Date.class ));
		permis.setNumero(rs.getObject( "numero", String.class ));
		permis.setPrefectureDeliv(rs.getObject( "prefecture_de_delivrance", String.class ));
		return permis;
	}

}

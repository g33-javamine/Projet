package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.Balise;
import projet.data.Benevole;
import projet.data.Poste;


public class DaoBenevole {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoPermisDeConduire	daoPermisDeConduire;
	@Inject
	private DaoPoste			daoPoste;
	@Inject
	private DaoBalise			daoBalise;
	
	// Actions

	public void inserer(Benevole benevole)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le benevole
			sql = "INSERT INTO Benevole ( id) VALUES ( ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setObject(	1, benevole.getId() );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM a_poste WHERE id = ?";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, benevole.getId());
			stmt.executeUpdate();
			stmt.close();
			
			sql = "INSERT INTO a_poste(id,nom_poste VALUES (?,?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, benevole.getId());
			stmt.setObject( 2, benevole.getPosteAssignee().getNomPoste());
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM est_assignee WHERE id = ?";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, benevole.getId());
			stmt.executeUpdate();
			stmt.close();
			for(Balise balise : benevole.getBalises())
			{
				sql = "INSERT INTO est_assignee ( id,Id_Balise ) VALUES( ?,?) ";
				stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
				stmt.setObject( 1, benevole.getId());
				stmt.setObject( 2, balise.getId());
				stmt.executeUpdate();
				stmt.close();
			}
			
			daoPermisDeConduire.inserer(benevole.getPermis());
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public void modifier(Benevole benevole)  
	{
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "DELETE FROM a_poste WHERE id = ?";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, benevole.getId());
			stmt.executeUpdate();
			stmt.close();
			
			sql = "INSERT INTO a_poste(id,nom_poste VALUES (?,?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, benevole.getId());
			stmt.setObject( 2, benevole.getPosteAssignee().getNomPoste());
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM est_assignee WHERE id = ?";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, benevole.getId());
			stmt.executeUpdate();
			stmt.close();
			for(Balise balise : benevole.getBalises())
			{
				sql = "INSERT INTO est_assignee ( id,Id_Balise ) VALUES( ?,?) ";
				stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
				stmt.setObject( 1, benevole.getId());
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

	
	public void supprimer(int idBenevole)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le benevole
			sql = "DELETE FROM benevole WHERE id = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idBenevole );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM a_poste WHERE id = ?";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, idBenevole);
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM est_assignee WHERE id = ?";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			stmt.setObject( 1, idBenevole);
			stmt.executeUpdate();
			stmt.close();
			
			daoPermisDeConduire.supprimer(idBenevole);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Benevole retrouver(int idBenevole)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM benevole WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idBenevole);
            rs = stmt.executeQuery();
            if ( rs.next() ) {
            	Benevole benevole = construireBenevole(rs);
            	stmt.close();
            	
            	sql = "SELECT id_poste FROM a_poste WHERE id = ?";
    			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
    			stmt.setObject( 1, benevole.getId());
    			rs = stmt.executeQuery();
    			if(rs.next())
    			{
    				benevole.setPosteAssignee(daoPoste.retrouver(rs.getObject("id_poste",String.class)));
    			}
    			stmt.close();
    			
    			sql = "SELECT Id_Balise FROM est_assignee WHERE id =?";
    			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
    			stmt.setObject( 1, benevole.getId());
    			rs = stmt.executeQuery();
    			
    			ArrayList<Balise> listBalises = new ArrayList<Balise>();
    			while(rs.next())
    			{
    				listBalises.add(daoBalise.retrouver(rs.getInt("Id_Balise")));
    			}
    			benevole.setBalises(listBalises);
    			stmt.close();
    			
                return benevole;
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public Benevole retrouver(int idBenevole,Poste poste)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM benevole WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idBenevole);
            rs = stmt.executeQuery();
            if ( rs.next() ) {
            	Benevole benevole = construireBenevole(rs);
            	stmt.close();
            	
    			benevole.setPosteAssignee(poste);
    			
    			sql = "SELECT Id_Balise FROM est_assignee WHERE id =?";
    			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
    			stmt.setObject( 1, benevole.getId());
    			rs = stmt.executeQuery();
    			
    			ArrayList<Balise> listBalises = new ArrayList<Balise>();
    			while(rs.next())
    			{
    				listBalises.add(daoBalise.retrouver(rs.getInt("Id_Balise")));
    			}
    			benevole.setBalises(listBalises);
    			stmt.close();
    			
                return benevole;
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
	
	private Benevole construireBenevole( ResultSet rs) throws SQLException 
	{
		Benevole benevole = new Benevole();
		benevole.setId(rs.getObject("id",Integer.class));
		benevole.setPermis(daoPermisDeConduire.retrouver(benevole.getId()));
		return benevole;
	}
	
}

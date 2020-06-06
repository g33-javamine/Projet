package projet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.Equipe;
import projet.data.Participant;


public class DaoParticipant {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoEquipe	daoEquipe;
	@Inject 
	private DaoClub 	daoClub;
	
	// Actions

	public void inserer(Participant participant)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le participant
			sql = "INSERT INTO Participant (id,Autorisation_medicale,Autorisation_parentale,id_Equipe,Id_Club) VALUES (?,?,?,?,?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setObject(	1, participant.getId() );
			stmt.setObject(	2, participant.getAutoMedicale() );
			stmt.setObject(	3, participant.getAutoParentale());
			stmt.setObject(	4, participant.getIdEquipe().getId());
			if(participant.getIdClub() == null)
			{
				stmt.setObject(	5, null);
			}
			else
			{
				stmt.setObject(	4, participant.getIdClub().getId());
			}
			stmt.executeUpdate();

	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public void modifier(Participant participant)  
	{
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE Participant SET Autorisation_medicale = ?,Autorisation_parentale = ?,id_Equipe = ?,Id_Club = ? WHERE id = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(	1, participant.getAutoMedicale() );
			stmt.setObject(	2, participant.getAutoParentale());
			stmt.setObject(	3, participant.getIdEquipe().getId());
			if(participant.getIdClub() == null)
			{
				stmt.setObject(	4, null);
			}
			else
			{
				stmt.setObject(	4, participant.getIdClub().getId());
			}
			stmt.setObject(	5, participant.getId() );
			stmt.executeUpdate();
			

			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}


	}
	
	public Participant retrouver(int idParticipant)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM participant WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idParticipant);
            rs = stmt.executeQuery();
            if ( rs.next() ) {
            	Participant participant = construireParticipant(rs );
            	participant.setIdEquipe(daoEquipe.retrouver(rs.getObject("id_Equipe",Integer.class),participant));
                return participant;
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public Participant retrouver(int idParticipant, Equipe equipe)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM participant WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idParticipant);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
            	Participant participant = construireParticipant(rs );
            	participant.setIdEquipe(equipe);
                return participant;
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
	
	private Participant construireParticipant( ResultSet rs ) throws SQLException 
	{
		Participant participant = new Participant();
		participant.setAutoMedicale(rs.getObject("Autorisation_medicale",Boolean.class));
		participant.setAutoParentale(rs.getObject("Autorisation_parentale",Boolean.class));
		participant.setId(rs.getObject("id",Integer.class));
		participant.setIdClub(daoClub.retrouver(rs.getObject("Id_Club",Integer.class)));
		
		return participant;
	}
	
}

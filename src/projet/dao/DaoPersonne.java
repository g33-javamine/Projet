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
import projet.data.Administrateurs;
import projet.data.Benevole;
import projet.data.Equipe;
import projet.data.Participant;
import projet.data.Personne;
import projet.data.Poste;


public class DaoPersonne {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoBenevole	daoBenevole;
	@Inject
	private DaoAdministrateurs	daoAdministrateurs;
	@Inject
	private DaoParticipant	daoParticipant;

	
	// Actions

	public int inserer(Personne personne)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le personne
			sql = "INSERT INTO Personne (Nom,Prenom,DateNaissance,Tel,Mail,Adresse) VALUES (?,?,?,?,?,?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setObject(	1, personne.getNom() );
			stmt.setObject(	2, personne.getPrenom() );
			stmt.setObject(	3, personne.getDateNaissance() );
			stmt.setObject(	4, personne.getTel() );
			stmt.setObject(	5, personne.getMail() );
			stmt.setObject(	6, personne.getAdresse() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			personne.setId( rs.getObject( 1, Integer.class ) );
			
			if(personne instanceof Administrateurs)
			{
				daoAdministrateurs.inserer((Administrateurs) personne);
			}
			else if(personne instanceof Benevole)
			{
				daoBenevole.inserer((Benevole) personne);
			}
			else if(personne instanceof Participant)
			{
				daoParticipant.inserer((Participant) personne);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
		
		// Retourne l'identifiant
		return personne.getId();
	}

	
	public void modifier(Personne personne)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE Personne SET Nom =  ?,Prenom =  ?,DateNaissance =  ?,Tel =  ?,Mail =  ?,Adresse =  ? WHERE id =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, personne.getNom() );
			stmt.setObject( 2, personne.getPrenom() );
			stmt.setObject( 3, personne.getDateNaissance() );
			stmt.setObject( 4, personne.getTel() );
			stmt.setObject( 5, personne.getMail() );
			stmt.setObject( 6, personne.getAdresse() );
			stmt.setObject( 7, personne.getId() );
			stmt.executeUpdate();
			
			if(personne instanceof Administrateurs)
			{
				daoAdministrateurs.modifier((Administrateurs) personne);
			}
			else if(personne instanceof Benevole)
			{
				daoBenevole.modifier((Benevole) personne);
			}
			else if(personne instanceof Participant)
			{
				daoParticipant.modifier((Participant) personne);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}


	}

	
	public void supprimer(int idPersonne)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;


		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "DELETE FROM Personne WHERE id = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idPersonne );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM Participant WHERE id = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idPersonne );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM Benevole WHERE id = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idPersonne );
			stmt.executeUpdate();
			stmt.close();
			
			sql = "DELETE FROM Administrateurs WHERE id = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idPersonne );
			stmt.executeUpdate();
			stmt.close();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Personne retrouver(int idPersonne)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null, stmtSub = null;
		ResultSet 			rs 		= null, rsSub = null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Personne WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idPersonne);
            rs = stmt.executeQuery();
            

            if ( rs.next() ) 
            {
            	Personne personne = null;
            	sql = "SELECT * FROM Participant WHERE id = ?";
            	stmtSub = cn.prepareStatement(sql);
            	stmtSub.setObject( 1, idPersonne);
                rsSub = stmtSub.executeQuery();
                if(rsSub.next())
                {
                	personne = daoParticipant.retrouver(idPersonne);
                }
                stmtSub.close();
                
                sql = "SELECT * FROM Benevole WHERE id = ?";
                stmtSub = cn.prepareStatement(sql);
                stmtSub.setObject( 1, idPersonne);
                rsSub = stmtSub.executeQuery();
                if(rsSub.next())
                {
                	personne = daoBenevole.retrouver(idPersonne);
                }
                stmtSub.close();
                
                sql = "SELECT * FROM Administrateurs WHERE id = ?";
                stmtSub = cn.prepareStatement(sql);
                stmtSub.setObject( 1, idPersonne);
                rsSub = stmtSub.executeQuery();
                if(rsSub.next())
                {
                	personne = daoAdministrateurs.retrouver(idPersonne);
                }
                stmtSub.close();
                
                completerPersonne(rs, personne );
                return personne;
            }
            else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public Personne retrouver(int idPersonne,Equipe equipe)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null, stmtSub = null;
		ResultSet 			rs 		= null, rsSub = null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Personne WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idPersonne);
            rs = stmt.executeQuery();
            

            if ( rs.next() ) 
            {
            	Personne personne = null;
            	sql = "SELECT * FROM Participant WHERE id = ?";
            	stmtSub = cn.prepareStatement(sql);
            	stmtSub.setObject( 1, idPersonne);
                rsSub = stmtSub.executeQuery();
                if(rsSub.next())
                {
                	personne = daoParticipant.retrouver(idPersonne,equipe);
                }
                stmtSub.close();
                completerPersonne(rs, personne );
                return personne;
            }
            else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	public List<Benevole> listerBenevolesPoste(Poste poste) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT Personne.id as id,* FROM Personne INNER JOIN a_poste ON a_poste.id = Personne.id WHERE a_poste.id_poste LIKE ? ORDER BY Personne.id";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, poste.getNomPoste());
			rs = stmt.executeQuery();

			List<Benevole> Benevoles = new LinkedList<>();
			while (rs.next()) {
				Benevole benevole = daoBenevole.retrouver(rs.getObject( "id", Integer.class ),poste);
				completerPersonne( rs,benevole);
				Benevoles.add( benevole);
			}
			return Benevoles;
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Benevole> listerBenevolesSansPoste()
	{
		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM Personne WHERE id NOT IN (SELECT id FROM a_poste) AND id IN (SELECT id FROM benevole) ORDER BY id";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Benevole> Benevoles = new LinkedList<>();
			while (rs.next()) {
				Benevole benevole = daoBenevole.retrouver(rs.getObject( "id", Integer.class ));
				completerPersonne( rs,benevole);
				Benevoles.add( benevole);
			}
			return Benevoles;
 
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	// Méthodes auxiliaires
	
	private void completerPersonne( ResultSet rs, Personne personne ) throws SQLException {
		personne.setAdresse(rs.getObject( "Adresse", String.class ));
		personne.setDateNaissance(rs.getObject( "DateNaissance", Date.class ));
		personne.setMail(rs.getObject( "Mail", String.class ));
		personne.setNom(rs.getObject( "Nom", String.class ));
		personne.setPrenom(rs.getObject( "Prenom", String.class ));
		personne.setTel(rs.getObject( "Tel", String.class ));

	}
	
}

package projet.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.dao.jdbc.UtilJdbc;
import projet.data.Administrateurs;
import projet.data.Benevole;
import projet.data.Equipe;
import projet.data.Participant;
import projet.data.Personne;


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
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null, rsSub = null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personne WHERE idpersonne = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idPersonne);
            rs = stmt.executeQuery();
            stmt.close();
            

            if ( rs.next() ) 
            {
            	Personne personne = null;
            	sql = "SELECT * FROM Participant WHERE idpersonne = ?";
                stmt = cn.prepareStatement(sql);
                stmt.setObject( 1, idPersonne);
                rsSub = stmt.executeQuery();
                stmt.close();
                if(rsSub.next())
                {
                	personne = daoParticipant.retrouver(idPersonne);
                }
                sql = "SELECT * FROM Benevole WHERE idpersonne = ?";
                stmt = cn.prepareStatement(sql);
                stmt.setObject( 1, idPersonne);
                rsSub = stmt.executeQuery();
                stmt.close();
                if(rsSub.next())
                {
                	personne = daoBenevole.retrouver(idPersonne);
                }
                sql = "SELECT * FROM Administrateurs WHERE idpersonne = ?";
                stmt = cn.prepareStatement(sql);
                stmt.setObject( 1, idPersonne);
                rsSub = stmt.executeQuery();
                stmt.close();
                if(rsSub.next())
                {
                	personne = daoAdministrateurs.retrouver(idPersonne);
                }
                
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
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null, rsSub = null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM personne WHERE idpersonne = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idPersonne);
            rs = stmt.executeQuery();
            stmt.close();
            

            if ( rs.next() ) 
            {
            	Personne personne = null;
            	sql = "SELECT * FROM Participant WHERE idpersonne = ?";
                stmt = cn.prepareStatement(sql);
                stmt.setObject( 1, idPersonne);
                rsSub = stmt.executeQuery();
                stmt.close();
                if(rsSub.next())
                {
                	personne = daoParticipant.retrouver(idPersonne,equipe);
                }
                
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

	// Méthodes auxiliaires
	
	private void completerPersonne( ResultSet rs, Personne personne ) throws SQLException {
		
		personne.setId(rs.getObject( "id", Integer.class ));
		personne.setAdresse(rs.getObject( "Adresse", String.class ));
		personne.setDateNaissance(rs.getObject( "DateNaissance", Date.class ));
		personne.setMail(rs.getObject( "Mail", String.class ));
		personne.setNom(rs.getObject( "Nom", String.class ));
		personne.setPrenom(rs.getObject( "Prenom", String.class ));
		personne.setTel(rs.getObject( "Tel", String.class ));

	}
	
}

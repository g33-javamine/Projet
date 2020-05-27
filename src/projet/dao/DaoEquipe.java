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
import projet.data.Equipe;
import projet.data.Participant;


public class DaoEquipe {

	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoPersonne	daoPersonne;
	@Inject
	private DaoParcours	daoParcours;
	
	// Actions

	public int inserer( Equipe equipe )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le compte
			sql = "INSERT INTO Equipe (Payement,Nbr_repas,Categorie,Id_Parcours,Id_Capitaine,Id_Equipier) VALUES (?,?,?,?,?,?); ";
			stmt = cn.prepareCall( sql ); 
			stmt.setObject( 1, equipe.getPaiement());
			stmt.setObject( 2, equipe.getNbrRepas());
			stmt.setObject( 3, equipe.getCategorie());
			stmt.setObject( 4, equipe.getIdParcours().getId());
			stmt.setObject( 5, equipe.getIdCapitaine().getId());
			stmt.setObject( 6, equipe.getIdEquipier().getId());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			equipe.setId( rs.getObject( 1, Integer.class) );
			return equipe.getId();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}

	}
	

	public void modifier( Equipe equipe )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le compte
			sql = "UPDATE Poste SET Payement =  ?,Nbr_repas =  ?,Categorie =  ?,Id_Parcours =  ?,Id_Capitaine =  ?,Id_Equipier =  ? WHERE Id =  ? ";
			stmt = cn.prepareCall( sql );
			stmt.setObject( 1, equipe.getPaiement());
			stmt.setObject( 2, equipe.getNbrRepas());
			stmt.setObject( 3, equipe.getCategorie());
			stmt.setObject( 4, equipe.getIdParcours().getId());
			stmt.setObject( 5, equipe.getIdCapitaine().getId());
			stmt.setObject( 6, equipe.getIdEquipier().getId());
			stmt.setObject( 7, equipe.getId() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

	public void supprimer( int idEquipe )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le compte
			sql = "DELETE FROM Equipe WHERE Id = ?  ";
			stmt = cn.prepareCall( sql );
			stmt.setObject( 1, idEquipe );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

	public Equipe retrouver( int idEquipe )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Equipe WHERE Id = ?";
            stmt = cn.prepareCall( sql );
            stmt.setObject( 1, idEquipe );
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireEquipe( rs );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public Equipe retrouver( Participant participant )  {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Equipe WHERE Id = ?";
            stmt = cn.prepareCall( sql );
            stmt.setObject( 1, participant.getIdEquipe() );
            rs = stmt.executeQuery();
            
            if ( rs.next() ) {
            	Equipe equipe = construireEquipe( rs );
            	if(rs.getObject( "Id_Capitaine", Integer.class ) == participant.getId())
            	{
            		equipe.setIdCapitaine(participant);
            		equipe.setIdEquipier((Participant) daoPersonne.retrouver(rs.getObject( "Id_Equipier", Integer.class ),equipe));
            	}
            	else if(rs.getObject( "Id_Equipier", Integer.class ) == participant.getId())
            	{
            		equipe.setIdCapitaine((Participant) daoPersonne.retrouver(rs.getObject( "Id_Capitaine", Integer.class ),equipe));
            		equipe.setIdEquipier(participant);
            	}
            	participant.setIdEquipe(equipe);
                return equipe;
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

	public List<Equipe> listerTout()   {

		Connection			cn		= null;
		CallableStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;
		List<Equipe> equipes = new ArrayList<>();
		
		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM Equipe ORDER BY Id ";
			stmt = cn.prepareCall( sql );
			rs = stmt.executeQuery();
			
			while ( rs.next() ) {
				equipes.add( construireEquipe(rs) );
			}
			return equipes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	// Méthodes auxiliaires
	
	private Equipe construireEquipe( ResultSet rs ) throws SQLException {
		Equipe equipe = new Equipe();
		equipe.setCategorie(rs.getObject( "Categorie", String.class ) );
		equipe.setId(rs.getObject( "Id", Integer.class ) );
		equipe.setNbrRepas(rs.getObject( "Nbr_repas", Integer.class ) );
		equipe.setPaiement(rs.getObject( "Paiement", Boolean.class ) );
		equipe.setIdParcours(daoParcours.retrouver(rs.getObject( "Id_Parcours", Integer.class ) ));


		return equipe;
	}
	
}

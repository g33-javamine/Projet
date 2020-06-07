package projet.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import jfox.javafx.view.IManagerGui;
import projet.commun.Roles;
import projet.data.Utilisateur;
import projet.view.systeme.ModelConnexion;


public class MenuBarAppli extends MenuBar {

	
	// Champs
	
	private Menu	menuAdmin;
	private Menu	menuParticipants;
	private Menu	menuBenevole;
	private Menu	menuGeneral;
	
	private MenuItem itemDeconnecter;
	
	@Inject
	private IManagerGui 	managerGui;
	@Inject
	private ModelConnexion	modelConnexion;	
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {

		
		// Variables de travail
		Menu menu;
		MenuItem item;
		
		
		// Menu Système
		
		menu =  new Menu( "Système" );
		this.getMenus().add(menu);
		
		item = new MenuItem( "Se déconnecter" );
		item.setOnAction(  
			(e) -> {
			Utilisateur.setCourant(null); 
			managerGui.showView( EnumView.Connexion );
			});
		menu.getItems().add( item );
		itemDeconnecter = item;
		
		item = new MenuItem( "Quitter" );
		item.setOnAction(  (e) -> managerGui.exit()  );
		menu.getItems().add( item );

		
		// Menu Admin
		
		menu =  new Menu( "Admin" );
		this.getMenus().add(menu);
		menuAdmin = menu;
		
		item = new MenuItem( "Accueil" );
		item.setOnAction(  (e) -> managerGui.showView( EnumView.AccueilAdmin )  );
		menu.getItems().add( item );
		
		item = new MenuItem( "Gestion Bénévole" );
		item.setOnAction(  (e) -> managerGui.showView( EnumView.GestionBenevole )  );
		menu.getItems().add( item );
		
		item = new MenuItem( "Gestion Participant" );
		item.setOnAction(  (e) -> managerGui.showView( EnumView.GestionParticipant )  );
		menu.getItems().add( item );

		
		// Menu Participant
		
		menu =  new Menu( "Participant" );
		this.getMenus().add(menu);
		menuParticipants = menu;
		
		item = new MenuItem( "Accueil" );
		item.setOnAction(  (e) -> managerGui.showView( EnumView.AccueilParticipant )  );
		menu.getItems().add( item );
		
		item = new MenuItem( "Gestion Compte" );
		item.setOnAction(  (e) -> managerGui.showView( EnumView.CompteParticipant )  );
		menu.getItems().add( item );

		
		// Manu Bénévole
		
		menu =  new Menu( "Bénévole" );
		this.getMenus().add(menu);
		menuBenevole = menu;
		
		item = new MenuItem( "Accueil" );
		item.setOnAction(  (e) -> managerGui.showView( EnumView.AccueilBenevole )  );
		menu.getItems().add( item );
		
		item = new MenuItem( "Gestion Compte" );
		item.setOnAction(  (e) -> managerGui.showView( EnumView.CompteBenevole )  );
		menu.getItems().add( item );
		
		// Manu Tests
		
		menu =  new Menu( "Générale" );;
		this.getMenus().add(menu);
		menuGeneral = menu;
				
		item = new MenuItem( "Vue Parcours" );
		item.setOnAction(  (e) -> managerGui.showView( EnumView.Parcours )  );
		menu.getItems().add( item );
		

		// Configuration initiale du menu
		configurerMenu( modelConnexion.getCompteActif() );

		// Le changement du compte connecté modifie automatiquement le menu
		modelConnexion.compteActifProperty().addListener( (obs) -> {
					Platform.runLater( () -> configurerMenu( modelConnexion.getCompteActif() ) );
				}
			); 
		
	}

	
	// Méthodes auxiliaires
	
	private void configurerMenu( Utilisateur compteActif  ) {

		itemDeconnecter.setDisable(true);
		menuAdmin.setVisible(false);
		menuParticipants.setVisible(false);
		menuBenevole.setVisible(false);
		menuGeneral.setVisible(false);
		
		if( compteActif != null ) {
			itemDeconnecter.setDisable(false);
			if( compteActif.getRole() == Roles.PARTICIPANT ) {
				menuParticipants.setVisible(true);
				menuGeneral.setVisible(true);
			}
			if( compteActif.getRole() == Roles.BENEVOLE ) {
				menuBenevole.setVisible(true);
				menuGeneral.setVisible(true);
			}
			if( compteActif.getRole() == Roles.ADMINISTRATEUR ) {
				menuAdmin.setVisible(true);
				menuGeneral.setVisible(true);
			}
		}
	}
	
}

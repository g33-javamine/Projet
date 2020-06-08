package projet.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import jfox.commun.context.IContext;
import jfox.javafx.view.ManagerGuiAbstract;


public class ManagerGui extends ManagerGuiAbstract {

	
	// Champs
	
	@Inject
	private IContext		context;
	
	
	// Initialisation
	
	@PostConstruct
	public void init() {
		setFactoryController( context::getBeanNew );
		addRessourcesToClose( context );
	}

	
	// Actions

	@Override
	protected void configureStage()  {
		
		// Choisit la vue à afficher
		showView( EnumView.Connexion);
		
		// Configure le stage
		stage.setTitle( "Bol d'air" );
		stage.setWidth(600);
		stage.setHeight(440);
		stage.setMinWidth(400);
		stage.setMinHeight(300);
		stage.getIcons().add(new Image(getClass().getResource("bolAir.png").toExternalForm()));
		
		// Configuration par défaut pour les boîtes de dialogue
		typeConfigDialogDefault = ConfigDialog.class;
	}

	
	@Override
	public Scene createScene( Parent root ) {
		BorderPane paneMenu = new BorderPane( root );
		paneMenu.setTop( context.getBeanNew( MenuBarAppli.class ) );
		Scene scene = new Scene( paneMenu );
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	
}
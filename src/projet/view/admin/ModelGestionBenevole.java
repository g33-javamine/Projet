package projet.view.admin;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import projet.dao.DaoPersonne;
import projet.dao.DaoPoste;
import projet.data.Poste;
import projet.data.Benevole;


public class ModelGestionBenevole  {
	
	
	// Données observables 
	
		private final ObservableList<Benevole> benevoleList = FXCollections.observableArrayList(); 
		
		private Poste		posteCourant = new Poste();
		private Benevole	benevoleCourant = new Benevole();
		
		private final ObservableList<Poste> posteList = FXCollections.observableArrayList();
		
		private final Property<Image>	schema = new SimpleObjectProperty<>();
		

		
		// Autres champs
	    @Inject
		private DaoPoste	daoPoste;
	    @Inject
		private DaoPersonne	daoPersonne;
	    

	    
		// Initialisations
		
		@PostConstruct
		public void init() 
		{
			
		}
		
		
		// Getters 
		
		public ObservableList<Benevole> getBenevoleList() {
			return benevoleList;
		}
		
		public void setPosteCourant(Poste posteCourant) {
			this.posteCourant = posteCourant;
		}


		public void setBenevoleCourant(Benevole benevoleCourant) {
			this.benevoleCourant = benevoleCourant;
		}


		public Poste getPosteCourant() {
			return posteCourant;
		}
		

		public Benevole getBenevoleCourant() {
			return benevoleCourant;
		}


		public ObservableList<Poste> getPosteList() 
		{
			return posteList;
		}


		public Property<Image> schemaProperty() {
			return schema;
		}
		
		
		// Actualisations
		
		public void actualiserListeBenevole() 
		{
			benevoleList.clear();
			benevoleList.addAll(posteCourant.getBenevoles());
	 	}
		
		public void actualiserListePoste() 
		{
			posteList.clear();
			posteList.addAll(daoPoste.listerTout());
			Poste vide = new Poste();
			vide.setNomPoste("Sans poste");
			vide.setNbrBenevole(0);
			vide.setBenevoles(daoPersonne.listerBenevolesSansPoste());
			posteList.add(vide);
	 	}
		
		public void actualiserDragAndDrop(Poste debut,Poste fin) 
		{
			if(debut.getNomPoste() != "Sans poste")
			{
				daoPoste.modifier(debut, debut.getNomPoste());
			}
			
			if(fin.getNomPoste() != "Sans poste")
			{
				daoPoste.modifier(fin, fin.getNomPoste());
			}
	 	}
		
		public void supprimer()
		{
			daoPoste.supprimer(posteCourant.getNomPoste());
			actualiserListePoste();
		}
}

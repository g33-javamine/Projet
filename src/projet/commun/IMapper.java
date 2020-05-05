package projet.commun;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import projet.data.Club;
import projet.data.Utilisateur;
import projet.data.Participant;
import projet.data.Personne;
import projet.data.Benevole;


@Mapper
public interface IMapper {  
	
	Utilisateur update( @MappingTarget Utilisateur target, Utilisateur source  );
	
	Club update( @MappingTarget Club target, Club source );



	Benevole update( @MappingTarget Benevole target, Benevole source );
	
}

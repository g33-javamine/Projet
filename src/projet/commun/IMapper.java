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
	
	Benevole update( @MappingTarget Benevole target, Benevole source );
	Participant update (@MappingTarget Participant target, Participant source);
	
}

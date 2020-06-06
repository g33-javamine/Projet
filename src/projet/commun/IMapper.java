package projet.commun;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import projet.data.Participant;
import projet.data.Poste;
import projet.data.Benevole;


@Mapper
public interface IMapper {  
	
	Benevole update( @MappingTarget Benevole target, Benevole source );
	Participant update (@MappingTarget Participant target, Participant source);
	Poste update (@MappingTarget Poste target, Poste source);
	
}

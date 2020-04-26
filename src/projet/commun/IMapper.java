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

	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Personne update( @MappingTarget Personne target, Personne source );

	@Mapping( target="categorie", expression="java( source.getCategorie() )" )
	Participant update( @MappingTarget Participant target, Participant source );

	Benevole update( @MappingTarget Benevole target, Benevole source );
	
}

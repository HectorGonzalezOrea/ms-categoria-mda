package mx.com.nmp.escenariosdinamicos.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.escenariosdinamicos.mongodb.entity.EscenarioEntity;

public interface EscenarioRepository extends MongoRepository <EscenarioEntity, String> {
	
	EscenarioEntity findByIdEscenario(Integer idEscenario);
	

}

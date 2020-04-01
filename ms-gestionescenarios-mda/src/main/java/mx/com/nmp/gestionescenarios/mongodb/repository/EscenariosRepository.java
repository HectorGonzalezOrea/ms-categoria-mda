package mx.com.nmp.gestionescenarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.gestionescenarios.mongodb.entity.GestionEscenarioEntity;

public interface EscenariosRepository extends MongoRepository<GestionEscenarioEntity, String> {
	
	//GestionEscenarioEntity findById(Integer idRegla);

}

package mx.com.nmp.gestionescenarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.gestionescenarios.mongodb.entity.GestionEscenarioEntity;

public interface OrigenRepository extends MongoRepository<GestionEscenarioEntity, String> {
	
	GestionEscenarioEntity findByOrigen (Integer id, String descripcion);
	
	

}

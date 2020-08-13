package mx.com.nmp.gestionescenarios.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;



import mx.com.nmp.gestionescenarios.mongodb.entity.GestionEscenarioEntity;

public interface EscenariosRepository extends MongoRepository<GestionEscenarioEntity, String> {
	
	GestionEscenarioEntity findByIdRegla(Integer idRegla);
	
	@Query(value="{'fechaAplicacion.fechas':{$in:[?0]}}",sort="{_id:1}")
	List<GestionEscenarioEntity> lstEscenarios(String fechaAplicacion);
	

}

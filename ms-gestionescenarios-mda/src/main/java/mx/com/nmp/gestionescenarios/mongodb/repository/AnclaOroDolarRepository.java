package mx.com.nmp.gestionescenarios.mongodb.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mx.com.nmp.gestionescenarios.mongodb.entity.AnclaOroDolarEntity;

public interface AnclaOroDolarRepository extends MongoRepository <AnclaOroDolarEntity, String> {
	
	@Query(value="{'fechaAplicacion':{$in:[?0]}}", sort="{_id:1}")
	List<AnclaOroDolarEntity> getListAnclaOroDolar(LocalDate fecha);

}

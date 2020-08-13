package mx.com.nmp.gestionescenarios.mongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mx.com.nmp.gestionescenarios.mongodb.entity.MonedasEntity;

public interface GestionMonedasRepository extends MongoRepository<MonedasEntity, String> {
    @Query(value="{oro : ?0,fechaAplicacion:{$gte:?1, $lt:?2}}", sort="{_id:1}")
	List<MonedasEntity> obtenerMonedas(Boolean oro, Date fechaInicio, Date fechaFin);
	

}

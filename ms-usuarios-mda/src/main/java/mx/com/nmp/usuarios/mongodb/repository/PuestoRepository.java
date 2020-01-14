package mx.com.nmp.usuarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.usuarios.mongodb.entity.PuestoEntity;

public interface PuestoRepository extends MongoRepository<PuestoEntity, String>  {

	PuestoEntity findByIdPuesto(Integer idPuesto);
	
}

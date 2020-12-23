package mx.com.nmp.usuarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.usuarios.mongodb.entity.CapacidadEntity;

public interface CapacidadRepository extends MongoRepository<CapacidadEntity, String> {
	CapacidadEntity findByIdCapacidad(Integer idCapacidad);
	CapacidadEntity findByIdCapacidad(Integer idCapacidad, String descripcion);
}

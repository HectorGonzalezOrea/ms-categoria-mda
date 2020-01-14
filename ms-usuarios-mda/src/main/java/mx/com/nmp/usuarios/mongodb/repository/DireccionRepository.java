package mx.com.nmp.usuarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.usuarios.mongodb.entity.DireccionEntity;


public interface DireccionRepository extends MongoRepository<DireccionEntity, 	String> {
	DireccionEntity findByIdDireccion(Integer idDirecccion);

}

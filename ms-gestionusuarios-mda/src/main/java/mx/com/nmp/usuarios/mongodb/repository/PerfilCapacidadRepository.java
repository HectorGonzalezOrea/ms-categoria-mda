package mx.com.nmp.usuarios.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.usuarios.mongodb.entity.PerfilCapacidadEntity;

public interface PerfilCapacidadRepository extends MongoRepository<PerfilCapacidadEntity, String> {

	List<PerfilCapacidadEntity> findByPerfil(Integer perfil);
	
}
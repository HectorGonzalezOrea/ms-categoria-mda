package mx.com.nmp.usuarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.usuarios.mongodb.entity.GerenciaEntity;


public interface GerenciaRepository extends MongoRepository<GerenciaEntity, String> {

	GerenciaEntity findByIdGerencia(Integer idGerencia);
}

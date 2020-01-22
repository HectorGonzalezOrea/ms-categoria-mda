package mx.com.nmp.usuarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.usuarios.mongodb.entity.PerfilEntity;

public interface PerfilRepository  extends MongoRepository<PerfilEntity, String>{

	PerfilEntity findByIdPerfil(Integer idPerfil);

}

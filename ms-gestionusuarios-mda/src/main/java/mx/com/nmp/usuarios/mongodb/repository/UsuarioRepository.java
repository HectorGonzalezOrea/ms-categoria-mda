package mx.com.nmp.usuarios.mongodb.repository;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String> {

	
	UsuarioEntity findByIdUsuario(Integer idUsuario);
	UsuarioEntity findByUsuario(String usuario);
	ArrayList<UsuarioEntity> findAllByUsuario(String usuario);
}

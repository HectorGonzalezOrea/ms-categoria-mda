package mx.com.nmp.usuarios.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;

public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String> {
	//UsuarioEntity findByIdUsuario(Integer idUsuario);
	//UsuarioEntity findByUsuario(String usuario);
	
	UsuarioEntity findByIdUsuario(Integer idUsuario);
	List<UsuarioEntity> findAllByUsuario(String usuario);
}

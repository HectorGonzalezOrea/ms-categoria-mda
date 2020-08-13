package mx.com.nmp.usuarios.mongodb.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import mx.com.nmp.usuarios.mongodb.entity.HistorialEntity;

public interface HistorialRepository extends MongoRepository<HistorialEntity, String>{
	HistorialEntity findByUsuario(String usuario);
}
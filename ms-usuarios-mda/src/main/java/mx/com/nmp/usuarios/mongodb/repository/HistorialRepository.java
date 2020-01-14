package mx.com.nmp.usuarios.mongodb.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import mx.com.nmp.usuarios.mongodb.entity.HistorialEntity;

public interface HistorialRepository extends MongoRepository<HistorialEntity, String>{
	HistorialEntity findByUsuario(String usuario);
}
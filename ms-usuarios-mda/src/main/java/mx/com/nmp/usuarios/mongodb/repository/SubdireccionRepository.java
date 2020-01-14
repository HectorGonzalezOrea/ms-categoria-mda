package mx.com.nmp.usuarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import mx.com.nmp.usuarios.mongodb.entity.SubdireccionEntity;

public interface SubdireccionRepository extends MongoRepository<SubdireccionEntity,  String> {
	
	SubdireccionEntity findByIdSubdireccion(Integer idSubdireccion);

}

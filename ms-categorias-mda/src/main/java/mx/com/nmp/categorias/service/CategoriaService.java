package mx.com.nmp.categorias.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.com.nmp.categorias.entity.CategoriaEntity;
@Repository
public interface CategoriaService extends MongoRepository<CategoriaEntity, Long> {
	/*No es soportado deleteAll*/
	//void deleteAll(List<CategoriaEntity> aux);

}

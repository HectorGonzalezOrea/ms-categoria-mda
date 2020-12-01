package mx.com.nmp.categorias.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.com.nmp.categorias.entity.CategoriaEntity;
@Repository
public interface CategoriaService extends MongoRepository<CategoriaEntity, Long> {
}

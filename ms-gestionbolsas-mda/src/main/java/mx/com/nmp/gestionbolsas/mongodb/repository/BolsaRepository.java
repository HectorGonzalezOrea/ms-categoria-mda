package mx.com.nmp.gestionbolsas.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.gestionbolsas.mongodb.entity.BolsasEntity;

public interface BolsaRepository extends MongoRepository<BolsasEntity, String>{
	

	BolsasEntity findByIdBolsa(Integer idBolsa);
}

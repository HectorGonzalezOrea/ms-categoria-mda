package mx.com.nmp.usuarios.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.com.nmp.usuarios.mongodb.entity.DepartamentoAreaEntity;


public interface DepartamentoAreaRepository extends MongoRepository<DepartamentoAreaEntity, String>{

	DepartamentoAreaEntity findByIdDepartamento(Integer idDepartamento);
}

package mx.com.nmp.usuarios.mongodb.service;

import java.util.List;

import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.mongodb.entity.PerfilEntity;

public interface PerfilService {
	
	List<CapacidadUsuariosRes> buscarPerfilConCapacidades() throws Exception;
	CapacidadUsuariosRes perfilById(Integer perfil) throws Exception;
	Boolean consultarPerfil(Integer idPerfil) throws Exception;
	PerfilEntity consultarPerfilEntity(Integer idPerfil) throws Exception;
	Boolean consultarCapacidad(Integer capacidad) throws Exception;
	Boolean consultarCapacidad(List<Integer> capacidades) throws Exception;
}

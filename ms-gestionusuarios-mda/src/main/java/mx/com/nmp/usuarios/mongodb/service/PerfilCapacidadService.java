package mx.com.nmp.usuarios.mongodb.service;

import java.util.List;

import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;

public interface PerfilCapacidadService {

	CapacidadUsuariosRes crearPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq informacionPerfil, List<String> grupos) throws Exception;
	Boolean validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq) throws Exception;
	CapacidadUsuariosRes modificarPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq modCapacidadReq, List<String> grupos) throws Exception;
	CapacidadUsuariosRes actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil, List<String> grupos) throws Exception;
	
	Boolean validarUsuarioAdmin(CapacidadUsuariosRes perfil);
	
}

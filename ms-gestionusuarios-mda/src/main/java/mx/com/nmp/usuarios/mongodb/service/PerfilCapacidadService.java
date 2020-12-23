package mx.com.nmp.usuarios.mongodb.service;

import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.InternalServerError;
import mx.com.nmp.usuarios.model.ModCapacidadUsuario;

public interface PerfilCapacidadService {

	CapacidadUsuariosRes crearPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq informacionPerfil);
	InternalServerError validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq);
	InternalServerError validarCapacidadesMod(ModCapacidadUsuario modCapacidadReq);
	CapacidadUsuariosRes modificarPerfilCapacidad(Integer idPerfil, ModCapacidadUsuario modCapacidadReq);
	CapacidadUsuariosRes actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil);
	
	Boolean validarUsuarioAdmin(CapacidadUsuariosRes perfil);
	
}

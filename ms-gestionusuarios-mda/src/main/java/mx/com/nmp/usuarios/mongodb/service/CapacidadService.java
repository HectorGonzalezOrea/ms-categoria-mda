package mx.com.nmp.usuarios.mongodb.service;

import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;

public interface CapacidadService {

	Boolean validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq) throws Exception;

}

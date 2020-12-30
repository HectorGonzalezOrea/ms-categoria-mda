package mx.com.nmp.usuarios.api.business;

import java.util.Date;
import java.util.List;

import mx.com.nmp.usuarios.api.exception.ApiException;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.EliminarUsuariosRes;
import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.PerfilUsuario;
import mx.com.nmp.usuarios.model.ResEstatus;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;
import mx.com.nmp.usuarios.model.ReqHistorico.AccionEnum;

public interface GestionUsuarios {

	void sincronizarUsuarios(String grupo) throws ApiException ;
	List<InfoUsuario> consultarUsuarios(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean estatus, Integer perfil, String usuario, List<String> grupos) throws ApiException;
	CapacidadUsuariosRes actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil, List<String> grupos) throws ApiException;
	ResEstatus actualizarEstatusUsuario(Integer idUsuario, Boolean activo, List<String> grupos) throws ApiException;
	EliminarUsuariosRes deleteUsuario(Integer idUsuario, List<String> grupos) throws ApiException;
	PerfilUsuario consultarUsuariosConPerfil(String usuario) throws ApiException;
	UsuarioEntity consultarUsuarios(String usuario) throws ApiException;
	PerfilUsuario consultarUsuariosConPerfil(Integer id, List<String> grupos) throws ApiException;
	PerfilUsuario consultarUsuariosConPerfil(Integer id) throws ApiException;
	Boolean validarUsuarioPerfil(String usuario, Integer perfil) throws ApiException;
	CrearHistoricoRes crearHistorico(AccionEnum accion, Date fecha, Integer idPerfil, String usuario) throws ApiException;
	ConsultaHistoricoRes getHistorico(Integer idUsuario) throws ApiException;
	
	Boolean validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq) throws ApiException;
	CapacidadUsuariosRes crearPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq informacionPerfil, List<String> grupos) throws ApiException;
	
}

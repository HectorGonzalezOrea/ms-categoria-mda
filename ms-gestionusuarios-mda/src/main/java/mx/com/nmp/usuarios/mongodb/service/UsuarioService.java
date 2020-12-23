package mx.com.nmp.usuarios.mongodb.service;

import java.util.Date;
import java.util.List;

import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.InternalServerError;
import mx.com.nmp.usuarios.model.ModCapacidadUsuario;
import mx.com.nmp.usuarios.model.ReqHistorico.AccionEnum;
import mx.com.nmp.usuarios.model.ResEstatus;

public interface UsuarioService {
	
	CrearHistoricoRes crearHistorico(AccionEnum accion, Date fecha, Integer idPerfil, String usuario);
	ConsultaHistoricoRes getHistorico(Integer idUsuario);
	ResEstatus actualizarEstatusUsuario(Integer idUsuario, Boolean activo);
	Boolean deleteUsuario(Integer idUsuario);
	List<InfoUsuario> getUsuarios(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean activo, String usuario, Integer perfil);
	List<InfoUsuario> getUsuariosSinFiltro();
	CapacidadUsuariosRes crearPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq informacionPerfil);
	InternalServerError validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq);
	InternalServerError validarCapacidadesMod(ModCapacidadUsuario modCapacidadReq);
	CapacidadUsuariosRes modificarPerfilCapacidad(Integer idPerfil, ModCapacidadUsuario modCapacidadReq);
	CapacidadUsuariosRes actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil);
	Object consultaPrefil(String usuario, String token);
	Object consultaPrefilByToken(String token);
	Boolean validarUsuarioAdmin(CapacidadUsuariosRes perfil);
	Boolean crearUsuario(InfoUsuario request);
	Boolean consultarPerfil(Integer idPerfil);
	Boolean consultarUsuario(String usuario);
	Boolean consultarIdUsuario(Integer idUsuario);
	
}

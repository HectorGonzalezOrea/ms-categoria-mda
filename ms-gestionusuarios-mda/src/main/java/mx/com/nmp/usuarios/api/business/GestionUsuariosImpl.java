package mx.com.nmp.usuarios.api.business;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.usuarios.api.exception.ApiException;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.EliminarUsuariosRes;
import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.PerfilUsuario;
import mx.com.nmp.usuarios.model.ResEstatus;
import mx.com.nmp.usuarios.model.ReqHistorico.AccionEnum;
import mx.com.nmp.usuarios.model.ResEstatus.DescripcionEnum;
import mx.com.nmp.usuarios.mongodb.entity.UsuarioEntity;
import mx.com.nmp.usuarios.mongodb.service.CapacidadService;
import mx.com.nmp.usuarios.mongodb.service.HistoricoService;
import mx.com.nmp.usuarios.mongodb.service.PerfilCapacidadService;
import mx.com.nmp.usuarios.mongodb.service.PerfilService;
import mx.com.nmp.usuarios.mongodb.service.UsuarioService;
import mx.com.nmp.usuarios.oag.client.service.OAGService;
import mx.com.nmp.usuarios.oag.vo.BusquedaGrupoVO;
import mx.com.nmp.usuarios.oag.vo.UsuariosResponseVO;
import mx.com.nmp.usuarios.utils.Constantes;

@Service
public class GestionUsuariosImpl implements GestionUsuarios {

	private static final Logger logger = LoggerFactory.getLogger(GestionUsuariosImpl.class);
	
	@Autowired
	OAGService oagService; 
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	private PerfilService perfilService;
	
	@Autowired
	private HistoricoService historicoService;
	
	@Autowired
	private CapacidadService capacidadService;
	
	@Autowired
	private PerfilCapacidadService perfilCapacidadService;
	
	@Override
	public void sincronizarUsuarios(String grupo) throws ApiException {
		logger.info("sincronizarUsuarios");
		
		try {
			String token = oagService.getToken();
			
			BusquedaGrupoVO bg = new BusquedaGrupoVO();
			bg.setDirecto(Boolean.TRUE);
			bg.setGrupo(grupo);
			
			UsuariosResponseVO usuarios = oagService.getUsersByGroup(token, bg);
			
			usuarioService.upsertUsers(usuarios.getUsuario(), grupo);
			
		} catch (Exception e) {
			throw new ApiException(e);
		}
		
	}

	@Override
	public List<InfoUsuario> consultarUsuarios(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean estatus, Integer perfil, String usuario, List<String> grupos) throws ApiException {
		logger.info("consultarUsuarios");

		List<InfoUsuario> usuarios = null;
		
		try {
			usuarios = usuarioService.getAllUsers(nombre, apellidoPaterno, apellidoMaterno, estatus, perfil, usuario, grupos);
		} catch (Exception e) {
			throw new ApiException(e);
		}
				
		return usuarios;
	}

	@Override
	public CapacidadUsuariosRes actualizarPerfilUsuario(Integer idUsuario, Integer idPerfil, List<String> grupos) throws ApiException {
		logger.info("actualizarPerfilUsuario");
		
		Boolean actualizado = false;
		CapacidadUsuariosRes resp = null;
		try {
			if(idPerfil != null && idUsuario != null) {
				actualizado = usuarioService.actualizarPerfilUsuario(idUsuario, idPerfil, grupos);
			}
			
			if(actualizado.equals(Boolean.TRUE)) {
				resp = perfilService.perfilById(idPerfil);
			}
		} catch(Exception e) {
			throw new ApiException(e);
		}

		return resp;
	}

	@Override
	public ResEstatus actualizarEstatusUsuario(Integer idUsuario, Boolean activo, List<String> grupos) throws ApiException {
		logger.info("actualizarEstatusUsuario");
		
		Boolean actualizado = false;
		ResEstatus resp = null;
		
		try {
			if(activo != null && idUsuario != null) {
				actualizado = usuarioService.actualizarEstatusUsuario(idUsuario,activo, grupos);
			}
			
			if (actualizado.equals(Boolean.TRUE)) {
				
				resp = new ResEstatus();
				resp.setIdUsuario(idUsuario);
				if (Boolean.TRUE.equals(activo)) {
					resp.setDescripcion(DescripcionEnum.ACTIVO);
				} else {
					resp.setDescripcion(DescripcionEnum.INACTIVO);
				}
			}
		} catch(Exception e) {
			throw new ApiException(e);
		}

		return resp;
	}

	@Override
	public EliminarUsuariosRes deleteUsuario(Integer idUsuario, List<String> grupos) throws ApiException {
		logger.info("deleteUsuario");
		
		Boolean eliminado = false;
		EliminarUsuariosRes resp = null;
		try {
			if(idUsuario != null) {
				eliminado = usuarioService.deleteUsuario(idUsuario, grupos);
			}
			if (eliminado.equals(Boolean.TRUE)) {
				resp = new EliminarUsuariosRes();
				resp.setCode(Constantes.SUCESS_CODE);
				resp.setMessage(Constantes.SUCESS_MESSAGE_ELIMINAR_USUARIO);
			}
		} catch(Exception e) {
			throw new ApiException(e);
		}
		
		return resp;
	}

	@Override
	public PerfilUsuario consultarUsuariosConPerfil(Integer id, List<String> grupos) throws ApiException {
		logger.info("consultarUsuariosConPerfil");
		
		PerfilUsuario userVo = null;
		
		try {
			userVo = usuarioService.consultaUsuarioPerfil(id, grupos);
		} catch (Exception e) {
			throw new ApiException(e);
		}
		
		return userVo;
	}
	
	@Override
	public PerfilUsuario consultarUsuariosConPerfil(String usuario) throws ApiException {
		logger.info("consultarUsuariosConPerfil");
		
		PerfilUsuario userVo = null;
		
		try {
			userVo = usuarioService.consultaUsuarioPerfil(usuario);
		} catch (Exception e) {
			throw new ApiException(e);
		}
		
		return userVo;
	}

	@Override
	public PerfilUsuario consultarUsuariosConPerfil(Integer id) throws ApiException {
		logger.info("consultarUsuariosConPerfil");
		
		PerfilUsuario userVo = null;
		
		try {
			userVo = usuarioService.consultaUsuarioPerfil(id);
		} catch (Exception e) {
			throw new ApiException(e);
		}
		
		return userVo;
	}
	
	@Override
	public Boolean validarUsuarioPerfil(String usuario, Integer perfil) throws ApiException {
		logger.info("validarUsuarioPerfil");
		
		Boolean validacion = false;
		try {
			Boolean existeUsuario = usuarioService.consultarUsuario(usuario);
			Boolean existePerfil = perfilService.consultarPerfil(perfil);
			
			if(Boolean.TRUE.equals(existeUsuario) && Boolean.TRUE.equals(existePerfil)) {
				validacion = true;
			}
			
		} catch (Exception e) {
			throw new ApiException(e);
		}

		return validacion;
	}

	@Override
	public CrearHistoricoRes crearHistorico(AccionEnum accion, Date fecha, Integer idPerfil, String usuario)
			throws ApiException {

		logger.info("UsuarioService.crearHistorico");
		CrearHistoricoRes resp = null;
		
		try {
			resp = historicoService.crearHistorico(accion, fecha, idPerfil, usuario);
			
		} catch (Exception e) {
			throw new ApiException(e);
		}
		
		return resp;
	}

	@Override
	public ConsultaHistoricoRes getHistorico(Integer idUsuario) throws ApiException {
		logger.info("getHistorico");
		
		ConsultaHistoricoRes resp = null;
		
		try {
			resp = historicoService.getHistorico(idUsuario);
		} catch (Exception e) {
			throw new ApiException(e);
		}
		
		return resp;
	}

	@Override
	public Boolean validarCapacidadesCreacion(CapacidadUsuariosReq capacidadUsuarioReq) throws ApiException {
		logger.info("validarCapacidadesCreacion");
		
		Boolean validacion = false;
		
		try {
			validacion = capacidadService.validarCapacidadesCreacion(capacidadUsuarioReq);
		} catch (Exception e) {
			throw new ApiException(e);
		}
		
		return validacion;
	}

	@Override
	public CapacidadUsuariosRes crearPerfilCapacidad(Integer idPerfil, CapacidadUsuariosReq informacionPerfil,
			List<String> grupos) throws ApiException {
		logger.info("crearPerfilCapacidad");

		CapacidadUsuariosRes resp = null;
		
		try {
			resp = perfilCapacidadService.crearPerfilCapacidad(idPerfil, informacionPerfil, grupos);
		} catch (Exception e) {
			throw new ApiException(e);
		}

		return resp;
	}

	@Override
	public UsuarioEntity consultarUsuarios(String usuario) throws ApiException {

		UsuarioEntity encontrado = null;
		
		try {
			encontrado = usuarioService.consultarUsuarios(usuario);
		} catch (Exception e) {
			throw new ApiException(e);
		}
		
		return encontrado;
	}
	
}

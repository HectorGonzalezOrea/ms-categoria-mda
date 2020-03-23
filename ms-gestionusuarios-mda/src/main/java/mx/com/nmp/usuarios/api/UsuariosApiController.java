package mx.com.nmp.usuarios.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import mx.com.nmp.usuarios.model.BadRequest;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.ConflictRequest;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.ConsultaUsuarioRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.EliminarUsuariosRes;
import mx.com.nmp.usuarios.model.GeneralResponse;
import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.InternalServerError;
import mx.com.nmp.usuarios.model.InvalidAuthentication;
import mx.com.nmp.usuarios.model.ModCapacidadUsuario;
import mx.com.nmp.usuarios.model.NotFound;
import mx.com.nmp.usuarios.model.PerfilUsuario;
import mx.com.nmp.usuarios.model.ReqEstatus;
import mx.com.nmp.usuarios.model.ReqHistorico;
import mx.com.nmp.usuarios.model.ReqPerfil;
import mx.com.nmp.usuarios.model.ResEstatus;
import mx.com.nmp.usuarios.mongodb.service.UsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-05T00:59:45.211Z")

@Controller
public class UsuariosApiController implements UsuariosApi {

	private static final Logger log = LoggerFactory.getLogger(UsuariosApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;
	
	@Autowired
	private UsuarioService usuarioService;

	@org.springframework.beans.factory.annotation.Autowired
	public UsuariosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#consultaUsuarioGET(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 * GET
	 * /usuarios 
	 * Consulta el listado de los usuarios actuales.
	 */
	public ResponseEntity<?> consultaUsuarioGET(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Nombre del usuario.") @Valid @RequestParam(value = "nombre", required = false) String nombre,
			@ApiParam(value = "Apellido paterno del usuario.") @Valid @RequestParam(value = "apellidoPaterno", required = false) String apellidoPaterno,
			@ApiParam(value = "Apellido materno del usuario.") @Valid @RequestParam(value = "apellidoMaterno", required = false) String apellidoMaterno,
			@ApiParam(value = "Estatus registrado por el administrador.") @Valid @RequestParam(value = "activo", required = false) Boolean activo,
			@ApiParam(value = "Username registrado en el directorio activo.") @Valid @RequestParam(value = "usuario", required = false) String usuario2,
			@ApiParam(value = "Username registrado en el directorio activo.") @Valid @RequestParam(value = "idPerfil", required = false) Integer idPerfil) {
		
		log.info("Consulta Usuario.");
		
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {

				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				log.info("nombre: " + nombre);
				log.info("apellidoPaterno: " + apellidoPaterno);
				log.info("apellidoMaterno: " + apellidoMaterno);
				log.info("activo: " + activo);
				log.info("usuario2: " + usuario2);
				log.info("idPerfil: "+ idPerfil);

				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (nombre == null && apellidoPaterno == null && apellidoMaterno == null && activo == null
						&& usuario2 == null && idPerfil == null) {
					List<InfoUsuario> usuarios = usuarioService.getUsuariosSinFiltro();
					ConsultaUsuarioRes resp = new ConsultaUsuarioRes();

					if (usuarios != null) {
						log.info("Si hubo considencias.");
						resp.setUsuarios(usuarios);
						return new ResponseEntity<ConsultaUsuarioRes>(resp, HttpStatus.OK);
					} else {
						log.info("No concidencias.");
						
						NotFound nf = new NotFound();
						nf.setCodigo("No existe el recurso con la información proporcionada. Verificar y volver a enviar la petición.");
						nf.setMensaje("NMP-MDA-404");
						
						return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
					}
				}

				List<InfoUsuario> usuarios = usuarioService.getUsuarios(nombre, apellidoPaterno, apellidoMaterno,
						activo, usuario2, idPerfil);
				ConsultaUsuarioRes resp = new ConsultaUsuarioRes();

				if (usuarios != null) {
					log.info("Si hubo considencias.");
					resp.setUsuarios(usuarios);
					return new ResponseEntity<ConsultaUsuarioRes>(resp, HttpStatus.OK);
				} else {
					log.info("No concidencias.");
					return new ResponseEntity<ConsultaUsuarioRes>(resp, HttpStatus.OK);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return new ResponseEntity<ConsultaUsuarioRes>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#capacidadUsuarioPOST(java.lang.String, java.lang.String, java.lang.String, java.lang.String, mx.com.nmp.usuarios.model.CapacidadUsuariosReq)
	 * POST
	 * /usuarios/{idPerfil}/capacidades
	 * Capacidades que puede tener un perfil previamente registrado.
	 */
	public ResponseEntity<?> capacidadUsuarioPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del perfil.", required = true) @PathVariable("idPerfil") String idPerfil,
			@ApiParam(value = "") @Valid @RequestBody CapacidadUsuariosReq capacidadUsuarioReq) {
		
		log.info("Agregar Capacidades al Perfil");
		
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				log.info("idPerfil: " + idPerfil);
				
				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
				
				CapacidadUsuariosRes resp = null;
				
				if(idPerfil != null && !capacidadUsuarioReq.isEmpty()) {
					
					InternalServerError ie = usuarioService.validarCapacidadesCreacion(capacidadUsuarioReq);
					
					if(ie != null) {
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					} else {
						resp = usuarioService.crearPerfilCapacidad(new Integer(idPerfil), capacidadUsuarioReq);
					}
				}
				
				if (resp != null) {
					return new ResponseEntity<CapacidadUsuariosRes>(resp, HttpStatus.OK);
				} else {
					InternalServerError ie = new InternalServerError();
					ie.setCodigo("NMP-MDA-500");
					ie.setMensaje("Error interno del servidor");
					
					return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<CapacidadUsuariosRes>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#modCapacidadPOST(java.lang.String, java.lang.String, java.lang.String, java.lang.String, mx.com.nmp.usuarios.model.ModCapacidadUsuario)
	 * PUT
	 * /usuarios/{idPerfil}/capacidades
	 * Modifica las capacidades que puede tener un perfil previamente registrado.
	 */
	public ResponseEntity<?> modCapacidadPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del perfil.", required = true) @PathVariable("idPerfil") String idPerfil,
			@ApiParam(value = "") @Valid @RequestBody ModCapacidadUsuario modCapacidadReq) {
		
		log.info("Modificar las capacidades de un perfil");
		
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				
				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
				
				CapacidadUsuariosRes resp = null;
				
				if(idPerfil != null && !modCapacidadReq.isEmpty()) {
					
					InternalServerError ie = usuarioService.validarCapacidadesMod(modCapacidadReq);
					
					if(ie != null) {
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					} else {
						resp = usuarioService.modificarPerfilCapacidad(new Integer(idPerfil), modCapacidadReq);
					}
				}
				
				if(resp != null) {
					return new ResponseEntity<CapacidadUsuariosRes>(resp, HttpStatus.OK);
				} else {
					InternalServerError ie = new InternalServerError();
					ie.setCodigo("NMP-MDA-500");
					ie.setMensaje("Error interno del servidor");
					
					return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<CapacidadUsuariosRes>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#eliminarUsuariosDELETE(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 * DELETE
	 * /usuarios/{idUsuario}
	 * Elimina a un usuario previamente registrado.
	 */
	public ResponseEntity<?> eliminarUsuariosDELETE(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "identificador para eliminar a un usuario.", required = true) @PathVariable("idUsuario") Integer idUsuario) {
		
		log.info("Eliminar Usuario.");
		
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				log.info("idUsuario: " + idUsuario);

				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (idUsuario == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				} else {
					Boolean eliminado = usuarioService.deleteUsuario(idUsuario);
					EliminarUsuariosRes resp = new EliminarUsuariosRes();

					if (eliminado) {
						resp.setCode("200");
						resp.setMessage("Usuario eliminado exitosamente");
						return new ResponseEntity<EliminarUsuariosRes>(resp, HttpStatus.OK);
					} else {
						resp.setCode("200");
						resp.setMessage("Usuario no eliminado");
						return new ResponseEntity<EliminarUsuariosRes>(resp, HttpStatus.OK);
					}
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<EliminarUsuariosRes>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#estatusUsuariosPUT(java.lang.String, java.lang.String, java.lang.String, java.lang.String, mx.com.nmp.usuarios.model.ReqEstatus)
	 * PUT
	 * /usuarios/{idUsuario}/estatus
	 * Modifica el estatus de un usuario actual.
	 */
	public ResponseEntity<?> estatusUsuariosPUT(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del usuario.", required = true) @PathVariable("idUsuario") String idUsuario,
			@ApiParam(value = "peticion para modificar el estatus de un usuario.") @Valid @RequestBody ReqEstatus modificaEstatusReq) {
		
		log.info("Modificar Usuario Estatus.");

		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {

				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				log.info("idUsuario: " + idUsuario);
				log.info("estatus: " + modificaEstatusReq.isActivo());
				
				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (idUsuario != null && modificaEstatusReq != null) {
					ResEstatus resp = usuarioService.actualizarEstatusUsuario(new Integer(idUsuario), modificaEstatusReq.isActivo());

					if (resp != null) {
						return new ResponseEntity<ResEstatus>(resp, HttpStatus.OK);
					} else {
						InternalServerError ie = new InternalServerError();
						ie.setCodigo("NMP-MDA-500");
						ie.setMensaje("Error interno del servidor");
						
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ResEstatus>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#modificarUsuariosPUT(java.lang.String, java.lang.String, java.lang.String, java.lang.String, mx.com.nmp.usuarios.model.ReqPerfil)
	 * PUT
	 * /usuarios/{idUsuario}/perfil
	 * Modifica el perfil de un usuario actual.
	 */
	public ResponseEntity<?> modificarUsuariosPUT(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del usuario.", required = true) @PathVariable("idUsuario") String idUsuario,
			@ApiParam(value = "petición para modificar el perfil a un usuario.") @Valid @RequestBody ReqPerfil modificarPerfilReq) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				
				CapacidadUsuariosRes resp = null;
				
				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				
				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
				
				if(idUsuario != null && modificarPerfilReq.getIdPerfil() != null) {
					resp = usuarioService.actualizarPerfilUsuario(new Integer(idUsuario), modificarPerfilReq.getIdPerfil());
				} else {
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
				
				if(resp != null) {
					return new ResponseEntity<CapacidadUsuariosRes>(resp, HttpStatus.OK);
				} else {
					InternalServerError ie = new InternalServerError();
					ie.setCodigo("NMP-MDA-500");
					ie.setMensaje("Error interno del servidor");
					
					return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				/*
				return new ResponseEntity<CapacidadUsuariosRes>(objectMapper.readValue(
						"{  \"idPerfil\" : 3,  \"descripcionPerfil\" : \"Consultor\",  \"informacionPerfil\" : \"\"}",
						CapacidadUsuariosRes.class), HttpStatus.NOT_IMPLEMENTED);
				*/
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<CapacidadUsuariosRes>(HttpStatus.NOT_IMPLEMENTED);
	}
	

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#historialUsuarioGET(String, String, String, Integer)
	 * GET
	 * /usuarios/historico
	 * Consulta el historial de un usuario en el portal.
	 */
	public ResponseEntity<?> historialUsuarioGET(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@NotNull @ApiParam(value = "identificador del usuario.", required = true) @Valid @RequestParam(value = "idUsuario", required = true) Integer idUsuario) {
		log.info("Get Historial");

		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {

				ConsultaHistoricoRes resp = null;
				
				if (usuario == null || destino == null || origen == null) {
					log.info("usuario: " + usuario);
					log.info("origen: " + origen);
					log.info("destino: " + destino);
					
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (idUsuario != null) {
					
					resp = usuarioService.getHistorico(idUsuario);
					
					if(resp != null) {
						return new ResponseEntity<ConsultaHistoricoRes>(resp, HttpStatus.OK);
					} else {
						return new ResponseEntity<ConsultaHistoricoRes>(resp, HttpStatus.OK);
					}
				} else {
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ConsultaHistoricoRes>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#historialUsuarioPOST(String, String, String, ReqHistorico)
	 * POST
	 * /usuarios/historico
	 * Creación del historico de Usuarios
	 */
	public ResponseEntity<?> historialUsuarioPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "peticion para crear el registro histórico de un usuario en el portal.") @Valid @RequestBody ReqHistorico historicoEnvioReq) {
		log.info("Agregar historico");

		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {

				if (usuario == null || destino == null || origen == null) {
					log.info("usuario: " + usuario);
					log.info("origen: " + origen);
					log.info("destino: " + destino);
					
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (historicoEnvioReq != null) {
					log.info("Perfil: " + historicoEnvioReq.getIdPerfil());
					log.info("Usuario: " + historicoEnvioReq.getUsuario());
					log.info("Accion: " + historicoEnvioReq.getAccion());
					log.info("Fecha: " + historicoEnvioReq.getFecha());
					
					CrearHistoricoRes resp = usuarioService.crearHistorico(historicoEnvioReq.getAccion(), historicoEnvioReq.getFecha(), historicoEnvioReq.getIdPerfil(), historicoEnvioReq.getUsuario());
					
					if(resp != null) {
						return new ResponseEntity<CrearHistoricoRes>(resp, HttpStatus.OK);
					} else {
						InternalServerError ie = new InternalServerError();
						ie.setCodigo("NMP-MDA-500");
						ie.setMensaje("Error interno del servidor");
						
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<CrearHistoricoRes>(HttpStatus.NOT_IMPLEMENTED);
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#usuariosPerfilGet(String, String, String)
	 * GET
	 * /usuarios/perfil
	 * Consulta Perfil
	 */
    public ResponseEntity<?> usuariosPerfilGet(@ApiParam(value = "Usuario en el sistema origen que lanza la petición." ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición." ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información." ,required=true, allowableValues="Mongo, mockserver") @RequestHeader(value="destino", required=true) String destino) {
    	log.info("Consultar Perfil Usuario");
    	
    	String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {

            	if (usuario == null || destino == null || origen == null) {
					log.info("usuario: " + usuario);
					log.info("origen: " + origen);
					log.info("destino: " + destino);
					
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}

            	if(usuario != null && !usuario.equals("")) {
            		PerfilUsuario resp = usuarioService.consultaPrefil(usuario);
            		
            		if(resp != null) {
            			return new ResponseEntity<PerfilUsuario>(resp, HttpStatus.OK);
            		} else {
            			InternalServerError ie = new InternalServerError();
        				ie.setCodigo("NMP-MDA-500");
        				ie.setMensaje("Error interno del servidor");
        				
            			return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            		}
            	}
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                
                InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
                
                return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<PerfilUsuario>(HttpStatus.NOT_IMPLEMENTED);
    }

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#usuariosPerfilGet(String, String, String, InfoUsuario)
	 * POST
	 * /usuarios
	 * Crear Usuario
	 */
    public ResponseEntity<?> usuariosPost(@ApiParam(value = "Usuario en el sistema origen que lanza la petición." ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición." ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información." ,required=true, allowableValues="Mongo, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = ""  )  @Valid @RequestBody InfoUsuario peticion) {
    	log.info("Crear Usuario");
    	
    	String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	
            	if (usuario == null || destino == null || origen == null) {
					log.info("usuario: " + usuario);
					log.info("origen: " + origen);
					log.info("destino: " + destino);
					
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
            	
            	if(peticion != null) {
            		
            		log.info("peticion: " + peticion.toString());
            		
            		Boolean insertado = usuarioService.crearUsuario(peticion);
            		
            		if(insertado) {
            			GeneralResponse resp =  new GeneralResponse();
            			resp.setMessage("Usuario creado correctamente.");
            			
            			return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
            		} else {
            			InternalServerError ie = new InternalServerError();
        				ie.setCodigo("NMP-MDA-500");
        				ie.setMensaje("Error interno del servidor");
                        
                        return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            		}
            		
            	} else {
            		BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
                
                return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
	
}

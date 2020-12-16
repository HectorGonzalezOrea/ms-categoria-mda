package mx.com.nmp.usuarios.api;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import mx.com.nmp.usuarios.model.BadRequest;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
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
import mx.com.nmp.usuarios.oag.client.service.OAGBaseService;
import mx.com.nmp.usuarios.oag.client.service.OAGService;
import mx.com.nmp.usuarios.oag.vo.TokenProviderErrorVO;

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

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import mx.com.nmp.usuarios.utils.Constantes;
import mx.com.nmp.usuarios.utils.ConverterUtil;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-05T00:59:45.211Z")

@Controller
public class UsuariosApiController implements UsuariosApi {

	private static final Logger log = LoggerFactory.getLogger(UsuariosApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private OAGService oagService;

	@org.springframework.beans.factory.annotation.Autowired
	public UsuariosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
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
		
		log.info("*********************************************************");
		log.info("Consulta Usuario.");
		log.info("*********************************************************");
		
		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
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
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
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
            			nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
        				nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND);
        				
        				log.info("{}" , ConverterUtil.messageToJson(nf));
        				
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
					NotFound nf = new NotFound();
        			nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
    				nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND);
    				
    				log.info("{}" , ConverterUtil.messageToJson(nf));
    				
        			return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
				
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			BadRequest br = new BadRequest();
			br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
			log.info("{}" , ConverterUtil.messageToJson(br));
			
			return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#capacidadUsuarioPOST(java.lang.String, java.lang.String, java.lang.String, java.lang.String, mx.com.nmp.usuarios.model.CapacidadUsuariosReq)
	 * POST
	 * /perfil/{idPerfil}/capacidades
	 * Capacidades que puede tener un perfil previamente registrado.
	 */
	public ResponseEntity<?> capacidadUsuarioPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del perfil.", required = true) @PathVariable("idPerfil") String idPerfil,
			@ApiParam(value = "") @Valid @RequestBody CapacidadUsuariosReq capacidadUsuarioReq) {
		
		log.info("*********************************************************");
		log.info("Agregar Capacidades al Perfil");
		log.info("*********************************************************");
		
		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				log.info("idPerfil: " + idPerfil);
				
				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
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
					ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
    				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
					
    				log.info("{}" , ConverterUtil.messageToJson(ie));
    				
					return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo("NMP-MDA-500");
				ie.setMensaje("Error interno del servidor");
				
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			BadRequest br = new BadRequest();
			br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
			log.info("{}" , ConverterUtil.messageToJson(br));
			
			return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		}
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
		
		log.info("*********************************************************");
		log.info("Modificar las capacidades de un perfil");
		log.info("*********************************************************");
		
		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				
				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
				
				CapacidadUsuariosRes resp = null;
				
				if(idPerfil != null && !modCapacidadReq.isEmpty()) {
					
					log.info("Request : {}" , modCapacidadReq.toString());
					
					InternalServerError ie = usuarioService.validarCapacidadesMod(modCapacidadReq);
					
					if(ie != null) {
						
						log.info("{}" , ConverterUtil.messageToJson(ie));
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					} else {
						resp = usuarioService.modificarPerfilCapacidad(new Integer(idPerfil), modCapacidadReq);
					}
				}
				
				if(resp != null) {
					return new ResponseEntity<CapacidadUsuariosRes>(resp, HttpStatus.OK);
				} else {
					InternalServerError ie = new InternalServerError();
					ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
    				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
					
    				log.info("{}" , ConverterUtil.messageToJson(ie));
    				
					return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
				
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			BadRequest br = new BadRequest();
			br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
			log.info("{}" , ConverterUtil.messageToJson(br));
			
			return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		}
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
		
		log.info("*********************************************************");
		log.info("Eliminar Usuario.");
		log.info("*********************************************************");
		
		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				log.info("idUsuario: " + idUsuario);

				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (idUsuario == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				} else {
					
					if(Boolean.TRUE.equals(usuarioService.consultarIdUsuario(idUsuario))) {
						Boolean eliminado = usuarioService.deleteUsuario(idUsuario);
						EliminarUsuariosRes resp = new EliminarUsuariosRes();

						if (eliminado) {
							resp.setCode(Constantes.SUCESS_CODE);
							resp.setMessage(Constantes.SUCESS_MESSAGE_ELIMINAR_USUARIO);
							return new ResponseEntity<EliminarUsuariosRes>(resp, HttpStatus.OK);
						} else {
							InternalServerError ie = new InternalServerError();
							ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
							ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
							
							log.info("{}" , ConverterUtil.messageToJson(ie));
							
							return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
						}
					} else {
						NotFound nf = new NotFound();
						nf.codigo(Constantes.ERROR_CODE_NOT_FOUND);
						nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND_USUARIO);
						
						log.info("{}" , ConverterUtil.messageToJson(nf));
						
						return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
					}
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
				
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			BadRequest br = new BadRequest();
			br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
			log.info("{}" , ConverterUtil.messageToJson(br));
			
			return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		}
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
		
		log.info("*********************************************************");
		log.info("Modificar Usuario Estatus.");
		log.info("*********************************************************");
		
		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {

				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				log.info("idUsuario: " + idUsuario);
				log.info("estatus: " + modificaEstatusReq.isActivo());
				
				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (idUsuario != null && modificaEstatusReq != null) {
					ResEstatus resp = usuarioService.actualizarEstatusUsuario(new Integer(idUsuario), modificaEstatusReq.isActivo());

					if (resp != null) {
						return new ResponseEntity<ResEstatus>(resp, HttpStatus.OK);
					} else {
						InternalServerError ie = new InternalServerError();
						ie.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
						ie.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND_USUARIO);
						
						log.info("{}" , ConverterUtil.messageToJson(ie));
						
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
				
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			BadRequest br = new BadRequest();
			br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
			log.info("{}" , ConverterUtil.messageToJson(br));
			
			return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#modificarUsuariosPUT(java.lang.String, java.lang.String, java.lang.String, java.lang.String, mx.com.nmp.usuarios.model.ReqPerfil)
	 * PUT
	 * /perfil/{idUsuario}/perfil
	 * Modifica el perfil de un usuario actual.
	 */
	public ResponseEntity<?> modificarUsuariosPUT(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del usuario.", required = true) @PathVariable("idUsuario") String idUsuario,
			@ApiParam(value = "petición para modificar el perfil a un usuario.") @Valid @RequestBody ReqPerfil modificarPerfilReq) {
		
		log.info("*********************************************************");
		log.info("Modificar Perfil del usuario actual");
		log.info("*********************************************************");
		
    	String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				
				CapacidadUsuariosRes resp = null;
				
				log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				
				if (usuario == null || origen == null || destino == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
				
				if(idUsuario != null && modificarPerfilReq.getIdPerfil() != null) {
					resp = usuarioService.actualizarPerfilUsuario(new Integer(idUsuario), modificarPerfilReq.getIdPerfil());
				} else {
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
				
				if(resp != null) {
					return new ResponseEntity<CapacidadUsuariosRes>(resp, HttpStatus.OK);
				} else {
					InternalServerError ie = new InternalServerError();
					ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
    				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
					
    				log.info("{}" , ConverterUtil.messageToJson(ie));
    				
					return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
				
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			BadRequest br = new BadRequest();
			br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
			log.info("{}" , ConverterUtil.messageToJson(br));
			
			return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		}
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
		
		log.info("*********************************************************");
		log.info("Get Historial");
		log.info("*********************************************************");
		
    	String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {

				ConsultaHistoricoRes resp = null;
				
				if (usuario == null || destino == null || origen == null) {
					log.info("usuario: " + usuario);
					log.info("origen: " + origen);
					log.info("destino: " + destino);
					
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (idUsuario != null) {
					
					resp = usuarioService.getHistorico(idUsuario);
					
					if(resp != null) {
						return new ResponseEntity<ConsultaHistoricoRes>(resp, HttpStatus.OK);
					}
				} else {
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
				
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
		br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
		
		log.info("{}" , ConverterUtil.messageToJson(br));
		
		return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		
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
		
		log.info("*********************************************************");
		log.info("Agregar historico");
		log.info("*********************************************************");

    	String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {

				if (usuario == null || destino == null || origen == null) {
					log.info("usuario: " + usuario);
					log.info("origen: " + origen);
					log.info("destino: " + destino);
					
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}

				if (historicoEnvioReq != null) {
					log.info("Perfil: " + historicoEnvioReq.getIdPerfil());
					log.info("Usuario: " + historicoEnvioReq.getUsuario());
					log.info("Accion: " + historicoEnvioReq.getAccion());
					log.info("Fecha: " + historicoEnvioReq.getFecha());
					
					if(Boolean.TRUE.equals(usuarioService.consultarUsuario(historicoEnvioReq.getUsuario())) && Boolean.TRUE.equals(usuarioService.consultarPerfil(historicoEnvioReq.getIdPerfil()))) {
						CrearHistoricoRes resp = usuarioService.crearHistorico(historicoEnvioReq.getAccion(), historicoEnvioReq.getFecha(), historicoEnvioReq.getIdPerfil(), historicoEnvioReq.getUsuario());
						
						if(resp != null) {
							return new ResponseEntity<CrearHistoricoRes>(resp, HttpStatus.OK);
						} else {
							InternalServerError ie = new InternalServerError();
							ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
	        				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
							
	        				log.info("{}" , ConverterUtil.messageToJson(ie));
	        				
							return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
						}
					} else {
						InternalServerError ie = new InternalServerError();
						ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
						ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR_PERFIL_USUARIO);
						
						log.info("{}" , ConverterUtil.messageToJson(ie));
						
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
				
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			BadRequest br = new BadRequest();
			br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
			log.info("{}" , ConverterUtil.messageToJson(br));
			
			return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#usuariosPerfilGet(String, String, String)
	 * GET
	 * /usuarios/perfil
	 * Consulta Perfil
	 */
	public ResponseEntity<?> usuariosPerfilGet(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = false) @RequestHeader(value = "usuario", required = false) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@RequestHeader(value = "oauth_bearer", required = true) String oauth_bearer) {
		
		log.info("*********************************************************");
    	log.info("Consultar Perfil Usuario");
    	log.info("*********************************************************");
    	
    	String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
    	
    	String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
            try {
            	
            	log.info("usuario: " + usuario);
				log.info("origen: " + origen);
				log.info("destino: " + destino);
				log.info("oauth_bearer: " + oauth_bearer);
            	
            	if (destino == null || origen == null  || oauth_bearer == null) {
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}

            	if(oauth_bearer != null && !oauth_bearer.equals("")) {
            		Object obj =  usuarioService.consultaPrefilByToken(oauth_bearer);
            		
            		if(obj != null) {
            			if(obj instanceof PerfilUsuario) {
            				
            				PerfilUsuario resp = (PerfilUsuario) obj;
            				log.info("{}" , resp);
                			return new ResponseEntity<PerfilUsuario>(resp, HttpStatus.OK);
            			} else if(obj instanceof TokenProviderErrorVO) {
            				
            				TokenProviderErrorVO tpeVo = (TokenProviderErrorVO) obj;
            				
            				InternalServerError ie = new InternalServerError();
                            ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
            				ie.setMensaje(tpeVo.getDescripcionError());
                            
            				log.info("{}" , ConverterUtil.messageToJson(ie));
            				
                            return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            			}
            		} else {
            			NotFound nf = new NotFound();
            			nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
        				nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND);
        				
        				log.info("{}" , ConverterUtil.messageToJson(nf));
        				
            			return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
            		}
            	}
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                
                InternalServerError ie = new InternalServerError();
                ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
                
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
                return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        
        BadRequest br = new BadRequest();
		br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
		br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
		log.info("{}" , ConverterUtil.messageToJson(br));
		
		return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
    }

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#usuariosPerfilGet(String, String, String, InfoUsuario)
	 * POST
	 * /usuarios
	 * Crear Usuario
	 */
	public ResponseEntity<?> usuariosPost(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "") @Valid @RequestBody InfoUsuario peticion) {
    	
		log.info("*********************************************************");
		log.info("Crear Usuario");
		log.info("*********************************************************");
		
    	String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
    	
    	String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
            try {
            	
            	if (usuario == null || destino == null || origen == null) {
					log.info("usuario: " + usuario);
					log.info("origen: " + origen);
					log.info("destino: " + destino);
					
					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
            	
            	if(peticion != null) {
            		
            		log.info("peticion: " + peticion.toString());
            		
            		if(Boolean.TRUE.equals(usuarioService.validarUsuarioAdmin(peticion.getPerfil()))) {
                        InternalServerError ie = new InternalServerError();
        				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
        				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR_ADMIN);
                        
        				log.info("{}" , ConverterUtil.messageToJson(ie));
        				
                        return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);

            			
            		} else {
            			Boolean insertado = usuarioService.crearUsuario(peticion);
                		
                		if(insertado) {
                			GeneralResponse resp =  new GeneralResponse();
                			resp.setMessage("Usuario creado correctamente.");
                			
                			return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
                		} else {
                			InternalServerError ie = new InternalServerError();
                			ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
            				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
                            
            				log.info("{}" , ConverterUtil.messageToJson(ie));
            				
                            return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
                		}
            		}
            	} else {
            		BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					
					log.info("{}" , ConverterUtil.messageToJson(br));
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                InternalServerError ie = new InternalServerError();
				ie.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ie.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
                
				log.info("{}" , ConverterUtil.messageToJson(ie));
				
                return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
			BadRequest br = new BadRequest();
			br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			
			log.info("{}" , ConverterUtil.messageToJson(br));
			
			return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
		}
    }

	public ResponseEntity<?> usuariosSincronizarPost(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición." ,required=true)@RequestHeader("usuario") String usuario
			,@ApiParam(value = "Sistema que origina la petición." ,required=true, allowableValues="portalMotorDescuentosAutomatizados")@RequestHeader("origen") String origen
			,@ApiParam(value = "Destino final de la información." ,required=true, allowableValues="Mongo, mockserver")@RequestHeader("destino") String destino
			,@ApiParam(value = "Grupo que se sincronizara.",required=true) @RequestHeader("grupo") String grupo) {
		log.info("*********************************************************");
		log.info("Sincronizar Usuarios");
		log.info("*********************************************************");
		
    	String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ConverterUtil.messageToJson(ia));
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
    	
    	String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
        	log.info("Entrando a migrar usuarios");
        	log.info(usuario);
        	log.info(origen);
        	log.info(destino);
        	log.info(grupo);
    		usuarioService.consultaPrefil(usuario, oagService.getToken());
    		log.info("saliendo a migrar usuarios");
        }
		
		return null;
	}
	
	
}

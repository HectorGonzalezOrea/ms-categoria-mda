package mx.com.nmp.usuarios.api;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import mx.com.nmp.usuarios.api.business.GestionUsuarios;
import mx.com.nmp.usuarios.api.exception.ApiException;
import mx.com.nmp.usuarios.model.BadRequest;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
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
import mx.com.nmp.usuarios.mongodb.service.PerfilCapacidadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	private PerfilCapacidadService perfilCapService;

	@Autowired
	private GestionUsuarios gestionUsuarios; 
	
	@org.springframework.beans.factory.annotation.Autowired
	public UsuariosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
		this.objectMapper = objectMapper;
		this.request = request;
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
					
					InternalServerError ie = perfilCapService.validarCapacidadesCreacion(capacidadUsuarioReq);
					
					if(ie != null) {
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					} else {
						resp = perfilCapService.crearPerfilCapacidad(new Integer(idPerfil), capacidadUsuarioReq);
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
					
					InternalServerError ie = perfilCapService.validarCapacidadesMod(modCapacidadReq);
					
					if(ie != null) {
						
						log.info("{}" , ConverterUtil.messageToJson(ie));
						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					} else {
						resp = perfilCapService.modificarPerfilCapacidad(new Integer(idPerfil), modCapacidadReq);
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
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#historialUsuarioGET(String, String, String, Integer)
	 * GET
	 * /usuarios/historico
	 * Consulta el historial de un usuario en el portal.
	 */
	@GetMapping(value = "/usuarios/historico", produces = { "application/json" })
	public ResponseEntity<?> historialUsuarioGET(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "APiKey.", required = true) @RequestHeader(value =Constantes.HEADER_APIKEY_KEY, required = true) String apikey,

			@NotNull @ApiParam(value = "identificador del usuario.", required = true) @Valid @RequestParam(value = "idUsuario", required = true) Integer idUsuario)
			throws ApiException, MissingServletRequestParameterException {

		log.info("*********************************************************");
		log.info("Get Historial");
		log.info("*********************************************************");

		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {

			ConsultaHistoricoRes resp = gestionUsuarios.getHistorico(idUsuario);

			if (resp != null) {
				return new ResponseEntity<ConsultaHistoricoRes>(resp, HttpStatus.OK);
			}
		}

		throw new MissingServletRequestParameterException(Constantes.HEADER_ACCEPT_KEY, "String");

	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#historialUsuarioPOST(String, String, String, ReqHistorico)
	 * POST
	 * /usuarios/historico
	 * Creación del historico de Usuarios
	 */
	@Override
	@PostMapping(value = "/usuarios/historico", produces = { "application/json" })
	public ResponseEntity<?> historialUsuarioPOST(
		@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
		@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
		@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
		@ApiParam(value = "APiKey.", required = true) @RequestHeader(value = Constantes.HEADER_APIKEY_KEY, required = true) String apikey,

		@ApiParam(value = "peticion para crear el registro histórico de un usuario en el portal.") @Valid @RequestBody ReqHistorico historicoEnvioReq) throws ApiException, MissingServletRequestParameterException {

		log.info("*********************************************************");
		log.info("Agregar historico");
		log.info("*********************************************************");

		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {

			if (historicoEnvioReq != null) {
				log.info("Perfil: " + historicoEnvioReq.getIdPerfil());
				log.info("Usuario: " + historicoEnvioReq.getUsuario());
				log.info("Accion: " + historicoEnvioReq.getAccion());
				log.info("Fecha: " + historicoEnvioReq.getFecha());

				if (Boolean.TRUE.equals(gestionUsuarios.validarUsuarioPerfil(historicoEnvioReq.getUsuario(),
						historicoEnvioReq.getIdPerfil()))) {
					
					CrearHistoricoRes resp = gestionUsuarios.crearHistorico(historicoEnvioReq.getAccion(),
							historicoEnvioReq.getFecha(), historicoEnvioReq.getIdPerfil(),
							historicoEnvioReq.getUsuario());

					if (resp != null) {
						return new ResponseEntity<CrearHistoricoRes>(resp, HttpStatus.OK);
					}

					BadRequest br = new BadRequest();
					br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);

					log.info("{}", ConverterUtil.messageToJson(br));

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
				
				BadRequest br = new BadRequest();
				br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
				br.setCodigo("Datos no existentes para el historial");

				log.info("{}", ConverterUtil.messageToJson(br));

				return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				
			} else {
				BadRequest br = new BadRequest();
				br.setMensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
				br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);

				log.info("{}", ConverterUtil.messageToJson(br));

				return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
			}

		}
		throw new MissingServletRequestParameterException(Constantes.HEADER_ACCEPT_KEY, "String");
	}

	
	@Override
	@PostMapping(value = "/usuarios/sincronizar", produces = {"application/json" })
	public ResponseEntity<?> usuariosSincronizarPost(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required=true) @RequestHeader("usuario") String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader("origen") String origen,
			@ApiParam(value = "Destino final de la información.", required=true, allowableValues="Mongo, mockserver") @RequestHeader("destino") String destino,
			@ApiParam(value = "APiKey.", required=true) @RequestHeader(Constantes.HEADER_APIKEY_KEY) String apikey,
			
			@ApiParam(value = "Grupo que se sincronizara.",required=true) @RequestParam("grupo") String grupo) throws ApiException, MissingServletRequestParameterException {
		
		log.info("*********************************************************");
		log.info("Sincronizar Usuarios");
		log.info("*********************************************************");
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			log.info("usuario: {}" , usuario);
			log.info("origen: {}" , origen);
			log.info("destino: {}" , destino);
        	log.info("grupo: {}" , grupo);
    		
    		gestionUsuarios.sincronizarUsuarios(grupo);
    		
    		GeneralResponse gr = new GeneralResponse();
    		gr.setMessage("Exitoso");
    		
    		log.info("{}" , ConverterUtil.messageToJson(gr));
    		
    		return new ResponseEntity<GeneralResponse>(gr, HttpStatus.OK); 
        }
    	
        throw new MissingServletRequestParameterException(Constantes.HEADER_ACCEPT_KEY, "String");
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#consultaUsuarioGET(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 * GET
	 * /usuarios 
	 * Consulta el listado de los usuarios actuales.
	 */
	@Override
	@GetMapping(value = "/usuarios", produces = { "application/json" })
	public ResponseEntity<?> consultaUsuarioGET(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "APiKey.", required=true) @RequestHeader(Constantes.HEADER_APIKEY_KEY) String apikey,
			
			@ApiParam(value = "Nombre del usuario.") @Valid @RequestParam(value = "nombre", required = false) String nombre,
			@ApiParam(value = "Apellido paterno del usuario.") @Valid @RequestParam(value = "apellidoPaterno", required = false) String apellidoPaterno,
			@ApiParam(value = "Apellido materno del usuario.") @Valid @RequestParam(value = "apellidoMaterno", required = false) String apellidoMaterno,
			@ApiParam(value = "Estatus registrado por el administrador.") @Valid @RequestParam(value = "activo", required = false) Boolean activo,
			@ApiParam(value = "Username registrado en el directorio activo.") @Valid @RequestParam(value = "usuario", required = false) String usuario2,
			@ApiParam(value = "Perfil del usuario registrado en Mongo.") @Valid @RequestParam(value = "idPerfil", required = false) Integer idPerfil) throws ApiException, MissingServletRequestParameterException {
		
		log.info("*********************************************************");
		log.info("Consulta Usuario.");
		log.info("*********************************************************");
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			log.info("usuario: {}" , usuario);
			log.info("origen: {}" , origen);
			log.info("destino: {}" , destino);
			
			log.info("nombre: {}" , nombre);
			log.info("apellidoPaterno: {}" , apellidoPaterno);
			log.info("apellidoMaterno: {}" , apellidoMaterno);
			log.info("activo: {}" , activo);
			log.info("usuario2: {}" , usuario2);
			log.info("idPerfil: {}" , idPerfil);
			
			List<InfoUsuario> usuarios = gestionUsuarios.consultarUsuarios(nombre, apellidoPaterno, apellidoMaterno, activo, idPerfil, usuario2);
			
			if(usuarios.size() != 0) {
				return new ResponseEntity<List<InfoUsuario>>(usuarios, HttpStatus.OK); 
			}
			
			NotFound nf = new NotFound();
			nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
			nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND);
			
			return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
		}
		throw new MissingServletRequestParameterException(Constantes.HEADER_ACCEPT_KEY, "String");
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#modificarUsuariosPUT(java.lang.String, java.lang.String, java.lang.String, java.lang.String, mx.com.nmp.usuarios.model.ReqPerfil)
	 * PUT
	 * /perfil/{idUsuario}/perfil
	 * Modifica el perfil de un usuario actual.
	 */
	@Override
	@PutMapping(value = "/usuarios/{idUsuario}/perfil", produces = {"application/json" })
	public ResponseEntity<?> modificarUsuariosPUT(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "APiKey.", required=true) @RequestHeader(Constantes.HEADER_APIKEY_KEY) String apikey,
			
			@ApiParam(value = "Identificador del usuario.", required = true) @PathVariable(value = "idUsuario", required = true) Integer idUsuario,
			@ApiParam(value = "petición para modificar el perfil a un usuario.") @Valid @RequestBody ReqPerfil modificarPerfilReq) throws ApiException, MissingServletRequestParameterException {
		
		log.info("*********************************************************");
		log.info("Modifica el perfil de un usuario actual.");
		log.info("*********************************************************");
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			
			log.info("usuario: {}" , usuario);
			log.info("origen: {}" , origen);
			log.info("destino: {}" , destino);
			
			log.info("idUsuario: {}" , idUsuario);
			log.info("modificarPerfilReq: {}" , modificarPerfilReq);
			
			CapacidadUsuariosRes resp = gestionUsuarios.actualizarPerfilUsuario(idUsuario, modificarPerfilReq.getIdPerfil());
			
			if(resp != null) {
				return new ResponseEntity<CapacidadUsuariosRes>(resp, HttpStatus.OK);
			}
			
			NotFound nf = new NotFound();
			nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
			nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND);
			
			return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
			
		}
		throw new MissingServletRequestParameterException(Constantes.HEADER_ACCEPT_KEY, "String");

	}
	
	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#estatusUsuariosPUT(java.lang.String, java.lang.String, java.lang.String, java.lang.String, mx.com.nmp.usuarios.model.ReqEstatus)
	 * PUT
	 * /usuarios/{idUsuario}/estatus
	 * Modifica el estatus de un usuario actual.
	 */
	@Override
	@PutMapping(value = "/usuarios/{idUsuario}/estatus", produces = { "application/json" })
	public ResponseEntity<?> estatusUsuariosPUT(String usuario, String origen, String destino, String apikey,
			Integer idUsuario, @Valid ReqEstatus modificaEstatusReq)
			throws ApiException, MissingServletRequestParameterException {
		
		log.info("*********************************************************");
		log.info("Modificar Usuario Estatus.");
		log.info("*********************************************************");
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			log.info("usuario: " + usuario);
			log.info("origen: " + origen);
			log.info("destino: " + destino);
			
			log.info("idUsuario: " + idUsuario);
			log.info("estatus: " + modificaEstatusReq.isActivo());
			
			ResEstatus resp = gestionUsuarios.actualizarEstatusUsuario(idUsuario, modificaEstatusReq.isActivo());
			
			if(resp != null) {
				return new ResponseEntity<ResEstatus>(resp, HttpStatus.OK);
			}
			
			NotFound nf = new NotFound();
			nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
			nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND);
			
			return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
			
		}
		throw new MissingServletRequestParameterException(Constantes.HEADER_ACCEPT_KEY, "String");
	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#eliminarUsuariosDELETE(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 * DELETE
	 * /usuarios/{idUsuario}
	 * Elimina a un usuario previamente registrado.
	 */
	@Override
	@DeleteMapping(value = "/usuarios/{idUsuario}", produces = { "application/json" })
	public ResponseEntity<?> eliminarUsuariosDELETE(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "APiKey.", required=true) @RequestHeader(Constantes.HEADER_APIKEY_KEY) String apikey,
			@ApiParam(value = "identificador para eliminar a un usuario.", required = true) @PathVariable("idUsuario") Integer idUsuario) throws ApiException, MissingServletRequestParameterException {
		
		log.info("*********************************************************");
		log.info("Eliminar Usuario.");
		log.info("*********************************************************");
		
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			log.info("usuario: " + usuario);
			log.info("origen: " + origen);
			log.info("destino: " + destino);
			
			log.info("idUsuario: " + idUsuario);
			
			EliminarUsuariosRes resp = gestionUsuarios.deleteUsuario(idUsuario);
			
			if(resp != null) {
				return new ResponseEntity<EliminarUsuariosRes>(resp, HttpStatus.OK);
			}
			
			NotFound nf = new NotFound();
			nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
			nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND);
			
			return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
			
		}
		throw new MissingServletRequestParameterException(Constantes.HEADER_ACCEPT_KEY, "String");

	}

	/*
	 * (non-Javadoc)
	 * @see mx.com.nmp.usuarios.api.UsuariosApi#usuariosPerfilGet(String, String, String)
	 * GET
	 * /usuarios/perfil
	 * Consulta Perfil
	 */
	@Override
	@GetMapping(value = "/usuarios/perfil", produces = { "application/json" })
	public ResponseEntity<?> usuariosPerfilGet(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = false) @RequestHeader(value = "usuario", required = false) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "APiKey.", required=true) @RequestHeader(Constantes.HEADER_APIKEY_KEY) String apikey) throws ApiException, MissingServletRequestParameterException {
		
		log.info("*********************************************************");
    	log.info("Consultar Perfil Usuario");
    	log.info("*********************************************************");
    	
    	String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
        	log.info("usuario: " + usuario);
			log.info("origen: " + origen);
			log.info("destino: " + destino);
			
			PerfilUsuario user = gestionUsuarios.consultarUsuariosConPerfil(usuario);
			
			if(user != null) {
				return new ResponseEntity<PerfilUsuario>(user, HttpStatus.OK);
			}
			
			NotFound nf = new NotFound();
			nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
			nf.setMensaje(Constantes.ERROR_MESSAGE_NOT_FOUND);
			
			return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
        }
		
        throw new MissingServletRequestParameterException(Constantes.HEADER_ACCEPT_KEY, "String");
	}
	
}

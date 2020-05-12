package mx.com.nmp.consolidados.api;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import mx.com.nmp.consolidados.constantes.Constantes.Common;
import mx.com.nmp.consolidados.model.BadRequest;
import mx.com.nmp.consolidados.model.Consolidados;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoRes;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoResInner;
import mx.com.nmp.consolidados.model.GeneralResponse;
import mx.com.nmp.consolidados.model.InlineResponse200;
import mx.com.nmp.consolidados.model.InternalServerError;
import mx.com.nmp.consolidados.model.ModificarPrioridadArchivoConsolidadoReq;
import mx.com.nmp.consolidados.model.NotFound;
import mx.com.nmp.consolidados.model.SuccessfulResponse;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
import mx.com.nmp.consolidados.ms.service.ConsolidadoService;
import mx.com.nmp.consolidados.model.InvalidAuthentication;

import static mx.com.nmp.consolidados.utils.Constantes.HEADER_ACCEPT;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_APP_JSON;
import static mx.com.nmp.consolidados.utils.Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION;
import static mx.com.nmp.consolidados.utils.Constantes.ERROR_CODE_INVALID_AUTHENTICATION;
import static mx.com.nmp.consolidados.utils.Constantes.HEADER_APIKEY_KEY;
import static mx.com.nmp.consolidados.utils.Constantes.CSV_MIN;
import static mx.com.nmp.consolidados.utils.Constantes.CSV_MAY;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

@Controller
public class ConsolidadosApiController implements ConsolidadosApi {

	private static final Logger log = LoggerFactory.getLogger(ConsolidadosApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private ConsolidadoService consolidadoService; 

    @org.springframework.beans.factory.annotation.Autowired
    public ConsolidadosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
    	objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
        this.objectMapper = objectMapper;
        this.request = request;
    }

    /*
     * Actualizar prioridad de ejecución del archivo
     */
	public ResponseEntity<?> actualizarPosicionArchivoPUT(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petici\u00F3n", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petici\u00F3n", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la informaci\u00F3n", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del archivo", required = true) @PathVariable("idArchivo") String idArchivo,
			@ApiParam(value = "peticion para modificar la posicion de un archivo") @Valid @RequestBody ModificarPrioridadArchivoConsolidadoReq modificarPosicionReq) {

		log.info("Actualizar la posición de los archivos consolidados.");

		String apiKeyBluemix = request.getHeader(HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
			ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(HEADER_ACCEPT);
		if (accept != null && accept.contains(HEADER_APP_JSON)) {
			try {

				log.info("usuario : {}", usuario);
				log.info("origen : {}", origen);
				log.info("destino : {}", destino);
				log.info("idArchivo : {}", idArchivo);
				log.info("request : {}", modificarPosicionReq);

				if (modificarPosicionReq == null || idArchivo == null || idArchivo.equals(Common.BLANK_SPACE)
						|| usuario == null || usuario.equals(Common.BLANK_SPACE) || origen == null
						|| origen.equals(Common.BLANK_SPACE) || destino == null || destino.equals(Common.BLANK_SPACE)) {
					
					BadRequest br = new BadRequest();
					br.setCodigo(Common.ERROR_CODE);
					br.setMensaje(Common.ERROR_MENSAJE);

					log.info("{}", br);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
				
				if(idArchivo.equals(modificarPosicionReq.getIdArchivo().toString())) {
					InlineResponse200 resp = consolidadoService.actualizarPrioridadArchivo(modificarPosicionReq);
					if (resp != null) {
						
						log.info("{}", resp);
						
						return new ResponseEntity<InlineResponse200>(resp, HttpStatus.OK);
					} else {
						InternalServerError isr = new InternalServerError();
						isr.setCodigo(Common.ERROR_SERVER);
						isr.setMensaje(Common.ERROR_SERVER_MSG);

						log.info("{}", isr);

						return new ResponseEntity<InternalServerError>(isr, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					BadRequest br = new BadRequest();
					br.setCodigo(Common.ERROR_CODE);
					br.setMensaje(Common.ERROR_MENSAJE_IDS);

					log.info("{}", br);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError isr = new InternalServerError();
				isr.setCodigo(Common.ERROR_SERVER);
				isr.setMensaje(Common.ERROR_SERVER_MSG);

				log.info("{}", isr);

				return new ResponseEntity<InternalServerError>(isr, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setCodigo(Common.ERROR_CODE);
		br.setMensaje(Common.ERROR_MENSAJE);

		log.info("{}", br);

		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

    /*
     * Consulta los archivos por la fecha en la que estan programados
     */
    public ResponseEntity<?> consultaConsolidadosArchivosGET(
    		@ApiParam(value = "Usuario en el sistema origen que lanza la peticion" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Sistema que origina la petici\u00F3n" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,
    		@ApiParam(value = "Destino final de la informaci\u00F3n" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,
    		@NotNull @ApiParam(value = "Fecha de ejecuci\u00F3n del proceso de consolidados", required = true) @Valid @RequestParam(value = "vigencia", required = true) String vigencia,
    		@NotNull @ApiParam(value = "Prioridad en la ejecuci\u00F3n del archivo", required = false) @Valid @RequestParam(value = "idPrioridad", required = false) String idPrioridad) {
		log.info("ConsolidadosApiController.consultaConsolidadosArchivosGET");
		String apiKeyBluemix = request.getHeader(HEADER_APIKEY_KEY);
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		log.info("{}" , ia);
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		String accept = request.getHeader(HEADER_ACCEPT);
		if (accept != null && accept.contains(HEADER_APP_JSON)) {
			BadRequest badReq = null;
			if (usuario == null || origen == null || destino == null || vigencia == null) {
				badReq = new BadRequest();
				badReq.setCodigo(Common.ERROR_CODE);
				badReq.setMensaje(Common.ERROR_MENSAJE);
				log.info("{}", badReq);
				return new ResponseEntity<BadRequest>(badReq, HttpStatus.BAD_REQUEST);
			}
			log.info(usuario);
			log.info(origen);
			log.info(vigencia);
			log.info(idPrioridad);
			
			if(!consolidadoService.validaFormatoFecha(vigencia)){
				log.info("validando.......");
				System.out.println(consolidadoService.validaFormatoFecha(vigencia));
				badReq = new BadRequest();
				badReq.setCodigo(Common.ERROR_CODE);
				badReq.setMensaje(Common.ERROR_MENSAJE_DATE);
				return new ResponseEntity<BadRequest>(badReq, HttpStatus.BAD_REQUEST);
			}
			ArrayList<ConsultarArchivoConsolidadoResInner> result = consolidadoService.getConsolidados(vigencia);
			ConsultarArchivoConsolidadoRes response = new ConsultarArchivoConsolidadoRes();
			if (!result.isEmpty()) {
				response.addAll(result);
				return new ResponseEntity<ConsultarArchivoConsolidadoRes>(response, HttpStatus.OK);
			} else {
				NotFound nf = new NotFound();
				nf.setCodigo(Common.CODE_NOT_FOUND);
				nf.setMensaje(Common.MESSAGE_NOT_FOUND);
				return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
			}
		}
		BadRequest br = new BadRequest();
		br.setCodigo(Common.ERROR_CODE);
		br.setMensaje(Common.ERROR_MENSAJE);
		log.info("{}" , br);
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

    /*
     * Elimina archivo consolidados
     */
	public ResponseEntity<?> eliminarArchivoConsolidadoDELETE(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petici\u00F3n", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petici\u00F3n", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la informaci\u00F3n", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del archivo", required = true) @PathVariable("idArchivo") String idArchivo) {
    		
    	log.info("Elimina archivos consolidados.");
    	
    	String apiKeyBluemix = request.getHeader(HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ia);
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
    	
    	String accept = request.getHeader(HEADER_ACCEPT);
        if (accept != null && accept.contains(HEADER_APP_JSON)) {
            try {
            	
            	log.info("usuario : {}" , usuario);
            	log.info("origen : {}" , origen);
            	log.info("destino : {}" , destino);
            	log.info("id Archivo : {}" , idArchivo);
            	
            	if(idArchivo == null || idArchivo.equals("") || usuario == null || usuario.equals("") || origen == null || origen.equals("")  || destino == null || destino.equals("") ) {
            		BadRequest br =  new BadRequest();
            		br.setCodigo(Common.ERROR_CODE);
            		br.setMensaje(Common.ERROR_MENSAJE);
            		
            		log.info("{}" , br);
            		
            		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            	
             	Boolean eliminado = consolidadoService.eliminarConsolidado(idArchivo);
            	
             	SuccessfulResponse sr = new SuccessfulResponse();
             	if(Boolean.TRUE.equals(eliminado)) {
             		sr.setCodigo(Common.EXITO_ELIMINAR);
             		sr.setMensaje(Common.EXITO_ELIMINAR_MSG);
             		
             		log.info("{}" , sr);
             		
             		return new ResponseEntity<SuccessfulResponse>(sr, HttpStatus.OK);
             	} else {
             		
             		sr.setCodigo(Common.ERROR_SERVER);
             		sr.setMensaje(Common.NO_EXITO_ELIMINAR_MSG);    		
             		
             		log.info("{}" , sr);
             		
             		return new ResponseEntity<SuccessfulResponse>(sr, HttpStatus.INTERNAL_SERVER_ERROR);
             	}
                //return new ResponseEntity<SuccessfulResponse>(objectMapper.readValue("{  \"codigo\" : \"NMP-MDA-000\",  \"mensaje\" : \"Operación ejecutada satisfactoriamente\"}", SuccessfulResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
            	log.error("Couldn't serialize response for content type application/json", e);
            	InternalServerError isr = new InternalServerError();
         		isr.setCodigo(Common.ERROR_SERVER);
         		isr.setMensaje(Common.ERROR_SERVER_MSG);
         		
         		log.info("{}" , isr);
         		
         		return new ResponseEntity<InternalServerError>(isr, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        BadRequest br = new BadRequest();
		br.setCodigo(Common.ERROR_CODE);
		br.setMensaje(Common.ERROR_MENSAJE);

		log.info("{}" , br);
		
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
    }

	/*
	 * Procesar consolidado
	 */
    public ResponseEntity<?> procesarConsolidadoPOST(
    		@ApiParam(value = "Usuario en el sistema origen que lanza la petici\u00F3n" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Sistema que origina la petici\u00F3n" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,
    		@ApiParam(value = "Destino final de la informaci\u00F3n" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,
    		@NotNull @ApiParam(value = "Fecha de ejecuci\u00F3n del proceso de consolidados", required = true) @Valid @RequestParam(value = "fechaAplicacion", required = true) String fechaAplicacion) {
        
        log.info("procesarConsolidadoPOST");
        
        String apiKeyBluemix = request.getHeader(HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ia);
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
        
        String accept = request.getHeader(HEADER_ACCEPT);
        
        if (accept != null && accept.contains(HEADER_APP_JSON)) {
        	try {
        		BadRequest badReq=null;
        		if(usuario != null && origen != null && destino != null && fechaAplicacion != null) {
            		consolidadoService.procesarConsolidados(usuario, fechaAplicacion);
            		
            		SuccessfulResponse sr = new SuccessfulResponse();
            		
            		sr.setCodigo(Common.EXITO_PROCESAR);
            		sr.setMensaje(Common.EXITO_PROCESAR_MSG);
            		
            		log.info("{}" , sr);
            		
            		return new ResponseEntity<SuccessfulResponse>(sr, HttpStatus.OK);
            	} else {
            		badReq=new BadRequest();
        			badReq.setCodigo(Common.ERROR_CODE);
        			badReq.setMensaje(Common.ERROR_MENSAJE);
        			return new ResponseEntity<BadRequest>(badReq, HttpStatus.BAD_REQUEST);
            	}
        		
                //return new ResponseEntity<SuccessfulResponse>(objectMapper.readValue("{  \"codigo\" : \"NMP-MDA-000\",  \"mensaje\" : \"Operaci\u00F3n ejecutada satisfactoriamente\"}", SuccessfulResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
            	log.error("Couldn't serialize response for content type application/json", e);
            	
            	InternalServerError is = new InternalServerError();
            	is.setCodigo(Common.ERROR_SERVER);
            	is.setMensaje(Common.ERROR_SERVER_MSG);
            	
            	log.info("{}" , is);
            	
                return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        BadRequest br = new BadRequest();
		br.setCodigo(Common.ERROR_CODE);
		br.setMensaje(Common.ERROR_MENSAJE);

		log.info("{}" , br);
		
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
    }

    /*
     * Registrar archivo con información de consolidados
     */
	public ResponseEntity<?> registrarConsolidadoPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petici\u00F3n", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petici\u00F3n", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la informaci\u00F3n", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Archivo CSV de consolidados") @Valid @RequestPart(value = "adjunto", required = true) MultipartFile adjunto,
			@ApiParam(value = "Fecha de vigencia para el ajuste", required = true) @RequestHeader(value = "vigencia", required = true) String vigencia,
			@ApiParam(value = "Nombre del ajuste", required = true) @RequestHeader(value = "nombreAjuste", required = true) String nombreAjuste,
			@ApiParam(value = "Flag para indicar si el ajuste es emergente", required = true) @RequestHeader(value = "emergente", required = true) Boolean emergente) {
		log.info("ConsolidadosApiController.registrarConsolidadoPOST");
		String apiKeyBluemix = request.getHeader(HEADER_APIKEY_KEY);
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		log.info("{}" , ia);
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		log.info("usuario " + usuario);
		log.info("origen " + origen);
		log.info("destino " + destino);
		log.info("adjunto " + adjunto.getOriginalFilename());
		log.info("adjunto " + adjunto.getOriginalFilename().toLowerCase().endsWith(CSV_MIN));
		log.info("vigencia " + vigencia);
		log.info("emergente " + emergente);
		String accept = request.getHeader(HEADER_ACCEPT);
		if (accept != null && accept.contains(HEADER_APP_JSON)) {
			GeneralResponse res = null;
			BadRequest bad = null;
			if (usuario == null || origen == null || destino == null || adjunto == null || vigencia == null
					|| emergente == null) {
				bad = new BadRequest();
				bad.setCodigo(Common.ERROR_CODE);
				bad.setMensaje(Common.ERROR_MENSAJE);
				log.info("{}", bad);
				return new ResponseEntity<BadRequest>(bad, HttpStatus.BAD_REQUEST);
			}
			if(Boolean.FALSE.equals(adjunto.getOriginalFilename().toLowerCase().endsWith(CSV_MIN))) {
				bad = new BadRequest();
				bad.setCodigo(Common.ERROR_CODE);
				bad.setMensaje(Common.ERROR_BAD_REQUEST_EXT);
				log.info("{}", bad);
				return new ResponseEntity<BadRequest>(bad, HttpStatus.BAD_REQUEST);
			}
			try {
				CastConsolidados util = new CastConsolidados();
				Consolidados consolidado = new Consolidados();
				consolidado.setUsuario(origen);
				consolidado.setEmergente(emergente);
				consolidado.setFechaAplicacion(new Date(vigencia));
				consolidado.setVigencia(new Date(vigencia));
				consolidado.setNombreAjuste(nombreAjuste);
				consolidado.setAdjunto(util.convert(adjunto));
				Boolean service = consolidadoService.crearConsolidado(consolidado);
				if (service) {
					res = new GeneralResponse();
					res.setMessage(Common.EXITO_GUARDAR);
					log.info("{}", res);
					return new ResponseEntity<GeneralResponse>(res, HttpStatus.OK);
				}
			} catch (IOException e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError is = new InternalServerError();
				is.setCodigo(Common.ERROR_SERVER);
				is.setMensaje(Common.ERROR_SERVER_MSG);
				log.info("{}", is);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		BadRequest br = new BadRequest();
		br.setCodigo(Common.ERROR_CODE);
		br.setMensaje(Common.ERROR_MENSAJE);
		log.info("{}" , br);
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

}

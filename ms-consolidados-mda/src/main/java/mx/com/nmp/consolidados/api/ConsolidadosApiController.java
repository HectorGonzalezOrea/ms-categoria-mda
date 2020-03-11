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
import mx.com.nmp.consolidados.model.SuccessfulResponse;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
import mx.com.nmp.consolidados.mongodb.service.ConsolidadoService;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

@Controller
public class ConsolidadosApiController implements ConsolidadosApi {

	private static final Logger LOG = LoggerFactory.getLogger(ConsolidadosApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private ConsolidadoService consolidadoService; 

    @org.springframework.beans.factory.annotation.Autowired
    public ConsolidadosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> actualizarPosicionArchivoPUT(
    		@ApiParam(value = "Usuario en el sistema origen que lanza la petici\u00F3n" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Sistema que origina la petici\u00F3n" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,
    		@ApiParam(value = "Destino final de la informaci\u00F3n" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,
    		@ApiParam(value = "Identificador del archivo",required=true) @PathVariable("idArchivo") String idArchivo,
    		@ApiParam(value = "peticion para modificar la posicion de un archivo"  )  @Valid @RequestBody ModificarPrioridadArchivoConsolidadoReq modificarPosicionReq) {
    	
    	LOG.info("Actualizar la posición de los archivos consolidados.");
    	
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	
            	LOG.info("usuario : {}" , usuario);
            	LOG.info("origen : {}" , origen);
            	LOG.info("destino : {}" , destino);
            	LOG.info("idArchivo : {}" , idArchivo);
            	LOG.info("request : {}" , modificarPosicionReq);
            	
            	if(modificarPosicionReq == null || idArchivo == null || idArchivo.equals(Common.BLANK_SPACE) || usuario == null || usuario.equals(Common.BLANK_SPACE) || origen == null || origen.equals(Common.BLANK_SPACE)  || destino == null || destino.equals(Common.BLANK_SPACE) ) {
            		BadRequest br =  new BadRequest();
            		br.setCodigo(Common.ERROR_GUARDAR);
            		br.setMensaje(Common.ERROR_MENSAJE);
            		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            	
            	InlineResponse200 resp = consolidadoService.actualizarPrioridadArchivo(modificarPosicionReq);
            	if(resp != null) {
            		return new ResponseEntity<InlineResponse200>(resp, HttpStatus.OK);
            	} else {
            		InternalServerError isr = new InternalServerError();
             		isr.setCodigo(Common.ERROR_SERVER);
             		isr.setMensaje(Common.ERROR_SERVER_MSG);
            		return new ResponseEntity<InternalServerError>(isr, HttpStatus.INTERNAL_SERVER_ERROR);
            	}
                //return new ResponseEntity<InlineResponse200>(objectMapper.readValue("{  \"nombreArchivo\" : \"nombreArchivo\",  \"idPosicion\" : 0}", InlineResponse200.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
            	LOG.error("Couldn't serialize response for content type application/json", e);
            	InternalServerError isr = new InternalServerError();
         		isr.setCodigo(Common.ERROR_SERVER);
         		isr.setMensaje(Common.ERROR_SERVER_MSG);
            }
        }

        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> consultaConsolidadosArchivosGET(
    		@ApiParam(value = "Usuario en el sistema origen que lanza la peticion" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Sistema que origina la petici\u00F3n" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,
    		@ApiParam(value = "Destino final de la informaci\u00F3n" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,
    		@NotNull @ApiParam(value = "Fecha de ejecuci\u00F3n del proceso de consolidados", required = true) @Valid @RequestParam(value = "fechaAplicacion", required = true) String fechaAplicacion,
    		@NotNull @ApiParam(value = "Prioridad en la ejecuci\u00F3n del archivo", required = false) @Valid @RequestParam(value = "idPrioridad", required = false) String idPrioridad) {
    		LOG.info("ConsolidadosApiController.consultaConsolidadosArchivosGET");
    		BadRequest badReq=null;
    		if(usuario==null||origen==null||destino==null||fechaAplicacion==null) {
    			badReq=new BadRequest();
    			badReq.setCodigo(Common.ERROR_GUARDAR);
    			badReq.setMensaje(Common.ERROR_MENSAJE);
    			return new ResponseEntity<BadRequest>(badReq, HttpStatus.BAD_REQUEST);
    		}
        	LOG.info(usuario);
        	LOG.info(origen);
        	LOG.info(fechaAplicacion);
        	LOG.info(idPrioridad);
			ArrayList<ConsultarArchivoConsolidadoResInner> result=consolidadoService.getConsolidados(fechaAplicacion);		
			ConsultarArchivoConsolidadoRes response=new ConsultarArchivoConsolidadoRes();
			if(result!=null) {
				response.addAll(result);	
			}
			return new ResponseEntity<ConsultarArchivoConsolidadoRes>(response, HttpStatus.OK);
    }

    public ResponseEntity<?> eliminarArchivoConsolidadoDELETE(@ApiParam(value = "Usuario en el sistema origen que lanza la petici\u00F3n" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petici\u00F3n" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la informaci\u00F3n" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Identificador del archivo",required=true) @PathVariable("idArchivo") String idArchivo) {
    	LOG.info("Elimina archivos consolidados.");
    	
    	String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	
            	LOG.info("usuario : {}" , usuario);
            	LOG.info("origen : {}" , origen);
            	LOG.info("destino : {}" , destino);
            	LOG.info("id Archivo : {}" , idArchivo);
            	
            	if(idArchivo == null || idArchivo.equals("") || usuario == null || usuario.equals("") || origen == null || origen.equals("")  || destino == null || destino.equals("") ) {
            		BadRequest br =  new BadRequest();
            		br.setCodigo(Common.ERROR_GUARDAR);
            		br.setMensaje(Common.ERROR_MENSAJE);
            		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            	
             	Boolean eliminado = consolidadoService.eliminarConsolidado(idArchivo);
            	
             	if(Boolean.TRUE.equals(eliminado)) {
             		SuccessfulResponse sr = new SuccessfulResponse();
             		sr.setCodigo(Common.EXITO_ELIMINAR);
             		sr.setMensaje(Common.EXITO_ELIMINAR_MSG);    		
             		return new ResponseEntity<SuccessfulResponse>(sr, HttpStatus.OK);
             	}  else {
             		InternalServerError isr = new InternalServerError();
             		isr.setCodigo(Common.ERROR_SERVER);
             		isr.setMensaje(Common.ERROR_SERVER_MSG);
             		
             		return new ResponseEntity<InternalServerError>(isr, HttpStatus.INTERNAL_SERVER_ERROR);
             	}
                //return new ResponseEntity<SuccessfulResponse>(objectMapper.readValue("{  \"codigo\" : \"NMP-MDA-000\",  \"mensaje\" : \"Operación ejecutada satisfactoriamente\"}", SuccessfulResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
            	LOG.error("Couldn't serialize response for content type application/json", e);
            	InternalServerError isr = new InternalServerError();
         		isr.setCodigo(Common.ERROR_SERVER);
         		isr.setMensaje(Common.ERROR_SERVER_MSG);
         		return new ResponseEntity<InternalServerError>(isr, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<SuccessfulResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<SuccessfulResponse> procesarConsolidadoPOST(
    		@ApiParam(value = "Usuario en el sistema origen que lanza la petici\u00F3n" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Sistema que origina la petici\u00F3n" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,
    		@ApiParam(value = "Destino final de la informaci\u00F3n" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,
    		@NotNull @ApiParam(value = "Fecha de ejecuci\u00F3n del proceso de consolidados", required = true) @Valid @RequestParam(value = "fechaAplicacion", required = true) LocalDate fechaAplicacion) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<SuccessfulResponse>(objectMapper.readValue("{  \"codigo\" : \"NMP-MDA-000\",  \"mensaje\" : \"Operaci\u00F3n ejecutada satisfactoriamente\"}", SuccessfulResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
            	LOG.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<SuccessfulResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<SuccessfulResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> registrarConsolidadoPOST(
    		@ApiParam(value = "Usuario en el sistema origen que lanza la petici\u00F3n" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Sistema que origina la petici\u00F3n" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,
    		@ApiParam(value = "Destino final de la informaci\u00F3n" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,
    		@ApiParam(value = "Archivo CSV de consolidados") @Valid @RequestPart(value="adjunto", required=true) MultipartFile adjunto,
    		@ApiParam(value = "Fecha de vigencia para el ajuste" ,required=true) @RequestHeader(value="vigencia", required=true) String vigencia,
    		@ApiParam(value = "Nombre del ajuste" ,required=true) @RequestHeader(value="nombreAjuste", required=true) String nombreAjuste,@ApiParam(value = "Flag para indicar si el ajuste es emergente" ,required=true) @RequestHeader(value="emergente", required=true) Boolean emergente) {
        LOG.info("ConsolidadosApiController.registrarConsolidadoPOST");
        LOG.info("usuario "+usuario);
        LOG.info("origen "+origen);
        LOG.info("destino "+destino);
        LOG.info("adjunto "+adjunto.getOriginalFilename());
        LOG.info("vigencia "+vigencia);
        LOG.info("emergente "+emergente);
        GeneralResponse res=null;
        BadRequest bad=null;
        if(usuario==null||origen==null||destino==null||adjunto==null||vigencia==null||emergente==null) {
        	bad=new BadRequest();
        	bad.setCodigo(Common.ERROR_GUARDAR);
        	bad.setMensaje(Common.ERROR_MENSAJE);
        	return new ResponseEntity<BadRequest>(bad, HttpStatus.BAD_REQUEST);
        }
            try {
            	CastConsolidados util=new CastConsolidados();
            	Consolidados consolidado=new Consolidados();
            	consolidado.setEmergente(emergente);
            	consolidado.setFechaAplicacion(new Date());
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            	LocalDate localDate = LocalDate.parse(vigencia, formatter);
            	consolidado.setVigencia(localDate);
            	consolidado.setNombreAjuste(nombreAjuste);
            	consolidado.setAdjunto(util.convert(adjunto));
            	Boolean service=consolidadoService.crearConsolidado(consolidado);
            	if(service) {
            		res=new GeneralResponse();
            		res.setMessage(Common.EXITO_GUARDAR);
            		return new ResponseEntity<GeneralResponse>(res, HttpStatus.OK);
            	}
            } catch (IOException e) {
            	LOG.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}

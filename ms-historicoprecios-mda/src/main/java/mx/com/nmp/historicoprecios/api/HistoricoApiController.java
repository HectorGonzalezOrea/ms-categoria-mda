package mx.com.nmp.historicoprecios.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.historicoprecios.elastic.service.HistoricoPreciosService;
import mx.com.nmp.historicoprecios.model.BadRequest;
import mx.com.nmp.historicoprecios.model.GeneralResponse;
import mx.com.nmp.historicoprecios.model.HistoricoPrecios;
import mx.com.nmp.historicoprecios.model.InternalServerError;
import mx.com.nmp.historicoprecios.model.InvalidAuthentication;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-19T17:57:16.669Z")

@Controller
public class HistoricoApiController implements HistoricoApi {

    private static final Logger log = LoggerFactory.getLogger(HistoricoApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
	private HistoricoPreciosService historicoPreciosService;
    
    @org.springframework.beans.factory.annotation.Autowired
    public HistoricoApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> historicoPreciosPost(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody HistoricoPrecios peticion) {
        log.info("Historico de precios.");
    	
    	String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	
            	log.info("Usuario: " + usuario);
            	
            	GeneralResponse gr = null;
            	BadRequest br = null;
            	InternalServerError ise = null;
            	InvalidAuthentication ia = null;
            	
            	if(usuario == null) {
            		br = new BadRequest();
            		br.setCode("NMP-MDA-400");
            		br.setMessage("El cuerpo de la petición no está bien formado, verifique su información");
            		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            	
            	if(peticion != null) {
            		
                	log.info("Fecha: " + peticion.getFecha());
                	log.info("Folio Partida: " + peticion.getFolioPartida());
                	log.info("Precio Actual: " + peticion.getPrecioActual());
                	log.info("Precio Modificado: " + peticion.getPrecioModificado());
                	log.info("SKU: " + peticion.getSku());
            		
            		if(peticion.getFecha() == null || peticion.getFolioPartida() == null || peticion.getPrecioActual() == null || peticion.getPrecioModificado() == null ||  peticion.getSku() == null) {
                		br = new BadRequest();
                		br.setCode("NMP-MDA-400");
                		br.setMessage("El cuerpo de la petición no está bien formado, verifique su información");
                		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
                	} else {
                		Boolean insertado = historicoPreciosService.insertarHistoricoPrecios(peticion, usuario);
                		
                		if(insertado) {
                			gr = new GeneralResponse();
                			gr.setMessage("Se inserto de manera exitosa.");
                			return new ResponseEntity<GeneralResponse>(gr,HttpStatus.OK);
                		} else {
                			ise = new InternalServerError();
                			ise.setCode("NMP-MDA-500");
                			ise.setMessage("Error interno del servidor. Falla de comunicación.");
                			
                			return new ResponseEntity<InternalServerError>(ise,HttpStatus.INTERNAL_SERVER_ERROR);
                		}
                	}
            	} else {
            		br = new BadRequest();
            		br.setCode("NMP-MDA-400");
            		br.setMessage("El cuerpo de la petición no está bien formado, verifique su información");
            		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
                //return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}

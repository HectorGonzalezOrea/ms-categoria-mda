package mx.com.nmp.establecimientoprecios.api;

import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_APIKEY_KEY;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ACCEPT_KEY;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ACCEPT_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.establecimientoprecios.model.BadRequest;
import mx.com.nmp.establecimientoprecios.model.GeneralResponse;
import mx.com.nmp.establecimientoprecios.model.InternalServerError;
import mx.com.nmp.establecimientoprecios.model.InvalidAuthentication;
import mx.com.nmp.establecimientoprecios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.establecimientoprecios.service.AnclaService;

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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-03T00:30:35.505Z")

@Controller
public class AnclaApiController implements AnclaApi {

    private static final Logger log = LoggerFactory.getLogger(AnclaApiController.class);

    @Autowired
    private AnclaService oagService;
    
    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AnclaApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> anclaOroDolarPost(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody ModificarValorAnclaOroDolar peticion) {
    	log.info("Solicitar cambio de valores ancla para Oro y Dolar");
    	
    	String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	String apikey = request.getHeader(HEADER_APIKEY_KEY);
            	
            	log.info("Usuario: " + usuario);
            	log.info("Request:" + peticion.toString());
            	
            	if(apikey == null || apikey.equals("")) {
            		
            		InvalidAuthentication ia = new InvalidAuthentication();
            		ia.setCode("NMP-MDA-401");
            		ia.setMessage("Se ha producido un error de autorización.");
            		
            		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.INTERNAL_SERVER_ERROR);
            	}
            	
            	if(usuario == null || usuario.equals("")) {
            		BadRequest bd = new BadRequest();
            		
            		bd.setCode("NMP-MDA-400");
            		bd.setMessage("El cuerpo de la petición no está bien formado, verifique su información.");
            		
            		return new ResponseEntity<BadRequest>(bd, HttpStatus.BAD_REQUEST);
            	}
            	
            	if(peticion == null || peticion.getFechaAplicacion() == null || peticion.getIdBolsa() == null || peticion.getValorAnclaDolar() == null || peticion.getValorAnclaOro() == null || peticion.getSucursales() == null) {
            		BadRequest bd = new BadRequest();
            		
            		bd.setCode("NMP-MDA-400");
            		bd.setMessage("El cuerpo de la petición no está bien formado, verifique su información.");
            		
            		return new ResponseEntity<BadRequest>(bd, HttpStatus.BAD_REQUEST);
            	} else {
            		GeneralResponse resp = oagService.ajusteValorAncla(peticion);
            		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
            	}
                //return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                
                InternalServerError ise = new InternalServerError();
                ise.setCode("NMP-MDA-500");
                ise.setMessage("Error interno del servidor. Falla de comunicación.");
                
                return new ResponseEntity<InternalServerError>(ise, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}

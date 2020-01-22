package mx.com.nmp.establecimientoprecios.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.establecimientoprecios.model.BadRequest;
import mx.com.nmp.establecimientoprecios.model.GeneralResponse;
import mx.com.nmp.establecimientoprecios.model.InternalServerError;
import mx.com.nmp.establecimientoprecios.model.InvalidAuthentication;
import mx.com.nmp.establecimientoprecios.model.ListaMonedas;
import mx.com.nmp.establecimientoprecios.service.MonedasService;

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

import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ACCEPT_KEY;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ACCEPT_VALUE;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_APIKEY_KEY;

import java.io.IOException;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-03T00:30:35.505Z")

@Controller
public class MonedasApiController implements MonedasApi {

    private static final Logger log = LoggerFactory.getLogger(MonedasApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private MonedasService monedasService;
    
    @org.springframework.beans.factory.annotation.Autowired
    public MonedasApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<?> monedasPatch(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody ListaMonedas peticion) {
    	log.info("Actualizar la configuración de Monedas");
    	
    	String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	String apikey = request.getHeader(HEADER_APIKEY_KEY);
            	
            	log.info("Usuario: " + usuario);
            	
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
            	
            	if(peticion != null) {
            		log.info("Request:" + peticion.toString());
            		GeneralResponse resp = monedasService.actualizarMonedas(peticion);
            		
            		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
            	} else {
            		BadRequest bd = new BadRequest();
            		
            		bd.setCode("NMP-MDA-400");
            		bd.setMessage("El cuerpo de la petición no está bien formado, verifique su información.");
            		
            		return new ResponseEntity<BadRequest>(bd, HttpStatus.BAD_REQUEST);
            	}
            	
                //return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> monedasPost(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody ListaMonedas peticion) {
    	log.info("Almacenar configuración de Monedas");
    	
    	String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	String apikey = request.getHeader(HEADER_APIKEY_KEY);
            	
            	log.info("Usuario: " + usuario);
            	
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
            	
            	if(peticion != null) {
            		log.info("Request:" + peticion.toString());
            		GeneralResponse resp = monedasService.agregarMonedas(peticion);
            		
            		return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
            	} else {
            		BadRequest bd = new BadRequest();
            		
            		bd.setCode("NMP-MDA-400");
            		bd.setMessage("El cuerpo de la petición no está bien formado, verifique su información.");
            		
            		return new ResponseEntity<BadRequest>(bd, HttpStatus.BAD_REQUEST);
            	}
            	
               // return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}

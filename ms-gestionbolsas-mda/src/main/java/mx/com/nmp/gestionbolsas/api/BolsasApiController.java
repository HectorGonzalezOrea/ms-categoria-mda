package mx.com.nmp.gestionbolsas.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.gestionbolsas.model.BadRequest;
import mx.com.nmp.gestionbolsas.model.Bolsa;
import mx.com.nmp.gestionbolsas.model.GeneralResponse;
import mx.com.nmp.gestionbolsas.model.InternalServerError;
import mx.com.nmp.gestionbolsas.model.InvalidAuthentication;
import mx.com.nmp.gestionbolsas.model.ListaBolsas;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsas;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsasInner;
import mx.com.nmp.gestionbolsas.mongodb.service.BolsasService;


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
import java.util.ArrayList;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-02-21T17:43:22.226Z")

@Controller
public class BolsasApiController implements BolsasApi {

    private static final Logger log = LoggerFactory.getLogger(BolsasApiController.class);
    

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private BolsasService bolsaService;

    @org.springframework.beans.factory.annotation.Autowired
    public BolsasApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ListaBolsas> bolsasGet(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = " Identificador del tipo de bolsa a buscar") @Valid @RequestParam(value = "idTipo", required = false) String idTipo,@ApiParam(value = "Nombre de la Bolsa") @Valid @RequestParam(value = "nombre", required = false) String nombre,@ApiParam(value = "Ramo configurado en la Bolsa") @Valid @RequestParam(value = "ramo", required = false) String ramo,@ApiParam(value = "Subramo configurado en la Bolsa") @Valid @RequestParam(value = "subramo", required = false) String subramo,@ApiParam(value = "Factor configurado en la Bolsa") @Valid @RequestParam(value = "factor", required = false) String factor) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ListaBolsas>(objectMapper.readValue("\"\"", ListaBolsas.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ListaBolsas>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ListaBolsas>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> bolsasIdBolsaDelete(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Identificador de la Bolsa a eliminar",required=true) @PathVariable("idBolsa") Integer idBolsa) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	
            	if(idBolsa == null) {
            		log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMessage("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCode("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
            	}else {
            		Boolean eliminado = bolsaService.deleteBolsa(idBolsa);
            		GeneralResponse resp =  new GeneralResponse();
            		if(eliminado) {
						resp.setMessage("Usuario eliminado exitosamente");
						return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
					} else {
						resp.setMessage("Usuario no eliminado");
						return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
					}
            		}
                               	
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InternalServerError>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> bolsasPatch(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody Bolsa peticion) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	if (peticion != null) {
            		log.info("peticion: " + peticion.toString());
            		Boolean actualizado = bolsaService.updateBolsa(peticion);
            		
            		if(actualizado) {
            			GeneralResponse resp =  new GeneralResponse();
            			resp.setMessage("Bolsa actualizada correctamente.");
            			
            			return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
            			
            		}else {
            			InternalServerError ie = new InternalServerError();
        				ie.setCode("NMP-MDA-500");
        				ie.setMessage("Error interno del servidor");
                        
                        return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            		}
            	}else {
            		BadRequest br = new BadRequest();
					br.setMessage("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCode("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            		
                
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> bolsasPost(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody Bolsa peticion) {
    	log.info("Crear Bolsa");
    	String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	
            	if (peticion != null) {
            		
            		log.info("peticion: " + peticion.toString());
            		
            		Boolean insertado = bolsaService.crearBolsa(peticion);
            		if(insertado) {
            			GeneralResponse resp =  new GeneralResponse();
            			resp.setMessage("Bolsa creada correctamente.");
            			
            			return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
            		} else {
            			InternalServerError ie = new InternalServerError();
        				ie.setCode("NMP-MDA-500");
        				ie.setMessage("Error interno del servidor");
                        
                        return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            		}
            	} else {
            		BadRequest br = new BadRequest();
					br.setMessage("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCode("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            } catch (Exception e) {
            	log.error("Couldn't serialize response for content type application/json", e);
                InternalServerError ie = new InternalServerError();
				ie.setCode("NMP-MDA-500");
				ie.setMessage("Error interno del servidor");
                
                return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> bolsasTiposGet(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
        	BadRequest br = new BadRequest();
            try {
            	if(usuario == null) {
            		br.setCode("NMP-MDA-400");
            		br.setMessage("El cuerpo de la petición no está bien formado, verifique su información");
            		
            		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	}
            	ListaTipoBolsas response = bolsaService.consultaTipoBolsa();		
            	
    			if(!response.isEmpty()) {
    				log.info("Result: {}" , response);
    				return new ResponseEntity<ListaTipoBolsas>(response, HttpStatus.OK);
    			}else {
    				log.info("No se encontro nada");
    				return new ResponseEntity<ListaTipoBolsas>(response, HttpStatus.BAD_REQUEST);
    			}
    			
        
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ListaTipoBolsas>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ListaTipoBolsas>(HttpStatus.NOT_IMPLEMENTED);
    }

}

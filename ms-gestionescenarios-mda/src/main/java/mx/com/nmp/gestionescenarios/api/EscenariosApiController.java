package mx.com.nmp.gestionescenarios.api;

import java.io.IOException;
import java.util.List;

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
import org.threeten.bp.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import mx.com.nmp.gestionescenarios.model.BadRequest;
import mx.com.nmp.gestionescenarios.model.Bolsa;
import mx.com.nmp.gestionescenarios.model.Escenarios;
import mx.com.nmp.gestionescenarios.model.EstatusRegla;
import mx.com.nmp.gestionescenarios.model.GeneralResponse;
import mx.com.nmp.gestionescenarios.model.InfoGeneralRegla;
import mx.com.nmp.gestionescenarios.model.InfoRegla;
import mx.com.nmp.gestionescenarios.model.ListaBolsas;
import mx.com.nmp.gestionescenarios.model.ListaInfoGeneralRegla;
import mx.com.nmp.gestionescenarios.model.ListaMonedas;
import mx.com.nmp.gestionescenarios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.gestionescenarios.model.ValorAnclaOroDolar;
import mx.com.nmp.gestionescenarios.mongodb.service.GestionEscenarioService;
import mx.com.nmp.gestionescenarios.model.InternalServerError;
import mx.com.nmp.gestionescenarios.model.InvalidAuthentication;

import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_APIKEY_KEY;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ACCEPT_KEY;
import static mx.com.nmp.gestionescenarios.utils.Constantes.HEADER_ACCEPT_VALUE;
import static mx.com.nmp.gestionescenarios.utils.Constantes.CADENA_VACIA;

import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_CODE_INVALID_AUTHENTICATION;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_CODE_INTERNAL_SERVER_ERROR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_MESSAGE_INTERNAL_SERVER_ERROR;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_CODE_BAD_REQUEST;
import static mx.com.nmp.gestionescenarios.utils.Constantes.ERROR_MESSAGE_BAD_REQUEST;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-20T16:07:47.599Z")

@Controller
public class EscenariosApiController implements EscenariosApi {

    private static final Logger log = LoggerFactory.getLogger(EscenariosApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private GestionEscenarioService gestionEscenarioService;
    
    

    @org.springframework.beans.factory.annotation.Autowired
    public EscenariosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ValorAnclaOroDolar> escenariosAnclaOroDolarGet(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
                return new ResponseEntity<ValorAnclaOroDolar>(objectMapper.readValue("{  \"valorAnclaDolar\" : 19.4523,  \"valorAnclaOro\" : 800.02}", ValorAnclaOroDolar.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ValorAnclaOroDolar>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ValorAnclaOroDolar>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<GeneralResponse> escenariosAnclaOroDolarPost(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody ModificarValorAnclaOroDolar peticion) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
                return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    /*
     * Almacenar o procesar los documentos excel de consolidados
     */
	public ResponseEntity<?> escenariosConsolidadosArchivoPost(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Archivo CSV de consolidados") @Valid @RequestPart(value = "adjunto", required = true) MultipartFile adjunto,
			@ApiParam(value = "Fecha de vigencia para el ajuste", required = true) @RequestHeader(value = "vigencia", required = true) LocalDate vigencia,
			@ApiParam(value = "Nombre del ajuste", required = true) @RequestHeader(value = "nombreAjuste", required = true) String nombreAjuste,
			@ApiParam(value = "Flag para indicar si el ajuste es emergente", required = true) @RequestHeader(value = "emergente", required = true) Boolean emergente) {
        
    	String apiKey = request.getHeader(HEADER_APIKEY_KEY);
    	
    	if(apiKey != null && !apiKey.equals(CADENA_VACIA)) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
    	}
    	
    	String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	
            	
            	if(usuario == null || adjunto == null || vigencia == null || nombreAjuste == null || emergente == null) {
            		BadRequest br = new BadRequest();
            		
            		br.setCode(ERROR_CODE_BAD_REQUEST);
            		br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
            		
            		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
            	} else {
            		
            	}
                return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                
                InternalServerError ise = new InternalServerError();
                ise.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
                ise.setMessage(ERROR_MESSAGE_INTERNAL_SERVER_ERROR);
                
                return new ResponseEntity<InternalServerError>(ise, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<ListaMonedas> escenariosMonedasGet(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@NotNull @ApiParam(value = "Flag para indicar si se consultan las monedas oro o sin oro", required = true) @Valid @RequestParam(value = "oro", required = true) Boolean oro) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
                return new ResponseEntity<ListaMonedas>(objectMapper.readValue("\"\"", ListaMonedas.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ListaMonedas>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ListaMonedas>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<GeneralResponse> escenariosMonedasPatch(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody ListaMonedas peticion) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
                return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<GeneralResponse> escenariosMonedasPost(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody ListaMonedas peticion) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
                return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<GeneralResponse> escenariosPost(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody Escenarios peticion) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
                return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> escenariosReglasEstatusPut(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody EstatusRegla peticion) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	if(peticion != null) {
            		log.info("Peticion : {}", peticion.toString());
            		Boolean actualizado = gestionEscenarioService.actualizaEstatus(peticion);
            		GeneralResponse gr = new GeneralResponse ();
            		gr.setMessage("Regla actualizada exitosamente");
            		return new ResponseEntity<GeneralResponse>(gr, HttpStatus.OK);
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

    public ResponseEntity<?> escenariosReglasGet(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Nombre de las reglas a buscar") @Valid @RequestParam(value = "nombre", required = false) String nombre,
    		@ApiParam(value = "Ramo de las reglas a buscar") @Valid @RequestParam(value = "ramo", required = false) String ramo,
    		@ApiParam(value = "Subramo de las reglas a buscar") @Valid @RequestParam(value = "subramo", required = false) String subramo,
    		@ApiParam(value = "Factor de las reglas a buscar") @Valid @RequestParam(value = "factor", required = false) String factor,
    		@ApiParam(value = "Origen de las partidas") @Valid @RequestParam(value = "origen", required = false) String origen,
    		@ApiParam(value = "Clasificación de los clientes") @Valid @RequestParam(value = "clasificacionClientes", required = false) String clasificacionClientes,
    		@ApiParam(value = "Estatus de la partida") @Valid @RequestParam(value = "estatusPartida", required = false) String estatusPartida,
    		@ApiParam(value = "Canal de comrcialización") @Valid @RequestParam(value = "canalComrcializacion", required = false) String canalComercializacion,
    		@ApiParam(value = "Fecha de aplicación") @Valid @RequestParam(value = "fechaAplicacion", required = false) String fechaAplicacion) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	if(nombre == null && ramo == null && subramo == null && factor == null && origen == null && clasificacionClientes == null && estatusPartida == null && canalComercializacion== null && fechaAplicacion == null) {
            		
            		List<InfoGeneralRegla> reglas = gestionEscenarioService.consultaReglaSinFiltro();
            		ListaInfoGeneralRegla resp = new ListaInfoGeneralRegla ();
            		log.info("Reglas: {}", reglas);
            		if(reglas!= null) {
            			log.info("Si hubo considencias.");
            			resp.addAll(reglas);
            			return new ResponseEntity<ListaInfoGeneralRegla>(resp, HttpStatus.OK);
            		}else {
            			log.info("No concidencias.");
            			return new ResponseEntity<ListaInfoGeneralRegla>(resp, HttpStatus.OK);
            		}
            	}
            	
            	List<InfoGeneralRegla> reglas = gestionEscenarioService.consultaRegla(nombre, ramo, subramo, factor, origen, clasificacionClientes, estatusPartida, canalComercializacion, fechaAplicacion);
        		ListaInfoGeneralRegla resp = new ListaInfoGeneralRegla ();
        		
        		if(reglas!= null) {
        			log.info("Si hubo considencias.");
        			resp.addAll(reglas);
        			return new ResponseEntity<ListaInfoGeneralRegla>(resp, HttpStatus.OK);
        		}else {
        			log.info("No concidencias.");
        			return new ResponseEntity<ListaInfoGeneralRegla>(resp, HttpStatus.OK);
        		}
            		
            } catch (Exception e) {
            	log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCode("NMP-MDA-500");
				ie.setMessage("Error interno del servidor");
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ListaInfoGeneralRegla>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InfoRegla> escenariosReglasIdReglaDelete(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "",required=true) @PathVariable("idRegla") Integer idRegla) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	
            	if(idRegla!=null) {
            		log.info("Eliminar Regla");
            		
            		InfoRegla eliminar = gestionEscenarioService.eliminaRegla(idRegla);
            		if(eliminar!= null) {
            			
            			return new ResponseEntity<InfoRegla>(eliminar, HttpStatus.OK);
            		}
            	}
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InfoRegla>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InfoRegla>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> escenariosReglasIdReglaGet(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "",required=true) @PathVariable("idRegla") Integer idRegla) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	
            	if (idRegla!= null) {
            		
            		Boolean encontrar = gestionEscenarioService.consultaIdRegla(idRegla);
            		log.info("Resultado de existencia: {}", encontrar);
            		if (encontrar) {
            			InfoRegla regla = gestionEscenarioService.consultaReglaId(idRegla);
                			try {
                				log.info("Si hubo coincidencias");
                    			return new ResponseEntity<InfoRegla>(regla,HttpStatus.OK);
                			}
                			catch(Exception e) {
                				log.error("Exception : {}", e);
                			}
                			
                			
                		
            		}else {
            			log.info("No hubo coincidencias");
            			BadRequest bd=new BadRequest();
            			bd.setCode("NMP-MDA-404");
            			bd.setMessage("No se encontro el idRegla");
            			return new ResponseEntity<BadRequest>(bd,HttpStatus.NOT_FOUND);
            		}
            		
            	}
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InfoRegla>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InfoRegla>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> escenariosReglasPatch(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody InfoRegla peticion) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
            try {
            	
            	if(peticion != null) {
            		log.info("Peticion : {}", peticion.toString());
            		Boolean actualizado = gestionEscenarioService.actualizaRegla(peticion);
            		GeneralResponse gr = new GeneralResponse ();
            		gr.setMessage("Regla actualizada exitosamente");
            		return new ResponseEntity<GeneralResponse>(gr, HttpStatus.OK);
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

    public ResponseEntity<?> escenariosReglasPost(@ApiParam(value = "Usuario de sistema que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Cuerpo de la petición" ,required=true )  @Valid @RequestBody InfoRegla peticion) {
        String accept = request.getHeader(HEADER_ACCEPT_KEY);
        if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
        	GeneralResponse gr = new GeneralResponse ();
            try {
            	if(peticion != null) {
            		log.info("Peticion : {}", peticion.toString());
            		Boolean insert = gestionEscenarioService.almacenarRegla(peticion);
            		gr.setMessage("Regla almacenada exitosamente");
            		return new ResponseEntity<GeneralResponse>(gr, HttpStatus.OK);
            		
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

}

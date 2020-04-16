package mx.com.nmp.escenariosdinamicos.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.clienteservicios.service.ClientesMicroservicios;
import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.CalculoValorVO;
import mx.com.nmp.escenariosdinamicos.elastic.properties.ElasticProperties;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.model.BadRequest;
import mx.com.nmp.escenariosdinamicos.model.ConsultarEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.ConsultarEscenariosResInner;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoRes;
import mx.com.nmp.escenariosdinamicos.model.EliminarEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.ModEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.ModEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.PartidaPrecioFinal;
import mx.com.nmp.escenariosdinamicos.model.SimularEscenarioDinamicoRes;
import mx.com.nmp.escenariosdinamicos.mongodb.service.ElasticService;
import mx.com.nmp.escenariosdinamicos.mongodb.service.EscenariosService;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

@Controller
public class EscenariosApiController implements EscenariosApi {

    private static final Logger log = LoggerFactory.getLogger(EscenariosApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private EscenariosService escenarioService;
    @Autowired
    private ElasticService elasticService;
    @Autowired
    private ElasticProperties elasticProperties;
    @Autowired 
    private ClientesMicroservicios clientesMicroservicios;
    @Autowired
    private CastObjectGeneric castObjectGeneric;

    @org.springframework.beans.factory.annotation.Autowired
    public EscenariosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

	public ResponseEntity<?> consultarEscenariosGET(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalInteligenciaComercial") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="MongoDB, mockserver") @RequestHeader(value="destino", required=true) String destino) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	List<ConsultarEscenariosResInner> escenario = escenarioService.consultaEscenario();
            	ConsultarEscenariosRes escenariosRes = new ConsultarEscenariosRes();
            	if(escenario!=null) {
            		log.info("Si hubo considencias.");
            		escenariosRes.addAll(escenario);
            		return new ResponseEntity<ConsultarEscenariosRes>(escenariosRes, HttpStatus.OK);
            	}else {
            		log.info("No concidencias.");
            		return new ResponseEntity<ConsultarEscenariosRes>(escenariosRes, HttpStatus.OK);
            	}
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ConsultarEscenariosRes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ConsultarEscenariosRes>(HttpStatus.NOT_IMPLEMENTED);
    }
	
    public ResponseEntity<?> crearEscenariosPOST(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalInteligenciaComercial") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Peticion para crear las reglas de precios en los escenarios dinámicos"  )  @Valid @RequestBody CrearEscenariosReq crearEscenariosRequest) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	if(crearEscenariosRequest !=null) {
            		log.info("peticion: {}", crearEscenariosRequest.toString());
            		CrearEscenariosRes esce = escenarioService.crearEscenario(crearEscenariosRequest);
      
            			
            			return new ResponseEntity<CrearEscenariosRes>(esce, HttpStatus.OK);
            			
            		}else {
                		BadRequest br = new BadRequest();
    					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
    					br.setCodigo("NMP-MDA-400");
    					
    					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
                	}
            	
            	
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<CrearEscenariosRes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<CrearEscenariosRes>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> editaEscenariosPUT(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalInteligenciaComercial") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Identificador del escenario",required=true) @PathVariable("idEscenario") Integer idEscenario,@ApiParam(value = "peticion para modificar las reglas de precios en los escenarios dinámicos."  )  @Valid @RequestBody ModEscenariosReq modEscenariosRequest) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	if(modEscenariosRequest !=null) {
            		log.info("peticion: {}", modEscenariosRequest.toString());
            		ModEscenariosRes esce = escenarioService.editaEscenario(modEscenariosRequest);
      
            			
            			return new ResponseEntity<ModEscenariosRes>(esce, HttpStatus.OK);
            			
            		}else {
                		BadRequest br = new BadRequest();
    					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
    					br.setCodigo("NMP-MDA-400");
    					
    					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
                	}
            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ModEscenariosRes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ModEscenariosRes>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<EjecutarEscenarioDinamicoRes> ejecutarEscenariosDinamicosPOST(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalInteligenciaComercial") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Peticion para crear las reglas de precios en los escenarios dinámicos"  )  @Valid @RequestBody EjecutarEscenarioDinamicoReq crearEscenariosRequest) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<EjecutarEscenarioDinamicoRes>(objectMapper.readValue("\"\"", EjecutarEscenarioDinamicoRes.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<EjecutarEscenarioDinamicoRes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EjecutarEscenarioDinamicoRes>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<?> eliminarEscenariosDELETE(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalInteligenciaComercial") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Identificador del escenario",required=true) @PathVariable("idEscenario") Integer idEscenario) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	
            	if(idEscenario == null) {
            		
            		log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMensaje("El cuerpo de la petición no está bien formado, verifique su información");
					br.setCodigo("NMP-MDA-400");
					
					return new ResponseEntity<BadRequest>(br,HttpStatus.BAD_REQUEST);
            	}else {
            		Boolean eliminado = escenarioService.eliminaEscenario(idEscenario);
            		EliminarEscenariosRes resp =  new EliminarEscenariosRes();
            		if(eliminado) {
            			resp.setCode("NMP-MDA-200");
						resp.setMessage("Escenario eliminado exitosamente");
						return new ResponseEntity<EliminarEscenariosRes>(resp, HttpStatus.OK);
					} else {
						resp.setMessage("Escenario no eliminado");
						return new ResponseEntity<EliminarEscenariosRes>(resp, HttpStatus.OK);
					}
            		
            	}

            } catch (Exception e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<EliminarEscenariosRes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<EliminarEscenariosRes>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<SimularEscenarioDinamicoRes> simularEscenariosDinamicosPOST(
    		@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalInteligenciaComercial") @RequestHeader(value="origen", required=true) String origen,
    		@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino//,
    		//@ApiParam(value = "Peticion para crear las reglas de precios en los escenarios dinámicos"  ) @Valid @RequestBody SimularEscenarioDinamicoReq crearEscenariosReques
    		) {
    	SimularEscenarioDinamicoRes response=new SimularEscenarioDinamicoRes();
    	ArrayList<PartidaPrecioFinal> lstPartidaPrecioFinal=new ArrayList();
        System.out.println("obtencion de indices");
        List<IndexGarantiaVO>lstIndexGarantia=null;
        	
			try {
				//lstIndexGarantia=elasticService.scrollElastic(elasticProperties.getIndexGarantia());
				//lstIndexGarantia.forEach(i->System.out.println(i.toString()));
				//clientesMicroservicios.actualizaPrecio(castObjectGeneric.castGarantiasToCalculoValor(lstIndexGarantia));
				List<CalculoValorVO> lstIndexGarantiv1=fillValues();
				lstIndexGarantiv1.forEach(x->System.out.println(x.toString()));
				lstPartidaPrecioFinal=(ArrayList<PartidaPrecioFinal>) clientesMicroservicios.actualizaPrecio(lstIndexGarantiv1);
		} catch (Exception e) {
				e.printStackTrace();
			}
			response.addAll(lstPartidaPrecioFinal);
			return new ResponseEntity<SimularEscenarioDinamicoRes>(response, HttpStatus.OK);

        //return new ResponseEntity<SimularEscenarioDinamicoRes>(HttpStatus.NOT_IMPLEMENTED);
    }
    
    private List<CalculoValorVO> fillValues(){
    	 List<CalculoValorVO> lst=new ArrayList<>();
    	 CalculoValorVO calculoValorVO=new CalculoValorVO(143906442, "nmp-al-al-32080084",new Float(1415.00),new Float(2.000), new Float(14.00), new Float(15.00), new Float(5.00), new Float(0));
    	 lst.add(calculoValorVO);
		return lst;
    }

}

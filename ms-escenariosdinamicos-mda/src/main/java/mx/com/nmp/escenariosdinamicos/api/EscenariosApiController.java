package mx.com.nmp.escenariosdinamicos.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


import io.swagger.annotations.ApiParam;
import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.clienteoag.service.ClientOAGService;
import mx.com.nmp.escenariosdinamicos.clienteservicios.service.ClientesMicroservicios;
import mx.com.nmp.escenariosdinamicos.elastic.properties.ElasticProperties;
import mx.com.nmp.escenariosdinamicos.elastic.service.ElasticSearchAsynComponent;
import mx.com.nmp.escenariosdinamicos.elastic.service.ElasticService;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexVentasVO;
import mx.com.nmp.escenariosdinamicos.model.BadRequest;
import mx.com.nmp.escenariosdinamicos.model.Bodydiasreq;
import mx.com.nmp.escenariosdinamicos.model.CatalogoVO;
import mx.com.nmp.escenariosdinamicos.model.ConsultarEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.ConsultarEscenariosResInner;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoRes;
import mx.com.nmp.escenariosdinamicos.model.EliminarEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.InsertadoEscenario;
import mx.com.nmp.escenariosdinamicos.model.InternalServerError;
import mx.com.nmp.escenariosdinamicos.model.InvalidAuthentication;
import mx.com.nmp.escenariosdinamicos.model.ModEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.ModEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.ModificadoResponse;
import mx.com.nmp.escenariosdinamicos.model.NotFound;
import mx.com.nmp.escenariosdinamicos.model.PartidaPrecioFinal;
import mx.com.nmp.escenariosdinamicos.model.ReglaResponseDto;
import mx.com.nmp.escenariosdinamicos.model.SimularEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.SimularEscenarioDinamicoRes;
import mx.com.nmp.escenariosdinamicos.model.SuccessfulResponse;
import mx.com.nmp.escenariosdinamicos.mongodb.entity.EscenarioEntity;
import mx.com.nmp.escenariosdinamicos.mongodb.service.EscenariosService;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;
import mx.com.nmp.escenariosdinamicos.utils.EmuMacroCategoria;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-03-04T01:28:01.968Z")

@Controller
public class EscenariosApiController implements EscenariosApi {

	private static final Logger log = LoggerFactory.getLogger(EscenariosApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	private EscenariosService escenarioService;

	@Autowired
	private ElasticProperties elasticProperties;
	@Autowired
	private ClientesMicroservicios clientesMicroservicios;
	@Autowired
	private CastObjectGeneric castObjectGeneric;
	@Autowired
	private ClientOAGService clientOAGService;
	@Autowired
	private EmuMacroCategoria emuMacroCategoria;

	@Autowired
	private ElasticSearchAsynComponent elasticSearchAsynComponent;
	@Autowired
	private ElasticService elasticService;

	@org.springframework.beans.factory.annotation.Autowired
	public EscenariosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
		this.objectMapper = objectMapper;
		this.request = request;
	}

	/*
	 * Consultar escenario
	 */
	public ResponseEntity<?> consultarEscenariosGET(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalInteligenciaComercial") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "MongoDB, mockserver") @RequestHeader(value = "destino", required = true) String destino) {
		
		log.info("*********************************************************");
		log.info("Consultar escenario.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Constantes.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Constantes.MESSAGE_ERROR_AUTORIZACION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				List<ConsultarEscenariosResInner> escenario = escenarioService.consultaEscenario();
				ConsultarEscenariosRes escenariosRes = new ConsultarEscenariosRes();
				if (escenario != null) {
					log.info("Si hubo considencias.");
					escenariosRes.addAll(escenario);
					return new ResponseEntity<ConsultarEscenariosRes>(escenariosRes, HttpStatus.OK);
				} else {
					log.info("No concidencias.");
					return new ResponseEntity<ConsultarEscenariosRes>(escenariosRes, HttpStatus.OK);
				}
			} catch (Exception e) {
				log.error("Exception: {}", e);
				
				InternalServerError is = new InternalServerError();
				is.setCodigo(Constantes.ERROR_SERVER);
				is.setMensaje(Constantes.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
		br.setMensaje(Constantes.MESSAGE_ERROR_BAD_REQUEST);
		
		log.info("{}" , br);
		
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Crear Escenarios
	 */
	public ResponseEntity<?> crearEscenariosPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalInteligenciaComercial") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Peticion para crear las reglas de precios en los escenarios dinámicos") @Valid @RequestBody List<CrearEscenariosReq> escenarios) {
		
		log.info("*********************************************************");
		log.info("Crear escenario.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Constantes.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Constantes.MESSAGE_ERROR_AUTORIZACION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				if (escenarios != null) {
					log.info("peticion: {}", escenarios.size());
					List<String> repeditos=escenarioService.existenRepetidos(escenarios);
					log.info("existen repetidos: {}", repeditos.size());
					if(repeditos.size()==0){
							escenarioService.crearEscenario(escenarios);
						InsertadoEscenario in=new InsertadoEscenario();
						in.setCode(Constantes.EXITO_MODIFICAR);
						in.setMessage(Constantes.EXITO_MODIFICAR_MSG);
						return new ResponseEntity<InsertadoEscenario>(in, HttpStatus.OK);

					}else{
						InsertadoEscenario in=new InsertadoEscenario();
						in.setCode(Constantes.ERROR_REPETIDOS);
						in.setMessage(Constantes.ERROR_REPETIDOS_MENSAJE+ConcatenaEscenarioStr(repeditos)+Constantes.ERROR_REPETIDOS_COMPLEMENTO);
						return new ResponseEntity<InsertadoEscenario>(in, HttpStatus.BAD_REQUEST);
					}

				} else {
					BadRequest br = new BadRequest();
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					br.setMensaje(Constantes.MESSAGE_ERROR_BAD_REQUEST);
					
					log.info("{}" , br);
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				log.error("Exception: {}", e);
				
				InternalServerError is = new InternalServerError();
				is.setCodigo(Constantes.ERROR_SERVER);
				is.setMensaje(Constantes.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
		br.setMensaje(Constantes.MESSAGE_ERROR_BAD_REQUEST);
		
		log.info("{}" , br);
		
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Editar Escenarios
	 */
	public ResponseEntity<?> editaEscenariosPUT(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalInteligenciaComercial") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "peticion para modificar las reglas de precios en los escenarios dinámicos.") @Valid @RequestBody List<ModEscenariosReq> escenarios) {
		
		log.info("*********************************************************");
		log.info("Editar escenario.");
		log.info("*********************************************************");
		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Constantes.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Constantes.MESSAGE_ERROR_AUTORIZACION);
			log.info("{}", ia);
			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				if (escenarios != null) {
					log.info("peticion: {}", escenarios.size());
					List<Integer> lstIdsRequest =transformaIdEscenarios(escenarios);
					List<EscenarioEntity> consultaGrupoEscenarios=escenarioService.consultaGrupoEscenarios(lstIdsRequest);
					List<Integer> getEscenarios=escenarioService.transformaIds(consultaGrupoEscenarios);
					log.info("getEscenarios: {}", getEscenarios.size());
					if(getEscenarios.size()==0){
						log.info("getEscenarios: {}", getEscenarios.size());
						NotFound nf = new NotFound();
						nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
						nf.setMensaje(Constantes.MESSAGE_ERROR_CODE_NOT_FOUND+ConcatenaEscenario(lstIdsRequest)+Constantes.MESSAGE_ERROR_NOT_FOUND_COMPLEMENT);
						return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
					}else{
						log.info("consultados {}, idsServicio {}", getEscenarios.size(),lstIdsRequest.size());
						String idsNotFound=retornaIdsNoEncontrados(getEscenarios, lstIdsRequest);
						log.info("idsNotFound size [{}]",idsNotFound);
							 escenarioService.editaEscenario(escenarios);
						ModificadoResponse mod=new ModificadoResponse();
						mod.setCode(Constantes.EXITO_MODIFICAR);
						mod.setMessage(!idsNotFound.equals(Constantes.CERO)
								  ?Constantes.EXITO_MODIFICAR_MSG+Constantes.EXITO_MODIFICAR_EXCEPCION+idsNotFound+"}"
								 :Constantes.EXITO_MODIFICAR_MSG);
						 return new ResponseEntity<ModificadoResponse>(mod, HttpStatus.OK);
					}
				} else {
					BadRequest br = new BadRequest();
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					br.setMensaje(Constantes.MESSAGE_ERROR_BAD_REQUEST);
					
					log.info("{}" , br);
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Exception: {}", e);
				
				InternalServerError is = new InternalServerError();
				is.setCodigo(Constantes.ERROR_SERVER);
				is.setMensaje(Constantes.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<ModEscenariosRes>(HttpStatus.NOT_IMPLEMENTED);
	}

	/*
	 * Ejecutar Escenarios
	 */
	public ResponseEntity<?> ejecutarEscenariosDinamicosPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalInteligenciaComercial") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Peticion para crear las reglas de precios en los escenarios dinámicos") @Valid @RequestBody EjecutarEscenarioDinamicoReq crearEscenariosRequest) {
		log.info("*********************************************************");
		log.info("Ejecutar escenario dinamicos.");
		log.info("*********************************************************");
		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Constantes.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Constantes.MESSAGE_ERROR_AUTORIZACION);
			log.info("{}", ia);
			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				elasticSearchAsynComponent.consultarElasticSearch(crearEscenariosRequest, elasticProperties.getIndexVenta(), elasticProperties.getIndexGarantia());
				
			} catch (IOException e) {
				log.error("Exception: {}", e);
				InternalServerError is = new InternalServerError();
				is.setCodigo(Constantes.ERROR_SERVER);
				is.setMensaje(Constantes.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			EjecutarEscenarioDinamicoRes response = new EjecutarEscenarioDinamicoRes();
			response.setCode(Constantes.EXITO_EJECUTAR_ESCENARIODINAMICO);
			response.setMessage(Constantes.MENSAJE_OK);
			log.info("{}", response);
			return new ResponseEntity<EjecutarEscenarioDinamicoRes>(response, HttpStatus.OK);
		}
		BadRequest badRequest = new BadRequest();
		badRequest.setCodigo(Constantes.ERROR_CODE);
		badRequest.setMensaje(Constantes.ERROR_MENSAJE);
		log.info("{}", badRequest);
		return new ResponseEntity<BadRequest>(badRequest, HttpStatus.BAD_REQUEST);
	}
	

	/*
	 * Eliminar Escenarios
	 */
	public ResponseEntity<?> eliminarEscenariosDELETE(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalInteligenciaComercial") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del escenario", required = true) @RequestBody List<Integer> idEscenarios) {
		
		log.info("*********************************************************");
		log.info("Eliminar escenario.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
		List<Integer> getEscenarios=null;
		List<EscenarioEntity> escenarios=null;
		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Constantes.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Constantes.MESSAGE_ERROR_AUTORIZACION);
			log.info("{}", ia);
			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				if (idEscenarios == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					br.setMensaje(Constantes.MESSAGE_ERROR_BAD_REQUEST);
					log.info("{}" , br);
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				} else {
					escenarios=escenarioService.consultaGrupoEscenarios(idEscenarios);
					getEscenarios=escenarioService.transformaIds(escenarios);
					if(getEscenarios.size()==0){
						NotFound nf = new NotFound();
						nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
						nf.setMensaje(Constantes.MESSAGE_ERROR_CODE_NOT_FOUND+ConcatenaEscenario(idEscenarios)+Constantes.MESSAGE_ERROR_NOT_FOUND_COMPLEMENT);
						return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
					}else{
						String idsNotFound=retornaIdsNoEncontrados(getEscenarios, idEscenarios);
						 escenarioService.eliminaEscenario(getEscenarios);
						 EliminarEscenariosRes resp = new EliminarEscenariosRes();
						 resp.setCode(Constantes.EXITO_ELIMINAR);
						 resp.setMessage(!idsNotFound.equals(Constantes.CERO)
								  ?Constantes.EXITO_ELIMINAR_MSG+Constantes.EXITO_EXCEPCION+idsNotFound
								 :Constantes.EXITO_ELIMINAR_MSG);
						 return new ResponseEntity<EliminarEscenariosRes>(resp,HttpStatus.OK);
					}
				}
			} catch (Exception e) {
				log.error("Exception: {}", e);
				InternalServerError is = new InternalServerError();
				is.setCodigo(Constantes.ERROR_SERVER);
				is.setMensaje(Constantes.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest badRequest = new BadRequest();
		badRequest.setCodigo(Constantes.ERROR_CODE);
		badRequest.setMensaje(Constantes.ERROR_MENSAJE);
		
		log.info("{}", badRequest);
		
		return new ResponseEntity<BadRequest>(badRequest, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Simular Escenarios
	 */
	public ResponseEntity<?> simularEscenariosDinamicosPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalInteligenciaComercial") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Peticion para crear las reglas de precios en los escenarios dinámicos") @Valid @RequestBody SimularEscenarioDinamicoReq crearEscenariosReques) {
		
		log.info("*********************************************************");
		log.info("Simular escenarios dinamicos");
		log.info("*********************************************************");
		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Constantes.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Constantes.MESSAGE_ERROR_AUTORIZACION);
			log.info("{}", ia);
			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}
		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			ObjectMapper mapper=new ObjectMapper();
			SimularEscenarioDinamicoRes response = new SimularEscenarioDinamicoRes();
			ArrayList<PartidaPrecioFinal> lstPartidaPrecioValorMonte = new ArrayList<PartidaPrecioFinal>();
			log.info("obtencion de indices");
			List<IndexGarantiaVO> lstIndexGarantia = null;
			RequestReglaEscenarioDinamicoDto wrapperReglaEscenarioDinamico = new RequestReglaEscenarioDinamicoDto();
			List<PartidaVO> castIndexToVO = null;
			List<IndexVentasVO> scrollElasticVentas = null;
			try {
				// primero obtenemos las ventas de los ultimos tres dias
				CatalogoVO nivelAgrupacion=elasticSearchAsynComponent.deserializaNivelAgrupacion(crearEscenariosReques.getInfoRegla().getNivelAgrupacion());
				String criterio=elasticSearchAsynComponent.crearCriterioDeBusqueda(nivelAgrupacion, castObjectGeneric.convertirEjeTOSim(crearEscenariosReques));
				lstIndexGarantia = elasticService.scrollElasticGarantias(criterio,elasticProperties.getIndexGarantia());
				
				List<String> lstCuos =elasticSearchAsynComponent.extraeCuos(lstIndexGarantia);
				 scrollElasticVentas = elasticService.scrollElasticVentas(elasticProperties.getIndexVenta(),lstCuos);
				// despues consultamos las partidas a partir de las ventas
				
				lstIndexGarantia.forEach(i -> log.info(i.toString()));
				lstPartidaPrecioValorMonte = (ArrayList<PartidaPrecioFinal>)
						clientesMicroservicios.calcularValorMonte(
								castObjectGeneric.castGarantiasToCalculoValor(lstIndexGarantia), usuario, origen, destino);
				castIndexToVO = castObjectGeneric.castPartidasToPartidaValorMonte(lstIndexGarantia,
						crearEscenariosReques.getInfoRegla());
				wrapperReglaEscenarioDinamico.setPartida(castIndexToVO);
				ResponseOAGDto res=clientOAGService.reglaEscenarioDinamico(wrapperReglaEscenarioDinamico);
			} catch (Exception e) {
				log.error("Exception: {}", e);
				InternalServerError is = new InternalServerError();
				is.setCodigo(Constantes.ERROR_SERVER);
				is.setMensaje(Constantes.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.addAll(lstPartidaPrecioValorMonte);
			return new ResponseEntity<SimularEscenarioDinamicoRes>(response, HttpStatus.OK);
		}
		
		BadRequest badRequest = new BadRequest();
		badRequest.setCodigo(Constantes.ERROR_CODE);
		badRequest.setMensaje(Constantes.ERROR_MENSAJE);
		return new ResponseEntity<BadRequest>(badRequest, HttpStatus.BAD_REQUEST);
	}
	
	private static String ConcatenaEscenario(List<Integer> lstEscenarios){
		String delim = ",";
	    StringBuilder sb = new StringBuilder();
	    int i = 0;
	    while (i < lstEscenarios.size() - 1) {
	        sb.append(lstEscenarios.get(i));
	        sb.append(delim);
	        i++;
	    }
	    sb.append(lstEscenarios.get(i));
	    String res = sb.toString();
	    return res;
	}
	private String ConcatenaEscenarioStr(List<String> lstEscenarios){
		String delim = ",";
	    StringBuilder sb = new StringBuilder();
	    int i = 0;
	    while (i < lstEscenarios.size() - 1) {
	        sb.append(lstEscenarios.get(i));
	        sb.append(delim);
	        i++;
	    }
	    sb.append(lstEscenarios.get(i));
	    String res = sb.toString();
	    return res;
	}
	
	public static String retornaIdsNoEncontrados(List<Integer> escenariosEncontrados, List<Integer> escenariosRequest){
		log.info("consultados [{}], request [{}]",escenariosEncontrados.size(),escenariosRequest.size());
		List<Integer> similar = new ArrayList<>(escenariosEncontrados); 
		List<Integer> different = new ArrayList<>();  
	    different.addAll(escenariosEncontrados); 
	    different.addAll(escenariosRequest); 
	    similar.retainAll(escenariosRequest); 
	    different.removeAll(similar); 
	    log.info("diferentes::{}"+different.size());
	    if(different.size()==0)
	    	return Constantes.CERO;
	    else
	    	return ConcatenaEscenario(different);
	}
	
	
	private List<Integer> transformaIdEscenarios(List<ModEscenariosReq> escenarios){
		log.info("transformaIdEscenarios size ::[{}]",escenarios.size());
		List<Integer> listaIds=new ArrayList<>();
		escenarios.stream().forEach(escenario->{
			listaIds.add(escenario.getIdEscenario());
		});
		return listaIds;
	}

	@Override
	public ResponseEntity<?> escenariosDinamicosProbarReglasPOST(String usuario, String origen, String destino,
			@RequestBody Bodydiasreq listaDeDias) {
		log.info("*********************************************************");
		log.info("probar regla de dias.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Constantes.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Constantes.MESSAGE_ERROR_AUTORIZACION);
			log.info("{}", ia);
			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			if(usuario==null&&origen==null&&destino==null&&listaDeDias==null){
				BadRequest br = new BadRequest();
				br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
				br.setMensaje(Constantes.MESSAGE_ERROR_BAD_REQUEST);
				log.info("{}" , br);
				return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
			}else if(listaDeDias.getDias().get(0).length()>1||listaDeDias.getDias().get(1).length()>1||listaDeDias.getDias().get(2).length()>1){//Si la longitud de los dias es mayor a uno es error
				BadRequest br = new BadRequest();
				br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
				br.setMensaje(Constantes.MESSAGE_ERROR_BAD_REQUEST_DIAS);
				log.info("Los dias contienen mas de un caracter");
			}else{
				int[] dias=clientOAGService.obtenerVentas(listaDeDias.getDias());
				List<PartidaVO> partida =new ArrayList<>();
				RequestReglaEscenarioDinamicoDto request=new RequestReglaEscenarioDinamicoDto();
					PartidaVO par=new PartidaVO();
					par.setIdPartida(Constantes.SKU_OPT);
					par.setSku(Constantes.SKU_OPT);
					par.setVentasDiaUno(Long.valueOf(dias[0]));
					par.setVentasDiaDos(Long.valueOf(dias[1]));
					par.setVentasDiaTres(Long.valueOf(dias[2]));
					par.setBaseAjusteUnoPA((double) 0);
					par.setBaseAjusteUnoPM((double) 0);
					par.setBaseAjusteUnoPB((double) 0);
					par.setBaseAjusteDosPA((double) 0);
					par.setBaseAjusteDosPM((double) 0);
					par.setBaseAjusteDosPB((double) 0);
					par.setPrecioFinal((double) 0);
					par.setPrecioEtiqueta((double) 0);
					par.setCriterio(Constantes.MAX);
					par.setCandadoPA((double)0);
					par.setCandadoPB((double)0);
					par.setCandadoPM((double)0);
					par.setPrecioVenta((double)0);
					par.setMontoPrestamo((double)0);
					partida.add(par);
					partida.stream().forEach(d->
					log.info("dias"+d.getVentasDiaUno()+","+d.getVentasDiaDos()+","+d.getVentasDiaTres()+"}")
					);
					request.setPartida(partida);
					ResponseOAGDto clienteServiceOag=clientOAGService.reglaEscenarioDinamico(request);
					if(clienteServiceOag!=null&&clienteServiceOag.getPartida().get(0).getReglaAplicada().contains(Constantes.ESCENARIO)){
						ReglaResponseDto response=new ReglaResponseDto();
						response.setCodigo(Constantes.EXITO_REGLA_CODE);
						response.setExiste(Constantes.EXITO_REGLA_MSG+clienteServiceOag.getPartida().get(0).getReglaAplicada());
						return new ResponseEntity<ReglaResponseDto>(response, HttpStatus.OK);
					}else{
						NotFound nf = new NotFound();
						nf.setCodigo(Constantes.ERROR_CODE_NOT_FOUND);
						nf.setMensaje(Constantes.MESSAGE_ERROR_RULE_NOT_FOUND);
						return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
					}
					
			}
		}
		BadRequest badRequest = new BadRequest();
		badRequest.setCodigo(Constantes.ERROR_CODE);
		badRequest.setMensaje(Constantes.ERROR_MENSAJE);
		log.info("{}", badRequest);
		return new ResponseEntity<BadRequest>(badRequest, HttpStatus.BAD_REQUEST);
	}

}

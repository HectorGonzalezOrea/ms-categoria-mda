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

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.swagger.annotations.ApiParam;
import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.clienteoag.service.ClientOAGService;
import mx.com.nmp.escenariosdinamicos.clienteservicios.service.ClientesMicroservicios;
import mx.com.nmp.escenariosdinamicos.constantes.Constantes.Common;
import mx.com.nmp.escenariosdinamicos.elastic.properties.ElasticProperties;
import mx.com.nmp.escenariosdinamicos.elastic.service.ElasticService;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexVentasVO;
import mx.com.nmp.escenariosdinamicos.model.BadRequest;
import mx.com.nmp.escenariosdinamicos.model.ConsultarEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.ConsultarEscenariosResInner;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.CrearEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoRes;
import mx.com.nmp.escenariosdinamicos.model.EliminarEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.InternalServerError;
import mx.com.nmp.escenariosdinamicos.model.InvalidAuthentication;
import mx.com.nmp.escenariosdinamicos.model.ModEscenariosReq;
import mx.com.nmp.escenariosdinamicos.model.ModEscenariosRes;
import mx.com.nmp.escenariosdinamicos.model.NotFound;
import mx.com.nmp.escenariosdinamicos.model.PartidaPrecioFinal;
import mx.com.nmp.escenariosdinamicos.model.SimularEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.SimularEscenarioDinamicoRes;
import mx.com.nmp.escenariosdinamicos.model.component.ProducerMessageComponent;
import mx.com.nmp.escenariosdinamicos.mongodb.service.EscenariosService;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;


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
	@Autowired
	private ClientOAGService clientOAGService;
	@Autowired
	private ProducerMessageComponent producerMessage;

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

		String apiKeyBluemix = request.getHeader(Common.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Common.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Common.MESSAGE_ERROR_AUTORIZACION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Common.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Common.HEADER_ACCEPT_VALUE)) {
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
				is.setCodigo(Common.ERROR_SERVER);
				is.setMensaje(Common.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setCodigo(Common.ERROR_CODE_BAD_REQUEST);
		br.setMensaje(Common.MESSAGE_ERROR_BAD_REQUEST);
		
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
			@ApiParam(value = "Peticion para crear las reglas de precios en los escenarios dinámicos") @Valid @RequestBody CrearEscenariosReq crearEscenariosRequest) {
		
		log.info("*********************************************************");
		log.info("Crear escenario.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Common.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Common.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Common.MESSAGE_ERROR_AUTORIZACION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Common.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Common.HEADER_ACCEPT_VALUE)) {
			try {
				if (crearEscenariosRequest != null) {
					log.info("peticion: {}", crearEscenariosRequest.toString());
					CrearEscenariosRes esce = escenarioService.crearEscenario(crearEscenariosRequest);

					return new ResponseEntity<CrearEscenariosRes>(esce, HttpStatus.OK);

				} else {
					BadRequest br = new BadRequest();
					br.setCodigo(Common.ERROR_CODE_BAD_REQUEST);
					br.setMensaje(Common.MESSAGE_ERROR_BAD_REQUEST);
					
					log.info("{}" , br);
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				log.error("Exception: {}", e);
				
				InternalServerError is = new InternalServerError();
				is.setCodigo(Common.ERROR_SERVER);
				is.setMensaje(Common.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setCodigo(Common.ERROR_CODE_BAD_REQUEST);
		br.setMensaje(Common.MESSAGE_ERROR_BAD_REQUEST);
		
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
			@ApiParam(value = "Identificador del escenario", required = true) @PathVariable("idEscenario") Integer idEscenario,
			@ApiParam(value = "peticion para modificar las reglas de precios en los escenarios dinámicos.") @Valid @RequestBody ModEscenariosReq modEscenariosRequest) {
		
		log.info("*********************************************************");
		log.info("Editar escenario.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Common.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Common.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Common.MESSAGE_ERROR_AUTORIZACION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Common.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Common.HEADER_ACCEPT_VALUE)) {
			try {
				if (modEscenariosRequest != null) {
					log.info("peticion: {}", modEscenariosRequest.toString());
					ModEscenariosRes esce = escenarioService.editaEscenario(modEscenariosRequest);

					return new ResponseEntity<ModEscenariosRes>(esce, HttpStatus.OK);

				} else {
					BadRequest br = new BadRequest();
					br.setCodigo(Common.ERROR_CODE_BAD_REQUEST);
					br.setMensaje(Common.MESSAGE_ERROR_BAD_REQUEST);
					
					log.info("{}" , br);
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Exception: {}", e);
				
				InternalServerError is = new InternalServerError();
				is.setCodigo(Common.ERROR_SERVER);
				is.setMensaje(Common.ERROR_SERVER_MSG);
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

		String apiKeyBluemix = request.getHeader(Common.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Common.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Common.MESSAGE_ERROR_AUTORIZACION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Common.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Common.HEADER_ACCEPT_VALUE)) {

			try {
				ArrayList<PartidaPrecioFinal> lstPartidaPrecioValorMonte = new ArrayList<PartidaPrecioFinal>();
				List<IndexGarantiaVO> lstIndexGarantia = null;
				RequestReglaEscenarioDinamicoDto requestReglaEscenarioDinamico = new RequestReglaEscenarioDinamicoDto();
				List<PartidaVO> lstPartidaVO = null;
				List<IndexVentasVO> scrollElasticVentas = null;

				// primero obtenemos las ventas de los ultimos tres dias
				scrollElasticVentas = elasticService.scrollElasticVentas(elasticProperties.getIndexVenta());
				// despues consultamos las partidas a partir de las ventas
				lstIndexGarantia = elasticService.scrollElasticGarantias(elasticProperties.getIndexGarantia(),
						crearEscenariosRequest.getInfoRegla().getRamo(),
						crearEscenariosRequest.getInfoRegla().getSubramo().get(0), scrollElasticVentas);
				
				lstPartidaVO = castObjectGeneric.castPartidasToPartidaValorMonte(lstIndexGarantia,
						crearEscenariosRequest.getInfoRegla());
				requestReglaEscenarioDinamico.setPartida(lstPartidaVO);
				String jsonMessage = new Gson().toJson(requestReglaEscenarioDinamico);
				producerMessage.producerReglaEscenarioDinamico(jsonMessage);
				
			} catch (IOException e) {
				log.error("Exception: {}", e);

				InternalServerError is = new InternalServerError();
				is.setCodigo(Common.ERROR_SERVER);
				is.setMensaje(Common.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			EjecutarEscenarioDinamicoRes response = new EjecutarEscenarioDinamicoRes();
			response.setCode(Common.EXITO_EJECUTAR_ESCENARIODINAMICO);
			response.setMessage(Common.MENSAJE_OK);
			
			log.info("{}", response);
			
			return new ResponseEntity<EjecutarEscenarioDinamicoRes>(response, HttpStatus.OK);
		}

		BadRequest badRequest = new BadRequest();
		badRequest.setCodigo(Common.ERROR_CODE);
		badRequest.setMensaje(Common.ERROR_MENSAJE);
		
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
			@ApiParam(value = "Identificador del escenario", required = true) @PathVariable("idEscenario") Integer idEscenario) {
		
		log.info("*********************************************************");
		log.info("Eliminar escenario.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Common.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Common.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Common.MESSAGE_ERROR_AUTORIZACION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Common.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Common.HEADER_ACCEPT_VALUE)) {
			try {
				if (idEscenario == null) {

					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setCodigo(Common.ERROR_CODE_BAD_REQUEST);
					br.setMensaje(Common.MESSAGE_ERROR_BAD_REQUEST);
					
					log.info("{}" , br);
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				} else {
					Object resp = escenarioService.eliminaEscenario(idEscenario);
					
					if(resp instanceof EliminarEscenariosRes) {
						return new ResponseEntity<EliminarEscenariosRes>((EliminarEscenariosRes)resp, HttpStatus.OK);
					} else if(resp instanceof InternalServerError) {
						return new ResponseEntity<InternalServerError>((InternalServerError)resp, HttpStatus.INTERNAL_SERVER_ERROR);
					} else if(resp instanceof NotFound) {
						return new ResponseEntity<NotFound>((NotFound)resp, HttpStatus.NOT_FOUND);
					}
				}
			} catch (Exception e) {
				log.error("Exception: {}", e);
				
				InternalServerError is = new InternalServerError();
				is.setCodigo(Common.ERROR_SERVER);
				is.setMensaje(Common.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest badRequest = new BadRequest();
		badRequest.setCodigo(Common.ERROR_CODE);
		badRequest.setMensaje(Common.ERROR_MENSAJE);
		
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

		String apiKeyBluemix = request.getHeader(Common.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Common.ERROR_CODE_AUTORIZACION);
			ia.setMessage(Common.MESSAGE_ERROR_AUTORIZACION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Common.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Common.HEADER_ACCEPT_VALUE)) {
			SimularEscenarioDinamicoRes response = new SimularEscenarioDinamicoRes();
			ArrayList<PartidaPrecioFinal> lstPartidaPrecioValorMonte = new ArrayList<PartidaPrecioFinal>();
			log.info("obtencion de indices");
			List<IndexGarantiaVO> lstIndexGarantia = null;
			RequestReglaEscenarioDinamicoDto wrapperReglaEscenarioDinamico = new RequestReglaEscenarioDinamicoDto();
			List<PartidaVO> castIndexToVO = null;
			List<IndexVentasVO> scrollElasticVentas = null;
			try {
				// primero obtenemos las ventas de los ultimos tres dias
				scrollElasticVentas = elasticService.scrollElasticVentas(elasticProperties.getIndexVenta());
				// despues consultamos las partidas a partir de las ventas
				lstIndexGarantia = elasticService.scrollElasticGarantias(elasticProperties.getIndexGarantia(),
						crearEscenariosReques.getInfoRegla().getRamo(),
						crearEscenariosReques.getInfoRegla().getSubramo().get(0), scrollElasticVentas);
				lstIndexGarantia.forEach(i -> log.info(i.toString()));
				lstPartidaPrecioValorMonte = (ArrayList<PartidaPrecioFinal>) clientesMicroservicios.calcularValorMonte(
						castObjectGeneric.castGarantiasToCalculoValor(lstIndexGarantia), usuario, origen, destino);
				castIndexToVO = castObjectGeneric.castPartidasToPartidaValorMonte(lstIndexGarantia,
						crearEscenariosReques.getInfoRegla());
				wrapperReglaEscenarioDinamico.setPartida(castIndexToVO);
				clientOAGService.reglaEscenarioDinamico(wrapperReglaEscenarioDinamico);
			} catch (Exception e) {
				log.error("Exception: {}", e);

				InternalServerError is = new InternalServerError();
				is.setCodigo(Common.ERROR_SERVER);
				is.setMensaje(Common.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.addAll(lstPartidaPrecioValorMonte);
			return new ResponseEntity<SimularEscenarioDinamicoRes>(response, HttpStatus.OK);
		}
		
		BadRequest badRequest = new BadRequest();
		badRequest.setCodigo(Common.ERROR_CODE);
		badRequest.setMensaje(Common.ERROR_MENSAJE);
		return new ResponseEntity<BadRequest>(badRequest, HttpStatus.BAD_REQUEST);
	}
}

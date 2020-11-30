package mx.com.nmp.gestionbolsas.api;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.gestionbolsas.model.BadRequest;
import mx.com.nmp.gestionbolsas.model.Bolsa;
import mx.com.nmp.gestionbolsas.model.GeneralResponse;
import mx.com.nmp.gestionbolsas.model.InternalServerError;
import mx.com.nmp.gestionbolsas.model.InvalidAuthentication;
import mx.com.nmp.gestionbolsas.model.ListaBolsas;
import mx.com.nmp.gestionbolsas.model.ListaTipoBolsas;
import mx.com.nmp.gestionbolsas.mongodb.service.BolsasService;
import mx.com.nmp.gestionbolsas.utils.BolsaUtils;
import mx.com.nmp.gestionbolsas.utils.Constantes;

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
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

import static mx.com.nmp.gestionbolsas.utils.Constantes.HEADER_APIKEY_KEY;
import static mx.com.nmp.gestionbolsas.utils.Constantes.HEADER_ACCEPT_KEY;
import static mx.com.nmp.gestionbolsas.utils.Constantes.HEADER_ACCEPT_VALUE;
import static mx.com.nmp.gestionbolsas.utils.Constantes.CADENA_VACIA;

import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_CODE_INVALID_AUTHENTICATION;
import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION;
import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_CODE_INTERNAL_SERVER_ERROR;
import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_MESSAGE_INTERNAL_SERVER_ERROR;
import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_CODE_BAD_REQUEST;
import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_MESSAGE_BAD_REQUEST;
import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_MESSAGE_NAME;
import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_MESSAGE_TIPO;
import static mx.com.nmp.gestionbolsas.utils.Constantes.MESSAGE_DELETE;
import static mx.com.nmp.gestionbolsas.utils.Constantes.MESSAGE_NO_DELETE;
import static mx.com.nmp.gestionbolsas.utils.Constantes.MESSAGE_OK_BOLSA;
import static mx.com.nmp.gestionbolsas.utils.Constantes.ERROR_MESSAGE_BOLSA_DUPLICADA;

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
    	objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
        this.objectMapper = objectMapper;
        this.request = request;
    }

	/*
	 * Get Bolsa
	 */
	public ResponseEntity<?> bolsasGet(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = false) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = " Identificador del tipo de bolsa a buscar") @Valid @RequestParam(value = "idTipo", required = false) String idTipo,
			@ApiParam(value = "Nombre de la Bolsa") @Valid @RequestParam(value = "nombre", required = false) String nombre,
			@ApiParam(value = "Ramo configurado en la Bolsa") @Valid @RequestParam(value = "ramo", required = false) String ramo,
			@ApiParam(value = "Subramo configurado en la Bolsa") @Valid @RequestParam(value = "subramo", required = false) String subramo,
			@ApiParam(value = "Factor configurado en la Bolsa") @Valid @RequestParam(value = "factor", required = false) String factor,
			@ApiParam(value = "origen configurado en la Bolsa") @Valid @RequestParam(value = "origen", required = false) String origen,
			@ApiParam(value = "categoria configurado en la Bolsa") @Valid @RequestParam(value = "categoria", required = false) String categoria
			) {

		log.info("*********************************************************");
		log.info("Consultar Bolsas.");
		log.info("*********************************************************");
		System.out.println("parameters "+nombre);
		String apiKey = request.getHeader(HEADER_APIKEY_KEY);

		if (apiKey == null || apiKey.equals(CADENA_VACIA)) {

			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
			ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);

			log.error("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(HEADER_ACCEPT_KEY);

		if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
			try {
				log.info("Consulta Bolsas");
				if (idTipo == null && nombre == null && ramo == null && subramo == null && factor == null) {
					ListaBolsas list = bolsaService.getBolsasSinFiltro();
					
					log.info("{}", list);
					
					return new ResponseEntity<ListaBolsas>(list, HttpStatus.OK);
				} else {
					ListaBolsas listaBolsa = bolsaService.getBolsas(idTipo, nombre, ramo, subramo, factor, origen, categoria);

					ListaBolsas listBol = new ListaBolsas();
					if (!listaBolsa.isEmpty()) {
						log.info("Resultado: {}", listaBolsa);
						listBol.addAll(listaBolsa);
						
						log.info("{}", listBol);
						
						return new ResponseEntity<ListaBolsas>(listBol, HttpStatus.OK);
					} else {
						
						log.info("{}", listBol);
						return new ResponseEntity<ListaBolsas>(listBol, HttpStatus.OK);
					}
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
				ie.setMessage(ERROR_MESSAGE_INTERNAL_SERVER_ERROR);

				log.error("{}", ie);
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
		br.setCode(ERROR_CODE_BAD_REQUEST);

		log.error("{}" , br);
		
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Eliminar bolsa por id
	 */
	@Override
	public ResponseEntity<?> bolsasIdBolsaDelete(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = true) 
			@RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Identificador de la Bolsa a eliminar", required = true)
			@PathVariable("idBolsa") Integer idBolsa
			) {
		log.info("*********************************************************");
		log.info("Eliminar Bolsa.");
		log.info("*********************************************************");
		
		String apiKey = request.getHeader(HEADER_APIKEY_KEY);

		if (apiKey == null || apiKey.equals(CADENA_VACIA)) {

			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
			ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);

			log.error("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
			try {

				if (idBolsa == null) {
					log.error("Error en el mensaje de petición, verifique la información");
					BadRequest br = new BadRequest();
					br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
					br.setCode(ERROR_CODE_BAD_REQUEST);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				} else {
					Boolean eliminado = bolsaService.deleteBolsa(idBolsa);
					GeneralResponse resp = new GeneralResponse();
					if (eliminado) {
						resp.setMessage(MESSAGE_DELETE);
						return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
					} else {
						resp.setMessage(MESSAGE_NO_DELETE);
						return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
					}
				}

			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
				ie.setMessage(ERROR_MESSAGE_INTERNAL_SERVER_ERROR);

				log.error("{}", ie);
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
		br.setCode(ERROR_CODE_BAD_REQUEST);

		log.error("{}" , br);
		
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Actualizar Bolsas
	 */
	@Override
	public ResponseEntity<?> bolsasPatch(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = true)
			@RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Cuerpo de la petición", required = true) 
			@Valid @RequestBody Bolsa peticion
			) {
		log.info("*********************************************************");
		log.info("Actualizar Bolsas.");
		log.info("*********************************************************");
		BolsaUtils utils= new BolsaUtils();
		
		String apiKey = request.getHeader(HEADER_APIKEY_KEY);

		if (apiKey == null || apiKey.equals(CADENA_VACIA)) {

			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
			ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);

			log.error("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
			try {
				if (peticion != null) {
					log.info("peticion: " + peticion.toString());
					if(peticion.getSucursales()!=null){
						Boolean bandera=bolsaService.validarActualizarBolsas(utils.paseraLista(peticion.getSucursales()));
						if(bandera==true){
							BadRequest br = new BadRequest();
							br.setCode(ERROR_CODE_BAD_REQUEST);
							br.setMessage(Constantes.ERROR_MESSGE_BOLSA);

							log.error("{}", br);

							return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
						}	
					}
					
					if(Boolean.TRUE.equals(bolsaService.validaRamoSubramoFactor(peticion.getRamo(),peticion.getSubramo(),peticion.getFactor()))) {
						BadRequest br = new BadRequest();
						br.setCode(ERROR_CODE_BAD_REQUEST);
						br.setMessage(ERROR_MESSAGE_BOLSA_DUPLICADA);
						log.error("{}", br);
						return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
					}
					
					Boolean actualizado = bolsaService.updateBolsa(peticion);

					if (actualizado) {
						GeneralResponse resp = new GeneralResponse();
						resp.setMessage("Bolsa actualizada correctamente.");

						return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);

					} else {
						InternalServerError ie = new InternalServerError();
						ie.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
						ie.setMessage(ERROR_MESSAGE_INTERNAL_SERVER_ERROR);

						return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					BadRequest br = new BadRequest();
					br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
					br.setCode(ERROR_CODE_BAD_REQUEST);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
				ie.setMessage(ERROR_MESSAGE_INTERNAL_SERVER_ERROR);

				log.error("{}", ie);
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
		br.setCode(ERROR_CODE_BAD_REQUEST);

		log.error("{}" , br);
		
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

	/*
	 * Crear bolsa
	 */
	@Override
	public ResponseEntity<?> bolsasPost(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Cuerpo de la petición", required = true) @Valid @RequestBody Bolsa peticion) {
		log.info("*********************************************************");
		log.info("Crear Bolsa");
		log.info("*********************************************************");

		String apiKey = request.getHeader(HEADER_APIKEY_KEY);

		if (apiKey == null || apiKey.equals(CADENA_VACIA)) {

			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
			ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);

			log.error("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
			try {
				if(peticion != null) {
					log.info("peticion: {}" , peticion);
					
					//Boolean validacion = bolsaService.validarBolsas(peticion.getRamo(), peticion.getSubramo(), peticion.getFactor(), peticion.getSucursales());
					if(!peticion.getSucursales().isEmpty()) {
						Boolean validacion = bolsaService.validarExistenciaBolsas(peticion.getSucursales());
						if (Boolean.TRUE.equals(validacion)) {
							BadRequest br = new BadRequest();
							br.setCode(ERROR_CODE_BAD_REQUEST);
							br.setMessage(Constantes.ERROR_MESSGE_BOLSA);

							log.error("{}", br);

							return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
						}else if(Boolean.TRUE.equals(bolsaService.validaRamoSubramoFactor(peticion.getRamo(),peticion.getSubramo(),peticion.getFactor()))) {
							BadRequest br = new BadRequest();
							br.setCode(ERROR_CODE_BAD_REQUEST);
							br.setMessage(ERROR_MESSAGE_BOLSA_DUPLICADA);
							log.error("{}", br);
							return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
						} else {
							if (Boolean.TRUE.equals(bolsaService.consultaBolsa(peticion.getNombre()))) {
								BadRequest br = new BadRequest();
								br.setCode(ERROR_CODE_BAD_REQUEST);
								br.setMessage(ERROR_MESSAGE_NAME);

								log.error("{}", br);

								return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
							}
							if(peticion.getTipo() == null) {
								BadRequest br = new BadRequest();
								br.setCode(ERROR_CODE_BAD_REQUEST);
								br.setMessage(ERROR_MESSAGE_TIPO);

								log.error("{}", br);

								return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
							} else {
								if (Boolean.FALSE.equals(bolsaService.consultaTipoBolsa(peticion.getTipo().getId()))) {
									BadRequest br = new BadRequest();
									br.setCode(ERROR_CODE_BAD_REQUEST);
									br.setMessage(ERROR_MESSAGE_TIPO);

									log.error("{}", br);

									return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);

								} else {
									Boolean tipoBolsa = bolsaService.consultaTipoBolsaRegla(peticion.getTipo().getId());
									log.info("Tipo Bolsa", tipoBolsa);
									if (Boolean.TRUE.equals(tipoBolsa)) {
										if ((peticion.getFactor() == null || peticion.getFactor().equals(CADENA_VACIA))
												|| (peticion.getRamo() == null || peticion.getRamo().equals(CADENA_VACIA))
												|| (peticion.getSubramo() == null || peticion.getSubramo().equals(CADENA_VACIA))) {
											
											BadRequest br = new BadRequest();
											br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
											br.setCode(ERROR_CODE_BAD_REQUEST);
											
											log.error("{}", br);
											
											return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
										} else {
											Boolean insertado = bolsaService.crearBolsa(peticion);
											if (Boolean.TRUE.equals(insertado)) {
												GeneralResponse resp = new GeneralResponse();
												resp.setMessage(MESSAGE_OK_BOLSA);

												log.info("{}", resp);
												
												return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
											} else {
												InternalServerError ie = new InternalServerError();
												ie.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
												ie.setMessage("Error interno del servidor");

												log.error("{}", ie);
												
												return new ResponseEntity<InternalServerError>(ie,
														HttpStatus.INTERNAL_SERVER_ERROR);
											}
										}
									} else {
										
											Boolean insertado = bolsaService.crearBolsa(peticion);
											if (insertado) {
												GeneralResponse resp = new GeneralResponse();
												resp.setMessage(MESSAGE_OK_BOLSA);

												return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
											} else {
												InternalServerError ie = new InternalServerError();
												ie.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
												ie.setMessage("Error interno del servidor");

												log.error("{}", ie);
												
												return new ResponseEntity<InternalServerError>(ie,
														HttpStatus.INTERNAL_SERVER_ERROR);
											}
										
									}
								}
							}
						}
					} else {
						BadRequest br = new BadRequest();
						br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
						br.setCode(ERROR_CODE_BAD_REQUEST);
						
						log.error("{}", br);
						
						return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
					}
				} else {
					BadRequest br = new BadRequest();
					br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
					br.setCode(ERROR_CODE_BAD_REQUEST);
					
					log.error("{}", br);
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
				ie.setMessage(ERROR_MESSAGE_INTERNAL_SERVER_ERROR);

				log.error("{}", ie);

				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		BadRequest br = new BadRequest();
		br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
		br.setCode(ERROR_CODE_BAD_REQUEST);

		log.error("{}", br);

		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}			
					
	/*
	 * Consultar tipo de bolsa
	 */
	@Override
	public ResponseEntity<?> bolsasTiposGet(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = true)
	       @RequestHeader(value = "usuario", required = true) String usuario) {
		
		log.info("*********************************************************");
		log.info("Consultar Bolsa por su tipo");
		log.info("*********************************************************");
		
		String apiKey = request.getHeader(HEADER_APIKEY_KEY);

		if (apiKey == null || apiKey.equals(CADENA_VACIA)) {

			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
			ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);

			log.error("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
			BadRequest br = new BadRequest();
			try {
				if (usuario == null) {
					br.setCode(ERROR_CODE_BAD_REQUEST);
					br.setMessage(ERROR_MESSAGE_BAD_REQUEST);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
				ListaTipoBolsas response = bolsaService.consultaTipoBolsa();

				if (!response.isEmpty()) {
					log.info("Result: {}", response);
					return new ResponseEntity<ListaTipoBolsas>(response, HttpStatus.OK);
				} else {
					log.info("No se encontro nada");
					return new ResponseEntity<ListaTipoBolsas>(response, HttpStatus.BAD_REQUEST);
				}

			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ie = new InternalServerError();
				ie.setCode(ERROR_CODE_INTERNAL_SERVER_ERROR);
				ie.setMessage(ERROR_MESSAGE_INTERNAL_SERVER_ERROR);

				log.error("{}", ie);
				
				return new ResponseEntity<InternalServerError>(ie, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();
		br.setMessage(ERROR_MESSAGE_BAD_REQUEST);
		br.setCode(ERROR_CODE_BAD_REQUEST);

		log.error("{}" , br);
		
		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

}

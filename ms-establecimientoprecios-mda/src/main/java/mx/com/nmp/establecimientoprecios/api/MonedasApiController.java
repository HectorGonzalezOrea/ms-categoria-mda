package mx.com.nmp.establecimientoprecios.api;

import com.fasterxml.jackson.databind.MapperFeature;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ACCEPT_KEY;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_ACCEPT_VALUE;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.HEADER_APIKEY_KEY;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.ERROR_CODE_BAD_REQUEST;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.ERROR_MESSAGE_BAD_REQUEST_0;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.ERROR_MESSAGE_BAD_REQUEST_1;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.ERROR_MESSAGE_BAD_REQUEST_2;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.ERROR_CODE_INTERNAL_SERVER;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.ERROR_MESSAGE_INTERNAL_SERVER;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.ERROR_CODE_INVALID_AUTHENTICATION;
import static mx.com.nmp.establecimientoprecios.utils.Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION;

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
		objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
		this.objectMapper = objectMapper;
		this.request = request;
	}

	public ResponseEntity<?> monedasPatch(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Cuerpo de la petición", required = true) @Valid @RequestBody ListaMonedas peticion) {
		log.info("Actualizar la configuración de Monedas");

		String accept = request.getHeader(HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
			try {
				String apikey = request.getHeader(HEADER_APIKEY_KEY);

				log.info("Usuario: " + usuario);

				if (apikey == null || apikey.equals("")) {
					InvalidAuthentication ia = new InvalidAuthentication();
					ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
					ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);

					return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
				}

				if (usuario == null || usuario.equals("")) {
					BadRequest br = new BadRequest();

					br.setCode(ERROR_CODE_BAD_REQUEST);
					br.setMessage(ERROR_MESSAGE_BAD_REQUEST_1);

					log.error("{}", br);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}

				if (peticion != null) {
					log.info("Request:" + peticion.toString());
					GeneralResponse resp = monedasService.actualizarMonedas(peticion);
					log.info("{}", resp);
					return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
				} else {
					BadRequest br = new BadRequest();

					br.setCode(ERROR_CODE_BAD_REQUEST);
					br.setMessage(ERROR_MESSAGE_BAD_REQUEST_1);

					log.error("{}", br);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				InternalServerError ise = new InternalServerError();
				ise.setCode(ERROR_CODE_INTERNAL_SERVER);
				ise.setMessage(ERROR_MESSAGE_INTERNAL_SERVER);
				log.error("{}", ise);
				return new ResponseEntity<InternalServerError>(ise, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = new BadRequest();

		br.setCode(ERROR_CODE_BAD_REQUEST);
		br.setMessage(ERROR_MESSAGE_BAD_REQUEST_1);

		log.error("{}", br);

		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<?> monedasPost(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Cuerpo de la petición", required = true) @Valid @RequestBody ListaMonedas peticion) {
		log.info("Almacenar configuración de Monedas");

		String accept = request.getHeader(HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(HEADER_ACCEPT_VALUE)) {
			try {
				String apikey = request.getHeader(HEADER_APIKEY_KEY);

				log.info("Usuario: " + usuario);

				if (apikey == null || apikey.equals("")) {

					InvalidAuthentication ia = new InvalidAuthentication();
					ia.setCode(ERROR_CODE_INVALID_AUTHENTICATION);
					ia.setMessage(ERROR_MESSAGE_INVALID_AUTHENTICATION);

					return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.INTERNAL_SERVER_ERROR);
				}

				if (usuario == null || usuario.equals("")) {
					BadRequest br = new BadRequest();

					br.setCode(ERROR_CODE_BAD_REQUEST);
					br.setMessage(ERROR_MESSAGE_BAD_REQUEST_1);

					log.error("{}", br);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}

				if (peticion != null) {
					log.info("Request:" + peticion.toString());
					GeneralResponse resp = monedasService.agregarMonedas(peticion);

					return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
				} else {
					BadRequest br = new BadRequest();

					br.setCode(ERROR_CODE_BAD_REQUEST);
					br.setMessage(ERROR_MESSAGE_BAD_REQUEST_1);

					log.error("{}", br);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);

				InternalServerError ise = new InternalServerError();
				ise.setCode(ERROR_CODE_INTERNAL_SERVER);
				ise.setMessage(ERROR_MESSAGE_INTERNAL_SERVER);
				log.error("{}", ise);
				return new ResponseEntity<InternalServerError>(ise, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		BadRequest br = new BadRequest();

		br.setCode(ERROR_CODE_BAD_REQUEST);
		br.setMessage(ERROR_MESSAGE_BAD_REQUEST_1);

		log.error("{}", br);

		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

}

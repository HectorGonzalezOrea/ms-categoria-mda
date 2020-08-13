package mx.com.nmp.historicoprecios.api;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.historicoprecios.elastic.service.HistoricoPreciosService;
import mx.com.nmp.historicoprecios.model.BadRequest;
import mx.com.nmp.historicoprecios.model.GeneralResponse;
import mx.com.nmp.historicoprecios.model.HistoricoPrecios;
import mx.com.nmp.historicoprecios.model.InternalServerError;
import mx.com.nmp.historicoprecios.model.InvalidAuthentication;
import mx.com.nmp.historicoprecios.utils.Constantes;

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
		objectMapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, false);
		this.objectMapper = objectMapper;
		this.request = request;
	}

	public ResponseEntity<?> historicoPreciosPost(
			@ApiParam(value = "Usuario de sistema que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Cuerpo de la petición", required = true) @Valid @RequestBody HistoricoPrecios peticion) {

		log.info("*********************************************************");
		log.info("Historico de precios.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);

		if (apiKeyBluemix == null || apiKeyBluemix.equals("")) {
			InvalidAuthentication ia = new InvalidAuthentication();
			ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
			ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);

			log.info("{}", ia);

			return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED);
		}

		String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {

				log.info("Usuario: " + usuario);

				GeneralResponse gr = null;
				BadRequest br = null;
				InternalServerError ise = null;

				if (usuario == null) {
					br = new BadRequest();
					br.setCode(Constantes.ERROR_CODE_BAD_REQUEST);
					br.setMessage(Constantes.ERROR_MESSAGE_BAD_REQUEST);

					log.info("{}", br);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}

				if (peticion != null) {

					log.info("{}: " + peticion);

					if (peticion.getFecha() == null || peticion.getFolioPartida() == null
							|| peticion.getPrecioActual() == null || peticion.getPrecioModificado() == null
							|| peticion.getSku() == null || peticion.getFolioPartida().equals("")
							|| peticion.getSku().equals("") || !(peticion.getPrecioActual() instanceof Float)
							|| !(peticion.getPrecioModificado() instanceof Float)) {

						br = new BadRequest();
						br.setCode(Constantes.ERROR_CODE_BAD_REQUEST);
						br.setMessage(Constantes.ERROR_MESSAGE_BAD_REQUEST);

						log.info("{}", br);

						return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
					} else {
						Boolean insertado = historicoPreciosService.insertarHistoricoPrecios(peticion, usuario);

						if (insertado) {
							gr = new GeneralResponse();
							gr.setMessage(Constantes.ERROR_MESSAGE_SUCCESS);

							log.info("{}", gr);

							return new ResponseEntity<GeneralResponse>(gr, HttpStatus.OK);
						} else {
							ise = new InternalServerError();
							ise.setCode(Constantes.ERROR_CODE_INTERNAL_ERROR);
							ise.setMessage(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);

							log.info("{}", ise);

							return new ResponseEntity<InternalServerError>(ise, HttpStatus.INTERNAL_SERVER_ERROR);
						}
					}
				} else {
					br = new BadRequest();
					br.setCode(Constantes.ERROR_CODE_BAD_REQUEST);
					br.setMessage(Constantes.ERROR_MESSAGE_BAD_REQUEST);

					log.info("{}", br);

					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				}
			} catch (Exception e) {
				InternalServerError ise = new InternalServerError();
				ise.setCode(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ise.setMessage(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);

				log.info("{}", ise);

				return new ResponseEntity<InternalServerError>(ise, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		BadRequest br = null;

		br = new BadRequest();
		br.setCode(Constantes.ERROR_CODE_BAD_REQUEST);
		br.setMessage(Constantes.ERROR_MESSAGE_BAD_REQUEST);

		log.info("{}", br);

		return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
	}

}

package mx.com.nmp.categorias.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;
import mx.com.nmp.categorias.cast.CastMongoObjToVo;
import mx.com.nmp.categorias.model.BadRequest;
import mx.com.nmp.categorias.model.Categoria;
import mx.com.nmp.categorias.model.GeneralResponse;
import mx.com.nmp.categorias.model.InternalServerError;
import mx.com.nmp.categorias.model.InvalidAuthentication;
import mx.com.nmp.categorias.model.NotFound;
import mx.com.nmp.categorias.model.ResponseGetCategorias;
import mx.com.nmp.categorias.service.CategoriaServiceImp;
import mx.com.nmp.categorias.utils.Constantes;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-11-12T23:53:03.246Z")

@Controller
public class CategoriasApiController implements CategoriasApi {

	private static final Logger log = LoggerFactory.getLogger(CategoriasApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;

	@Autowired
	private CategoriaServiceImp categoriaService;
	@Autowired
	private CastMongoObjToVo castMongoObjToVo;

	@org.springframework.beans.factory.annotation.Autowired
	public CategoriasApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	public ResponseEntity<?> categoriasGet(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalInteligenciaComercial") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino) {
		log.info("*********************************************************");
		log.info("consultar categorias");
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
			if (usuario == null && origen == null && destino == null) {
				BadRequest br = new BadRequest();
				br.setCode(Constantes.ERROR_CODE_BAD_REQUEST);
				br.setMessage(Constantes.MESSAGE_ERROR_BAD_REQUEST);
				log.info("{}", br);
				return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
			}
			try {
				List<Categoria> categorias = castMongoObjToVo.convierteDatos(categoriaService.consultaCategorias());
				List<Categoria> otros = castMongoObjToVo.convierteDatos(categoriaService.consultaOtros());
				if (!categorias.isEmpty() && !otros.isEmpty()) {
					ResponseGetCategorias response = new ResponseGetCategorias();
					response.setCategorias(categorias);
					response.setOtros(otros);
					return new ResponseEntity<ResponseGetCategorias>(response, HttpStatus.OK);
				}else{
					NotFound nf=new NotFound();
					nf.setCodigo(Constantes.ERROR_NOT_FOUND);
					nf.setMensaje(Constantes.ERROR_NOT_FOUND_MSG);
					return new ResponseEntity<NotFound>(nf, HttpStatus.NOT_FOUND);
				}
			} catch (Exception e) {
				log.error("Exception: {}", e);
				InternalServerError is = new InternalServerError();
				is.setCode(Constantes.ERROR_SERVER);
				is.setMessage(Constantes.ERROR_SERVER_MSG);
				return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} else {
			BadRequest br = new BadRequest();
			br.setMessage(Constantes.MESSAGE_ERROR_BAD_REQUEST);
			br.setCode(Constantes.ERROR_CODE_BAD_REQUEST);
			return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> categoriasGuardarconfiguracionPost(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalInteligenciaComercial") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "guardar la configuración de las categorias y otros") @Valid @RequestBody ResponseGetCategorias categoriasbody) {
		log.info("*********************************************************");
		log.info("Guardar caetgorias");
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
			if (usuario == null && origen == null && destino == null && categoriasbody == null) {
				BadRequest br = new BadRequest();
				br.setCode(Constantes.ERROR_CODE_BAD_REQUEST);
				br.setMessage(Constantes.MESSAGE_ERROR_BAD_REQUEST);
				log.info("{}", br);
				return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
			} else {
				try {
					Boolean insertado = categoriaService.configurarCategorias(categoriasbody);
					if (insertado) {
						GeneralResponse resp = new GeneralResponse();
						resp.setMessage(Constantes.ACCEPT_INSERT);
						return new ResponseEntity<GeneralResponse>(resp, HttpStatus.OK);
					}else{
						GeneralResponse resp = new GeneralResponse();
						resp.setMessage(Constantes.ERROR_INSERT);
						return new ResponseEntity<GeneralResponse>(resp, HttpStatus.CONFLICT);
					}
				} catch (Exception e) {
					log.error("Exception: {}", e);
					InternalServerError is = new InternalServerError();
					is.setCode(Constantes.ERROR_SERVER);
					is.setMessage(Constantes.ERROR_SERVER_MSG);
					return new ResponseEntity<InternalServerError>(is, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			BadRequest br = new BadRequest();
			br.setMessage(Constantes.MESSAGE_ERROR_BAD_REQUEST);
			br.setCode(Constantes.ERROR_CODE_BAD_REQUEST);
			return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
		}
	}

}

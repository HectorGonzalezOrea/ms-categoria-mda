package mx.com.nmp.valormonte.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.valormonte.utils.Constantes;
import mx.com.nmp.valormonte.utils.ConverterUtil;
import mx.com.nmp.valormonte.elastic.vo.ResponseElasticVO;
import mx.com.nmp.valormonte.model.BadRequest;
import mx.com.nmp.valormonte.model.CalculoValorMonteReq;
import mx.com.nmp.valormonte.model.CalculoValorMonteReqInner;
import mx.com.nmp.valormonte.model.CalculoValorMonteRes;
import mx.com.nmp.valormonte.model.CalculoValorMonteResInner;
import mx.com.nmp.valormonte.model.InternalServerError;
import mx.com.nmp.valormonte.model.InvalidAuthentication;
import mx.com.nmp.valormonte.service.ValorMonteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullFields;
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
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-30T17:34:52.695Z")

@Controller
public class ValorMonteApiController implements ValorMonteApi {

	private static final Logger log = LoggerFactory.getLogger(ValorMonteApiController.class);

	private final ObjectMapper objectMapper;

	private final HttpServletRequest request;
	@Autowired
	private ValorMonteService valorMonteService;

	@org.springframework.beans.factory.annotation.Autowired
	public ValorMonteApiController(ObjectMapper objectMapper, HttpServletRequest request) {
		this.objectMapper = objectMapper;
		this.request = request;
	}

	public ResponseEntity<?> calcularValorMontePOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información", required = true, allowableValues = "bluemix, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "", required = true) @Valid @RequestBody CalculoValorMonteReq valorMonteReq) {

		log.info("*********************************************************");
		log.info("Calcular Valor Monte.");
		log.info("*********************************************************");

		String apiKeyBluemix = request.getHeader(Constantes.HEADER_APIKEY_KEY);
    	
    	if(apiKeyBluemix == null || apiKeyBluemix.equals("")) {
    		InvalidAuthentication ia = new InvalidAuthentication();
    		ia.setCode(Constantes.ERROR_CODE_INVALID_AUTHENTICATION);
    		ia.setMessage(Constantes.ERROR_MESSAGE_INVALID_AUTHENTICATION);
    		
    		log.info("{}" , ia);
    		
    		return new ResponseEntity<InvalidAuthentication>(ia, HttpStatus.UNAUTHORIZED); 
    	}
		
    	String accept = request.getHeader(Constantes.HEADER_ACCEPT_KEY);
		if (accept != null && accept.contains(Constantes.HEADER_ACCEPT_VALUE)) {
			try {
				CalculoValorMonteRes resp;

				if (valorMonteReq.isEmpty()) {
					log.info("Request Invalido");
					BadRequest br = new BadRequest();
					br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
					br.mensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
					
					log.info("{}" , br);
					
					return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
				} else {
					
					log.info("{}" , valorMonteReq);
					
					if(Boolean.TRUE.equals(this.validarCalculoValorMonteReq(valorMonteReq))) {
						resp = valorMonteService.calcularValorMonte(valorMonteReq);
						if (resp != null) {
							log.info("{}", resp);

							return new ResponseEntity<CalculoValorMonteRes>(resp, HttpStatus.OK);
						} else {
							log.error("No se pudo hacer el calculo");
							InternalServerError ise = new InternalServerError();
							ise.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
							ise.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
							
							log.info("{}" , ConverterUtil.messageToJson(ise));
							
							return new ResponseEntity<InternalServerError>(ise, HttpStatus.INTERNAL_SERVER_ERROR);
					
						}
					} else {
						log.info("Request Invalido");
						BadRequest br = new BadRequest();
						br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
						br.mensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
						
						log.info("{}" , br);
						
						return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
					}
				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ise = new InternalServerError();

				ise.setCodigo(Constantes.ERROR_CODE_INTERNAL_ERROR);
				ise.setMensaje(Constantes.ERROR_MESSAGE_INTERNAL_ERROR);
				
				log.info("{}" , ise);
				return new ResponseEntity<InternalServerError>(ise, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			BadRequest br = new BadRequest();

			br.setCodigo(Constantes.ERROR_CODE_BAD_REQUEST);
			br.mensaje(Constantes.ERROR_MESSAGE_BAD_REQUEST);
			
			log.info("{}" , br);
			return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
		}
	}

	private Boolean validarCalculoValorMonteReq(CalculoValorMonteReq req) {
		log.info("validarCalculoValorMonteReq");
		Boolean validado = true;

		if (!req.isEmpty()) {
			for (CalculoValorMonteReqInner r : req) {
				if (r.getIdPartida() == null || r.getSKU() == null)
					return false;
				/* if (r.getIdPartida() == null || r.getSKU() == null || r.getAvaluoComplementario() == null
						|| r.getDesplazamiento() == null || r.getGramaje() == null || r.getIncremento() == null
						|| r.getKilataje() == null || r.getValorAncla() == null)
					return false;
				*/
			}
		}

		return validado;
	}

}

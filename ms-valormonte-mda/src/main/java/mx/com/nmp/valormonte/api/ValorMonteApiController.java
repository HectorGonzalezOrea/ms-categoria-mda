package mx.com.nmp.valormonte.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.valormonte.elastic.service.ValorMonteService;
import mx.com.nmp.valormonte.elastic.vo.ValorMonteVO;
import mx.com.nmp.valormonte.model.BadRequest;
import mx.com.nmp.valormonte.model.CalculoValorMonteReq;
import mx.com.nmp.valormonte.model.CalculoValorMonteReqInner;
import mx.com.nmp.valormonte.model.CalculoValorMonteRes;
import mx.com.nmp.valormonte.model.CalculoValorMonteResInner;
import mx.com.nmp.valormonte.model.InternalServerError;
import mx.com.nmp.valormonte.model.InvalidAuthentication;

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
import java.util.List;
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
			@ApiParam(value = "") @Valid @RequestBody CalculoValorMonteReq valorMonteReq) {
		String accept = request.getHeader("Accept");
		if (accept != null && accept.contains("application/json")) {
			try {
				CalculoValorMonteRes resp;
				
				if (valorMonteReq == null) {
					log.info("Esto esta vacio");
				} else {
					resp = valorMonteService.calcularValorMonte(valorMonteReq);
					if (resp != null) {
						log.info("ya estas en esta parte");
						log.info("Datos: " + resp);

						return new ResponseEntity<CalculoValorMonteRes>(resp, HttpStatus.OK);
					} else {
						BadRequest br = new BadRequest();
						br.setCodigo("NMP-MDA-400");
						br.mensaje("El cuerpo de la petición no está bien formado, verifique su información");
						return new ResponseEntity<BadRequest>(br, HttpStatus.BAD_REQUEST);
					}

				}
			} catch (Exception e) {
				log.error("Couldn't serialize response for content type application/json", e);
				InternalServerError ise = new InternalServerError();
				ise.setCodigo("NMP-MDA-500");
				ise.setMensaje("Internal Server Error");
				return new ResponseEntity<InternalServerError>(ise, HttpStatus.BAD_REQUEST);
			}
		}

		return new ResponseEntity<CalculoValorMonteRes>(HttpStatus.NOT_IMPLEMENTED);
	}

}

package mx.com.nmp.consolidados.api;

import org.threeten.bp.LocalDate;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import mx.com.nmp.consolidados.model.BadRequest;
import mx.com.nmp.consolidados.model.ConflictRequest;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoRes;
import mx.com.nmp.consolidados.model.GeneralResponse;
import mx.com.nmp.consolidados.model.InlineResponse200;
import mx.com.nmp.consolidados.model.InternalServerError;
import mx.com.nmp.consolidados.model.InvalidAuthentication;
import mx.com.nmp.consolidados.model.ModificarPrioridadArchivoConsolidadoReq;
import mx.com.nmp.consolidados.model.NotFound;
import mx.com.nmp.consolidados.model.SuccessfulResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

@Controller
public class ConsolidadosApiController implements ConsolidadosApi {

    private static final Logger log = LoggerFactory.getLogger(ConsolidadosApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public ConsolidadosApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<InlineResponse200> actualizarPosicionArchivoPUT(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Identificador del archivo",required=true) @PathVariable("idArchivo") String idArchivo,@ApiParam(value = "peticion para modificar la posicion de un archivo"  )  @Valid @RequestBody ModificarPrioridadArchivoConsolidadoReq modificarPosicionReq) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<InlineResponse200>(objectMapper.readValue("{  \"nombreArchivo\" : \"nombreArchivo\",  \"idPosicion\" : 0}", InlineResponse200.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);
    }



    public ResponseEntity<SuccessfulResponse> eliminarArchivoConsolidadoDELETE(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Identificador del archivo",required=true) @PathVariable("idArchivo") String idArchivo) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<SuccessfulResponse>(objectMapper.readValue("{  \"codigo\" : \"NMP-MDA-000\",  \"mensaje\" : \"Operación ejecutada satisfactoriamente\"}", SuccessfulResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<SuccessfulResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<SuccessfulResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<SuccessfulResponse> procesarConsolidadoPOST(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@NotNull @ApiParam(value = "Fecha de ejecución del proceso de consolidados", required = true) @Valid @RequestParam(value = "fechaAplicacion", required = true) LocalDate fechaAplicacion) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<SuccessfulResponse>(objectMapper.readValue("{  \"codigo\" : \"NMP-MDA-000\",  \"mensaje\" : \"Operación ejecutada satisfactoriamente\"}", SuccessfulResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<SuccessfulResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<SuccessfulResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<GeneralResponse> registrarConsolidadoPOST(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Archivo CSV de consolidados") @Valid @RequestPart(value="adjunto", required=true) MultipartFile adjunto,@ApiParam(value = "Fecha de vigencia para el ajuste" ,required=true) @RequestHeader(value="vigencia", required=true) LocalDate vigencia,@ApiParam(value = "Nombre del ajuste" ,required=true) @RequestHeader(value="nombreAjuste", required=true) String nombreAjuste,@ApiParam(value = "Flag para indicar si el ajuste es emergente" ,required=true) @RequestHeader(value="emergente", required=true) Boolean emergente) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<GeneralResponse>(objectMapper.readValue("{  \"message\" : \"Exitoso\"}", GeneralResponse.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<GeneralResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<GeneralResponse>(HttpStatus.NOT_IMPLEMENTED);
    }
	
    public ResponseEntity<ConsultarArchivoConsolidadoRes> consultaConsolidadosArchivosGET(
    		@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,
    		@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,
    		@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,
    		@NotNull @ApiParam(value = "Fecha de ejecución del proceso de consolidados", required = true) @Valid @RequestParam(value = "fechaAplicacion", required = true) LocalDate fechaAplicacion) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<ConsultarArchivoConsolidadoRes>(objectMapper.readValue("{  \"nombreArchivo\" : \"nombreArchivo\",  \"nombreCliente\" : \"nombreCliente\",  \"fechaReporte\" : \"2000-01-23T04:56:07.000+00:00\",  \"producto\" : [ {    \"prestamoCosto\" : 1.4658129,    \"folio_Sku\" : 6,    \"precioFinal\" : 5.962134,    \"idProducto\" : 0,    \"ubicacionActual\" : \"ubicacionActual\"  }, {    \"prestamoCosto\" : 1.4658129,    \"folio_Sku\" : 6,    \"precioFinal\" : 5.962134,    \"idProducto\" : 0,    \"ubicacionActual\" : \"ubicacionActual\"  } ]}", ConsultarArchivoConsolidadoRes.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ConsultarArchivoConsolidadoRes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ConsultarArchivoConsolidadoRes>(HttpStatus.NOT_IMPLEMENTED);
    }

}

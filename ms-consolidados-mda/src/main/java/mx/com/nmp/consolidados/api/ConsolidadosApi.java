/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.12).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package mx.com.nmp.consolidados.api;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import mx.com.nmp.consolidados.model.BadRequest;
import mx.com.nmp.consolidados.model.ConflictRequest;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoRes;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoResInner;
import mx.com.nmp.consolidados.model.GeneralResponse;
import mx.com.nmp.consolidados.model.InlineResponse200;
import mx.com.nmp.consolidados.model.InternalServerError;
import mx.com.nmp.consolidados.model.InvalidAuthentication;
import mx.com.nmp.consolidados.model.ModificarPrioridadArchivoConsolidadoReq;
import mx.com.nmp.consolidados.model.NotFound;
import mx.com.nmp.consolidados.model.SuccessfulResponse;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-01-22T02:47:50.165Z")

@Api(value = "consolidados", description = "the consolidados API")
public interface ConsolidadosApi {

	@ApiOperation(value = "Actualizar prioridad de ejecución del archivo", nickname = "actualizarPosicionArchivoPUT", notes = "Actualiza la prioridad de ejecución de los archivos.  ", response = InlineResponse200.class, authorizations = {
	        @Authorization(value = "apiKey"),
	        @Authorization(value = "apiSecret")
	    }, tags={ "Consolidados", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Modificacion exitosa", response = InlineResponse200.class),
	        @ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información", response = BadRequest.class),
	        @ApiResponse(code = 401, message = "Error de autorización en el uso del recurso", response = InvalidAuthentication.class),
	        @ApiResponse(code = 404, message = "Recurso no disponible", response = NotFound.class),
	        @ApiResponse(code = 409, message = "Conflicto con el mensaje de petición, verifique la información", response = ConflictRequest.class),
	        @ApiResponse(code = 500, message = "Error interno del servidor", response = InternalServerError.class) })
	    @RequestMapping(value = "/consolidados/archivos/{idArchivo}/prioridad",
	        produces = { "application/json" }, 
	        method = RequestMethod.PUT)
	    ResponseEntity<InlineResponse200> actualizarPosicionArchivoPUT(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Identificador del archivo",required=true) @PathVariable("idArchivo") String idArchivo,@ApiParam(value = "peticion para modificar la posicion de un archivo"  )  @Valid @RequestBody ModificarPrioridadArchivoConsolidadoReq modificarPosicionReq);


	    @ApiOperation(value = "Consulta los archivos por la fecha y/o prioridad en la que estan programados", nickname = "consultaConsolidadosArchivosGET", notes = "El consumidor podrá consultar los archivos de acuerdo a la fecha y/o prioridad en la que fue programada su ejecución. Como resultado el consumidor recibira la lista de archivos y la prioridad con la que se ejecutarán. ", response = ConsultarArchivoConsolidadoRes.class, authorizations = {
	        @Authorization(value = "apiKey"),
	        @Authorization(value = "apiSecret")
	    }, tags={ "Consolidados", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Consulta exitosa", response = ConsultarArchivoConsolidadoRes.class),
	        @ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información", response = BadRequest.class),
	        @ApiResponse(code = 401, message = "Error de autorización en el uso del recurso", response = InvalidAuthentication.class),
	        @ApiResponse(code = 404, message = "Recurso no disponible", response = NotFound.class),
	        @ApiResponse(code = 500, message = "Error interno del servidor", response = InternalServerError.class) })
	    @RequestMapping(value = "/consolidados/archivos",
	        produces = { "application/json" }, 
	        method = RequestMethod.GET)
	    ResponseEntity<ConsultarArchivoConsolidadoRes> consultaConsolidadosArchivosGET(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@NotNull @ApiParam(value = "Fecha de ejecución del proceso de consolidados", required = true) @Valid @RequestParam(value = "fechaAplicacion", required = true) String fechaAplicacion,@NotNull @ApiParam(value = "Prioridad en la ejecución del archivo", required = true) @Valid @RequestParam(value = "idPrioridad", required = true) String idPrioridad);


	    @ApiOperation(value = "Elimina archivo consolidados", nickname = "eliminarArchivoConsolidadoDELETE", notes = "Elimina archivo de consolidados. La eliminación del archivo solo se podrá realizar siempre y cuando el archivo este en estado inicial. ", response = SuccessfulResponse.class, authorizations = {
	        @Authorization(value = "apiKey"),
	        @Authorization(value = "apiSecret")
	    }, tags={ "Consolidados", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Archivo eliminado exitosamente", response = SuccessfulResponse.class),
	        @ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información", response = BadRequest.class),
	        @ApiResponse(code = 401, message = "Error de autorización en el uso del recurso", response = InvalidAuthentication.class),
	        @ApiResponse(code = 404, message = "Recurso no disponible", response = NotFound.class),
	        @ApiResponse(code = 500, message = "Error interno del servidor", response = InternalServerError.class) })
	    @RequestMapping(value = "/consolidados/archivos/{idArchivo}",
	        produces = { "application/json" }, 
	        method = RequestMethod.DELETE)
	    ResponseEntity<SuccessfulResponse> eliminarArchivoConsolidadoDELETE(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Identificador del archivo",required=true) @PathVariable("idArchivo") String idArchivo);


	    @ApiOperation(value = "Registrar archivo con información de consolidados", nickname = "procesarConsolidadoPOST", notes = "El consumidor enviará la fecha de los archivos que requiere se procesen. El proceso internamente buscará los archivos correspondientes y los ejecutará de acuerdo a la prioridad que tengan, en caso de no tener prioridad, estos se ejecutarán con la prioridad más baja. Esta operación asincrona actualizará los estados del archivo de acuerdo a la etapa interna en la que se encuentre del proceso de consolidados. El consumidor recibira como respuesta un mensaje de confrimación. ", response = SuccessfulResponse.class, authorizations = {
	        @Authorization(value = "apiKey"),
	        @Authorization(value = "apiSecret")
	    }, tags={ "Consolidados", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Procesamiento de archivo en curso", response = SuccessfulResponse.class),
	        @ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información", response = BadRequest.class),
	        @ApiResponse(code = 401, message = "Error de autorización en el uso del recurso", response = InvalidAuthentication.class),
	        @ApiResponse(code = 500, message = "Error interno del servidor", response = InternalServerError.class) })
	    @RequestMapping(value = "/consolidados/_procesar",
	        produces = { "application/json" }, 
	        consumes = { "application/json" },
	        method = RequestMethod.POST)
	    ResponseEntity<SuccessfulResponse> procesarConsolidadoPOST(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@NotNull @ApiParam(value = "Fecha de ejecución del proceso de consolidados", required = true) @Valid @RequestParam(value = "fechaAplicacion", required = true) LocalDate fechaAplicacion);


	    @ApiOperation(value = "Registrar archivo con información de consolidados", nickname = "registrarConsolidadoPOST", notes = "Se almacena el archivo que contiene la información de las partidas a consolidar. Cada registro tendrá un estado inicial para identificar que el archivo ha sido registrado y que aún no se procesa. ", response = GeneralResponse.class, authorizations = {
	        @Authorization(value = "apiKey"),
	        @Authorization(value = "apiSecret")
	    }, tags={ "Consolidados", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Procesamiento de archivo en curso", response = GeneralResponse.class),
	        @ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información", response = BadRequest.class),
	        @ApiResponse(code = 401, message = "Error de autorización en el uso del recurso", response = InvalidAuthentication.class),
	        @ApiResponse(code = 500, message = "Error interno del servidor", response = InternalServerError.class) })
	    @RequestMapping(value = "/consolidados/archivos",
	        produces = { "application/json" }, 
	        consumes = { "multipart/form-data" },
	        method = RequestMethod.POST)
	    ResponseEntity<GeneralResponse> registrarConsolidadoPOST(@ApiParam(value = "Usuario en el sistema origen que lanza la petición" ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición" ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información" ,required=true, allowableValues="bluemix, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = "Archivo CSV de consolidados") @Valid @RequestPart(value="adjunto", required=true) MultipartFile adjunto,@ApiParam(value = "Fecha de vigencia para el ajuste" ,required=true) @RequestHeader(value="vigencia", required=true) String vigencia,@ApiParam(value = "Nombre del ajuste" ,required=true) @RequestHeader(value="nombreAjuste", required=true) String nombreAjuste,@ApiParam(value = "Flag para indicar si el ajuste es emergente" ,required=true) @RequestHeader(value="emergente", required=true) Boolean emergente);

}

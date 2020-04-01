/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.10).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package mx.com.nmp.usuarios.api;

import io.swagger.annotations.*;
import mx.com.nmp.usuarios.model.BadRequest;
import mx.com.nmp.usuarios.model.CapacidadUsuariosReq;
import mx.com.nmp.usuarios.model.CapacidadUsuariosRes;
import mx.com.nmp.usuarios.model.ConflictRequest;
import mx.com.nmp.usuarios.model.ConsultaHistoricoRes;
import mx.com.nmp.usuarios.model.ConsultaUsuarioRes;
import mx.com.nmp.usuarios.model.CrearHistoricoRes;
import mx.com.nmp.usuarios.model.EliminarUsuariosRes;
import mx.com.nmp.usuarios.model.GeneralResponse;
import mx.com.nmp.usuarios.model.InfoUsuario;
import mx.com.nmp.usuarios.model.InternalServerError;
import mx.com.nmp.usuarios.model.InvalidAuthentication;
import mx.com.nmp.usuarios.model.ModCapacidadUsuario;
import mx.com.nmp.usuarios.model.NotFound;
import mx.com.nmp.usuarios.model.PerfilUsuario;
import mx.com.nmp.usuarios.model.ReqEstatus;
import mx.com.nmp.usuarios.model.ReqHistorico;
import mx.com.nmp.usuarios.model.ReqPerfil;
import mx.com.nmp.usuarios.model.ResEstatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-09T20:07:51.537Z")

@Api(value = "usuarios", description = "the usuarios API")
public interface UsuariosApi {

	@ApiOperation(value = "Capacidades que puede tener un perfil previamente registrado.", nickname = "capacidadUsuarioPOST", notes = "Cada perfil tiene sus permisos definidos de la siguiente manera:  * Administrador:   * Configuración de reglas y ajustes de precios.   * Reporteador.   * Administración de usuarios.   * Consultar usuarios.   * Alta de usuarios.   * Baja de usuarios.   * Activar/ Inactivar usuario.   * Buscar reglas.   * Generar reglas.   * Configurar sucursal.   * Validar ajustes de precios.   * Programar reglas.   * Ajustar precios.   * Generación de reportes.   * Generación de reportes Ad-Hoc.   * Descargar reportes.    * Operador:   * Configuración de reglas y ajustes de precios.   * Reporteador.   * Buscar reglas.   * Generar reglas.   * Configurar sucursal.   * Validar ajustes de precios.   * Programar reglas.   * Ajustar precios.   * Generación de reportes.   * Generación de reportes Ad-Hoc.   * Descargar reportes.    * Consultor:   * Reporteador.   * Buscar reglas.   * Generación de reportes.   * Generación de reportes Ad-Hoc.   * Descargar reportes.    ", response = CapacidadUsuariosRes.class, authorizations = {
			@Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Creación exitosa.", response = CapacidadUsuariosRes.class),
			@ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información.", response = BadRequest.class),
			@ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
			@ApiResponse(code = 404, message = "Recurso no disponible", response = NotFound.class),
			@ApiResponse(code = 409, message = "Conflicto con el mensaje de petición, verifique la información.", response = ConflictRequest.class),
			@ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	@RequestMapping(value = "/usuarios/{idPerfil}/capacidades", produces = {
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> capacidadUsuarioPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del perfil.", required = true) @PathVariable("idPerfil") String idPerfil,
			@ApiParam(value = "") @Valid @RequestBody CapacidadUsuariosReq capacidadUsuarioReq);

	@ApiOperation(value = "Consulta el listado de los usuarios actuales.", nickname = "consultaUsuarioGET", notes = "El Administrador encontrará la pantalla que le permite realizar búsquedas de los usuarios que se han asignado para el proceso de ajuste de precios, utilizando los siguientes filtros de búsqueda.      * Nombre   * Apellido Paterno   * Apellido Materno   * Estatus   * Usuario      Puede visualizar todos los usuarios que han sido agregados mediante un listado.    ", response = ConsultaUsuarioRes.class, authorizations = {
			@Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Consulta Exitosa.", response = ConsultaUsuarioRes.class),
			@ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información.", response = BadRequest.class),
			@ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
			@ApiResponse(code = 404, message = "Recurso no disponible.", response = NotFound.class),
			@ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	@RequestMapping(value = "/usuarios", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> consultaUsuarioGET(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Nombre del usuario.") @Valid @RequestParam(value = "nombre", required = false) String nombre,
			@ApiParam(value = "Apellido paterno del usuario.") @Valid @RequestParam(value = "apellidoPaterno", required = false) String apellidoPaterno,
			@ApiParam(value = "Apellido materno del usuario.") @Valid @RequestParam(value = "apellidoMaterno", required = false) String apellidoMaterno,
			@ApiParam(value = "Estatus registrado por el administrador.") @Valid @RequestParam(value = "activo", required = false) Boolean activo,
			@ApiParam(value = "Username registrado en el directorio activo.") @Valid @RequestParam(value = "usuario", required = false) String usuario2,
			@ApiParam(value = "Perfil del usuario registrado en Mongo.") @Valid @RequestParam(value = "perfil", required = false) Integer perfil);

	@ApiOperation(value = "Elimina a un usuario previamente registrado.", nickname = "eliminarUsuariosDELETE", notes = "La baja de usuarios es la selección de cualquier usuario asignado a Motor de descuentos Automatizados, por medio de las casillas de selección el administrador debe elegir los usuarios que se eliminaran de la consulta de usuarios. ", response = EliminarUsuariosRes.class, authorizations = {
			@Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuario eliminado exitosamente.", response = EliminarUsuariosRes.class),
			@ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información.", response = BadRequest.class),
			@ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
			@ApiResponse(code = 404, message = "Recurso no disponible.", response = NotFound.class),
			@ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	@RequestMapping(value = "/usuarios/{idUsuario}", produces = { "application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<?> eliminarUsuariosDELETE(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "identificador para eliminar a un usuario.", required = true) @PathVariable("idUsuario") Integer idUsuario);

	@ApiOperation(value = "Modifica el estatus de un usuario actual.", nickname = "estatusUsuariosPUT", notes = "Los usuarios que sean agregados para su ingreso al portal de Motor de Descuentos Automatizados deben tener un estatus \"Activo\" o \"inactivo\" con el cual podrán o no tener acceso al portal, por lo tanto es de carácter obligatorio seleccionarlo.  Si el usuario tiene un estatus \"Inactivo\" el usuario no podra ingresar al portal.   Se debe almacenar la información de la página en la cual se encuentra. La opción seleccionada en el catálogo \"Estatus\" podrá ser modificada en cualquier momento. ", response = ResEstatus.class, authorizations = {
			@Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Modificación de estatus Exitosa.", response = ResEstatus.class),
			@ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información.", response = BadRequest.class),
			@ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
			@ApiResponse(code = 404, message = "Recurso no disponible.", response = NotFound.class),
			@ApiResponse(code = 409, message = "Conflicto con el mensaje de petición, verifique la información.", response = ConflictRequest.class),
			@ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	@RequestMapping(value = "/usuarios/{idUsuario}/estatus", produces = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> estatusUsuariosPUT(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del usuario.", required = true) @PathVariable("idUsuario") String idUsuario,
			@ApiParam(value = "peticion para modificar el estatus de un usuario.") @Valid @RequestBody ReqEstatus modificaEstatusReq);

	@ApiOperation(value = "Consulta el historial de un usuario en el portal.", nickname = "historialUsuarioGET", notes = "Consulta la lista acciones registradas de un usuario en particular dentro del portal Motor de Descuentos Automatizados.", response = ConsultaHistoricoRes.class, authorizations = {
			@Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Consulta exitosa.", response = ConsultaHistoricoRes.class),
			@ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información.", response = BadRequest.class),
			@ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
			@ApiResponse(code = 404, message = "Recurso no disponible.", response = NotFound.class),
			@ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	@RequestMapping(value = "/usuarios/historico", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> historialUsuarioGET(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@NotNull @ApiParam(value = "identificador del usuario.", required = true) @Valid @RequestParam(value = "idUsuario", required = true) Integer idUsuario);

	@ApiOperation(value = "Historial de un usuario en el portal.", nickname = "historialUsuarioPOST", notes = "Se debe enviar  a un log de datos los registros históricos de acciones que el administrador realiza conforme a lo siguiente: * Fecha. * Hora. * Usuario que realizo la acción. * Perfil de Usuario que realizó la acción.  * Acción: \"Alta de usuario + (Nombre de usuario)\". * Acción: \"Baja de usuario + (Nombre de usuario)\". * Acción: \"Activación de usuario + (Nombre de usuario)\". * Acción: \"Inactivación de usuario + (Nombre de usuario)\". * Acción: \"Asignación del perfil + (Nombre de usuario)\". * Acción: \"Modificación del Perfil + (Nombre de usuario)\".  El almacenamiento de acciones históricas es apoyo para procesos de Auditorías , por lo tanto podrán ser consultadas en cualquier momento. ", response = CrearHistoricoRes.class, authorizations = {
			@Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Creación exitosa.", response = CrearHistoricoRes.class),
			@ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información.", response = BadRequest.class),
			@ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
			@ApiResponse(code = 404, message = "Recurso no disponible.", response = NotFound.class),
			@ApiResponse(code = 409, message = "Conflicto con el mensaje de petición, verifique la información.", response = ConflictRequest.class),
			@ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	@RequestMapping(value = "/usuarios/historico", produces = { "application/json" }, method = RequestMethod.POST)
	ResponseEntity<?> historialUsuarioPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "peticion para crear el registro histórico de un usuario en el portal.") @Valid @RequestBody ReqHistorico historicoEnvioReq);

	@ApiOperation(value = "Modifica las capacidades que puede tener un perfil previamente registrado.", nickname = "modCapacidadPOST", notes = "Cada perfil tiene sus permisos definidos de la siguiente manera:  * Administrador:   * Configuración de reglas y ajustes de precios.   * Reporteador.   * Administración de usuarios.   * Consultar usuarios.   * Alta de usuarios.   * Baja de usuarios.   * Activar/ Inactivar usuario.   * Buscar reglas.   * Generar reglas.   * Configurar sucursal.   * Validar ajustes de precios.   * Programar reglas.   * Ajustar precios.   * Generación de reportes.   * Generación de reportes Ad-Hoc.   * Descargar reportes.    * Operador:   * Configuración de reglas y ajustes de precios.   * Reporteador.   * Buscar reglas.   * Generar reglas.   * Configurar sucursal.   * Validar ajustes de precios.   * Programar reglas.   * Ajustar precios.   * Generación de reportes.   * Generación de reportes Ad-Hoc.   * Descargar reportes.    * Consultor:   * Reporteador.   * Buscar reglas.   * Generación de reportes.   * Generación de reportes Ad-Hoc.   * Descargar reportes.    `Al modificar las capacidades de un perfil, se remplazan todas las capacidades asignadas anteriormente, dejando solo las capacidades definidas en la modificación.`    ", response = CapacidadUsuariosRes.class, authorizations = {
			@Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Creación exitosa.", response = CapacidadUsuariosRes.class),
			@ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información.", response = BadRequest.class),
			@ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
			@ApiResponse(code = 404, message = "Recurso no disponible", response = NotFound.class),
			@ApiResponse(code = 409, message = "Conflicto con el mensaje de petición, verifique la información.", response = ConflictRequest.class),
			@ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	@RequestMapping(value = "/usuarios/{idPerfil}/capacidades", produces = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> modCapacidadPOST(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del perfil.", required = true) @PathVariable("idPerfil") String idPerfil,
			@ApiParam(value = "") @Valid @RequestBody ModCapacidadUsuario modCapacidadReq);

	@ApiOperation(value = "Modifica el perfil de un usuario actual.", nickname = "modificarUsuariosPUT", notes = "Todos los usuarios que sean agregados para el proceso de ajuste de precios deben tener un perfil asociado; las opciones del catálogo de perfil son:    * Consultor   * Operador  Siendo estas opciones excluyentes entre sí. La opción seleccionada en el catálogo \"Perfil\" podrá ser modificada en cualquier momento. ", response = CapacidadUsuariosRes.class, authorizations = {
			@Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Modificación Exitosa.", response = CapacidadUsuariosRes.class),
			@ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información", response = BadRequest.class),
			@ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
			@ApiResponse(code = 404, message = "Recurso no disponible.", response = NotFound.class),
			@ApiResponse(code = 409, message = "Conflicto con el mensaje de petición, verifique la información.", response = ConflictRequest.class),
			@ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	@RequestMapping(value = "/usuarios/{idUsuario}/perfil", produces = {
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<?> modificarUsuariosPUT(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Identificador del usuario.", required = true) @PathVariable("idUsuario") String idUsuario,
			@ApiParam(value = "petición para modificar el perfil a un usuario.") @Valid @RequestBody ReqPerfil modificarPerfilReq);
	
	@ApiOperation(value = "Consulta el perfil del usuario", nickname = "usuariosPerfilGet", notes = "Recurso para la consulta del perfil del usuario en el AD y en Mongo DB", response = PerfilUsuario.class, authorizations = {
			@Authorization(value = "Bearer"), @Authorization(value = "apiKey") }, tags = { "Usuarios", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Consulta exitosa", response = PerfilUsuario.class) })
	@RequestMapping(value = "/usuarios/perfil", produces = { "application/json" }, method = RequestMethod.GET)
	ResponseEntity<?> usuariosPerfilGet(
			@ApiParam(value = "Usuario en el sistema origen que lanza la petición.", required = true) @RequestHeader(value = "usuario", required = true) String usuario,
			@ApiParam(value = "Sistema que origina la petición.", required = true, allowableValues = "portalMotorDescuentosAutomatizados") @RequestHeader(value = "origen", required = true) String origen,
			@ApiParam(value = "Destino final de la información.", required = true, allowableValues = "Mongo, mockserver") @RequestHeader(value = "destino", required = true) String destino,
			@ApiParam(value = "Access Token del usuario loggeado." ,required=true) @RequestHeader(value="token", required=true) String token);


	    @ApiOperation(value = "Registra un usuario", nickname = "usuariosPost", notes = "Recurso utilizado para el registro de un usuario en Mongo DB", response = GeneralResponse.class, authorizations = {
	        @Authorization(value = "apiKey")
	    }, tags={ "Usuarios", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 200, message = "Alta exitosa", response = GeneralResponse.class),
	        @ApiResponse(code = 400, message = "Error en el mensaje de petición, verifique la información.", response = BadRequest.class),
	        @ApiResponse(code = 401, message = "Error de autorización en el uso del recurso.", response = InvalidAuthentication.class),
	        @ApiResponse(code = 404, message = "Recurso no disponible.", response = NotFound.class),
	        @ApiResponse(code = 500, message = "Error interno del servidor.", response = InternalServerError.class) })
	    @RequestMapping(value = "/usuarios",
	        produces = { "application/json" }, 
	        method = RequestMethod.POST)
	ResponseEntity<?> usuariosPost(@ApiParam(value = "Usuario en el sistema origen que lanza la petición." ,required=true) @RequestHeader(value="usuario", required=true) String usuario,@ApiParam(value = "Sistema que origina la petición." ,required=true, allowableValues="portalMotorDescuentosAutomatizados") @RequestHeader(value="origen", required=true) String origen,@ApiParam(value = "Destino final de la información." ,required=true, allowableValues="Mongo, mockserver") @RequestHeader(value="destino", required=true) String destino,@ApiParam(value = ""  )  @Valid @RequestBody InfoUsuario peticion);
	
}

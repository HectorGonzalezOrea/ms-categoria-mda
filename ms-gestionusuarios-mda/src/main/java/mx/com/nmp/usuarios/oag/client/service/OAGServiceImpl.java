package mx.com.nmp.usuarios.oag.client.service;

import static mx.com.nmp.usuarios.utils.Constantes.GRANT_TYPE;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_ID_CONSUMIDOR;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_ID_DESTINO;
import static mx.com.nmp.usuarios.utils.Constantes.HEADER_USUARIO;
import static mx.com.nmp.usuarios.utils.Constantes.SCOPE;
import static mx.com.nmp.usuarios.utils.Constantes.STATUS_CODE_OK;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.net.HttpHeaders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.usuarios.oag.vo.BusquedaGrupoVO;
import mx.com.nmp.usuarios.oag.vo.GetTokenResponseVO;
import mx.com.nmp.usuarios.oag.vo.IdentidadUsuarioRequestVO;
import mx.com.nmp.usuarios.oag.vo.UsuariosResponseVO;
import mx.com.nmp.usuarios.utils.ConvertStringToBase64;
import mx.com.nmp.usuarios.utils.ConverterUtil;

@Service
public class OAGServiceImpl extends OAGBaseService implements OAGService {

	private static final Logger log = LoggerFactory.getLogger(OAGService2Impl.class);

	@Override
	public String getToken() throws UnirestException {
		log.info("getToken");

		String accessToken = "";

		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);

		Unirest.setTimeouts(5000, 5000);
		HttpResponse<String> response = Unirest.post(urlBase + servicioGetToken)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED)
				.header(HEADER_USUARIO, headerUsuario)
				.header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
				.header(HEADER_ID_DESTINO, headerIdDestino)
				.header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
				.field(GRANT_TYPE, grantType).field(SCOPE, scope).asString();

		int statusCode = response.getStatus();

		log.info("Status Code Response: {}", statusCode);
		log.info("Body Response: {}", response.getBody());

		if (statusCode == STATUS_CODE_OK) {
			GetTokenResponseVO resp = ConverterUtil.stringJsonToObjectGetTokenResponseVO(response.getBody());
			accessToken = resp.getAccess_token();
		}

		return accessToken;
	}

	@Override
	public Object identidadUsuario(IdentidadUsuarioRequestVO request, String oauthBearer, String userLoggeado)
			throws UnirestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object identidadUsuario(String oauthBearer) throws UnirestException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public UsuariosResponseVO getUsersByGroup(String oauthBearer, BusquedaGrupoVO request) throws UnirestException {
		log.info("getUsersByGroup");
		
		UsuariosResponseVO resp = null;
		
		String credenciales = usuario + ":" + password;
		String autenticacionBasica = "Basic " + ConvertStringToBase64.encode(credenciales);
		
		String iRequestJson = ConverterUtil.messageToJson(request);
		
		Unirest.setTimeouts(0, 0);
		HttpResponse<String> response = Unirest.post("https://iamdr.montepiedad.com.mx:4444/NMP/GestionIdentidades/ServicioIdentidad/Usuarios/v1/grupos")
		  .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)		
		  .header(HEADER_USUARIO, headerUsuario)
		  .header(HEADER_ID_CONSUMIDOR, headerIdConsumidor)
		  .header(HEADER_ID_DESTINO, headerIdDestino)
		  .header(HttpHeaders.AUTHORIZATION, autenticacionBasica)
		  .header("oauth.bearer", oauthBearer)
		  .body(iRequestJson)
		  .asString();
		
		int statusCode = response.getStatus();

		log.info("Status Code Response: {}", statusCode);
		log.info("Body Response: {}", response.getBody());

		if (statusCode == STATUS_CODE_OK) {
			resp = ConverterUtil.stringJsonToObjectUsuariosResponseVO(response.getBody());
		}
		
		return resp;
	}
	
}

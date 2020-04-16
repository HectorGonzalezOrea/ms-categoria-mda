package mx.com.nmp.escenariosdinamicos.clienteservicios.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.CalculoValorVO;
import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.PartidaVO;
import mx.com.nmp.escenariosdinamicos.constantes.Constantes.Common;
@RestController
public class ClientesMicroservicios {
	@Autowired
	private CastObjectGeneric castObjectGeneric;
	
	 public List<PartidaVO> actualizaPrecio(List<CalculoValorVO> lstPartidas){
		List<PartidaVO> responseService=new ArrayList<>();
		Client client = ClientBuilder.newClient((Configuration) new ClientConfig());
		StringBuilder sb = new StringBuilder(Common.URL_VALOR_MONTE);
		WebTarget target = client.target(sb.toString());
		Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.json(castObjectGeneric.lstToJson(lstPartidas)));
		int statusCode = response.getStatus();
		if (statusCode == Common.STATUS_CODE_OK) {
			String output = response.readEntity(String.class);
			//convertir response a objeto
			responseService =castObjectGeneric.castJsonToList(output);
		}
		return responseService;
	 }
}

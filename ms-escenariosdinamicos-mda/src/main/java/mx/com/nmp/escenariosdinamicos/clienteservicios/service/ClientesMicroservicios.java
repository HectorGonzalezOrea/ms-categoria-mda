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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import mx.com.nmp.escenariosdinamicos.api.EscenariosApiController;
import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.CalculoValorVO;
import mx.com.nmp.escenariosdinamicos.constantes.Constantes.Common;
import mx.com.nmp.escenariosdinamicos.model.PartidaPrecioFinal;
@RestController
public class ClientesMicroservicios {
	@Autowired
	private CastObjectGeneric castObjectGeneric;
	@Value("${bluemix.host}")
	protected String endPointValorMonte;
	@Value("${bluemix.contex}")
	protected String contex;
	private static final Logger LOG = LoggerFactory.getLogger(ClientesMicroservicios.class);
	
	 public List<PartidaPrecioFinal> calcularValorMonte(List<CalculoValorVO> lstPartidas,String usuario,String origen,String destino){
		 LOG.info("entrando al servicio actuliza precio cliente");
		List<PartidaPrecioFinal> responseService=new ArrayList<>();
		Client client = ClientBuilder.newClient((Configuration) new ClientConfig());
		StringBuilder sb = new StringBuilder(endPointValorMonte);
		sb.append(contex);
		WebTarget target = client.target(sb.toString());
		Invocation.Builder invocationBuilder =  target.request(MediaType.APPLICATION_JSON);
		invocationBuilder.header(Common.USER,usuario);
		invocationBuilder.header(Common.ORIGEN,origen);
		invocationBuilder.header(Common.DESTINO,destino);
		Response response = invocationBuilder.post(Entity.json(castObjectGeneric.lstToJson(lstPartidas)));
		int statusCode = response.getStatus();
		if (statusCode == Common.STATUS_CODE_OK) {
			String output = response.readEntity(String.class);
			//convertir response a objeto
			responseService =castObjectGeneric.castJsonToList(output);
			LOG.info("responseCliente");
			LOG.info(output);
		}
		return responseService;
	 }
}

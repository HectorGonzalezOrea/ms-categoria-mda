package mx.com.nmp.escenariosdinamicos.model.component;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.clienteoag.service.ClientOAGService;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaResponseVO;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;


@Component
public class ConsumerMessageComponent {
	
	private static final Logger log = LoggerFactory.getLogger(ConsumerMessageComponent.class);
	
	@Autowired
	ClientOAGService clientOAG;
	@Autowired
	CastObjectGeneric castObject;

	@RabbitListener(queues = "${rabbitmq.queue}")
	public void consumerReglaEscenarioDinamico(String message) {
		log.info("Recieved Message==>consumerReglaEscenarioDinamico:: " + message);
		clientOAG.reglaEscenarioDinamico(castObject.convertJsonToRequestEscenario(message));
	}
	
	@RabbitListener(queues = "${rabbitmq.queue.reglasarbitraje}")
	public void consumerReglaArbitraje(String message) {
		log.info("Recieved Message: " + message);
		ResponseOAGDto messageVO=castObject.convertJsonToReponseOAGDto(message);
		clientOAG.aplicarReglaArbitraje(convertObjectToJson(messageVO));
	}
	
	@RabbitListener(queues = "${rabbitmq.queue.cambioprecio}")
	public void consumerCambioPrecio(String message) {
		log.info("Recieved Message: " + message);
		//clientOAG.reglaEscenarioDinamico(castObject.convertJsonToRequestEscenario(message));
	}
	
	
	private RequestReglaEscenarioDinamicoDto  convertObjectToJson(ResponseOAGDto messageVO) {
		PartidaVO partidaVO = new PartidaVO();
		RequestReglaEscenarioDinamicoDto reglasEscenario= new RequestReglaEscenarioDinamicoDto();
		Double pretamo=1200D;
		List<PartidaVO>lstPartidaVO= new ArrayList<PartidaVO>();
	for (PartidaResponseVO vo : messageVO.getPartida()) {
		partidaVO.setIdPartida(vo.getIdPartida());
		partidaVO.setSku(vo.getSku());
		partidaVO.setPrecioVenta(Double.parseDouble(vo.getPrecio()));
		partidaVO.setMontoPrestamo(pretamo);
		lstPartidaVO.add(partidaVO);
		}
		reglasEscenario.setPartida(lstPartidaVO);
		return reglasEscenario;
	}
	
	/*public RequestReglaEscenarioDinamicoDto convertJsonRquestEscenario(String message) {
		final Gson gson = new Gson();
		RequestReglaEscenarioDinamicoDto request= new RequestReglaEscenarioDinamicoDto();
		request=gson.fromJson(message, RequestReglaEscenarioDinamicoDto.class);
		return request;
	}*/
	
	
}

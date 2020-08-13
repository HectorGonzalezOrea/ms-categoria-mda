package mx.com.nmp.escenariosdinamicos.model.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import mx.com.nmp.escenariosdinamicos.cast.CastObjectGeneric;
import mx.com.nmp.escenariosdinamicos.clienteoag.service.ClientOAGService;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestActualizarPrecioPartidaDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaResponseVO;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;

@Component
public class ConsumerMessageComponent  {

	private static final Logger log = LoggerFactory.getLogger(ConsumerMessageComponent.class);

	@Autowired
	ClientOAGService clientOAG;
	@Autowired
	CastObjectGeneric castObject;
	
	@RabbitListener(queues = "${rabbitmq.queue}")
	@RabbitHandler
	public void consumerReglaEscenarioDinamico( Channel channel, Message message) throws IOException{
		
		log.info(Constantes.RECIEVED_LOG);
		String mensaje= new String(message.getBody());
		log.info("message EscenarioDinamico: {}", mensaje);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
		RequestReglaEscenarioDinamicoDto request=castObject.convertJsonToRequestEscenario(mensaje);
		if(request !=null && !request.getPartida().isEmpty()) {
			clientOAG.reglaEscenarioDinamico(request);
		}
		
	}

	@RabbitListener(queues = "${rabbitmq.queue.reglasarbitraje}")
	@RabbitHandler
	public void consumerReglaArbitraje(Channel channel, Message message) throws IOException {
		log.info(Constantes.RECIEVED_LOG);
		String mensaje= new String(message.getBody());
		log.info("message ReglaArbitraje {}", mensaje);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
		ResponseOAGDto messageVO = castObject.convertJsonToReponseOAGDto(mensaje);
		if(messageVO !=null && !messageVO.getPartida().isEmpty()) {
			clientOAG.aplicarReglaArbitraje(convertObjectToJson(messageVO));
		}
	}

	@RabbitListener(queues = "${rabbitmq.queue.cambioprecio}")
	@RabbitHandler
	public void consumerCambioPrecio(Channel channel, Message message) throws Throwable {
		log.info(Constantes.RECIEVED_LOG);
		String mensaje= new String(message.getBody());
		log.info("message CambioPrecio {}", message);
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
		RequestActualizarPrecioPartidaDto request=castObject.convertJsonToRequestActualizarPrecioPartida(mensaje);
		if(request !=null) {
			clientOAG.actualizarPrecioPartida(request);
		}
	}

	private RequestReglaEscenarioDinamicoDto convertObjectToJson(ResponseOAGDto messageVO) {
		PartidaVO partidaVO = new PartidaVO();
		RequestReglaEscenarioDinamicoDto reglasEscenario = new RequestReglaEscenarioDinamicoDto();
		Double pretamo = 1200D;
		List<PartidaVO> lstPartidaVO = new ArrayList<>();
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

	public RequestReglaEscenarioDinamicoDto convertJsonRquestEscenario(String message) {
		final Gson gson = new Gson();
		return gson.fromJson(message, RequestReglaEscenarioDinamicoDto.class);
	}



}

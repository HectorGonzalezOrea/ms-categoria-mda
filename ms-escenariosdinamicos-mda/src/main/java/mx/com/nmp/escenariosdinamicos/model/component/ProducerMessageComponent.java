package mx.com.nmp.escenariosdinamicos.model.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import mx.com.nmp.escenariosdinamicos.utils.Constantes;



@Component
public class ProducerMessageComponent {
	
	 private static final Logger log = LoggerFactory.getLogger(ProducerMessageComponent.class);
	
	@Autowired
    RabbitTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchange.escenarios}")
    private String exchangeName;
	
	@Value("${rabbitmq.exchange.arbitraje}")
	private String exchangeReglasArbitraje;
	
	@Value("${rabbitmq.exchange.cambioprecio}")
	private String exchangeCambioPrecio;
	
	@Value("${rabbitmq.routingkey}")
    private String routingKey;
	
	/**
	 * Encola los mensajes al exchange
	 * queue regla escenarios dinámicos 
	 * @param  message
	 * */
	@Retryable(value= {Exception.class},maxAttempts=3, backoff=@Backoff(delay=4000))
	public String producerReglaEscenarioDinamico(String message) {
		log.info(Constantes.SEND_LOG);
		log.info("{}" , message );
		rabbitTemplate.convertAndSend(exchangeName,routingKey,message);
		return Constantes.SUCCESS_SEND_LOG;
	}
	/**
	 * Encola los mensajes al exchange
	 * queue reglas de arbitraje
	 * @param message
	 * */	
	@Retryable(value= {Exception.class},maxAttempts=3, backoff=@Backoff(delay=4000))
	public String producerReglaArbitraje(String message) {
		log.info(Constantes.SEND_LOG);
		log.info("{}" , message );
		rabbitTemplate.convertAndSend(exchangeReglasArbitraje,routingKey,message);
		return Constantes.SUCCESS_SEND_LOG;
	}
	/**
	 * Encola los mensajes al exchange
	 * queue cambio precio
	 * @param message
	 * */
	@Retryable(value= {Exception.class},maxAttempts=3, backoff=@Backoff(delay=4000))
	public String producerCambioPrecio(String message) {
		log.info(Constantes.SEND_LOG);
		log.info("{}" , message );
		rabbitTemplate.convertAndSend(exchangeCambioPrecio,routingKey,message);
		return Constantes.SUCCESS_SEND_LOG;
	}

}

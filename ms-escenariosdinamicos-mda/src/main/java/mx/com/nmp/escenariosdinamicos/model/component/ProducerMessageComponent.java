package mx.com.nmp.escenariosdinamicos.model.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class ProducerMessageComponent {
	
	 private static final Logger log = LoggerFactory.getLogger(ProducerMessageComponent.class);
	
	@Autowired
    RabbitTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchange.escenarios}")
    private String EXCHANGE_NAME;
	@Value("${rabbitmq.exchange.arbitraje}")
	private String EXCHANGE_REGLASARBITRAJE;
	@Value("${rabbitmq.exchange.cambioprecio}")
	private String EXCHANGE_CAMBIOPRECIO;
	
	@Value("${mda.rabbitmq.routingkey}")
    private String ROUTING_KEY = "message";
	
	/**
	 * Encola los mensajes al exchange
	 * queue regla escenarios dinámicos 
	 * @param  message
	 * */
	public String producerReglaEscenarioDinamico(String message) {
		log.info("send message ==>> " +message );
		rabbitTemplate.convertAndSend(EXCHANGE_NAME,ROUTING_KEY,message);
		return "successful send ";
	}
	/**
	 * Encola los mensajes al exchange
	 * queue reglas de arbitraje
	 * @param message
	 * */	
	public String producerReglaArbitraje(String message) {
		log.info("send message ==>> " +message );
		rabbitTemplate.convertAndSend(EXCHANGE_REGLASARBITRAJE,ROUTING_KEY,message);
		
		return "successful send ";
	}
	/**
	 * Encola los mensajes al exchange
	 * queue cambio precio
	 * @param message
	 * */
	public String producerCambioPrecio(String message) {
		log.info("send message ==>> " +message );
		rabbitTemplate.convertAndSend(EXCHANGE_CAMBIOPRECIO,ROUTING_KEY,message);
		return "successful send ";
	}

}

package mx.com.nmp.escenariosdinamicos.model.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerMessageComponent {
	
	private static final Logger log = LoggerFactory.getLogger(ConsumerMessageComponent.class);

	@RabbitListener(queues = "${rabbitmq.queue}")
	public void consumerReglaEscenarioDinamico(String message) {
		log.info("Recieved Message: " + message);
	}
	
	@RabbitListener(queues = "${rabbitmq.queue.reglasarbitraje}")
	public void consumerReglaArbitraje(String message) {
		log.info("Recieved Message: " + message);
	}
	
	@RabbitListener(queues = "${rabbitmq.queue.cambioprecio}")
	public void consumerCambioPrecio(String message) {
		log.info("Recieved Message: " + message);
	}
	
	
}

package mx.com.nmp.establecimientoprecios.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.establecimientoprecios.model.GeneralResponse;
import mx.com.nmp.establecimientoprecios.model.ListaMonedas;
import mx.com.nmp.establecimientoprecios.oag.client.service.OAGService;

@Service
public class MonedasService {

private static final Logger log = LoggerFactory.getLogger(MonedasService.class);
	
	@Autowired
	private OAGService oagService;
	
	public GeneralResponse agregarMonedas( ) {
		log.info("Entrando al método agregarMonedas ");
		GeneralResponse gr = null;
		
		Boolean insertado = oagService.almacenarMonedas();
		
		if(Boolean.TRUE.equals(insertado)) {
			gr = new GeneralResponse();
			gr.setMessage("Alta exitosa.");
		}
		
		return gr;
	}
	
	public GeneralResponse actualizarMonedas(ListaMonedas monedas) {
		log.info("Entrando al método actualizarMonedas ");
		GeneralResponse gr = null;
		
		if(monedas != null) {
			Boolean insertado = oagService.actualizarMonedas();
			
			if(Boolean.TRUE.equals(insertado)) {
				gr = new GeneralResponse();
				gr.setMessage("Ajuste exitosa.");
			}
		}
		
		return gr;
	}
}

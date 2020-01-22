package mx.com.nmp.establecimientoprecios.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.establecimientoprecios.model.GeneralResponse;
import mx.com.nmp.establecimientoprecios.model.ListaMonedas;
import mx.com.nmp.establecimientoprecios.oag.controller.OAGController;

@Service
public class MonedasService {

private static final Logger log = LoggerFactory.getLogger(MonedasService.class);
	
	@Autowired
	private OAGController oagController;
	
	public GeneralResponse agregarMonedas(ListaMonedas monedas) {
	
		GeneralResponse gr = null;
		
		Boolean insertado = oagController.almacenarMonedas();
		
		if(insertado) {
			gr = new GeneralResponse();
			gr.setMessage("Alta exitosa.");
		}
		
		return gr;
	}
	
	public GeneralResponse actualizarMonedas(ListaMonedas monedas) {
		
		GeneralResponse gr = null;
		
		if(monedas != null) {
			Boolean insertado = oagController.actualizarMonedas();
			
			if(insertado) {
				gr = new GeneralResponse();
				gr.setMessage("Alta exitosa.");
			}
		}
		
		return gr;
	}
}

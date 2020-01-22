package mx.com.nmp.establecimientoprecios.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.establecimientoprecios.model.GeneralResponse;
import mx.com.nmp.establecimientoprecios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.establecimientoprecios.oag.controller.OAGController;

@Service
public class AnclaService {

	private static final Logger log = LoggerFactory.getLogger(AnclaService.class);
	
	@Autowired
	private OAGController oagController;
	
	public GeneralResponse ajusteValorAncla(ModificarValorAnclaOroDolar valorAncla) {
		log.info("ajusteValorAncla");
		
		GeneralResponse gr = null;
		
		if(valorAncla != null) {
			Boolean insertado = oagController.valorAncla();
			if(insertado) {
				gr = new GeneralResponse();
				gr.setMessage("Alta exitosa.");
			}
		}

		return gr;
	}
	
}
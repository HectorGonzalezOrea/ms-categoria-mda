package mx.com.nmp.establecimientoprecios.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.establecimientoprecios.apiproductos.controller.ApiProductosController;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ActualizarPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ActualizarPreciosResponseVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ConsultaPartidaRequestVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ConsultaPartidaResponseVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ProductoMidasVO;
import mx.com.nmp.establecimientoprecios.model.GeneralResponse;
import mx.com.nmp.establecimientoprecios.model.ListaPrecioPartidas;
import mx.com.nmp.establecimientoprecios.model.ListaPrecioPartidasInner;
import mx.com.nmp.establecimientoprecios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.establecimientoprecios.mshistoricoprecios.controller.HistoricoPreciosController;
import mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo.HistoricoPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.oag.controller.OAGController;
import mx.com.nmp.establecimientoprecios.oag.vo.GestionPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.oag.vo.ProductoOAGVO;
import mx.com.nmp.establecimientoprecios.utils.ConverterUtil;

@Service
public class PreciosService {

	private static final Logger log = LoggerFactory.getLogger(PreciosService.class);
	
	@Autowired
	private OAGController oagController;
	
	@Autowired
	private ApiProductosController apiProductosController;
	
	@Autowired
	private HistoricoPreciosController historicoPreciosController;

	public Boolean almacenarAjustePrecios(String usuario, ListaPrecioPartidas listaPrecioPartidas) {
		log.info("alamcenarAjustePrecios");

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		Boolean correcto = false;
		
		if (listaPrecioPartidas != null) {
			
			GestionPreciosRequestVO gpRequest = new GestionPreciosRequestVO();
			ArrayList<ProductoOAGVO> informacionProductoOAG = new ArrayList<>();
			ProductoOAGVO poag =  null;
			for(ListaPrecioPartidasInner partida : listaPrecioPartidas) {
				// llenado para el servicio gestionPrecios de OAG
				poag = new ProductoOAGVO();
				poag.setFolio(new Long(partida.getFolioPartida()));
				poag.setSku(partida.getSku());
				poag.setPrecio(partida.getPrecioModificado());
				
				informacionProductoOAG.add(poag);
				
				gpRequest.setEnLinea(partida.getEnLinea());
				
			}
			gpRequest.setInformacionProductos(informacionProductoOAG);
			
			Boolean insertadoOAG = oagController.gestionPrecios(gpRequest);
			
			if (Boolean.TRUE.equals(insertadoOAG)) {
				this.partidasPreciosAPIProductos(usuario, listaPrecioPartidas);
			}
		}

		return correcto;
	}
	
	/*
	 *
	 * Servicio para el ajuste de precios - API Productos
	 */
	private Boolean partidasPreciosAPIProductos(String usuario, ListaPrecioPartidas listaPrecioPartidas) {
		log.info("partidasPreciosAPIProductos");
		Boolean correcto = false;
		ConsultaPartidaRequestVO cpRequest = new ConsultaPartidaRequestVO();
		ActualizarPreciosRequestVO apRequest = new ActualizarPreciosRequestVO();
		
		for(ListaPrecioPartidasInner partida : listaPrecioPartidas) {
			try {
				cpRequest.setFolio(new Long(partida.getFolioPartida()));
				cpRequest.setSku(partida.getSku());
				
				ConsultaPartidaResponseVO cpResp = apiProductosController.consultaPartida(cpRequest);
				
				if(cpResp != null) {
					for(ProductoMidasVO pm : cpResp) {
						pm.setPrecioVentaAct(partida.getPrecioModificado().toString());
						pm.setPrecioVentaSalida(partida.getPrecioModificado().toString());
						
						apRequest.add(pm);
					}
				}
			} catch (Exception e) {
				log.error("Exception {} " , e);
			}
		}
		
		Boolean actualizadoAPIProductos = apiProductosController.actualizarPartida(apRequest);
		
		if(Boolean.TRUE.equals(actualizadoAPIProductos)) {
			correcto = this.partidasPreciosMSHP(usuario , listaPrecioPartidas);
		}
		
		return correcto;
	}
	
	/*
	 * Servicio para el ajuste de precios - MS Historico Precios
	 */
	private Boolean partidasPreciosMSHP(String usuario, ListaPrecioPartidas listaPrecioPartidas) {
		log.info("partidasPreciosMSHP");
		
		HistoricoPreciosRequestVO hpRequest = null;
		Boolean correcto = false;
		
		for(ListaPrecioPartidasInner partida : listaPrecioPartidas) {
			try {
				hpRequest = new HistoricoPreciosRequestVO();
				
				hpRequest.setSku(partida.getSku());
				hpRequest.setPrecioActual(partida.getPrecioActual());
				hpRequest.setPrecioModificado(partida.getPrecioModificado());
				hpRequest.setFolioPartida(partida.getFolioPartida());
				
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				hpRequest.setFecha(timestamp.toString());
				
				Boolean insertadoHistoricoPrecios = historicoPreciosController.insertaHistoricoPrecios(usuario, hpRequest);
				
				if(Boolean.TRUE.equals(insertadoHistoricoPrecios)) {
					correcto = true;
				}
			} catch (Exception e) {
				log.error("Exception {} " , e);
			}
		}
		
		return correcto;
	}
	
}

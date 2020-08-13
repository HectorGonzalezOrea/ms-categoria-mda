package mx.com.nmp.establecimientoprecios.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.nmp.establecimientoprecios.apiproductos.service.ApiProductosService;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ActualizarPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ConsultaPartidaRequestVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.ConsultaPartidaResponseVO;
import mx.com.nmp.establecimientoprecios.apiproductos.vo.DatumVO;
import mx.com.nmp.establecimientoprecios.model.ListaPrecioPartidas;
import mx.com.nmp.establecimientoprecios.model.ListaPrecioPartidasInner;
import mx.com.nmp.establecimientoprecios.mshistoricoprecios.service.HistoricoPreciosService;
import mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo.HistoricoPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.oag.client.service.OAGService;
import mx.com.nmp.establecimientoprecios.oag.vo.GestionPreciosRequestVO;
import mx.com.nmp.establecimientoprecios.oag.vo.ProductoOAGVO;


@Service
public class PreciosService {

	private static final Logger log = LoggerFactory.getLogger(PreciosService.class);
	
	@Autowired
	private OAGService oagController;
	
	@Autowired
	private ApiProductosService apiProductosController;
	
	@Autowired
	private HistoricoPreciosService historicoPreciosService;

	public Boolean almacenarAjustePrecios(String usuario, ListaPrecioPartidas listaPrecioPartidas) {
		log.info("alamcenarAjustePrecios");		
		Boolean correcto = false;
		
		if (listaPrecioPartidas != null) {
			
			GestionPreciosRequestVO gpRequest = new GestionPreciosRequestVO();
			ArrayList<ProductoOAGVO> informacionProductoOAG = new ArrayList<>();
			ProductoOAGVO poag =  null;
			for(ListaPrecioPartidasInner partida : listaPrecioPartidas) {
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
				correcto = this.partidasPreciosAPIProductos(usuario, listaPrecioPartidas);
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
		
		List<DatumVO> data = new ArrayList<>();
		for(ListaPrecioPartidasInner partida : listaPrecioPartidas) {
			try {
				cpRequest.setFolio(new Long(partida.getFolioPartida()));
				cpRequest.setSku(partida.getSku());
				
				ConsultaPartidaResponseVO cpResp = apiProductosController.consultaPartida(cpRequest);
				
				if(cpResp != null) {
					for(DatumVO producto : cpResp) {
						producto.setPrecioVentaAct(partida.getPrecioModificado().toString());
						producto.setPrecioVentaSalida(partida.getPrecioModificado().toString());
						
						data.add(producto);
						
					}
				}
			} catch (Exception e) {
				log.error("Exception {0} " , e);
			}
		}
		
		if(!data.isEmpty()) {
			apRequest.setData(data);
			
			Boolean actualizadoAPIProductos = apiProductosController.actualizarPartida(apRequest);
			
			if(Boolean.TRUE.equals(actualizadoAPIProductos)) {
				correcto = this.partidasPreciosMSHP(usuario , listaPrecioPartidas);
			}
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
				hpRequest.setFecha(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(timestamp));
				
				Boolean insertadoHistoricoPrecios = historicoPreciosService.insertaHistoricoPrecios(usuario, hpRequest);
				
				if(Boolean.TRUE.equals(insertadoHistoricoPrecios)) {
					correcto = true;
				}
			} catch (Exception e) {
				log.error("Exception {0} " , e);
			}
		}
		
		return correcto;
	}
	
}

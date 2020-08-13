package mx.com.nmp.establecimientoprecios.service;


import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.com.nmp.establecimientoprecios.exceptions.TablasReferenciaException;
import mx.com.nmp.establecimientoprecios.model.GeneralResponse;
import mx.com.nmp.establecimientoprecios.model.ModificarValorAnclaOroDolar;
import mx.com.nmp.establecimientoprecios.utils.Constantes;
import mx.com.nmp.ms.sivad.cambiario.api.ws.TipoCambiarioEndpointService;
import mx.com.nmp.ms.sivad.cambiario.api.ws.TipoCambiarioService;
import mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes.ActualizacionesType;
import mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes.SucursalesType;
import mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes.TipocambioType;
import mx.com.nmp.ms.sivad.cambiario.ws.diamantes.datatypes.TiposType;
import mx.com.nmp.ms.sivad.referencia.adminapi.ws.ReferenciaValorAnclaOroService;
import mx.com.nmp.ms.sivad.referencia.adminapi.ws.ReferenciaValorAnclaOroServiceEndpointService;
import mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes.ValorAnclaOroSucursal;
import mx.com.nmp.ms.sivad.referencia.ws.oro.datatypes.ValorAnclaOroSucursalRequest;

import static mx.com.nmp.establecimientoprecios.utils.Constantes.MESSAGE_SUCCESS;

@Service
public class AnclaService {

	

	@Value("${ws.tablasreferencia.tipocambio.urlBase}")
	protected String urlBaseTipoCambio;
	
	@Value("${ws.tablasreferencia.anclaoro.urlBase}")
	protected String urlBaseOro;
	
	@Value("${ws.tablareferencia.apikey.value}")
	protected String tablasReferenciaApiKeyValue;
	
	@Value("${ws.tablasreferencia.servicio.tipocambio.ajustar}")
	protected String servicioAjustarTipoCambio;
	
	@Value("${ws.tablasreferencia.servicio.valoranclaoro.ajustar}")
	protected String servicioAjustarValorAnclaOro;



	private static final Logger log = LoggerFactory.getLogger(AnclaService.class);
	
	
	/**
	 * @param valorAncla
	 * @return
	 */
	public GeneralResponse ajusteValorAncla(ModificarValorAnclaOroDolar valorAncla) throws TablasReferenciaException {
		log.info("ajusteValorAncla");
		
		GeneralResponse gr = new GeneralResponse();
		
		if(valorAncla != null && ajusteValorAnclaOro(valorAncla) && ajusteValorAnclaDolar(valorAncla)) {
			gr.setMessage(MESSAGE_SUCCESS);
		} else {
			throw new TablasReferenciaException("Parametros nulos o invalidos al envocar el ajuste de valor ancla. Parametros: " + valorAncla);
		}
		return gr;
	}
	
	/**
	 * @param valorAncla
	 * @return
	 */
	private boolean ajusteValorAnclaOro(ModificarValorAnclaOroDolar valorAncla)  {
		boolean ret = false;
		URL url = null;
		ReferenciaValorAnclaOroServiceEndpointService anclaOroServiceEndpointService = null;
		ReferenciaValorAnclaOroService anclaOroService = null;
		
		ValorAnclaOroSucursalRequest anclaOroSucursalRequest = new ValorAnclaOroSucursalRequest();
		ValorAnclaOroSucursal anclaOroSucursal = null;
		try {
			if (valorAncla.getSucursales() != null) {
				url = new URL(urlBaseOro+servicioAjustarValorAnclaOro+Constantes.SUFIJO_WSDL);
				anclaOroServiceEndpointService = new ReferenciaValorAnclaOroServiceEndpointService(url);
				anclaOroService = anclaOroServiceEndpointService.getReferenciaValorAnclaOroServiceEndpointPort();				
				Map<String, Object> reqCtx = ((BindingProvider)anclaOroService).getRequestContext();
				reqCtx.put(Constantes.HEADER_APIKEY, tablasReferenciaApiKeyValue);
				
				for (String sucursal: valorAncla.getSucursales() ) {
					anclaOroSucursal = new ValorAnclaOroSucursal();
					
					anclaOroSucursal.setCalidad(Constantes.CALIDAD_ORO_DEFAULT);
				    anclaOroSucursal.setPrecio(BigDecimal.valueOf(valorAncla.getValorAnclaOro()));
					anclaOroSucursal.setSucursal(Integer.valueOf(sucursal));
					anclaOroSucursalRequest.getValoresAnclaOroSucursal().add(anclaOroSucursal);
					anclaOroService.actualizarValorAnclaOro(anclaOroSucursalRequest);
					
					ret = true;
				}
				
				
			}
			} catch (MalformedURLException e) {
				
				log.error("error al ajustar el valor ancla oro {0}",e);
			}
		
		return ret;
		
	}
	
	/**
	 * @param valorAncla
	 * @return
	 */
	private boolean ajusteValorAnclaDolar(ModificarValorAnclaOroDolar valorAncla) {
		boolean ret = false;
		URL url = null;
		TipoCambiarioEndpointService cambiarioEndpointService = null;
		TipoCambiarioService cambiarioService = null;
		
		TiposType tiposType = null;
		ActualizacionesType actualizacionesType = null;
		TipocambioType tipocambioType = null;
		SucursalesType sucursalesType = null;
		XMLGregorianCalendar gregorianCalendar = null; 
		
		List<Integer> lstSucursales =  new ArrayList<>();
		
		try {
			if (valorAncla.getSucursales() != null && !valorAncla.getSucursales().isEmpty()) {
				tiposType = new TiposType();
				actualizacionesType = new ActualizacionesType();
				tipocambioType = new TipocambioType();
				sucursalesType = new SucursalesType();
				
			
				gregorianCalendar =  DatatypeFactory.newInstance().newXMLGregorianCalendar();
				gregorianCalendar.setDay(valorAncla.getFechaAplicacion().getDayOfMonth());
				gregorianCalendar.setMonth(valorAncla.getFechaAplicacion().getMonthValue());
				gregorianCalendar.setYear(valorAncla.getFechaAplicacion().getYear());
				
				tiposType.setFecha(gregorianCalendar);
				
				tipocambioType.setOrigen(Constantes.MONEDA_ORIGEN);
				tipocambioType.setDestino(Constantes.MONEDA_DESTINO);
				tipocambioType.setValor(BigDecimal.valueOf(valorAncla.getValorAnclaOro()));
				lstSucursales = valorAncla.getSucursales().stream().map(Integer::valueOf).collect(Collectors.toList());
				sucursalesType.getSucursal().addAll(lstSucursales);
				
				tipocambioType.setSucursales(sucursalesType);
				actualizacionesType.getTipocambio().add(tipocambioType);
				tiposType.setActualizaciones(actualizacionesType);
				
				url = new URL(urlBaseTipoCambio+servicioAjustarTipoCambio+Constantes.SUFIJO_WSDL);
				cambiarioEndpointService = new TipoCambiarioEndpointService(url);
				cambiarioService = cambiarioEndpointService.getTipoCambiarioEndpointPort();
				cambiarioService.actualizar(tiposType);
				ret = true;
			}
		
		} catch (Exception e) {
			log.error("error al ajustar el valor ancla dolar {0}",e);
		}
		
		return ret;
	}
	
}


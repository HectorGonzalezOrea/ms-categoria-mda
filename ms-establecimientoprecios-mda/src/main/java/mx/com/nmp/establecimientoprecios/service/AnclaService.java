package mx.com.nmp.establecimientoprecios.service;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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

import mx.com.nmp.establecimientoprecios.model.GeneralResponse;
import mx.com.nmp.establecimientoprecios.model.ModificarValorAnclaOroDolar;
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

@Service
public class AnclaService {

	private static final String HEADER_APIKEY = "X-IBM-Client-Id";

	@Value("${ws.tablasreferencia.tipocambio.urlBas}")
	protected String urlBaseTipoCambio;
	
	@Value("${ws.tablasreferencia.anclaoro.urlBase}")
	protected String urlBaseOro;
	
	@Value("${ws.tablareferencia.apikey.value}")
	protected String tablasReferenciaApiKeyValue;
	
	@Value("${ws.tablasreferencia.servicio.tipocambio.ajustar}")
	protected String servicioAjustarTipoCambio;
	
	@Value("${ws.tablasreferencia.servicio.valoranclaoro.ajustar}")
	protected String servicioAjustarValorAnclaOro;

	private static final String MONEDA_DESTINO = "MXN";
	private static final String MONEDA_ORIGEN = "USD";
	private static final String CALIDAD_ORO_DEFAULT = "24_Q";
	private static final String SUFIJO_WSDL = "?WSDL";

	private static final Logger log = LoggerFactory.getLogger(AnclaService.class);
	
	
	/**
	 * @param valorAncla
	 * @return
	 */
	public GeneralResponse ajusteValorAncla(ModificarValorAnclaOroDolar valorAncla) {
		log.info("ajusteValorAncla");
		
		GeneralResponse gr = new GeneralResponse();
		
		if(valorAncla != null && ajusteValorAnclaOro(valorAncla) && ajusteValorAnclaDolar(valorAncla)) {
			gr.setMessage("Alta exitosa. ajusteValorAncla oro y dolar ");
		} else {
			gr.setMessage("Parametros nulos o incompletos al envocar el ajuste de valor ancla. Paramtros: " + valorAncla);
		}
		
		return gr;
	}
	
	/**
	 * @param valorAncla
	 * @return
	 */
	private boolean ajusteValorAnclaOro(ModificarValorAnclaOroDolar valorAncla) {
		boolean ret = false;
		URL url = null;
		ReferenciaValorAnclaOroServiceEndpointService anclaOroServiceEndpointService = null;
		ReferenciaValorAnclaOroService anclaOroService = null;
		
		ValorAnclaOroSucursalRequest anclaOroSucursalRequest = new ValorAnclaOroSucursalRequest();
		try {
			if (valorAncla.getSucursales() != null) {
				for (String sucursal: valorAncla.getSucursales() ) {
					ValorAnclaOroSucursal anclaOroSucursal = new ValorAnclaOroSucursal();
					anclaOroSucursal.setCalidad(CALIDAD_ORO_DEFAULT);
					if (valorAncla.getValorAnclaOro()!=null) {
						anclaOroSucursal.setPrecio(new BigDecimal(valorAncla.getValorAnclaOro().doubleValue()));
					} else {
						anclaOroSucursal.setPrecio(new BigDecimal(valorAncla.getValorAnclaOro().doubleValue()));
					}
					anclaOroSucursal.setSucursal(Integer.valueOf(sucursal));
				}

				url = new URL(urlBaseOro+servicioAjustarValorAnclaOro+SUFIJO_WSDL);
				anclaOroServiceEndpointService = new ReferenciaValorAnclaOroServiceEndpointService(url);
				anclaOroService = anclaOroServiceEndpointService.getReferenciaValorAnclaOroServiceEndpointPort();				
				Map<String, Object> reqCtx = ((BindingProvider)anclaOroService).getRequestContext();
				reqCtx.put(HEADER_APIKEY, tablasReferenciaApiKeyValue);				
				anclaOroService.actualizarValorAnclaOro(anclaOroSucursalRequest);
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error al ajustar el valor ancla oro ",e);
			throw new RuntimeException("error al ajustar el valor ancla oro ",e);
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
		
		List<Integer> lstSucursales = null;
		
		try {
			if (valorAncla.getSucursales() != null) {
				tiposType = new TiposType();
				actualizacionesType = new ActualizacionesType();
				tipocambioType = new TipocambioType();
				sucursalesType = new SucursalesType();
				lstSucursales = new ArrayList<Integer>();
			
				gregorianCalendar =  DatatypeFactory.newInstance().newXMLGregorianCalendar();
				gregorianCalendar.setDay(valorAncla.getFechaAplicacion().getDayOfMonth());
				gregorianCalendar.setMonth(valorAncla.getFechaAplicacion().getMonthValue());
				gregorianCalendar.setYear(valorAncla.getFechaAplicacion().getYear());
				
				tiposType.setFecha(gregorianCalendar);
				
				tipocambioType.setOrigen(MONEDA_ORIGEN);
				tipocambioType.setDestino(MONEDA_DESTINO);
				if (valorAncla.getValorAnclaOro()!=null) {
					tipocambioType.setValor(new BigDecimal(valorAncla.getValorAnclaOro().doubleValue()));
				} else {
					tipocambioType.setValor(new BigDecimal(valorAncla.getValorAnclaOro().doubleValue()));
				}
				lstSucursales = valorAncla.getSucursales().stream().map(Integer::valueOf).collect(Collectors.toList());
				sucursalesType.getSucursal().addAll(lstSucursales);
				
				tipocambioType.setSucursales(sucursalesType);
				actualizacionesType.getTipocambio().add(tipocambioType);
				tiposType.setActualizaciones(actualizacionesType);
				
				url = new URL(urlBaseTipoCambio+servicioAjustarTipoCambio+SUFIJO_WSDL);
				cambiarioEndpointService = new TipoCambiarioEndpointService(url);
				Map<String, Object> reqCtx = ((BindingProvider)cambiarioEndpointService).getRequestContext();
				reqCtx.put(HEADER_APIKEY, tablasReferenciaApiKeyValue);
				cambiarioService = cambiarioEndpointService.getTipoCambiarioEndpointPort();
				cambiarioService.actualizar(tiposType);
				ret = true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error al ajustar el valor ancla dolar ",e);
			throw new RuntimeException("error al ajustar el valor ancla dolar ",e);
		}
		
		return ret;
	}
	
}


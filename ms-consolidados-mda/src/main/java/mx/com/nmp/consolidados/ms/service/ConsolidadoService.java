package mx.com.nmp.consolidados.ms.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import mx.com.nmp.consolidados.model.Consolidados;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoResInner;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.model.InlineResponse200;
import mx.com.nmp.consolidados.model.ModificarPrioridadArchivoConsolidadoReq;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
import mx.com.nmp.consolidados.mongodb.service.SequenceGeneratorService;
import mx.com.nmp.consolidados.msestablecimientoprecios.controller.EstablecimientoPreciosController;
import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePrecio;
import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePreciosRequestVO;
import mx.com.nmp.consolidados.oag.controller.OAGController;
import mx.com.nmp.consolidados.oag.vo.AdjuntoVO;
import mx.com.nmp.consolidados.oag.vo.AdjuntosVO;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasRequestVO;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasResponseVO;
import mx.com.nmp.consolidados.oag.vo.EnviarNotificacionRequestVO;
import mx.com.nmp.consolidados.oag.vo.PartidaResponseVO;
import mx.com.nmp.consolidados.oag.vo.PartidaVO;
import mx.com.nmp.consolidados.utils.ConvertStringToBase64;
import mx.com.nmp.consolidados.utils.ExcelUtils;
import mx.com.nmp.consolidados.utils.HtmlUtil;

import static mx.com.nmp.consolidados.utils.Constantes.ASUNTO_ARBITRARIEDAD_TRUE;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_ARBITRARIEDAD_TRUE;
import static mx.com.nmp.consolidados.utils.Constantes.NOMBRE_EXCEL;

import static mx.com.nmp.consolidados.utils.Constantes.ASUNTO_AJUSTE_PRECIOS_FALSE;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_PRECIOS_FALSE_INICIO;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_PRECIOS_FALSE_FINAL;

import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_BI;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_BI_FINAL;
import static mx.com.nmp.consolidados.utils.Constantes.ARMAR_TABLA_INICIO_FALLIDOS;
import static mx.com.nmp.consolidados.utils.Constantes.ARMAR_TABLA_FINAL_FALLIDOS;

@Service
public class ConsolidadoService {
	
	private static final Logger log = LoggerFactory.getLogger(ConsolidadoService.class);

	@Value("${oag.resource.oauth.enviarCorreo.para}")
	protected String para;
	
	@Value("${oag.resource.oauth.enviarCorreo.de}")
	protected String de;
	
	public static final String FECHA = "vigencia";
	private static final String SEQUENCE = "consolidado_sequence";
	public static final String ID_ARCHIVO = "idArchivo";
	public static final String PRIORIDAD = "prioridad";
	public static final String CONSOLIDADOS = "consolidados";

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private OAGController oAGController;
	
	@Autowired
	private EstablecimientoPreciosController establecimientoPreciosController;

	public Boolean crearConsolidado(Consolidados request) {
		log.info("ConsolidadoService.crearConsolidado");
		Boolean insertado = false;
		if (request != null) {
			CastConsolidados castConsolidados = new CastConsolidados();
			ArchivoEntity consolidado = new ArchivoEntity();
			File archivo = null;
			FileReader targetReader = null;

			try {
				consolidado.setVigencia(request.getVigencia());
				consolidado.setNombreAjuste(request.getNombreAjuste());
				consolidado.setEmergente(request.getEmergente());
				consolidado.setFechaAplicacion(request.getFechaAplicacion());
				consolidado.setIdArchivo(sequenceGeneratorService.generateSequence(SEQUENCE));
				archivo = request.getAdjunto();
				targetReader = new FileReader(archivo);
				BufferedReader b = new BufferedReader(targetReader);
				List<InfoProducto> lst = castConsolidados.cvsLectura(b);
				consolidado.setAdjunto(castConsolidados.lstToJson(lst));
				consolidado.setNombreArchivo(archivo.getName());
				consolidado.setPrioridad(consultaArhivoConsolidadoByDate(request.getFechaAplicacion()).size() + 1);
				log.info(castConsolidados.lstToJson(lst));
			} catch (FileNotFoundException e) {
				log.info("FileNotFoundException : {} ", e);
			}
			mongoTemplate.insert(consolidado);
			insertado = true;
		}
		return insertado;
	}

	public ArrayList<ConsultarArchivoConsolidadoResInner> getConsolidados(String vigencia) {
		log.info("entrando a ConsolidadoService.getConsolidados");
		ArrayList<ConsultarArchivoConsolidadoResInner> lstConsolidados = new ArrayList<>();

		Date fechaAplicaciondate = null;
		try {
			fechaAplicaciondate = new SimpleDateFormat("dd/MM/yyyy").parse(vigencia);
		} catch (ParseException e) {
			log.info("ParseException : {}", e);
		}

		List<ArchivoEntity> busquedaList = this.consultaArhivoConsolidadoByDate(fechaAplicaciondate);

		if (!busquedaList.isEmpty()) {
			CastConsolidados castConsolidados = new CastConsolidados();
			lstConsolidados = castConsolidados.toVOs(busquedaList);
		}
		log.info("saliendo a ConsolidadoService.getConsolidados");
		return lstConsolidados;
	}

	/*
	 * Eliminar Consolidados por id de archivo
	 */
	public Boolean eliminarConsolidado(String idArchivo) {
		log.info("eliminarConsolidado");

		Boolean eliminado = false;

		List<ArchivoEntity> consolidados = this.getConsolidados(new Integer(idArchivo));

		log.info("consolidados : {} ", consolidados.size());

		for (ArchivoEntity consolidado : consolidados) {
			String requestId = consolidado.getRequestIdCalendarizacion();
			
			log.info("requestId : {} ", requestId);
			if (requestId != null && !requestId.equals("")) {
				Boolean eliminadoESS = oAGController.eliminarCalendarizacion(requestId);
				if (Boolean.TRUE.equals(eliminadoESS)) {
					Boolean eliminadoMongo = this.eliminarConsolidado(consolidado);
					if (Boolean.TRUE.equals(eliminadoMongo)) {
						eliminado = true;
					}
				}
			} else {
				Boolean eliminadoMongo = this.eliminarConsolidado(consolidado);
				if (Boolean.TRUE.equals(eliminadoMongo)) {
					eliminado = true;
				}
			}
		}
		return eliminado;
	}

	/*
	 * Actualiza la prioridad del archivo consolidados
	 */
	public InlineResponse200 actualizarPrioridadArchivo(ModificarPrioridadArchivoConsolidadoReq request) {
		log.info("Actualizar Prioridad Archivo Consolidado");

		InlineResponse200 resp = null;

		Query query = new Query();
		query.addCriteria(Criteria.where(ID_ARCHIVO).is(request.getIdArchivo()));
		Update update = new Update();
		update.set(PRIORIDAD, request.getIdPrioridad());

		ArchivoEntity consolidado = mongoTemplate.findAndModify(query, update, ArchivoEntity.class);

		if (consolidado != null) {
			resp = new InlineResponse200();

			resp.setIdPosicion(consolidado.getPrioridad());
			resp.setNombreArchivo(consolidado.getNombreAjuste());
		}

		return resp;
	}

	/*
	 * Procesar Consolidados
	 */
	public Boolean procesarConsolidados(String usuario, String fechaAplicacion) {
		log.info("Procesar un archivo consolidado");

		Boolean procesado = false;
		
		// obtener consolidados
		ArrayList<ConsultarArchivoConsolidadoResInner> consolidados = this.getConsolidados(fechaAplicacion);

		ArrayList<ArbitrajePreciosPartidasRequestVO> listaVerificarRegistrosReq = null;
		// se arma los request de validar arbitrariedad
		if (consolidados != null) {
			listaVerificarRegistrosReq = this.armarVerificarRegistros(consolidados);

			ArrayList<ArbitrajePreciosPartidasResponseVO> listaVerificarRegistrosResp = null;
			// se verifica la arbitrariedad
			if (!listaVerificarRegistrosReq.isEmpty()) {
				listaVerificarRegistrosResp = this.verificarRegistros(listaVerificarRegistrosReq);

				this.enviarCorreoPartidasConArbitratierdad(listaVerificarRegistrosResp);
				
				// Establecimiento de precios
				this.enviarAjustePrecios(usuario, listaVerificarRegistrosReq, consolidados);
				
				procesado = true;
			}
		}
		
		return procesado;
	}
	
	private void enviarCorreoPartidasConArbitratierdad(ArrayList<ArbitrajePreciosPartidasResponseVO> listaVerificarRegistrosResp) {
		log.info("enviarCorreoPartidasConArbitratierdad");
		
		List<PartidaResponseVO> procesadas = new ArrayList<>();
		
		if(!listaVerificarRegistrosResp.isEmpty()) {
			listaVerificarRegistrosResp
			.stream()
			.forEach(lvr -> {
				if(!lvr.getPartida().isEmpty()) {
					lvr.getPartida()
					.stream()
					.forEach(p -> {
						if(Boolean.TRUE.equals(p.getCumpleArbitraje())) {
							procesadas.add(p);
						}
					});
				}
			});
			
			if(!procesadas.isEmpty()) {
				if(procesadas.size() <= 10) {
					this.enviarCorreoPartidasArbitrajeSinAdjunto(procesadas);
				} else {
					this.enviarCorreoPartidasArbitrariedadConAdjunto(procesadas);
				}
			}
			
		}
	}
	
	/*
	 * Arma un excel con las partidas que cumplieron el arbitraje
	 */
	private String armarExcelPartidasConArbitraje(List<PartidaResponseVO> procesadas) {
		log.info("armarExcelPartidasConArbitraje");

		String encodedString = "";

		ExcelUtils eu = new ExcelUtils();
		File archivo = eu.crearExcelNotificacionesCorrectas(procesadas);

		if (archivo != null) {
			try {
				encodedString = ConvertStringToBase64.encodeFileToBase64Binary(archivo);
			} catch (IOException e) {
				log.error("enviarCorreoPartidas {} ", e);
			}
		}

		return encodedString;

	}
	
	/*
	 * Enviar correo con adjunto sobre las partidas que si cumplieron con la arbitrariedad
	 */
	private void enviarCorreoPartidasArbitrariedadConAdjunto(List<PartidaResponseVO> procesadas) {
		log.info("enviarCorreoPartidasArbitrariedadConAdjunto");

		String contenido = this.armarExcelPartidasConArbitraje(procesadas);

		EnviarNotificacionRequestVO enr = new EnviarNotificacionRequestVO();
		enr.setDe(de);
		enr.setPara(para);
		enr.setAsunto(ASUNTO_ARBITRARIEDAD_TRUE);
		enr.setContenidoHTML(CONTENIDO_HTML_ARBITRARIEDAD_TRUE);

		AdjuntosVO ads = new AdjuntosVO();
		AdjuntoVO ad = new AdjuntoVO();
		
		ad.setNombreArchivo(NOMBRE_EXCEL);
		ad.setContenido(contenido);
		
		ArrayList<AdjuntoVO> lista = new ArrayList<>();
		lista.add(ad);
		
		ads.setAdjunto(lista);
		enr.setAdjuntos(ads);
		
		oAGController.enviarNotificacion(enr);

	}
	
	/*
	 * Enviar correo sobre las partidas que si cumplieron con la arbitrariedad
	 */
	private void enviarCorreoPartidasArbitrajeSinAdjunto(List<PartidaResponseVO> procesadas) {
		log.info("enviarCorreoPartidasArbitrajeSinAdjunto");

		EnviarNotificacionRequestVO enr = new EnviarNotificacionRequestVO();
		enr.setDe(de);
		enr.setPara(para);
		enr.setAsunto(ASUNTO_ARBITRARIEDAD_TRUE);
		
		HtmlUtil hu = new HtmlUtil();
		StringBuilder sb = hu.armarTablaPartidasConArbitrariedad(procesadas);
		
		enr.setContenidoHTML(CONTENIDO_HTML_ARBITRARIEDAD_TRUE + sb.toString());
		
		oAGController.enviarNotificacion(enr);
	}
	
	/*
	 * Servicio para armar la petición para verificar la arbitrariedad
	 */
	private ArrayList<ArbitrajePreciosPartidasRequestVO> armarVerificarRegistros(ArrayList<ConsultarArchivoConsolidadoResInner> consolidados) {
		log.info("armarVerificarRegistros"); 
		ArrayList<ArbitrajePreciosPartidasRequestVO> listAppRequest = new ArrayList<>();
		
		if (!consolidados.isEmpty()) {
			ArrayList<PartidaVO> partidas =  new ArrayList<>();
			
			consolidados
			.stream()
			.forEach( c -> {
				List<InfoProducto> productosConsolidado = c.getProducto();
				
				ArbitrajePreciosPartidasRequestVO appRequest = new ArbitrajePreciosPartidasRequestVO();
				
				productosConsolidado
				.stream()
				.forEach( ip -> {
					PartidaVO p = new PartidaVO();
					p.setIdPartida(ip.getIdProducto().toString());
					p.setSku(ip.getFolioSku());
					p.setPrecioVenta(ip.getPrecioFinal());
					p.setMontoPrestamo(ip.getPrestamoCosto());
					partidas.add(p);
				});
				
				appRequest.setPartida(partidas);
				
				listAppRequest.add(appRequest);
			});
		}
		
		return listAppRequest;
	}

	/*
	 * Servicio para verificar la arbitrariedad
	 */
	private ArrayList<ArbitrajePreciosPartidasResponseVO> verificarRegistros(ArrayList<ArbitrajePreciosPartidasRequestVO> listaVerificarRegistros) {
		log.info("verificarRegistros"); 
		
		ArrayList<ArbitrajePreciosPartidasResponseVO> listaResponse = new ArrayList<>();
		
		if(!listaVerificarRegistros.isEmpty()) {
			
			listaVerificarRegistros
			.stream()
			.forEach( r -> {
				try {
					ArbitrajePreciosPartidasResponseVO response = oAGController.validarArbitrajePreciosPartidas(r);
					if(response != null) {						
						listaResponse.add(response);
					}
				} catch (Exception e) {
					log.error("Exception {}" , e);
				}
			});
		}
		return listaResponse;
	}
	
	/*
	 * 
	 */
	private void armarExcelPartidasNoAjuste(AjustePreciosRequestVO apRequest) {
		log.info("armarExcelPartidasNoAjuste");
		
			
	}
	
	/*
	 * 
	 */
	private String armarExcelPartidasNoAjusteConAdjuto(AjustePreciosRequestVO apRequest) {
		log.info("armarExcelPartidasNoAjusteConAdjuto");
		
		String encodedString = "";
		ArrayList<PartidaResponseVO> partidas = new ArrayList<>();
		
		apRequest
		.stream()
		.forEach( a -> {
			
			PartidaResponseVO partida = new PartidaResponseVO();
			partida.setSku(a.getSku());
			partida.setIdPartida(a.getFolioPartida());

			partidas.add(partida);
			
		});
		
		ExcelUtils eu = new ExcelUtils();
		File archivo = eu.crearExcelNotificacionesFallidas(partidas);
		
		if(archivo != null) {
			try {
				encodedString = ConvertStringToBase64.encodeFileToBase64Binary(archivo);
			} catch (IOException e) {
				log.error("enviarCorreoPartidas {} ", e);
			}
		}
		
		return encodedString;
	}
	
	/*
	 * Servicio para enviar el ajuste de precios
	 */
	private void enviarAjustePrecios(String usuario, ArrayList<ArbitrajePreciosPartidasRequestVO> listaVerificarRegistrosReq, ArrayList<ConsultarArchivoConsolidadoResInner> consolidados) {
		log.info("enviarAjustePrecios");
		
		if(!listaVerificarRegistrosReq.isEmpty()) {
			
			listaVerificarRegistrosReq
			.stream()
			.forEach( req -> {
				
				int i = 0;
				
				if(!req.getPartida().isEmpty()) {
					AjustePreciosRequestVO apRequest = new AjustePreciosRequestVO();
					
					req.getPartida()
					.stream()
					.forEach( p -> {
						
						AjustePrecio ap = new AjustePrecio();
						
						ap.setEnLinea(false);
						ap.setEscenario(CONSOLIDADOS);
						ap.setFolioPartida(p.getIdPartida());
						ap.setSku(p.getSku());
						ap.setPrecioActual(p.getPrecioVenta());
						ap.setPrecioModificado(p.getMontoPrestamo());
						
						apRequest.add(ap);
					});
					
					Boolean estatus = establecimientoPreciosController.ajustePrecios(usuario, apRequest);
					
					if(Boolean.FALSE.equals(estatus)) {
						EnviarNotificacionRequestVO enr = new EnviarNotificacionRequestVO();
						
						enr.setDe(de);
						enr.setPara(para);
						enr.setAsunto(ASUNTO_AJUSTE_PRECIOS_FALSE);
						
						StringBuilder sb = new StringBuilder();
						
						sb.append(CONTENIDO_HTML_PRECIOS_FALSE_INICIO);
						sb.append(CONTENIDO_HTML_BI);
						sb.append(consolidados.get(i).getNombreArchivo());
						sb.append(CONTENIDO_HTML_BI_FINAL);
						sb.append(CONTENIDO_HTML_PRECIOS_FALSE_FINAL);
						
						if(apRequest.size() <= 10) {
							sb.append(ARMAR_TABLA_INICIO_FALLIDOS);
							
							HtmlUtil hu = new HtmlUtil();
							StringBuilder sbFallidas = hu.armarTablaPartidasFallidasAjuste(apRequest);
							
							sb.append(sbFallidas.toString());
							sb.append(ARMAR_TABLA_FINAL_FALLIDOS);
							
							enr.setContenidoHTML(sb.toString());
						} else {
							
							enr.setContenidoHTML(sb.toString());
							
							AdjuntosVO as = new AdjuntosVO();
							
							List<AdjuntoVO> listAd = new ArrayList<>();
							
							AdjuntoVO a = new AdjuntoVO();
							a.setContenido(this.armarExcelPartidasNoAjusteConAdjuto(apRequest));
							a.setNombreArchivo("reporteFallidas.xlsx");
							
							listAd.add(a);
							as.setAdjunto(listAd);
							
							enr.setAdjuntos(as);
						}
						
						oAGController.enviarNotificacion(enr);
					}
				}
				i++;
			});
		}
	}
	
	/*
	 * Consulta consolidados por fecha
	 */
	@SuppressWarnings("deprecation")
	private List<ArchivoEntity> consultaArhivoConsolidadoByDate(Date vigencia) {
		Date fechaAplicacionInicioDia = CastConsolidados.resetTimeToDown(vigencia);
		log.info("fecha inicio dia: {}", fechaAplicacionInicioDia);
		Date fechaAplicacionFinDia = CastConsolidados.resetTimeToUp(vigencia);
		log.info("fecha fin dia: {}", fechaAplicacionFinDia);
		Query q = new Query();
		q.addCriteria(Criteria.where(FECHA).gte(fechaAplicacionInicioDia).lt(fechaAplicacionFinDia));
		q.with(new Sort(new Order(Direction.ASC, PRIORIDAD)));
		
		List<ArchivoEntity> busquedaList = mongoTemplate.find(q, ArchivoEntity.class);
		log.info("query size(): {}", busquedaList.size());
		return busquedaList;
	}

	private List<ArchivoEntity> getConsolidados(Integer idArchivo) {
		log.info("getConsolidadosPorIdArchivo");

		Query query = new Query();
		Criteria aux = Criteria.where(ID_ARCHIVO).is(idArchivo);
		query.addCriteria(aux);

		return mongoTemplate.find(query, ArchivoEntity.class);
	}

	/*
	 * elimina consolidado en Mongo
	 */
	private Boolean eliminarConsolidado(ArchivoEntity consolidado) {
		log.info("eliminarConsolidadoMongo");

		Boolean eliminado = false;

		if (consolidado != null) {
			mongoTemplate.remove(consolidado);
			eliminado = true;
		}

		return eliminado;
	}
}

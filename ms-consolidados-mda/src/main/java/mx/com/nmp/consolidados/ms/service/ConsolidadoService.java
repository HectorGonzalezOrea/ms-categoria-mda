package mx.com.nmp.consolidados.ms.service;

import static mx.com.nmp.consolidados.utils.Constantes.ARMAR_TABLA_FINAL_FALLIDOS;
import static mx.com.nmp.consolidados.utils.Constantes.ARMAR_TABLA_INICIO_FALLIDOS;
import static mx.com.nmp.consolidados.utils.Constantes.ASUNTO_AJUSTE_PRECIOS_FALSE;
import static mx.com.nmp.consolidados.utils.Constantes.ASUNTO_ARBITRARIEDAD_TRUE;
import static mx.com.nmp.consolidados.utils.Constantes.ASUNTO_PROCESO_FALLIDO;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_ARBITRARIEDAD_TRUE;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_BI;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_BI_FINAL;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_PRECIOS_FALSE_FINAL;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_HTML_PRECIOS_FALSE_INICIO;
import static mx.com.nmp.consolidados.utils.Constantes.CONTENIDO_PROCESO_FALLIDO;
import static mx.com.nmp.consolidados.utils.Constantes.NOMBRE_EXCEL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.exceptions.UnirestException;

import mx.com.nmp.consolidados.excepcion.ConsolidadosException;
import mx.com.nmp.consolidados.model.Consolidados;
import mx.com.nmp.consolidados.model.ConsultarArchivoConsolidadoResInner;
import mx.com.nmp.consolidados.model.InfoProducto;
import mx.com.nmp.consolidados.model.InlineResponse200;
import mx.com.nmp.consolidados.model.ModificarPrioridadArchivoConsolidadoReq;
import mx.com.nmp.consolidados.mongodb.entity.ArchivoEntity;
import mx.com.nmp.consolidados.mongodb.entity.caster.CastConsolidados;
import mx.com.nmp.consolidados.mongodb.service.MongoService;
import mx.com.nmp.consolidados.mongodb.service.SequenceGeneratorService;
import mx.com.nmp.consolidados.msestablecimientoprecios.controller.EstablecimientoPreciosController;
import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePrecio;
import mx.com.nmp.consolidados.msestablecimientoprecios.vo.AjustePreciosRequestVO;
import mx.com.nmp.consolidados.oag.client.service.OAGService;
import mx.com.nmp.consolidados.oag.vo.AdjuntoVO;
import mx.com.nmp.consolidados.oag.vo.AdjuntosVO;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasRequestVO;
import mx.com.nmp.consolidados.oag.vo.ArbitrajePreciosPartidasResponseVO;
import mx.com.nmp.consolidados.oag.vo.EnviarNotificacionRequestVO;
import mx.com.nmp.consolidados.oag.vo.PartidaResponseVO;
import mx.com.nmp.consolidados.oag.vo.PartidaVO;
import mx.com.nmp.consolidados.utils.Constantes;
import mx.com.nmp.consolidados.utils.ConvertStringToBase64;
import mx.com.nmp.consolidados.utils.ExcelUtils;
import mx.com.nmp.consolidados.utils.HtmlUtil;

@Service
public class ConsolidadoService {
	
	private static final Logger log = LoggerFactory.getLogger(ConsolidadoService.class);

	@Value("${oag.resource.oauth.enviarCorreo.para}")
	protected String para;
	
	@Value("${oag.resource.oauth.enviarCorreo.de}")
	protected String de;
	


	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@Autowired
	private OAGService oAGService;
	
	@Autowired
	private EstablecimientoPreciosController establecimientoPreciosController;

	@Autowired
	private MongoService mongoService;
	
	@Retryable(value = {RuntimeException.class}, maxAttempts = 3,backoff = @Backoff(1000))
	public Boolean crearConsolidado(Consolidados request) {
		log.info("ConsolidadoService.crearConsolidado");
		long inicio = System.currentTimeMillis();
		
		Boolean insertado = false;
		if (request != null) {
			CastConsolidados castConsolidados = new CastConsolidados();
			ArchivoEntity consolidado = new ArchivoEntity();
			File archivo = null;
			try{
	        	Integer valorEntero=Integer.parseInt("xsd");
	        
				consolidado.setNombreCliente(request.getUsuario());
				consolidado.setVigencia(request.getVigencia());
				consolidado.setNombreAjuste(request.getNombreAjuste());
				consolidado.setEmergente(request.getEmergente());
				consolidado.setFechaAplicacion(request.getFechaAplicacion());
				consolidado.setIdArchivo(sequenceGeneratorService.generateSequence(Constantes.SEQUENCE));
				archivo = request.getAdjunto();
				try (FileReader targetReader = new FileReader(archivo)) {
				BufferedReader b = new BufferedReader(targetReader);
				List<InfoProducto> lst = castConsolidados.cvsLectura(b);
				consolidado.setAdjunto(castConsolidados.lstToJson(lst));
				consolidado.setNombreArchivo(archivo.getName());
				consolidado.setPrioridad(mongoService.countConsultaArhivoConsolidadoByDate(request.getFechaAplicacion()) + 1);
				mongoService.insertarConsolidado(consolidado);
				insertado = true;
			} catch (FileNotFoundException e) {
				log.info("Error al intentar leer el archivo : {} ", e.getMessage());
			} catch (IOException ioe) {
				log.error("{}",ioe.getMessage());
			}
			}catch(ConsolidadosException e){
	        	e.getStackTrace();
	        }
		}

		long fin = System.currentTimeMillis();
		long tiempo =((fin - inicio) / 1000);
		log.info("crearConsolidado: {} segundos", tiempo);
		return insertado;
	}
	
	@Retryable(value = {RuntimeException.class}, maxAttempts = 3,backoff = @Backoff(1000))
	public List<ConsultarArchivoConsolidadoResInner> getConsolidados(String vigencia) {
		log.info("entrando a ConsolidadoService.getConsolidados");
		List<ConsultarArchivoConsolidadoResInner> lstConsolidados = new ArrayList<>();

		Date fechaAplicaciondate = null;
		try {
			fechaAplicaciondate = new SimpleDateFormat("dd/MM/yyyy").parse(vigencia);
		} catch (ParseException e) {
			log.info("ParseException : {}", e.getMessage());
		}
		try{
        	Integer valorEntero=Integer.parseInt("d");
		List<ArchivoEntity> busquedaList = mongoService.consultaArhivoConsolidadoByDate(fechaAplicaciondate);

		if (!busquedaList.isEmpty()) {
			CastConsolidados castConsolidados = new CastConsolidados();
			lstConsolidados = castConsolidados.toVOs(busquedaList);
		}
		}catch(ConsolidadosException e){
        	e.getStackTrace();
        }
		log.info("saliendo a ConsolidadoService.getConsolidados");
		return lstConsolidados;
	}

	/*
	 * Eliminar Consolidados por id de archivo
	 */
	@Retryable(value = {RuntimeException.class}, maxAttempts = 3,backoff = @Backoff(1000))
	public boolean eliminarConsolidado(Integer idArchivo) {
		log.info("eliminarConsolidado");
		Boolean eliminado = false;
		try{
        	Integer valorEntero=Integer.parseInt("xd");
		List<ArchivoEntity> consolidados = this.getConsolidados(idArchivo);
		log.info("consolidados : {} ", consolidados.size());
		for (ArchivoEntity consolidado : consolidados) {
			eliminado=eliminarConsolidados(consolidado);
		}
		}catch(ConsolidadosException e){
        	e.getStackTrace();
        }
		return eliminado;
	}
	
	private Boolean eliminarConsolidados(ArchivoEntity consolidado) {
		Boolean eliminado = false;
		String requestId = consolidado.getRequestIdCalendarizacion();
		
		log.info("requestId : {} ", requestId);
		if (requestId != null && !requestId.equals("")) {
			Boolean eliminadoESS=false;
			try {
				eliminadoESS = oAGService.eliminarCalendarizacion(requestId);
			} catch (UnirestException ue) {
				log.error("Ocurrio un error en eliminar: {} " , ue.getMessage());
			}
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
		
		return eliminado;
	}

	/*
	 * Actualiza la prioridad del archivo consolidados
	 */
	@Retryable(value = {RuntimeException.class}, maxAttempts = 3,backoff = @Backoff(1000))
	public InlineResponse200 actualizarPrioridadArchivo(ModificarPrioridadArchivoConsolidadoReq request) {
		log.info("Actualizar Prioridad Archivo Consolidado_");
		InlineResponse200 resp = null;
		try{
		Query query1 = new Query();
		query1.addCriteria(Criteria.where(Constantes.ID_ARCHIVO).is(request.getIdArchivo()));
		Update update = new Update();
		update.set(Constantes.PRIORIDAD, request.getIdPrioridad());
		ArchivoEntity consolidado = mongoTemplate.findAndModify(query1, update, ArchivoEntity.class);
		
        	Integer valorEntero=Integer.parseInt("s");
        
		Query queryCons = new Query();
		queryCons.addCriteria(Criteria.where(Constantes.ID_ARCHIVO).is(request.getIdArchivo()));
		Date fechaInicio=CastConsolidados.resetTimeToDown(consolidado.getVigencia());
		Date fechaFin=CastConsolidados.resetTimeToUp(consolidado.getVigencia());
		Query query= new Query();
		query.addCriteria(Criteria.where(Constantes.FECHA).gte(fechaInicio).lt(fechaFin).and(Constantes.ID_ARCHIVO).nin(consolidado.getIdArchivo()));
		query.with(new Sort(new Order(Direction.ASC,Constantes.PRIORIDAD)));
		List<ArchivoEntity> lstEntity=mongoTemplate.find(query, ArchivoEntity.class);
		Integer prioridad=0;
		if(request.getIdPrioridad() ==1) {
			prioridad=1;
		}
		
		if(!lstEntity.isEmpty() && request.getIdPrioridad() <=lstEntity.size()) {
		for (ArchivoEntity entity : lstEntity) {
			if(!entity.getIdArchivo().equals(Long.valueOf(request.getIdArchivo()))) {
				prioridad ++;
				entity.setPrioridad(prioridad);
			}
			mongoTemplate.save(entity);
		}
		}else {
			log.info("Ocurrio un error el id prioridad esta fuera del rango.");
		}
		resp = new InlineResponse200();
		resp.setIdPosicion(consolidado.getPrioridad());
		resp.setNombreArchivo(consolidado.getNombreAjuste());
		}catch(ConsolidadosException e){
        	e.getStackTrace();
        }
		return resp;
	}

	/*
	 * Procesar Consolidados
	 */
	@Retryable(value = {RuntimeException.class}, maxAttempts = 3,backoff = @Backoff(1000))
	@Async
	public void procesarConsolidados(String usuario, String fechaAplicacion) {
		log.info("Procesar un archivo consolidado");
		// obtener consolidados
		try{
        	Integer valorEntero=Integer.parseInt("cd");
        	List<ConsultarArchivoConsolidadoResInner> consolidados = this.getConsolidados(fechaAplicacion);
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
    			}
    		}
        }catch(ConsolidadosException e){
        	e.getStackTrace();
        	throw new ConsolidadosException(e.getStackTrace().toString());
        }
		
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
				log.error("enviarCorreoPartidas {} ", e.getMessage());
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
		
		try {
			oAGService.enviarNotificacion(enr);
		} catch (UnirestException e) {
			log.error("Error en el servicio enviar correo: {0}" , e);
		} catch (Exception e) {
			log.error("Ocurrio un error interno {0}",e);
		}

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
		
		try {
			oAGService.enviarNotificacion(enr);
		} catch (UnirestException e) {
			log.error("Error en el servicio enviar correo {} " , e.getMessage());
		} catch (Exception e) {
			log.error("Ocurrio un error {0}",e);
		}
	}
	
	/*
	 * Servicio para armar la petición para verificar la arbitrariedad
	 */
	private ArrayList<ArbitrajePreciosPartidasRequestVO> armarVerificarRegistros(List<ConsultarArchivoConsolidadoResInner> consolidados) {
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
					ArbitrajePreciosPartidasResponseVO response = oAGService.validarArbitrajePreciosPartidas(r);
					if(response != null) {						
						listaResponse.add(response);
					}
				} catch (Exception e) {
					log.error("Error al verificar registros {}" , e.getMessage());
				}
			});
		}
		return listaResponse;
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
				log.error("enviarCorreoPartidas {} ", e.getMessage());
			}
		}
		
		return encodedString;
	}
	
	/*
	 * Servicio para enviar el ajuste de precios
	 */
	private void enviarAjustePrecios(String usuario,List<ArbitrajePreciosPartidasRequestVO> listaVerificarRegistrosReq,List<ConsultarArchivoConsolidadoResInner> consolidados) {
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
						ap.setEscenario(Constantes.CONSOLIDADOS);
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
							a.setNombreArchivo(Constantes.REPORTE_FALLIDOS);
							
							listAd.add(a);
							as.setAdjunto(listAd);
							
							enr.setAdjuntos(as);
						}
						
						try {
							oAGService.enviarNotificacion(enr);
						} catch (UnirestException e) {
							log.error("Error enviar ajuste precios {0}" , e);
						} catch (Exception e) {
							log.error("Ocurrio un error {0}",e);
						}
					}
				}
				i++;
			});
		}
	}

	private List<ArchivoEntity> getConsolidados(Integer idArchivo) {
		log.info("getConsolidadosPorIdArchivo");

		Query query = new Query();
		Criteria aux = Criteria.where(Constantes.ID_ARCHIVO).is(idArchivo);
		query.addCriteria(aux);

		return mongoTemplate.find(query, ArchivoEntity.class);
	}

	/*
	 * elimina consolidado en Mongo
	 */
	@Retryable(value= {Exception.class},maxAttempts=3, backoff=@Backoff(delay=2000))
	private Boolean eliminarConsolidado(ArchivoEntity consolidado) {
		log.info("eliminarConsolidadoMongo");

		Boolean eliminado = false;

		if (consolidado != null) {
			mongoTemplate.remove(consolidado);
			eliminado = true;
		}

		return eliminado;
	}
	
	public Boolean validaFormatoFecha(String stringDate){
		log.info("validando Fecha");
		Boolean flag = false;
		try {
		 new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
		  flag=true;
		} catch (ParseException e) {
			log.info("El formato de la fecha no es el esperado");
		}
		
		return flag;
	}
	
	@Recover
	public InlineResponse200 actualizarPrioridadArchivoRec(RuntimeException t,ModificarPrioridadArchivoConsolidadoReq request){
		armaCuerpoCorreo(t, Constantes.CONSOLIDADOS_PRIORIDAD+request.getIdPrioridad()+Constantes.CONSOLIDADOS_PRIORIDAD_COMPLEMENTO);
		return null;
	}
	
	@Recover
	public  List<ConsultarArchivoConsolidadoResInner> consultarDocumentoRec(RuntimeException t,String vigencia){
		armaCuerpoCorreo(t, Constantes.CONSOLIDADOS_ARCHIVOS);
		return Collections.emptyList();
	}
	
	@Recover
	public boolean eliminarConsolidadoRec(RuntimeException t,Integer idArchivo){
		armaCuerpoCorreo(t, Constantes.CONSOLIDADOS_DELETE+idArchivo);
		return false;
	}
	
	@Recover
    public void procesaConsolidadoRec(RuntimeException t,String usuario, String fechaAplicacion) {
        armaCuerpoCorreo(t, Constantes.CONSOLIDADOS_PROCESAR);
    }
	
	@Recover
    public Boolean registrarConsolidadoRec(RuntimeException t,Consolidados request ) {
        armaCuerpoCorreo(t, Constantes.CONSOLIDADOS_REGISTRAR);
		return false;
    }
	
	private EnviarNotificacionRequestVO armaCuerpoCorreo(RuntimeException runTime,String nombreFuncion){
		EnviarNotificacionRequestVO enr = new EnviarNotificacionRequestVO();
        enr.setDe(de);
		enr.setPara(para);
		enr.setAsunto(ASUNTO_PROCESO_FALLIDO);
		StringBuilder sb = new StringBuilder();
		sb.append(CONTENIDO_PROCESO_FALLIDO);
		sb.append(Constantes.SALTO_LINEA);
		sb.append(Constantes.NEGRITAS);
		sb.append(nombreFuncion);
		sb.append(Constantes.NEGRITAS_CLOSE);
		sb.append(Constantes.SALTO_LINEA);
		sb.append(Constantes.SALTO_LINEA);
		sb.append(Constantes.CONSOLIDADO_MENSAJE_DETALLE);
		sb.append(runTime.getMessage());
		sb.append(Constantes.SALTO_LINEA);
		sb.append(runTime.getStackTrace());
		sb.append(Constantes.SALTO_LINEA);
		sb.append(runTime.fillInStackTrace());
		enr.setContenidoHTML(sb.toString());
		try {
			oAGService.enviarNotificacion(enr);
		} catch (UnirestException e) {
			log.error("Error enviar correo  {0}" , e);
		} catch (Exception e) {
			log.error("Ocurrio un error {0}",e);
		}
		return enr;
	}
}

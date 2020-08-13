package mx.com.nmp.gestionescenarios.cast;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.nmp.gestionescenarios.model.InfoGeneralReglaCanalComercializacion;
import mx.com.nmp.gestionescenarios.model.InfoRegla;
import mx.com.nmp.gestionescenarios.vo.CommonVO;
import mx.com.nmp.gestionescenarios.vo.GestionReglasVO;
@Repository
public class CastObjectGeneric {
	private static final Logger log = LoggerFactory.getLogger(CastObjectGeneric.class);
	public List<CommonVO> convertObjectToPojo(Object obj) {
		ObjectMapper oMapper = new ObjectMapper();
		List<CommonVO> lstVO= null;
		try {
			lstVO=oMapper.readValue(convertListObjectToJson(obj), new TypeReference<List<CommonVO>>() {});
		} catch (Exception e) {
			log.error("{0}", e);
		}
		return lstVO;
		
	}
	
	private String convertListObjectToJson(Object obj) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return  gson.toJson(obj);
	}

	public CommonVO objectToPojoVO(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		CommonVO vo = new CommonVO();
		try {
		if(obj !=null) {
			
				vo=mapper.readValue(objectToJson(obj), CommonVO.class);
			
		}
		}  catch (IOException e) {
		  log.error("{0}",e);
		}
		return vo;
	}
	
	private String objectToJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	
	private Object formatObject(CommonVO vo) {
		Object obj= new Object();
		if(vo !=null) {
			obj=vo.getDescripcion();
		}
		return obj;	
	}
	
	private List<Object> lstObject(List<CommonVO> lstVO){
		List<Object> lstObject= new ArrayList<>();
		if(!lstVO.isEmpty()) {
			lstVO.stream().forEach(vo->
				lstObject.add(vo.getDescripcion())
			);
		}
		return lstObject;
	}
	
	private  List<Object> lstSucursales(List<CommonVO> lstVO){
		List<Object> lstObject= new ArrayList<>();
		if(!lstVO.isEmpty()) {
			lstVO.stream().forEach(vo->
				lstObject.add(vo.getId())
			);
		}
		return lstObject;
	}
	
	public List<InfoGeneralReglaCanalComercializacion> entityListToListCanalPojo(List<InfoGeneralReglaCanalComercializacion> lstCanalComercializacion){
		InfoGeneralReglaCanalComercializacion vo = new InfoGeneralReglaCanalComercializacion();
		List<InfoGeneralReglaCanalComercializacion> lstVO= new ArrayList<>();
		lstCanalComercializacion.stream().forEach(entity->{
			vo.setIdCanal(entity.getIdCanal());
			vo.setCanal(entity.getCanal());
			lstVO.add(vo);
		});
		return lstVO;
	}
	
	
	public InfoRegla formatInformacionEscenario(GestionReglasVO gestion) {
		InfoRegla vo = new InfoRegla();
		if(gestion !=null) {
			vo.setId(gestion.getId());
			vo.setNombre(gestion.getNombre());
			vo.setEstatus(gestion.getEstatus());
			vo.setRamo(formatObject(gestion.getRamo()));
			vo.setSubramo(lstObject(gestion.getSubramo()));
			vo.setFactor(lstObject(gestion.getFactor()));
			vo.setClasificacionClientes(lstObject(gestion.getClasificacionClientes()));
			vo.setBolsas(gestion.getBolsas());
			vo.setSucursales(lstSucursales(gestion.getSucursales()));
			vo.setCanalComercializacion(gestion.getCanalComercializacion());
			vo.setCompraCumplido(gestion.getCompraCumplido());
			vo.setAforo(gestion.getAforo());
			vo.setEstatusPartida(gestion.getEstatusPartida());
			vo.setCanalIngresoActual(gestion.getCanalIngresoActual());
			vo.setDiasAlmoneda(gestion.getDiasAlmoneda());
			vo.setTipoMonedas(gestion.getMonedas());
			vo.setNivelAgrupacion(gestion.getNivelAgrupacion());
			vo.setReglasDescuento(gestion.getReglasDescuento());
			vo.setCandadoInferior(gestion.getCandadoInferior());
			vo.setOrigen(gestion.getOrigen());
		
			
		}
		return vo;
	}
	


	public  String convertLocalDateToString(LocalDate fecha) {
		return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	
	public  String formatLocalDate(LocalDate fecha) {
		return  fecha.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public String covertDateToEstring (Date fecha) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(fecha);
	}
	
	public  Date resetTimeToUp(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public  Date resetTimeToDown(Date fecha) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
}

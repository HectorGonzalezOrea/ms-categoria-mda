package mx.com.nmp.gestionescenarios.cast;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.nmp.gestionescenarios.model.InfoGeneralReglaCanalComercializacion;
import mx.com.nmp.gestionescenarios.model.InfoRegla;
import mx.com.nmp.gestionescenarios.vo.CommonVO;
import mx.com.nmp.gestionescenarios.vo.GestionReglasVO;

public class CastObjectGeneric {
	
	public List<CommonVO> convertObjectToPojo(Object obj) {
		ObjectMapper oMapper = new ObjectMapper();
		List<CommonVO> lstVO= null;
		try {
			lstVO=oMapper.readValue(convertListObjectToJson(obj), new TypeReference<List<CommonVO>>() {});
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return lstVO;
		
	}
	
	private String convertListObjectToJson(Object obj) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(obj);
		return json;
	}

	public CommonVO objectToPojoVO(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		CommonVO vo = new CommonVO();
		try {
		if(obj !=null) {
			
				vo=mapper.readValue(objectToJson(obj), CommonVO.class);
			
		}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	private String objectToJson(Object obj) {
		Gson gson = new Gson();
		String json=gson.toJson(obj);
		return json;
	}
	
	
	private Object formatObject(CommonVO vo) {
		Object obj= new Object();
		if(vo !=null) {
			obj=vo.getDescripcion();
		}
		return obj;	
	}
	
	private List<Object> lstObject(List<CommonVO> lstVO){
		List<Object> lstObject= new ArrayList<Object>();
		if(!lstVO.isEmpty()) {
			lstVO.stream().forEach(vo->{
				lstObject.add(vo.getDescripcion());
			});
		}
		return lstObject;
	}
	
	private  List<Object> lstSucursales(List<CommonVO> lstVO){
		List<Object> lstObject= new ArrayList<Object>();
		if(!lstVO.isEmpty()) {
			lstVO.stream().forEach(vo->{
				lstObject.add(vo.getId());
			});
		}
		return lstObject;
	}
	
	public List<InfoGeneralReglaCanalComercializacion> entityListToListCanalPojo(List<InfoGeneralReglaCanalComercializacion> lstCanalComercializacion){
		InfoGeneralReglaCanalComercializacion vo = new InfoGeneralReglaCanalComercializacion();
		List<InfoGeneralReglaCanalComercializacion> lstVO= new ArrayList<InfoGeneralReglaCanalComercializacion>();
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
		String string = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.println("El formato de fecha es "+string);
		return string;
	}
}

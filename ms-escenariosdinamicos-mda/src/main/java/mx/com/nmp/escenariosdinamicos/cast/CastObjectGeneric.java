package mx.com.nmp.escenariosdinamicos.cast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.nmp.escenariosdinamicos.clienteservicios.vo.CalculoValorVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.IndexGarantiaVO;
import mx.com.nmp.escenariosdinamicos.elastic.vo.MdaVentasVO;
import mx.com.nmp.escenariosdinamicos.model.CatalogoVO;
import mx.com.nmp.escenariosdinamicos.model.CommonAjuste;
import mx.com.nmp.escenariosdinamicos.model.CommonBaseAjuste;
import mx.com.nmp.escenariosdinamicos.model.EjecutarEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.model.InfoRegla;
import mx.com.nmp.escenariosdinamicos.model.InformacionAjusteVO;
import mx.com.nmp.escenariosdinamicos.model.PartidaPrecioFinal;
import mx.com.nmp.escenariosdinamicos.model.SimularEscenarioDinamicoReq;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestActualizarPrecioPartidaDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.RequestReglaEscenarioDinamicoDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.dto.ResponseReglasArbitrajeOAGDto;
import mx.com.nmp.escenariosdinamicos.oag.vo.PartidaVO;
import mx.com.nmp.escenariosdinamicos.oag.vo.ReglasArbitrajeVO;
import mx.com.nmp.escenariosdinamicos.utils.Constantes;

@Repository
public class CastObjectGeneric {
	private static final Logger log = LoggerFactory.getLogger(CastObjectGeneric.class);
	
	public IndexGarantiaVO jsonFieldToObject(String jsonField) {
		ObjectMapper mapper = new ObjectMapper();
		IndexGarantiaVO participantJsonList = null;
			
		try {
			participantJsonList = mapper.readValue(jsonField,IndexGarantiaVO.class);
		} catch (IOException ioe) {
			log.error("{0}", ioe);
		}

		return participantJsonList;
	}
	
	public String lstToJson(List<CalculoValorVO> lst) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(lst);
	}
	
	public List<PartidaPrecioFinal> castJsonToList(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		List<PartidaPrecioFinal> participantJsonList = null;
			try {
				participantJsonList = mapper.readValue(jsonString, new TypeReference<List<PartidaPrecioFinal>>() {
				});
			} catch (IOException e) {
				log.error("{0}", e);
			}
		return participantJsonList;
	}
	//cast con lamda
	public List<CalculoValorVO> castGarantiasToCalculoValor(List<IndexGarantiaVO> lstCalculoValor) {
		List<CalculoValorVO> lstCalculoValors = new ArrayList<>();
		lstCalculoValor.stream().forEach(calc ->{
			CalculoValorVO calculoValor=new CalculoValorVO();
			calculoValor.setIdPartida(calc.getPartida()!=null?calc.getPartida():0); 
			calculoValor.setSKU(calc.getSku());
			calculoValor.setValorAncla(calc.getValorMonteAct()!= null ? calc.getValorMonteAct():0);
			calculoValor.setGramaje(calc.getAlhajasGramaje()!= null ? calc.getAlhajasGramaje() : 0);
			calculoValor.setKilataje(calc.getAlhajasKilates()!=null?calc.getAlhajasKilates():0);
			calculoValor.setIncremento(calc.getAlhajasIIncremento()!=null?calc.getAlhajasIIncremento():0);
			calculoValor.setDesplazamiento(calc.getAlhajasDesplComer()!= null ? calc.getAlhajasDesplComer() : 0);
			calculoValor.setAvaluoComplementario(calc.getAlhajasAvaluoCompl()!=null?calc.getAlhajasAvaluoCompl():0);
			lstCalculoValors.add(calculoValor);
		});
		return lstCalculoValors;
	}
	
	public ResponseOAGDto convertJsonToReponseOAGDto(String jsonString) {
		ResponseOAGDto response= new ResponseOAGDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			response=mapper.readValue(jsonString, ResponseOAGDto.class);
		} catch (IOException e) {
			log.error("{0}", e);
		}
		
		return response;
	}
	
	public ResponseReglasArbitrajeOAGDto convertJsonToReglasArbitraje(String jsonString) {
		ResponseReglasArbitrajeOAGDto response=new ResponseReglasArbitrajeOAGDto();
		ObjectMapper mapper = new ObjectMapper();
		try {
			response=mapper.readValue(jsonString, ResponseReglasArbitrajeOAGDto.class);
		} catch (IOException e) {
			log.error("{0}", e);
		}
		
		return response;
	}
	
	public List<PartidaVO> castPartidasToPartidaValorMonte(List<IndexGarantiaVO> lstResultServiceValorMonte,InfoRegla infoRegla){
		List<PartidaVO> lstPartidas  = new ArrayList<>();
		if (lstResultServiceValorMonte != null && !lstResultServiceValorMonte.isEmpty()) {
			lstResultServiceValorMonte.stream().forEach(v->lstPartidas.add(fillValues(v,infoRegla)));
		}
		return lstPartidas;
	}
	public PartidaVO fillValues(IndexGarantiaVO index,InfoRegla infoRegla){
		PartidaVO partida=null;
		if(index!=null){
			partida=new PartidaVO();
			partida.setIdPartida(index.getPartida()!=null?String.valueOf(index.getPartida()):Constantes.SKU_OPT);
			partida.setSku(index.getSku()!=null?index.getSku():Constantes.SKU_OPT);
			partida.setVentasDiaUno(Long.valueOf(0));
			partida.setVentasDiaDos(Long.valueOf(0));
			partida.setVentasDiaTres(Long.valueOf(0));	
			partida.setBaseAjusteUnoPA(validarPrimerBaseAjuste(infoRegla, index));
			partida.setBaseAjusteUnoPM(validarPrimerBaseAjuste(infoRegla, index));
			partida.setBaseAjusteUnoPB(validarPrimerBaseAjuste(infoRegla, index));
			partida.setBaseAjusteDosPA(validarSegundoBaseAjuste(infoRegla, index));
			partida.setBaseAjusteDosPM(validarSegundoBaseAjuste(infoRegla, index));
			partida.setBaseAjusteDosPB(validarSegundoBaseAjuste(infoRegla, index));
			partida.setPrecioFinal(infoRegla.getReglasDescuento().getFactorPrecioFinal());
			partida.setPrecioEtiqueta((double) 0);
			partida.setCriterio(infoRegla.getReglasDescuento().getCriterio().getDescripcion());
			partida.setCandadoPA(validarCandadoInferior(infoRegla, index));
			partida.setCandadoPB(validarCandadoInferior(infoRegla, index));
			partida.setCandadoPM(validarCandadoInferior(infoRegla, index));
			partida.setPrecioVenta(index.getPrecioVentaAct());
			partida.setMontoPrestamo(index.getImportePrestamo());
		}
		return partida;
	}
	
	/*Esto para que era ?*/
	
	private Double validarPrimerBaseAjuste(InfoRegla infoRegla,IndexGarantiaVO index) {
		Double flag=null;
		if(infoRegla!=null&&infoRegla.getReglasDescuento()!=null&&infoRegla.getReglasDescuento().getPrimerBaseAjuste()!=null){//Primer Ajuste
			for (CommonBaseAjuste item : infoRegla.getReglasDescuento().getPrimerBaseAjuste()) {
				if(item.getTipoPrecio().equals(Constantes.PRECIO_ALTO)){
					flag=calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste()));
				}
				if(item.getTipoPrecio().equals(Constantes.PRECIO_MEDIO)){
					flag=calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste()));
				}
				if(item.getTipoPrecio().equals(Constantes.PRECIO_BAJO)){
					flag=calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste()));
				}
			}
		}
		return flag;
	}

	private Double validarSegundoBaseAjuste(InfoRegla infoRegla,IndexGarantiaVO index) {
		Double flag=null;
		if(infoRegla!=null&&infoRegla.getReglasDescuento()!=null&&infoRegla.getReglasDescuento().getSegundaBaseAjuste()!=null){//Segungo Ajuste
			for (CommonBaseAjuste item : infoRegla.getReglasDescuento().getSegundaBaseAjuste()) {
				if(item.getTipoPrecio().equals(Constantes.PRECIO_ALTO)){
					flag=calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste()));
				}
				if(item.getTipoPrecio().equals(Constantes.PRECIO_MEDIO)){
					flag=calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste()));
				}
				if(item.getTipoPrecio().equals(Constantes.PRECIO_BAJO)){
					flag=calcularBaseAjuste(item.getFactorAjuste(), retornaPrecioPorTipoBaseAjuste(index, item.getBaseAjuste()));
				}
			}
		}
		return flag;
	}
	
	private Double validarCandadoInferior(InfoRegla infoRegla,IndexGarantiaVO index) {
		Double flag=null;
		if(infoRegla!=null&&infoRegla.getCandadoInferior()!=null){//Candado inferior
			for (InformacionAjusteVO factor : infoRegla.getCandadoInferior()) {
				if(factor.getTipoPrecio().equals(Constantes.PRECIO_ALTO)){
					flag=calcularBaseAjuste(Double.valueOf(factor.getFactorAjuste()), retornaPrecioPorTipoBaseAjuste(index, factor.getBaseAjuste()));
				}
				if(factor.getTipoPrecio().equals(Constantes.PRECIO_MEDIO)){
					flag=calcularBaseAjuste(Double.valueOf(factor.getFactorAjuste()), retornaPrecioPorTipoBaseAjuste(index, factor.getBaseAjuste()));		
				}
				if(factor.getTipoPrecio().equals(Constantes.PRECIO_BAJO)){
					flag=calcularBaseAjuste(Double.valueOf(factor.getFactorAjuste()), retornaPrecioPorTipoBaseAjuste(index, factor.getBaseAjuste()));
				}
			}
		}
		return flag;
	}
	
	public MdaVentasVO jsonFieldToObjectVenta(String jsonField) {
		ObjectMapper mapper = new ObjectMapper();
		MdaVentasVO participantJsonList = null;
		try {
			participantJsonList = mapper.readValue(jsonField,MdaVentasVO.class);
		} catch (IOException e) {
			log.error("{0}", e);
		}
		
		return participantJsonList;
	}
	
	private  Double calcularBaseAjuste(Double factorAjuste, Double precio  ) {
		Double total;
		Double porcentaje= (factorAjuste/100);
		total=precio*porcentaje;
		return total;
	}
	
	private Double retornaPrecioPorTipoBaseAjuste(IndexGarantiaVO index,CommonAjuste tipoAjuste){
		Double valorARetornar=null;
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.AVALUO_TECNICO){//falta revisar mapeo con Plataforma Com
			valorARetornar=index.getAvaluoTecOrigen();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.VALOR_MONTE_ACTUALIZADO){
			valorARetornar=index.getValorMonteAct();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.PRECIO_ETIQUETA){
			valorARetornar=(Double.parseDouble(index.getPrecioVentaSalida()));
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.PRECIO_ACTUAL){
			valorARetornar=index.getPrecioVentaAct();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.PRESTAMO){
			valorARetornar=index.getImportePrestamo();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.AVALUO_COMERCIAL){
			valorARetornar=index.getAvaluoComerc();
		}
		if(Integer.valueOf(tipoAjuste.getId())==Constantes.PRECIO_MERCADO){//falta revisar mapeo
			valorARetornar=(null);
		}
		return valorARetornar;
	}
	
	public RequestReglaEscenarioDinamicoDto convertJsonToRequestEscenario(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		RequestReglaEscenarioDinamicoDto request= new RequestReglaEscenarioDinamicoDto();
		try {
			request=mapper.readValue(jsonString, RequestReglaEscenarioDinamicoDto.class);
		} catch (IOException e) {
			log.error("Error request escenario dinamico: {0}", e);
		}

		return request;
	}

	public RequestActualizarPrecioPartidaDto convertJsonToRequestActualizarPrecioPartida(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		RequestActualizarPrecioPartidaDto newRequest= new RequestActualizarPrecioPartidaDto();
		
		try {
			ResponseReglasArbitrajeOAGDto request=mapper.readValue(jsonString, ResponseReglasArbitrajeOAGDto.class);
			for (ReglasArbitrajeVO vo : request.getPartida()) {
				newRequest.setEnLinea(true);
				newRequest.setEscenario("PM");
				newRequest.setFolioPartida(vo.getIdPartida());
				newRequest.setSku(vo.getSku());
				newRequest.setPrecioActual(1800D);
				newRequest.setPrecioModificado(1200D);
			}
		} catch (IOException e) {
			log.error("{0}", e);
		}
		
		return newRequest;
	}
	
	public EjecutarEscenarioDinamicoReq convertirEjeTOSim (SimularEscenarioDinamicoReq param){
		EjecutarEscenarioDinamicoReq esc=null;
		if(param!=null){
			esc=new EjecutarEscenarioDinamicoReq();
			esc.setInfoRegla(param.getInfoRegla());
		}
		return esc;
	}
	
	public CatalogoVO deserializaNivelAgrupacion(Object nivelAgrupacion){
		 ObjectMapper mapper = new ObjectMapper();
		 CatalogoVO nivelAgrupacionGeneric=null;
		 String json;
		try {
			json = mapper.writeValueAsString(nivelAgrupacion);
			 nivelAgrupacionGeneric=mapper.readValue(json, CatalogoVO.class);
		} catch (JsonProcessingException e) {
			log.error("Error al convertir String");
		} catch (IOException e) {
			log.error("error al convertir a pojo");
			e.printStackTrace();
		}
		log.info("nivel agrupacion ->[{}]",nivelAgrupacionGeneric.getDescripcion());
		return nivelAgrupacionGeneric;
	}
}



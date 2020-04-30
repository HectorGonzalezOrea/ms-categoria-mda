package mx.com.nmp.escenariosdinamicos.clienteservicios.vo;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public class CalculoValorVO {
	@JsonProperty("idPartida")
	private Integer idPartida;
	@JsonProperty("SKU")
	private String SKU;
	@JsonProperty("valorAncla")
	private Double valorAncla;//
	@JsonProperty("gramaje")
	private Double gramaje;
	@JsonProperty("kilataje")
	private Double kilataje;
	@JsonProperty("incremento")
	private Double incremento;
	@JsonProperty("desplazamiento")
	private Double desplazamiento;
	@JsonProperty("avaluoComplementario")
	private Double avaluoComplementario;
	  
	public CalculoValorVO(Integer idPartida,String SKU,Double valorAncla,Double gramaje,Double kilataje,Double incremento,Double desplazamiento,Double avaluoComplementario){
		this.idPartida=idPartida;
		this.SKU=SKU;
		this.valorAncla=valorAncla;
		this.gramaje=gramaje;
		this.kilataje=kilataje;
		this.incremento=incremento;
		this.desplazamiento=desplazamiento;
		this.avaluoComplementario=avaluoComplementario;
	}
	
	public Integer getIdPartida() {
		return idPartida;
	}
	public String getSKU() {
		return SKU;
	}
	public Double getValorAncla() {
		return valorAncla;
	}
	public Double getGramaje() {
		return gramaje;
	}
	public Double getKilataje() {
		return kilataje;
	}
	public Double getIncremento() {
		return incremento;
	}
	public Double getDesplazamiento() {
		return desplazamiento;
	}
	public Double getAvaluoComplementario() {
		return avaluoComplementario;
	}
	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public void setValorAncla(Double valorAncla) {
		this.valorAncla = valorAncla;
	}
	public void setGramaje(Double gramaje) {
		this.gramaje = gramaje;
	}
	public void setKilataje(Double kilataje) {
		this.kilataje = kilataje;
	}
	public void setIncremento(Double incremento) {
		this.incremento = incremento;
	}
	public void setDesplazamiento(Double desplazamiento) {
		this.desplazamiento = desplazamiento;
	}
	public void setAvaluoComplementario(Double avaluoComplementario) {
		this.avaluoComplementario = avaluoComplementario;
	}

	@Override
	public String toString() {
		return "CalculoValorVO [idPartida=" + idPartida + ", SKU=" + SKU + ", valorAncla=" + valorAncla + ", gramaje="
				+ gramaje + ", kilataje=" + kilataje + ", incremento=" + incremento + ", desplazamiento="
				+ desplazamiento + ", avaluoComplementario=" + avaluoComplementario + "]";
	}
	
}

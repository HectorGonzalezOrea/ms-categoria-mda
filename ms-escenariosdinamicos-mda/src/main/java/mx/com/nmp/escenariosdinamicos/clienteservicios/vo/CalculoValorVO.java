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
	private Float valorAncla;
	@JsonProperty("gramaje")
	private Float gramaje;
	@JsonProperty("kilataje")
	private Float kilataje;
	@JsonProperty("incremento")
	private Float incremento;
	@JsonProperty("desplazamiento")
	private Float desplazamiento;
	@JsonProperty("avaluoComplementario")
	private Float avaluoComplementario;
	  
	public Integer getIdPartida() {
		return idPartida;
	}
	public String getSKU() {
		return SKU;
	}
	public Float getValorAncla() {
		return valorAncla;
	}
	public Float getGramaje() {
		return gramaje;
	}
	public Float getKilataje() {
		return kilataje;
	}
	public Float getIncremento() {
		return incremento;
	}
	public Float getDesplazamiento() {
		return desplazamiento;
	}
	public Float getAvaluoComplementario() {
		return avaluoComplementario;
	}
	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public void setValorAncla(Float valorAncla) {
		this.valorAncla = valorAncla;
	}
	public void setGramaje(Float gramaje) {
		this.gramaje = gramaje;
	}
	public void setKilataje(Float kilataje) {
		this.kilataje = kilataje;
	}
	public void setIncremento(Float incremento) {
		this.incremento = incremento;
	}
	public void setDesplazamiento(Float desplazamiento) {
		this.desplazamiento = desplazamiento;
	}
	public void setAvaluoComplementario(Float avaluoComplementario) {
		this.avaluoComplementario = avaluoComplementario;
	}
}

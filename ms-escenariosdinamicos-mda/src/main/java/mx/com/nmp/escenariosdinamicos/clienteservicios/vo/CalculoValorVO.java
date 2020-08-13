package mx.com.nmp.escenariosdinamicos.clienteservicios.vo;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public class CalculoValorVO {
	@JsonProperty("idPartida")
	private Integer idPartida;
	@JsonProperty("SKU")
	private String sku;
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
	  
	public CalculoValorVO(Integer idPartida,String sku,Double valorAncla,Double gramaje,Double kilataje,Double incremento,Double desplazamiento,Double avaluoComplementario){
		this.idPartida=idPartida;
		this.sku=sku;
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
		return sku;
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
	public void setSKU(String sku) {
		this.sku = sku;
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
	    StringBuilder sb = new StringBuilder();
	    sb.append("class CalculoValorVO {\n");
	    sb.append("    idPartida: ").append(toIndentedString(idPartida)).append("\n");
	    sb.append("    SKU: ").append(toIndentedString(sku)).append("\n");
	    sb.append("    valorAncla: ").append(toIndentedString(valorAncla)).append("\n");
	    sb.append("    gramaje: ").append(toIndentedString(gramaje)).append("\n");
	    sb.append("    kilataje: ").append(toIndentedString(kilataje)).append("\n");
	    sb.append("    incremento: ").append(toIndentedString(incremento)).append("\n");
	    sb.append("    desplazamiento: ").append(toIndentedString(desplazamiento)).append("\n");
	    sb.append("   avaluoComplementario : ").append(toIndentedString(avaluoComplementario)).append("\n");
	    sb.append("}");
		return sb.toString();
	}

	private String toIndentedString(java.lang.Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
	
	
}

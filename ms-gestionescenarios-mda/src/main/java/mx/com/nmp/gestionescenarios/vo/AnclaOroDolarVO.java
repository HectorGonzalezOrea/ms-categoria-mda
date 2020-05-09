package mx.com.nmp.gestionescenarios.vo;

import java.util.Date;

public class AnclaOroDolarVO {
	
	private Float valorAnclaOro;
	private Float valorAnclaDolar;
	private String fechaAplicacion;
	private Integer idBolsa;
	private Object sucursales;
	
	public Float getValorAnclaOro() {
		return valorAnclaOro;
	}
	public Float getValorAnclaDolar() {
		return valorAnclaDolar;
	}
	public String getFechaAplicacion() {
		return fechaAplicacion;
	}
	public Integer getIdBolsa() {
		return idBolsa;
	}
	public Object getSucursales() {
		return sucursales;
	}
	public void setValorAnclaOro(Float valorAnclaOro) {
		this.valorAnclaOro = valorAnclaOro;
	}
	public void setValorAnclaDolar(Float valorAnclaDolar) {
		this.valorAnclaDolar = valorAnclaDolar;
	}
	public void setFechaAplicacion(String fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}
	public void setIdBolsa(Integer idBolsa) {
		this.idBolsa = idBolsa;
	}
	public void setSucursales(Object sucursales) {
		this.sucursales = sucursales;
	}
	

}

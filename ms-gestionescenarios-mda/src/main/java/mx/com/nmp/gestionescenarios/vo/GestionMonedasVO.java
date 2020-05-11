package mx.com.nmp.gestionescenarios.vo;

import java.time.LocalDate;

public class GestionMonedasVO {
	
	private Long id;
	private Object tipo;
	private Float precio;
	private Boolean oro;
	private String fechaCreacion;
	private LocalDate fechaModificacion;
	private String actualizadoPor;
	private String fechaAplicacion;
	
	
	public Long getId() {
		return id;
	}
	public Object getTipo() {
		return tipo;
	}
	public Float getPrecio() {
		return precio;
	}
	public Boolean getOro() {
		return oro;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public LocalDate getFechaModificacion() {
		return fechaModificacion;
	}
	public String getActualizadoPor() {
		return actualizadoPor;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setTipo(Object tipo) {
		this.tipo = tipo;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public void setOro(Boolean oro) {
		this.oro = oro;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public void setFechaModificacion(LocalDate fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public void setActualizadoPor(String actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public String getFechaAplicacion() {
		return fechaAplicacion;
	}
	public void setFechaAplicacion(String fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}

}

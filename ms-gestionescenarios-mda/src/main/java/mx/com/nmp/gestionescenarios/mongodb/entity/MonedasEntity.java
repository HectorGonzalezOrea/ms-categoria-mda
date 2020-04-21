package mx.com.nmp.gestionescenarios.mongodb.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.threeten.bp.LocalDate;


@Document(collection = "gestion-monedas")
public class MonedasEntity {
	
	@Transient
    public static final String SEQUENCE_NAME = "monedas_sequence";
	
	@Id
	private Long id;
	private Object tipo;
	private Float precio;
	private Boolean oro;
	private LocalDate fechaCreacion;
	private LocalDate fechaModificacion;
	private String actualizadoPor;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Object getTipo() {
		return tipo;
	}
	public void setTipo(Object tipo) {
		this.tipo = tipo;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public Boolean getOro() {
		return oro;
	}
	public void setOro(Boolean oro) {
		this.oro = oro;
	}
	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public LocalDate getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(LocalDate fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(String actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	
	
	

}

package mx.com.nmp.gestionescenarios.oag.vo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "idArchivo", "nombreArchivo", "fechaReporte", "nombreCliente", "idPrioridad", "producto" })
public class ConsolidadoVO {

	@JsonProperty("idArchivo")
	private Integer idArchivo;
	@JsonProperty("nombreArchivo")
	private String nombreArchivo;
	@JsonProperty("fechaReporte")
	private String fechaReporte;
	@JsonProperty("nombreCliente")
	private Object nombreCliente;
	@JsonProperty("idPrioridad")
	private Integer idPrioridad;
	@JsonProperty("producto")
	private List<Producto> producto = null;

	@JsonProperty("idArchivo")
	public Integer getIdArchivo() {
		return idArchivo;
	}

	@JsonProperty("idArchivo")
	public void setIdArchivo(Integer idArchivo) {
		this.idArchivo = idArchivo;
	}

	@JsonProperty("nombreArchivo")
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	@JsonProperty("nombreArchivo")
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	@JsonProperty("fechaReporte")
	public String getFechaReporte() {
		return fechaReporte;
	}

	@JsonProperty("fechaReporte")
	public void setFechaReporte(String fechaReporte) {
		this.fechaReporte = fechaReporte;
	}

	@JsonProperty("nombreCliente")
	public Object getNombreCliente() {
		return nombreCliente;
	}

	@JsonProperty("nombreCliente")
	public void setNombreCliente(Object nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	@JsonProperty("idPrioridad")
	public Integer getIdPrioridad() {
		return idPrioridad;
	}

	@JsonProperty("idPrioridad")
	public void setIdPrioridad(Integer idPrioridad) {
		this.idPrioridad = idPrioridad;
	}

	@JsonProperty("producto")
	public List<Producto> getProducto() {
		return producto;
	}

	@JsonProperty("producto")
	public void setProducto(List<Producto> producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class IniciarEjecucionEscenarioRequestVO {\n");
		sb.append("    idArchivo: ").append(toIndentedString(idArchivo)).append("\n");
		sb.append("    nombreArchivo: ").append(toIndentedString(nombreArchivo)).append("\n");
		sb.append("    fechaReporte: ").append(toIndentedString(fechaReporte)).append("\n");
		sb.append("    nombreCliente: ").append(toIndentedString(nombreCliente)).append("\n");
		sb.append("    idPrioridad: ").append(toIndentedString(idPrioridad)).append("\n");
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
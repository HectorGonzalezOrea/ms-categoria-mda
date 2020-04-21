package mx.com.nmp.gestionescenarios.mongodb.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "anclaOroDolar")
public class AnclaOroDolarEntity {
	@Id
	private ObjectId _id;
	//private Integer _id;
	private Double valorAnclaOro;
	private Double valorAnclaDolar;
	private Date fechaAplicacion;
	private Integer idBolsa;
	private List<Integer> sucursales;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Double getValorAnclaOro() {
		return valorAnclaOro;
	}
	public void setValorAnclaOro(Double valorAnclaOro) {
		this.valorAnclaOro = valorAnclaOro;
	}
	public Double getValorAnclaDolar() {
		return valorAnclaDolar;
	}
	public void setValorAnclaDolar(Double valorAnclaDolar) {
		this.valorAnclaDolar = valorAnclaDolar;
	}
	public Date getFechaAplicacion() {
		return fechaAplicacion;
	}
	public void setFechaAplicacion(Date fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}
	public Integer getIdBolsa() {
		return idBolsa;
	}
	public void setIdBolsa(Integer idBolsa) {
		this.idBolsa = idBolsa;
	}
	public List<Integer> getSucursales() {
		return sucursales;
	}
	public void setSucursales(List<Integer> sucursales) {
		this.sucursales = sucursales;
	}
}

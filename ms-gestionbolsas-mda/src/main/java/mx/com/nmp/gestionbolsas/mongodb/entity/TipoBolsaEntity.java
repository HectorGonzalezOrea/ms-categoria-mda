package mx.com.nmp.gestionbolsas.mongodb.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import mx.com.nmp.gestionbolsas.model.ListaTipoBolsasInner;

@Document(collection = "TipoBolsa")
public class TipoBolsaEntity {
	
	@Id
	private Integer id;
	private ListaTipoBolsasInner descripcion;
	
	public Integer getid() {
		return id;
	}
	public void setid(Integer id) {
		this.id = id;
	}
	public ListaTipoBolsasInner getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(ListaTipoBolsasInner descripcion) {
		this.descripcion = descripcion;
	}
	

}

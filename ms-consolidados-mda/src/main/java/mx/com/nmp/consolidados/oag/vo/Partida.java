package mx.com.nmp.consolidados.oag.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tip:partida")
@XmlAccessorType (XmlAccessType.FIELD)
public class Partida {
	@XmlElement(name="tip:idPartida")
	private Integer idPartida;
	@XmlElement(name="tip:sku")
	private String sku;
	@XmlElement(name="tip:precioVenta")
	private Float precioVenta;
	@XmlElement(name="tip:montoPrestamo")
	private Float montoPrestamo;
	public Integer getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Float getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(Float precioVenta) {
		this.precioVenta = precioVenta;
	}
	public Float getMontoPrestamo() {
		return montoPrestamo;
	}
	public void setMontoPrestamo(Float montoPrestamo) {
		this.montoPrestamo = montoPrestamo;
	}
}

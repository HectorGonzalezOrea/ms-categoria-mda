package mx.com.nmp.historicoprecios.elastic.vo;

import java.util.Date;

public class HistoricoPreciosVO {

	private String fecha;
	private String sku;
	private String partida;
	private String precio_actual;
	private String precio_modificado;
	private String usuario;
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getPartida() {
		return partida;
	}
	public void setPartida(String partida) {
		this.partida = partida;
	}
	public String getPrecio_actual() {
		return precio_actual;
	}
	public void setPrecio_actual(String precio_actual) {
		this.precio_actual = precio_actual;
	}
	public String getPrecio_modificado() {
		return precio_modificado;
	}
	public void setPrecio_modificado(String precio_modificado) {
		this.precio_modificado = precio_modificado;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	@Override
	public String toString() {
		return "HistoricoPreciosVO [fecha=" + fecha + ", sku=" + sku + ", partida=" + partida + ", precio_actual="
				+ precio_actual + ", precio_modificado=" + precio_modificado + ", usuario=" + usuario + "]";
	}
	
}

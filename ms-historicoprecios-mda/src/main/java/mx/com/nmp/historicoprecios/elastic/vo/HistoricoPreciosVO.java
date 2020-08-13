package mx.com.nmp.historicoprecios.elastic.vo;

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
		 StringBuilder sb = new StringBuilder();
		 sb.append("HistoricoPreciosVO {\n");
		 sb.append("fecha: ").append(toIndentedString(fecha)).append("\n");
		 sb.append("sku: ").append(toIndentedString(sku)).append("\n");
		 sb.append("partida: ").append(toIndentedString(partida)).append("\n");
		 sb.append("precio_actual: ").append(toIndentedString(precio_actual)).append("\n");
		 sb.append("precio_modificado: ").append(toIndentedString(precio_modificado)).append("\n");
		 sb.append("usuario: ").append(toIndentedString(usuario)).append("\n");

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

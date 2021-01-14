package mx.com.nmp.escenariosdinamicos.oag.vo;



public class PartidaResponseVO {
	
	private String idPartida;
	private String sku;
	private String reglaAplicada;
	private String precio;
	private DetalleReglasVO detalleReglas;
	
	public String getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getReglaAplicada() {
		return reglaAplicada;
	}
	public void setReglaAplicada(String reglaAplicada) {
		this.reglaAplicada = reglaAplicada;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public DetalleReglasVO getDetalleReglas() {
		return detalleReglas;
	}
	public void setDetalleReglas(DetalleReglasVO detalleReglas) {
		this.detalleReglas = detalleReglas;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class PartidaResponseVO {\n");
		sb.append("    idPartida: ").append(toIndentedString(idPartida)).append("\n");
		sb.append("    sku: ").append(toIndentedString(sku)).append("\n");
		sb.append("    reglaAplicada: ").append(toIndentedString(reglaAplicada)).append("\n");
		sb.append("    precio: ").append(toIndentedString(precio)).append("\n");
		sb.append("    detalleReglas: ").append(toIndentedString(detalleReglas)).append("\n");
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

package mx.com.nmp.establecimientoprecios.oag.vo;

import java.util.List;

public class GestionPreciosRequestVO {

	private Boolean enLinea;
	private List<ProductoOAGVO> informacionProductos;
	
	public Boolean getEnLinea() {
		return enLinea;
	}

	public void setEnLinea(Boolean enLinea) {
		this.enLinea = enLinea;
	}

	public List<ProductoOAGVO> getInformacionProductos() {
		return informacionProductos;
	}

	public void setInformacionProductos(List<ProductoOAGVO> informacionProductos) {
		this.informacionProductos = informacionProductos;
	}

	@Override
	public String toString() {
		return "GestionPreciosRequestVO [enLinea=" + enLinea + ", informacionProductos=" + informacionProductos + "]";
	}
	
}

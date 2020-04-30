package mx.com.nmp.escenariosdinamicos.oag.vo;

public class PartidaVO {
	

	private String idPartida;
	private String sku;
	private Long ventasDiaUno;
	private Long ventasDiaDos;
	private Long ventasDiaTres;
	private Double baseAjusteUnoPA;
	private Double baseAjusteUnoPM;
	private Double baseAjusteUnoPB;
	private Double baseAjusteDosPA;
	private Double baseAjusteDosPM;
	private Double baseAjusteDosPB;
	private Double precioFinal;
	private Double precioEtiqueta;
	private String criterio;
	private Double candadoPA;
	private Double candadoPM;
	private Double candadoPB;
	private Double precioVenta;
	private Double montoPrestamo;
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
	public Long getVentasDiaUno() {
		return ventasDiaUno;
	}
	public void setVentasDiaUno(Long ventasDiaUno) {
		this.ventasDiaUno = ventasDiaUno;
	}
	public Long getVentasDiaDos() {
		return ventasDiaDos;
	}
	public void setVentasDiaDos(Long ventasDiaDos) {
		this.ventasDiaDos = ventasDiaDos;
	}
	public Long getVentasDiaTres() {
		return ventasDiaTres;
	}
	public void setVentasDiaTres(Long ventasDiaTres) {
		this.ventasDiaTres = ventasDiaTres;
	}
	public Double getBaseAjusteUnoPA() {
		return baseAjusteUnoPA;
	}
	public void setBaseAjusteUnoPA(Double baseAjusteUnoPA) {
		this.baseAjusteUnoPA = baseAjusteUnoPA;
	}
	public Double getBaseAjusteUnoPM() {
		return baseAjusteUnoPM;
	}
	public void setBaseAjusteUnoPM(Double baseAjusteUnoPM) {
		this.baseAjusteUnoPM = baseAjusteUnoPM;
	}
	public Double getBaseAjusteUnoPB() {
		return baseAjusteUnoPB;
	}
	public void setBaseAjusteUnoPB(Double baseAjusteUnoPB) {
		this.baseAjusteUnoPB = baseAjusteUnoPB;
	}
	public Double getBaseAjusteDosPA() {
		return baseAjusteDosPA;
	}
	public void setBaseAjusteDosPA(Double baseAjusteDosPA) {
		this.baseAjusteDosPA = baseAjusteDosPA;
	}
	public Double getBaseAjusteDosPM() {
		return baseAjusteDosPM;
	}
	public void setBaseAjusteDosPM(Double baseAjusteDosPM) {
		this.baseAjusteDosPM = baseAjusteDosPM;
	}
	public Double getBaseAjusteDosPB() {
		return baseAjusteDosPB;
	}
	public void setBaseAjusteDosPB(Double baseAjusteDosPB) {
		this.baseAjusteDosPB = baseAjusteDosPB;
	}
	public Double getPrecioFinal() {
		return precioFinal;
	}
	public void setPrecioFinal(Double precioFinal) {
		this.precioFinal = precioFinal;
	}
	public Double getPrecioEtiqueta() {
		return precioEtiqueta;
	}
	public void setPrecioEtiqueta(Double precioEtiqueta) {
		this.precioEtiqueta = precioEtiqueta;
	}
	public String getCriterio() {
		return criterio;
	}
	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
	public Double getCandadoPA() {
		return candadoPA;
	}
	public void setCandadoPA(Double candadoPA) {
		this.candadoPA = candadoPA;
	}
	public Double getCandadoPM() {
		return candadoPM;
	}
	public void setCandadoPM(Double candadoPM) {
		this.candadoPM = candadoPM;
	}
	public Double getCandadoPB() {
		return candadoPB;
	}
	public void setCandadoPB(Double candadoPB) {
		this.candadoPB = candadoPB;
	}
	public Double getPrecioVenta() {
		return precioVenta;
	}
	public Double getMontoPrestamo() {
		return montoPrestamo;
	}
	public void setPrecioVenta(Double precioVenta) {
		this.precioVenta = precioVenta;
	}
	public void setMontoPrestamo(Double montoPrestamo) {
		this.montoPrestamo = montoPrestamo;
	}
	@Override
	public String toString() {
		return "PartidaVO [idPartida=" + idPartida + ", sku=" + sku + ", ventasDiaUno=" + ventasDiaUno
				+ ", ventasDiaDos=" + ventasDiaDos + ", ventasDiaTres=" + ventasDiaTres + ", baseAjusteUnoPA="
				+ baseAjusteUnoPA + ", baseAjusteUnoPM=" + baseAjusteUnoPM + ", baseAjusteUnoPB=" + baseAjusteUnoPB
				+ ", baseAjusteDosPA=" + baseAjusteDosPA + ", baseAjusteDosPM=" + baseAjusteDosPM + ", baseAjusteDosPB="
				+ baseAjusteDosPB + ", precioFinal=" + precioFinal + ", precioEtiqueta=" + precioEtiqueta
				+ ", criterio=" + criterio + ", candadoPA=" + candadoPA + ", candadoPM=" + candadoPM + ", candadoPB="
				+ candadoPB + ", precioVenta=" + precioVenta + ", montoPrestamo=" + montoPrestamo + "]";
	}
	

}

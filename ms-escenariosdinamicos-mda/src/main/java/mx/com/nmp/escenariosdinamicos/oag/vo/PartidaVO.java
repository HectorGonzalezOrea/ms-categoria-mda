package mx.com.nmp.escenariosdinamicos.oag.vo;

public class PartidaVO {
	

	private String idPartida;
	private String sku;
	private Long ventasDiaUno;
	private Long ventasDiaDos;
	private Long ventasDiaTres;
	private Long baseAjusteUnoPA;
	private Long baseAjusteUnoPM;
	private Long baseAjusteUnoPB;
	private Long baseAjusteDosPA;
	private Long baseAjusteDosPM;
	private Long baseAjusteDosPB;
	private Long precioFinal;
	private Long precioEtiqueta;
	private String criterio;
	private Long candadoPA;
	private Long candadoPM;
	private Long candadoPB;
	private Double precioVenta;
	private Double montoPrestamo;
	public PartidaVO(String idPartida,String sku,Long ventasDiaUno,Long ventasDiaDos,Long ventasDiaTres,Long baseAjusteUnoPA,
		Long baseAjusteUnoPM,Long baseAjusteUnoPB,Long baseAjusteDosPA,Long baseAjusteDosPM,Long baseAjusteDosPB,
		Long precioFinal,Long precioEtiqueta,String criterio,Long candadoPA,Long candadoPM,Long candadoPB,Double precioVenta,Double montoPrestamo){
		this.idPartida=idPartida;
		this.sku=sku;
		this.ventasDiaUno=ventasDiaUno;
		this.ventasDiaDos=ventasDiaDos;
		this.ventasDiaTres=ventasDiaTres;
		this.baseAjusteUnoPA=baseAjusteUnoPA;
		this.baseAjusteUnoPM=baseAjusteUnoPM;
		this.baseAjusteUnoPB=baseAjusteUnoPB;
		this.baseAjusteDosPA=baseAjusteDosPA;
		this.baseAjusteDosPM=baseAjusteDosPM;
		this.baseAjusteDosPB=baseAjusteDosPB;
		this.precioFinal=precioFinal;
		this.precioEtiqueta=precioEtiqueta;
		this.criterio=criterio;
		this.candadoPA=candadoPA;
		this.candadoPM=candadoPM;
		this.candadoPB=candadoPB;
		this.precioVenta=precioVenta;
		this.montoPrestamo=montoPrestamo;
	}
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
	public Long getBaseAjusteUnoPA() {
		return baseAjusteUnoPA;
	}
	public void setBaseAjusteUnoPA(Long baseAjusteUnoPA) {
		this.baseAjusteUnoPA = baseAjusteUnoPA;
	}
	public Long getBaseAjusteUnoPM() {
		return baseAjusteUnoPM;
	}
	public void setBaseAjusteUnoPM(Long baseAjusteUnoPM) {
		this.baseAjusteUnoPM = baseAjusteUnoPM;
	}
	public Long getBaseAjusteUnoPB() {
		return baseAjusteUnoPB;
	}
	public void setBaseAjusteUnoPB(Long baseAjusteUnoPB) {
		this.baseAjusteUnoPB = baseAjusteUnoPB;
	}
	public Long getBaseAjusteDosPA() {
		return baseAjusteDosPA;
	}
	public void setBaseAjusteDosPA(Long baseAjusteDosPA) {
		this.baseAjusteDosPA = baseAjusteDosPA;
	}
	public Long getBaseAjusteDosPM() {
		return baseAjusteDosPM;
	}
	public void setBaseAjusteDosPM(Long baseAjusteDosPM) {
		this.baseAjusteDosPM = baseAjusteDosPM;
	}
	public Long getBaseAjusteDosPB() {
		return baseAjusteDosPB;
	}
	public void setBaseAjusteDosPB(Long baseAjusteDosPB) {
		this.baseAjusteDosPB = baseAjusteDosPB;
	}
	public Long getPrecioFinal() {
		return precioFinal;
	}
	public void setPrecioFinal(Long precioFinal) {
		this.precioFinal = precioFinal;
	}
	public Long getPrecioEtiqueta() {
		return precioEtiqueta;
	}
	public void setPrecioEtiqueta(Long precioEtiqueta) {
		this.precioEtiqueta = precioEtiqueta;
	}
	public String getCriterio() {
		return criterio;
	}
	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
	public Long getCandadoPA() {
		return candadoPA;
	}
	public void setCandadoPA(Long candadoPA) {
		this.candadoPA = candadoPA;
	}
	public Long getCandadoPM() {
		return candadoPM;
	}
	public void setCandadoPM(Long candadoPM) {
		this.candadoPM = candadoPM;
	}
	public Long getCandadoPB() {
		return candadoPB;
	}
	public void setCandadoPB(Long candadoPB) {
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

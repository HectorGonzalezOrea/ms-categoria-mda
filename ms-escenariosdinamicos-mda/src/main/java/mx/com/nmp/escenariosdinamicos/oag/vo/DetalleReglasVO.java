package mx.com.nmp.escenariosdinamicos.oag.vo;


public class DetalleReglasVO {
	private ReglaAjusteVO  reglaAjuste;
	private CommonVO baseAjuste;
	private CommonVO precioFinal;
	private CommonVO candado;
	private CommonVO precioEtiqueta;
	private String ejecucion;
	
	public ReglaAjusteVO getReglaAjuste() {
		return reglaAjuste;
	}
	public void setReglaAjuste(ReglaAjusteVO reglaAjuste) {
		this.reglaAjuste = reglaAjuste;
	}
	public CommonVO getBaseAjuste() {
		return baseAjuste;
	}
	public void setBaseAjuste(CommonVO baseAjuste) {
		this.baseAjuste = baseAjuste;
	}
	public CommonVO getPrecioFinal() {
		return precioFinal;
	}
	public void setPrecioFinal(CommonVO precioFinal) {
		this.precioFinal = precioFinal;
	}
	public CommonVO getCandado() {
		return candado;
	}
	public void setCandado(CommonVO candado) {
		this.candado = candado;
	}
	public CommonVO getPrecioEtiqueta() {
		return precioEtiqueta;
	}
	public void setPrecioEtiqueta(CommonVO precioEtiqueta) {
		this.precioEtiqueta = precioEtiqueta;
	}
	public String getEjecucion() {
		return ejecucion;
	}
	public void setEjecucion(String ejecucion) {
		this.ejecucion = ejecucion;
	}
}

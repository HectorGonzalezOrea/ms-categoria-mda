package mx.com.nmp.escenariosdinamicos.model;

public class InformacionAjusteVO {
	private String tipoPrecio;
	private CommonAjuste baseAjuste;
	private Integer factorAjuste;
	
	public String getTipoPrecio() {
		return tipoPrecio;
	}
	public CommonAjuste getBaseAjuste() {
		return baseAjuste;
	}
	public void setTipoPrecio(String tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}
	public void setBaseAjuste(CommonAjuste baseAjuste) {
		this.baseAjuste = baseAjuste;
	}
	public Integer getFactorAjuste() {
		return factorAjuste;
	}
	public void setFactorAjuste(Integer factorAjuste) {
		this.factorAjuste = factorAjuste;
	}
}

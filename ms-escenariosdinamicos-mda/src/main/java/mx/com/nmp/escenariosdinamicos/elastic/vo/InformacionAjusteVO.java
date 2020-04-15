package mx.com.nmp.escenariosdinamicos.elastic.vo;

public class InformacionAjusteVO {
	private String tipoPrecio;
	private CatalogoVO baseAjuste;
	private Integer factorAjuste;
	
	public String getTipoPrecio() {
		return tipoPrecio;
	}
	public CatalogoVO getBaseAjuste() {
		return baseAjuste;
	}
	public void setTipoPrecio(String tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}
	public void setBaseAjuste(CatalogoVO baseAjuste) {
		this.baseAjuste = baseAjuste;
	}
	public Integer getFactorAjuste() {
		return factorAjuste;
	}
	public void setFactorAjuste(Integer factorAjuste) {
		this.factorAjuste = factorAjuste;
	}
}

package mx.com.nmp.escenariosdinamicos.model;

import mx.com.nmp.escenariosdinamicos.model.CommonAjuste;

public class CommonBaseAjuste {
	private String tipoPrecio;
	private CommonAjuste baseAjuste;
	private Double factorAjuste;
	
	public String getTipoPrecio() {
		return tipoPrecio;
	}
	public CommonAjuste getBaseAjuste() {
		return baseAjuste;
	}
	public Double getFactorAjuste() {
		return factorAjuste;
	}
	public void setTipoPrecio(String tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}
	public void setBaseAjuste(CommonAjuste baseAjuste) {
		this.baseAjuste = baseAjuste;
	}
	public void setFactorAjuste(Double factorAjuste) {
		this.factorAjuste = factorAjuste;
	}

}

package mx.com.nmp.escenariosdinamicos.elastic.vo;

import java.util.List;

public class ReglasDescuento {
	private List<InformacionAjusteVO> primerBaseAjuste;
	private List<InformacionAjusteVO> segundaBaseAjuste;
	private Integer factorPrecioFinal;
	private CatalogoVO criterio;
	
	public List<InformacionAjusteVO> getPrimerBaseAjuste() {
		return primerBaseAjuste;
	}
	public List<InformacionAjusteVO> getSegundaBaseAjuste() {
		return segundaBaseAjuste;
	}
	public Integer getFactorPrecioFinal() {
		return factorPrecioFinal;
	}
	public CatalogoVO getCriterio() {
		return criterio;
	}
	public void setPrimerBaseAjuste(List<InformacionAjusteVO> primerBaseAjuste) {
		this.primerBaseAjuste = primerBaseAjuste;
	}
	public void setSegundaBaseAjuste(List<InformacionAjusteVO> segundaBaseAjuste) {
		this.segundaBaseAjuste = segundaBaseAjuste;
	}
	public void setFactorPrecioFinal(Integer factorPrecioFinal) {
		this.factorPrecioFinal = factorPrecioFinal;
	}
	public void setCriterio(CatalogoVO criterio) {
		this.criterio = criterio;
	}
}

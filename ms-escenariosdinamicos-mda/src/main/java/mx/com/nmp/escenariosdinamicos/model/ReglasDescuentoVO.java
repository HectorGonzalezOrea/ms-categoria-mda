package mx.com.nmp.escenariosdinamicos.model;

import java.util.List;



public class ReglasDescuentoVO {
	private List<CommonBaseAjuste>primerBaseAjuste;
	private List<CommonBaseAjuste>segundaBaseAjuste;
	private Double factorPrecioFinal;
	private Criterio criterio;
	
	public List<CommonBaseAjuste> getPrimerBaseAjuste() {
		return primerBaseAjuste;
	}
	public List<CommonBaseAjuste> getSegundaBaseAjuste() {
		return segundaBaseAjuste;
	}
	public Double getFactorPrecioFinal() {
		return factorPrecioFinal;
	}
	public Criterio getCriterio() {
		return criterio;
	}
	public void setPrimerBaseAjuste(List<CommonBaseAjuste> primerBaseAjuste) {
		this.primerBaseAjuste = primerBaseAjuste;
	}
	public void setSegundaBaseAjuste(List<CommonBaseAjuste> segundaBaseAjuste) {
		this.segundaBaseAjuste = segundaBaseAjuste;
	}
	public void setFactorPrecioFinal(Double factorPrecioFinal) {
		this.factorPrecioFinal = factorPrecioFinal;
	}
	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}

}

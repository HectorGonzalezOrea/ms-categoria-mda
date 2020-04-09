package mx.com.nmp.escenariosdinamicos.oag.vo;

import java.util.List;

public class SimulaEscenarioDinamicoVO {
	private Integer id;
	private String nombre;
	private CatalogoVO origen;
	private String ramo;
	private List<String> subramo;
	private List<String> factor;
	private List<String> clasificacionClientes;
	private List<CatalogoVO> bolsas;
	private List<String> sucursales;
	private List<CanalComercializacionVO> canalComerciazacion;
	private Boolean compraCumplido;
	private AforoVO aforo;
	private List<CatalogoVO> estatusPartida;
	private List<CatalogoVO> canalIngresoActual;
	private DiasAlmonedaVO diasAlmoneda;
	private CatalogoVO nivelAgrupacion;
	private ReglasDescuento reglasDescuento;
	private List<InformacionAjusteVO> candadoInferior;
	private List<CatalogoVO> tipoMonedas;
	
	public Integer getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public CatalogoVO getOrigen() {
		return origen;
	}
	public String getRamo() {
		return ramo;
	}
	public List<String> getSubramo() {
		return subramo;
	}
	public List<String> getFactor() {
		return factor;
	}
	public List<String> getClasificacionClientes() {
		return clasificacionClientes;
	}
	public List<CatalogoVO> getBolsas() {
		return bolsas;
	}
	public List<String> getSucursales() {
		return sucursales;
	}
	public List<CanalComercializacionVO> getCanalComerciazacion() {
		return canalComerciazacion;
	}
	public Boolean getCompraCumplido() {
		return compraCumplido;
	}
	public AforoVO getAforo() {
		return aforo;
	}
	public List<CatalogoVO> getEstatusPartida() {
		return estatusPartida;
	}
	public List<CatalogoVO> getCanalIngresoActual() {
		return canalIngresoActual;
	}
	public DiasAlmonedaVO getDiasAlmoneda() {
		return diasAlmoneda;
	}
	public CatalogoVO getNivelAgrupacion() {
		return nivelAgrupacion;
	}
	public ReglasDescuento getReglasDescuento() {
		return reglasDescuento;
	}
	public List<InformacionAjusteVO> getCandadoInferior() {
		return candadoInferior;
	}
	public List<CatalogoVO> getTipoMonedas() {
		return tipoMonedas;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setOrigen(CatalogoVO origen) {
		this.origen = origen;
	}
	public void setRamo(String ramo) {
		this.ramo = ramo;
	}
	public void setSubramo(List<String> subramo) {
		this.subramo = subramo;
	}
	public void setFactor(List<String> factor) {
		this.factor = factor;
	}
	public void setClasificacionClientes(List<String> clasificacionClientes) {
		this.clasificacionClientes = clasificacionClientes;
	}
	public void setBolsas(List<CatalogoVO> bolsas) {
		this.bolsas = bolsas;
	}
	public void setSucursales(List<String> sucursales) {
		this.sucursales = sucursales;
	}
	public void setCanalComerciazacion(List<CanalComercializacionVO> canalComerciazacion) {
		this.canalComerciazacion = canalComerciazacion;
	}
	public void setCompraCumplido(Boolean compraCumplido) {
		this.compraCumplido = compraCumplido;
	}
	public void setAforo(AforoVO aforo) {
		this.aforo = aforo;
	}
	public void setEstatusPartida(List<CatalogoVO> estatusPartida) {
		this.estatusPartida = estatusPartida;
	}
	public void setCanalIngresoActual(List<CatalogoVO> canalIngresoActual) {
		this.canalIngresoActual = canalIngresoActual;
	}
	public void setDiasAlmoneda(DiasAlmonedaVO diasAlmoneda) {
		this.diasAlmoneda = diasAlmoneda;
	}
	public void setNivelAgrupacion(CatalogoVO nivelAgrupacion) {
		this.nivelAgrupacion = nivelAgrupacion;
	}
	public void setReglasDescuento(ReglasDescuento reglasDescuento) {
		this.reglasDescuento = reglasDescuento;
	}
	public void setCandadoInferior(List<InformacionAjusteVO> candadoInferior) {
		this.candadoInferior = candadoInferior;
	}
	public void setTipoMonedas(List<CatalogoVO> tipoMonedas) {
		this.tipoMonedas = tipoMonedas;
	}
}

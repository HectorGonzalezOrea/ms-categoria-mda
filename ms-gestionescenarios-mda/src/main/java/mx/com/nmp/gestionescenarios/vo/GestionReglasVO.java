package mx.com.nmp.gestionescenarios.vo;

import java.util.List;

import mx.com.nmp.gestionescenarios.model.InfoGeneralReglaCanalComercializacion;

public class GestionReglasVO {
	private Integer id;
	private String nombre;
	private Object origen;
	private CommonVO ramo;
	private List<CommonVO> subramo;
	private List<CommonVO> factor;
	private List<CommonVO> clasificacionClientes;
	private List<Object> bolsas;
	private List<CommonVO> sucursales;
	private List<InfoGeneralReglaCanalComercializacion>canalComercializacion;
	private Object fechaAplicacion;
	private String fechas;
	private Object estatus;
	private Boolean compraCumplido;
	private Object aforo;
	private List<Object> estatusPartida;
	private List<CommonVO> monedas;
	private List<Object> canalIngresoActual;
	private Object diasAlmoneda;
	private Object nivelAgrupacion;
	private Object reglasDescuento;
	private Object candadoInferior;
	
	public Integer getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public Object getOrigen() {
		return origen;
	}
	public CommonVO getRamo() {
		return ramo;
	}
	public List<CommonVO> getSubramo() {
		return subramo;
	}
	public List<CommonVO> getFactor() {
		return factor;
	}
	public List<CommonVO> getClasificacionClientes() {
		return clasificacionClientes;
	}
	public List<Object> getBolsas() {
		return bolsas;
	}
	public List<CommonVO> getSucursales() {
		return sucursales;
	}
	public List<InfoGeneralReglaCanalComercializacion> getCanalComercializacion() {
		return canalComercializacion;
	}
	public Object getFechaAplicacion() {
		return fechaAplicacion;
	}
	public String getFechas() {
		return fechas;
	}
	public Object getEstatus() {
		return estatus;
	}
	public Boolean getCompraCumplido() {
		return compraCumplido;
	}
	public Object getAforo() {
		return aforo;
	}
	public List<Object> getEstatusPartida() {
		return estatusPartida;
	}
	public List<CommonVO> getMonedas() {
		return monedas;
	}
	public List<Object> getCanalIngresoActual() {
		return canalIngresoActual;
	}
	public Object getDiasAlmoneda() {
		return diasAlmoneda;
	}
	public Object getNivelAgrupacion() {
		return nivelAgrupacion;
	}
	public Object getReglasDescuento() {
		return reglasDescuento;
	}
	public Object getCandadoInferior() {
		return candadoInferior;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setOrigen(Object origen) {
		this.origen = origen;
	}
	public void setRamo(CommonVO ramo) {
		this.ramo = ramo;
	}
	public void setSubramo(List<CommonVO> subramo) {
		this.subramo = subramo;
	}
	public void setFactor(List<CommonVO> factor) {
		this.factor = factor;
	}
	public void setClasificacionClientes(List<CommonVO> clasificacionClientes) {
		this.clasificacionClientes = clasificacionClientes;
	}
	public void setBolsas(List<Object> bolsas) {
		this.bolsas = bolsas;
	}
	public void setSucursales(List<CommonVO> sucursales) {
		this.sucursales = sucursales;
	}
	public void setCanalComercializacion(List<InfoGeneralReglaCanalComercializacion> canalComercializacion) {
		this.canalComercializacion = canalComercializacion;
	}
	public void setFechaAplicacion(Object fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}
	public void setFechas(String fechas) {
		this.fechas = fechas;
	}
	public void setEstatus(Object estatus) {
		this.estatus = estatus;
	}
	public void setCompraCumplido(Boolean compraCumplido) {
		this.compraCumplido = compraCumplido;
	}
	public void setAforo(Object aforo) {
		this.aforo = aforo;
	}
	public void setEstatusPartida(List<Object> estatusPartida) {
		this.estatusPartida = estatusPartida;
	}
	public void setMonedas(List<CommonVO> monedas) {
		this.monedas = monedas;
	}
	public void setCanalIngresoActual(List<Object> canalIngresoActual) {
		this.canalIngresoActual = canalIngresoActual;
	}
	public void setDiasAlmoneda(Object diasAlmoneda) {
		this.diasAlmoneda = diasAlmoneda;
	}
	public void setNivelAgrupacion(Object nivelAgrupacion) {
		this.nivelAgrupacion = nivelAgrupacion;
	}
	public void setReglasDescuento(Object reglasDescuento) {
		this.reglasDescuento = reglasDescuento;
	}
	public void setCandadoInferior(Object candadoInferior) {
		this.candadoInferior = candadoInferior;
	}
	
	
}

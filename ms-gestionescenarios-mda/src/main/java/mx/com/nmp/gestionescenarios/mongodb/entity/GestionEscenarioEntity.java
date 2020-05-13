package mx.com.nmp.gestionescenarios.mongodb.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import mx.com.nmp.gestionescenarios.model.InfoGeneralReglaCanalComercializacion;


@Document(collection = "Gest_Escenarios")
public class GestionEscenarioEntity {
	
	@Transient
    public static final String SEQUENCE_NAME = "escenario_sequence";
	
	@Id
	private Integer idRegla;
	private String nombre;
	private Object origen;
	private Object ramo;
	private List<Object> subramo;
	private List<Object> factor;
	private List<Object> clasificacionClientes;
	private List<Object> bolsas;
	private List<Object> sucursales;
	private List<InfoGeneralReglaCanalComercializacion> canalComercializacion;
	private Object fechaAplicacion;
	private String fechas;
	private Object estatus;
	private Boolean compraCumplido;
	private Object aforo;
	private List<Object> estatusPartida;
	private List<Object> monedas;
	private List<Object> canalIngresoActual;
	private Object diasAlmoneda;
	private Object nivelAgrupacion;
	private Object reglasDescuento;
	private Object candadoInferior;
	
	public Integer getIdRegla() {
		return idRegla;
	}
	public void setIdRegla(Integer idRegla) {
		this.idRegla = idRegla;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Object getOrigen() {
		return origen;
	}
	public void setOrigen(Object origen) {
		this.origen = origen;
	}
	public Object getRamo() {
		return ramo;
	}
	public void setRamo(Object ramo) {
		this.ramo = ramo;
	}
	public List<Object> getSubramo() {
		return subramo;
	}
	public void setSubramo(List<Object> subramo) {
		this.subramo = subramo;
	}
	public List<Object> getFactor() {
		return factor;
	}
	public void setFactor(List<Object> factor) {
		this.factor = factor;
	}
	public List<Object> getClasificacionClientes() {
		return clasificacionClientes;
	}
	public void setClasificacionClientes(List<Object> clasificacionClientes) {
		this.clasificacionClientes = clasificacionClientes;
	}
	public List<Object> getBolsas() {
		return bolsas;
	}
	public void setBolsas(List<Object> bolsas) {
		this.bolsas = bolsas;
	}
	public List<Object> getSucursales() {
		return sucursales;
	}
	public void setSucursales(List<Object> sucursales) {
		this.sucursales = sucursales;
	}
	public List<InfoGeneralReglaCanalComercializacion> getCanalComercializacion() {
		return canalComercializacion;
	}
	public void setCanalComercializacion(List<InfoGeneralReglaCanalComercializacion> canalComercializacion) {
		this.canalComercializacion = canalComercializacion;
	}
	public Object getFechaAplicacion() {
		return fechaAplicacion;
	}
	public void setFechaAplicacion(Object fechaAplicacion) {
		this.fechaAplicacion = fechaAplicacion;
	}
	public String getFechas() {
		return fechas;
	}
	public void setFechas(String fechas) {
		this.fechas = fechas;
	}
	public Object getEstatus() {
		return estatus;
	}
	public void setEstatus(Object estatus) {
		this.estatus = estatus;
	}
	public Boolean getCompraCumplido() {
		return compraCumplido;
	}
	public void setCompraCumplido(Boolean compraCumplido) {
		this.compraCumplido = compraCumplido;
	}
	public Object getAforo() {
		return aforo;
	}
	public void setAforo(Object aforo) {
		this.aforo = aforo;
	}
	public List<Object> getEstatusPartida() {
		return estatusPartida;
	}
	public void setEstatusPartida(List<Object> estatusPartida) {
		this.estatusPartida = estatusPartida;
	}
	public List<Object> getMonedas() {
		return monedas;
	}
	public void setMonedas(List<Object> monedas) {
		this.monedas = monedas;
	}
	public List<Object> getCanalIngresoActual() {
		return canalIngresoActual;
	}
	public void setCanalIngresoActual(List<Object> canalIngresoActual) {
		this.canalIngresoActual = canalIngresoActual;
	}
	public Object getDiasAlmoneda() {
		return diasAlmoneda;
	}
	public void setDiasAlmoneda(Object diasAlmoneda) {
		this.diasAlmoneda = diasAlmoneda;
	}
	public Object getNivelAgrupacion() {
		return nivelAgrupacion;
	}
	public void setNivelAgrupacion(Object nivelAgrupacion) {
		this.nivelAgrupacion = nivelAgrupacion;
	}
	public Object getReglasDescuento() {
		return reglasDescuento;
	}
	public void setReglasDescuento(Object reglasDescuento) {
		this.reglasDescuento = reglasDescuento;
	}
	public Object getCandadoInferior() {
		return candadoInferior;
	}
	public void setCandadoInferior(Object candadoInferior) {
		this.candadoInferior = candadoInferior;
	}
}

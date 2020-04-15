package mx.com.nmp.escenariosdinamicos.oag.vo;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public class IndexGarantiaVO {
	@JsonProperty("descripcion")
	private String descripcion;
	@JsonProperty("est_prenda")
	private String estPrenda;
	@JsonProperty("precio_venta_inicial")
	private String precioVentaInicial;
	@JsonProperty("alhajas_avaluo_compl")
	private String alhajasAvaluoCompl;
	@JsonProperty("avaluo_comerc")
	private String avaluoComerc;
	@JsonProperty("sku")
	private String sku;
	@JsonProperty("telefono")
	private String telefono;
	@JsonProperty("costo_pvia")
	private String costoPvia;
	@JsonProperty("nota_comercial")
	private String notaComercial;
	@JsonProperty("fecha_comercializacion")
	private String fechaComercializacion;
	@JsonProperty("est_asignacion")
	private String estAsignacion;
	@JsonProperty("edo_canal_venta")
	private String edoCanalVenta;
	@JsonProperty("ramo")
	private String ramo;
	@JsonProperty("suc_act")
	private String sucAct;
	@JsonProperty("direccion")
	private String direccion;
	@JsonProperty("delegacion_municipio")
	private String delegacionMunicipio;
	@JsonProperty("fecha_empenio")
	private String fechaEmpenio;
	@JsonProperty("canal_ing_origen")
	private String canalIngOrigen;
	@JsonProperty("colonia")
	private String colonia;
	@JsonProperty("est_caja")
	private String estCaja;
	@JsonProperty("alhajas_precio_oro")
	private String alhajasPrecioOro;
	@JsonProperty("ubicacion_fisica_act")
	private String ubicacionFisicaAct;
	@JsonProperty("num_cuo")
	private String numCuo;
	@JsonProperty("antiguedad")
	private String antiguedad;
	@JsonProperty("id_mercancia")
	private String idMercancia;
	@JsonProperty("est_producto")
	private String estProducto;
	@JsonProperty("alhajas_tipo_factor")
	private String alhajasTipoFactor;
	@JsonProperty("precio_venta_act")
	private String precioVentaAct;
	@JsonProperty("alhajas_gramaje")
	private String alhajasGramaje;
	@JsonProperty("precio_venta_salida")
	private String precioVentaSalida;
	@JsonProperty("canal_venta_act")
	private String canalVentaAct;
	@JsonProperty("edo_producto")
	private String edoProducto;
	@JsonProperty("avaluo_tec_origen")
	private String avaluoTecOrigen;
	@JsonProperty("fecha_ultima_modificacion")
	private String fechaUltimaModificacion;
	@JsonProperty("contrato_origen")
	private String contratoOrigen;
	@JsonProperty("importe_prestamo")
	private String importePrestamo;
	@JsonProperty("avaluo_cc")
	private String avaluoCc;
	@JsonProperty("expendio")
	private String expendio;
	@JsonProperty("alhajas_incremento")
	private String alhajasIIncremento;
	@JsonProperty("nota_prendaria")
	private String notaPrendaria;
	@JsonProperty("valor_monte_act")
	private String valorMonteAct;
	@JsonProperty("margen_venta")
	private String margenVenta;
	@JsonProperty("fecha_alta_inventario")
	private String fechaAltaInventario;
	@JsonProperty("edo_suc_origen")
	private String edoSucOrigen;
	@JsonProperty("sistema_origen")
	private String sistemaOrigen;
	@JsonProperty("importe_devolucion")
	private String importeDevolucion;
	@JsonProperty("canal_ingreso")
	private String canalIngreso;
	@JsonProperty("cp")
	private String cp;
	@JsonProperty("partida")
	private String partida;
	@JsonProperty("tipo_transaccion")
	private String tipoTransaccion;
	@JsonProperty("alhajas_kilates")
	private String alhajasKilates;
	@JsonProperty("alhajas_despl_comer")
	private String alhajasDesplComer;
	@JsonProperty("alhajas_metal")
	private String alhajasMetal;
	@JsonProperty("refrendos")
	private String refrendos;
	@JsonProperty("fecha_ing_deposito")
	private String fechaIngDeposito;
	@JsonProperty("subramo")
	private String subramo;
	@JsonProperty("partida_origen")
	private String partidaOrigen;
	
	public String getDescripcion() {
		return descripcion;
	}
	public String getEstPrenda() {
		return estPrenda;
	}
	public String getPrecioVentaInicial() {
		return precioVentaInicial;
	}
	public String getAlhajasAvaluoCompl() {
		return alhajasAvaluoCompl;
	}
	public String getAvaluoComerc() {
		return avaluoComerc;
	}
	public String getSku() {
		return sku;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getCostoPvia() {
		return costoPvia;
	}
	public String getNotaComercial() {
		return notaComercial;
	}
	public String getFechaComercializacion() {
		return fechaComercializacion;
	}
	public String getEstAsignacion() {
		return estAsignacion;
	}
	public String getEdoCanalVenta() {
		return edoCanalVenta;
	}
	public String getRamo() {
		return ramo;
	}
	public String getSucAct() {
		return sucAct;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getDelegacionMunicipio() {
		return delegacionMunicipio;
	}
	public String getFechaEmpenio() {
		return fechaEmpenio;
	}
	public String getCanalIngOrigen() {
		return canalIngOrigen;
	}
	public String getColonia() {
		return colonia;
	}
	public String getEstCaja() {
		return estCaja;
	}
	public String getAlhajasPrecioOro() {
		return alhajasPrecioOro;
	}
	public String getUbicacionFisicaAct() {
		return ubicacionFisicaAct;
	}
	public String getNumCuo() {
		return numCuo;
	}
	public String getAntiguedad() {
		return antiguedad;
	}
	public String getIdMercancia() {
		return idMercancia;
	}
	public String getEstProducto() {
		return estProducto;
	}
	public String getAlhajasTipoFactor() {
		return alhajasTipoFactor;
	}
	public String getPrecioVentaAct() {
		return precioVentaAct;
	}
	public String getAlhajasGramaje() {
		return alhajasGramaje;
	}
	public String getPrecioVentaSalida() {
		return precioVentaSalida;
	}
	public String getCanalVentaAct() {
		return canalVentaAct;
	}
	public String getEdoProducto() {
		return edoProducto;
	}
	public String getAvaluoTecOrigen() {
		return avaluoTecOrigen;
	}
	public String getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public String getContratoOrigen() {
		return contratoOrigen;
	}
	public String getImportePrestamo() {
		return importePrestamo;
	}
	public String getAvaluoCc() {
		return avaluoCc;
	}
	public String getExpendio() {
		return expendio;
	}
	public String getAlhajasIIncremento() {
		return alhajasIIncremento;
	}
	public String getNotaPrendaria() {
		return notaPrendaria;
	}
	public String getValorMonteAct() {
		return valorMonteAct;
	}
	public String getMargenVenta() {
		return margenVenta;
	}
	public String getFechaAltaInventario() {
		return fechaAltaInventario;
	}
	public String getEdoSucOrigen() {
		return edoSucOrigen;
	}
	public String getSistemaOrigen() {
		return sistemaOrigen;
	}
	public String getImporteDevolucion() {
		return importeDevolucion;
	}
	public String getCanalIngreso() {
		return canalIngreso;
	}
	public String getCp() {
		return cp;
	}
	public String getPartida() {
		return partida;
	}
	public String getTipoTransaccion() {
		return tipoTransaccion;
	}
	public String getAlhajasKilates() {
		return alhajasKilates;
	}
	public String getAlhajasDesplComer() {
		return alhajasDesplComer;
	}
	public String getAlhajasMetal() {
		return alhajasMetal;
	}
	public String getRefrendos() {
		return refrendos;
	}
	public String getFechaIngDeposito() {
		return fechaIngDeposito;
	}
	public String getSubramo() {
		return subramo;
	}
	public String getPartidaOrigen() {
		return partidaOrigen;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setEstPrenda(String estPrenda) {
		this.estPrenda = estPrenda;
	}
	public void setPrecioVentaInicial(String precioVentaInicial) {
		this.precioVentaInicial = precioVentaInicial;
	}
	public void setAlhajasAvaluoCompl(String alhajasAvaluoCompl) {
		this.alhajasAvaluoCompl = alhajasAvaluoCompl;
	}
	public void setAvaluoComerc(String avaluoComerc) {
		this.avaluoComerc = avaluoComerc;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setCostoPvia(String costoPvia) {
		this.costoPvia = costoPvia;
	}
	public void setNotaComercial(String notaComercial) {
		this.notaComercial = notaComercial;
	}
	public void setFechaComercializacion(String fechaComercializacion) {
		this.fechaComercializacion = fechaComercializacion;
	}
	public void setEstAsignacion(String estAsignacion) {
		this.estAsignacion = estAsignacion;
	}
	public void setEdoCanalVenta(String edoCanalVenta) {
		this.edoCanalVenta = edoCanalVenta;
	}
	public void setRamo(String ramo) {
		this.ramo = ramo;
	}
	public void setSucAct(String sucAct) {
		this.sucAct = sucAct;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setDelegacionMunicipio(String delegacionMunicipio) {
		this.delegacionMunicipio = delegacionMunicipio;
	}
	public void setFechaEmpenio(String fechaEmpenio) {
		this.fechaEmpenio = fechaEmpenio;
	}
	public void setCanalIngOrigen(String canalIngOrigen) {
		this.canalIngOrigen = canalIngOrigen;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public void setEstCaja(String estCaja) {
		this.estCaja = estCaja;
	}
	public void setAlhajasPrecioOro(String alhajasPrecioOro) {
		this.alhajasPrecioOro = alhajasPrecioOro;
	}
	public void setUbicacionFisicaAct(String ubicacionFisicaAct) {
		this.ubicacionFisicaAct = ubicacionFisicaAct;
	}
	public void setNumCuo(String numCuo) {
		this.numCuo = numCuo;
	}
	public void setAntiguedad(String antiguedad) {
		this.antiguedad = antiguedad;
	}
	public void setIdMercancia(String idMercancia) {
		this.idMercancia = idMercancia;
	}
	public void setEstProducto(String estProducto) {
		this.estProducto = estProducto;
	}
	public void setAlhajasTipoFactor(String alhajasTipoFactor) {
		this.alhajasTipoFactor = alhajasTipoFactor;
	}
	public void setPrecioVentaAct(String precioVentaAct) {
		this.precioVentaAct = precioVentaAct;
	}
	public void setAlhajasGramaje(String alhajasGramaje) {
		this.alhajasGramaje = alhajasGramaje;
	}
	public void setPrecioVentaSalida(String precioVentaSalida) {
		this.precioVentaSalida = precioVentaSalida;
	}
	public void setCanalVentaAct(String canalVentaAct) {
		this.canalVentaAct = canalVentaAct;
	}
	public void setEdoProducto(String edoProducto) {
		this.edoProducto = edoProducto;
	}
	public void setAvaluoTecOrigen(String avaluoTecOrigen) {
		this.avaluoTecOrigen = avaluoTecOrigen;
	}
	public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	public void setContratoOrigen(String contratoOrigen) {
		this.contratoOrigen = contratoOrigen;
	}
	public void setImportePrestamo(String importePrestamo) {
		this.importePrestamo = importePrestamo;
	}
	public void setAvaluoCc(String avaluoCc) {
		this.avaluoCc = avaluoCc;
	}
	public void setExpendio(String expendio) {
		this.expendio = expendio;
	}
	public void setAlhajasIIncremento(String alhajasIIncremento) {
		this.alhajasIIncremento = alhajasIIncremento;
	}
	public void setNotaPrendaria(String notaPrendaria) {
		this.notaPrendaria = notaPrendaria;
	}
	public void setValorMonteAct(String valorMonteAct) {
		this.valorMonteAct = valorMonteAct;
	}
	public void setMargenVenta(String margenVenta) {
		this.margenVenta = margenVenta;
	}
	public void setFechaAltaInventario(String fechaAltaInventario) {
		this.fechaAltaInventario = fechaAltaInventario;
	}
	public void setEdoSucOrigen(String edoSucOrigen) {
		this.edoSucOrigen = edoSucOrigen;
	}
	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}
	public void setImporteDevolucion(String importeDevolucion) {
		this.importeDevolucion = importeDevolucion;
	}
	public void setCanalIngreso(String canalIngreso) {
		this.canalIngreso = canalIngreso;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	public void setPartida(String partida) {
		this.partida = partida;
	}
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	public void setAlhajasKilates(String alhajasKilates) {
		this.alhajasKilates = alhajasKilates;
	}
	public void setAlhajasDesplComer(String alhajasDesplComer) {
		this.alhajasDesplComer = alhajasDesplComer;
	}
	public void setAlhajasMetal(String alhajasMetal) {
		this.alhajasMetal = alhajasMetal;
	}
	public void setRefrendos(String refrendos) {
		this.refrendos = refrendos;
	}
	public void setFechaIngDeposito(String fechaIngDeposito) {
		this.fechaIngDeposito = fechaIngDeposito;
	}
	public void setSubramo(String subramo) {
		this.subramo = subramo;
	}
	public void setPartidaOrigen(String partidaOrigen) {
		this.partidaOrigen = partidaOrigen;
	}
	@Override
	public String toString() {
		return "IndexGarantiaVO [descripcion=" + descripcion + ", estPrenda=" + estPrenda + ", precioVentaInicial="
				+ precioVentaInicial + ", alhajasAvaluoCompl=" + alhajasAvaluoCompl + ", avaluoComerc=" + avaluoComerc
				+ ", sku=" + sku + ", telefono=" + telefono + ", costoPvia=" + costoPvia + ", notaComercial="
				+ notaComercial + ", fechaComercializacion=" + fechaComercializacion + ", estAsignacion="
				+ estAsignacion + ", edoCanalVenta=" + edoCanalVenta + ", ramo=" + ramo + ", sucAct=" + sucAct
				+ ", direccion=" + direccion + ", delegacionMunicipio=" + delegacionMunicipio + ", fechaEmpenio="
				+ fechaEmpenio + ", canalIngOrigen=" + canalIngOrigen + ", colonia=" + colonia + ", estCaja=" + estCaja
				+ ", alhajasPrecioOro=" + alhajasPrecioOro + ", ubicacionFisicaAct=" + ubicacionFisicaAct + ", numCuo="
				+ numCuo + ", antiguedad=" + antiguedad + ", idMercancia=" + idMercancia + ", estProducto="
				+ estProducto + ", alhajasTipoFactor=" + alhajasTipoFactor + ", precioVentaAct=" + precioVentaAct
				+ ", alhajasGramaje=" + alhajasGramaje + ", precioVentaSalida=" + precioVentaSalida + ", canalVentaAct="
				+ canalVentaAct + ", edoProducto=" + edoProducto + ", avaluoTecOrigen=" + avaluoTecOrigen
				+ ", fechaUltimaModificacion=" + fechaUltimaModificacion + ", contratoOrigen=" + contratoOrigen
				+ ", importePrestamo=" + importePrestamo + ", avaluoCc=" + avaluoCc + ", expendio=" + expendio
				+ ", alhajasIIncremento=" + alhajasIIncremento + ", notaPrendaria=" + notaPrendaria + ", valorMonteAct="
				+ valorMonteAct + ", margenVenta=" + margenVenta + ", fechaAltaInventario=" + fechaAltaInventario
				+ ", edoSucOrigen=" + edoSucOrigen + ", sistemaOrigen=" + sistemaOrigen + ", importeDevolucion="
				+ importeDevolucion + ", canalIngreso=" + canalIngreso + ", cp=" + cp + ", partida=" + partida
				+ ", tipoTransaccion=" + tipoTransaccion + ", alhajasKilates=" + alhajasKilates + ", alhajasDesplComer="
				+ alhajasDesplComer + ", alhajasMetal=" + alhajasMetal + ", refrendos=" + refrendos
				+ ", fechaIngDeposito=" + fechaIngDeposito + ", subramo=" + subramo + ", partidaOrigen=" + partidaOrigen
				+ "]";
	}
}	

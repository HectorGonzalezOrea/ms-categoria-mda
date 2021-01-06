package mx.com.nmp.escenariosdinamicos.elastic.vo;

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
	private Double alhajasAvaluoCompl;
	@JsonProperty("avaluo_comerc")
	private Double avaluoComerc;
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
	private Double precioVentaAct;
	@JsonProperty("alhajas_gramaje")
	private Double alhajasGramaje;
	@JsonProperty("precio_venta_salida")
	private String precioVentaSalida;
	@JsonProperty("canal_venta_act")
	private String canalVentaAct;
	@JsonProperty("edo_producto")
	private String edoProducto;
	@JsonProperty("avaluo_tec_origen")
	private Double avaluoTecOrigen;
	@JsonProperty("fecha_ultima_modificacion")
	private String fechaUltimaModificacion;
	@JsonProperty("contrato_origen")
	private String contratoOrigen;
	@JsonProperty("importe_prestamo")
	private Double importePrestamo;
	@JsonProperty("avaluo_cc")
	private String avaluoCc;
	@JsonProperty("expendio")
	private String expendio;
	@JsonProperty("alhajas_incremento")
	private Double alhajasIIncremento;
	@JsonProperty("nota_prendaria")
	private String notaPrendaria;
	@JsonProperty("valor_monte_act")
	private Double valorMonteAct;
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
	private Integer partida;
	@JsonProperty("tipo_transaccion")
	private String tipoTransaccion;
	@JsonProperty("alhajas_kilates")
	private Double alhajasKilates;
	@JsonProperty("alhajas_despl_comer")
	private Double alhajasDesplComer;
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
	
	@JsonProperty("productName")
	private String productName;
	
	public String getDescripcion() {
		return descripcion;
	}
	public String getEstPrenda() {
		return estPrenda;
	}
	public String getPrecioVentaInicial() {
		return precioVentaInicial;
	}
	public Double getAlhajasAvaluoCompl() {
		return alhajasAvaluoCompl;
	}
	public Double getAvaluoComerc() {
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
	public Double getPrecioVentaAct() {
		return precioVentaAct;
	}
	public Double getAlhajasGramaje() {
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
	public Double getAvaluoTecOrigen() {
		return avaluoTecOrigen;
	}
	public String getFechaUltimaModificacion() {
		return fechaUltimaModificacion;
	}
	public String getContratoOrigen() {
		return contratoOrigen;
	}
	public Double getImportePrestamo() {
		return importePrestamo;
	}
	public String getAvaluoCc() {
		return avaluoCc;
	}
	public String getExpendio() {
		return expendio;
	}
	public Double getAlhajasIIncremento() {
		return alhajasIIncremento;
	}
	public String getNotaPrendaria() {
		return notaPrendaria;
	}
	public Double getValorMonteAct() {
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
	public Integer getPartida() {
		return partida;
	}
	public String getTipoTransaccion() {
		return tipoTransaccion;
	}
	public Double getAlhajasKilates() {
		return alhajasKilates;
	}
	public Double getAlhajasDesplComer() {
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
	public void setAlhajasAvaluoCompl(Double alhajasAvaluoCompl) {
		this.alhajasAvaluoCompl = alhajasAvaluoCompl;
	}
	public void setAvaluoComerc(Double avaluoComerc) {
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
	public void setPrecioVentaAct(Double precioVentaAct) {
		this.precioVentaAct = precioVentaAct;
	}
	public void setAlhajasGramaje(Double alhajasGramaje) {
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
	public void setAvaluoTecOrigen(Double avaluoTecOrigen) {
		this.avaluoTecOrigen = avaluoTecOrigen;
	}
	public void setFechaUltimaModificacion(String fechaUltimaModificacion) {
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}
	public void setContratoOrigen(String contratoOrigen) {
		this.contratoOrigen = contratoOrigen;
	}
	public void setImportePrestamo(Double importePrestamo) {
		this.importePrestamo = importePrestamo;
	}
	public void setAvaluoCc(String avaluoCc) {
		this.avaluoCc = avaluoCc;
	}
	public void setExpendio(String expendio) {
		this.expendio = expendio;
	}
	public void setAlhajasIIncremento(Double alhajasIIncremento) {
		this.alhajasIIncremento = alhajasIIncremento;
	}
	public void setNotaPrendaria(String notaPrendaria) {
		this.notaPrendaria = notaPrendaria;
	}
	public void setValorMonteAct(Double valorMonteAct) {
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
	public void setPartida(Integer partida) {
		this.partida = partida;
	}
	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	public void setAlhajasKilates(Double alhajasKilates) {
		this.alhajasKilates = alhajasKilates;
	}
	public void setAlhajasDesplComer(Double alhajasDesplComer) {
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

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Override
	public String toString() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("IndexGarantiaVO {\n");
		 sb.append("descripcion: ").append(toIndentedString(descripcion)).append("\n");
		 sb.append("estPrenda: ").append(toIndentedString(estPrenda)).append("\n");
		 sb.append("precioVentaInicial: ").append(toIndentedString(precioVentaInicial)).append("\n");
		 sb.append("alhajasAvaluoCompl: ").append(toIndentedString(alhajasAvaluoCompl)).append("\n");
		 sb.append("avaluoComerc: ").append(toIndentedString(avaluoComerc)).append("\n");
		 sb.append("sku: ").append(toIndentedString(sku)).append("\n");
		 sb.append("telefono: ").append(toIndentedString(telefono)).append("\n");
		 sb.append("costoPvia: ").append(toIndentedString(costoPvia)).append("\n");
		 sb.append("notaComercial: ").append(toIndentedString(notaComercial)).append("\n");
		 sb.append("fechaComercializacion: ").append(toIndentedString(fechaComercializacion)).append("\n");
		 sb.append("estAsignacion: ").append(toIndentedString(estAsignacion)).append("\n");
		 sb.append("edoCanalVenta: ").append(toIndentedString(edoCanalVenta)).append("\n");
		 sb.append("ramo: ").append(toIndentedString(ramo)).append("\n");
		 sb.append("sucAct: ").append(toIndentedString(sucAct)).append("\n");
		 sb.append("direccion: ").append(toIndentedString(direccion)).append("\n");
		 sb.append("delegacionMunicipio: ").append(toIndentedString(delegacionMunicipio)).append("\n");
		 sb.append("fechaEmpenio: ").append(toIndentedString(fechaEmpenio)).append("\n");
		 sb.append("canalIngOrigen: ").append(toIndentedString(canalIngOrigen)).append("\n");
		 sb.append("colonia: ").append(toIndentedString(colonia)).append("\n");
		 sb.append("estCaja: ").append(toIndentedString(estCaja)).append("\n");
		 sb.append("alhajasPrecioOro: ").append(toIndentedString(alhajasPrecioOro)).append("\n");
		 sb.append("ubicacionFisicaAct: ").append(toIndentedString(ubicacionFisicaAct)).append("\n");
		 sb.append("numCuo: ").append(toIndentedString(numCuo)).append("\n");
		 sb.append("antiguedad: ").append(toIndentedString(antiguedad)).append("\n");
		 sb.append("idMercancia: ").append(toIndentedString(idMercancia)).append("\n");
		 sb.append("estProducto: ").append(toIndentedString(estProducto)).append("\n");
		 sb.append("alhajasTipoFactor: ").append(toIndentedString(alhajasTipoFactor)).append("\n");
		 sb.append("precioVentaAct: ").append(toIndentedString(precioVentaAct)).append("\n");
		 sb.append("alhajasGramaje: ").append(toIndentedString(alhajasGramaje)).append("\n");
		 sb.append("precioVentaSalida: ").append(toIndentedString(precioVentaSalida)).append("\n");
		 sb.append("canalVentaAct: ").append(toIndentedString(canalVentaAct)).append("\n");
		 sb.append("edoProducto: ").append(toIndentedString(edoProducto)).append("\n");
		 sb.append("avaluoTecOrigen: ").append(toIndentedString(avaluoTecOrigen)).append("\n");
		 sb.append("fechaUltimaModificacion: ").append(toIndentedString(fechaUltimaModificacion)).append("\n");
		 sb.append("contratoOrigen: ").append(toIndentedString(contratoOrigen)).append("\n");
		 sb.append("importePrestamo: ").append(toIndentedString(importePrestamo)).append("\n");
		 sb.append("avaluoCc: ").append(toIndentedString(avaluoCc)).append("\n");
		 sb.append("expendio: ").append(toIndentedString(expendio)).append("\n");
		 sb.append("alhajasIIncremento: ").append(toIndentedString(alhajasIIncremento)).append("\n");
		 sb.append("notaPrendaria: ").append(toIndentedString(notaPrendaria)).append("\n");
		 sb.append("valorMonteAct: ").append(toIndentedString(valorMonteAct)).append("\n");
		 sb.append("margenVenta: ").append(toIndentedString(margenVenta)).append("\n");
		 sb.append("fechaAltaInventario: ").append(toIndentedString(fechaAltaInventario)).append("\n");
		 sb.append("edoSucOrigen: ").append(toIndentedString(edoSucOrigen)).append("\n");
		 sb.append("sistemaOrigen: ").append(toIndentedString(sistemaOrigen)).append("\n");
		 sb.append("importeDevolucion: ").append(toIndentedString(importeDevolucion)).append("\n");
		 sb.append("canalIngreso: ").append(toIndentedString(canalIngreso)).append("\n");
		 sb.append("cp: ").append(toIndentedString(cp)).append("\n");
		 sb.append("partida: ").append(toIndentedString(partida)).append("\n");
		 sb.append("tipoTransaccion: ").append(toIndentedString(tipoTransaccion)).append("\n");
		 sb.append("alhajasKilates: ").append(toIndentedString(alhajasKilates)).append("\n");
		 sb.append("alhajasDesplComer: ").append(toIndentedString(alhajasDesplComer)).append("\n");
		 sb.append("alhajasMetal: ").append(toIndentedString(alhajasMetal)).append("\n");
		 sb.append("refrendos: ").append(toIndentedString(refrendos)).append("\n");
		 sb.append("fechaIngDeposito: ").append(toIndentedString(fechaIngDeposito)).append("\n");
		 sb.append("subramo: ").append(toIndentedString(subramo)).append("\n");
		 sb.append("partidaOrigen: ").append(toIndentedString(partidaOrigen)).append("\n");
		 sb.append("productName: ").append(toIndentedString(productName)).append("\n\n");
		 sb.append("}");
		return sb.toString();
	}
	
	 private String toIndentedString(java.lang.Object o) {
		    if (o == null) {
		      return "null";
	   }
	   return o.toString().replace("\n", "\n    ");
	 }
}	

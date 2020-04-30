package mx.com.nmp.escenariosdinamicos.elastic.vo;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public class IndexVentasVO {
	@JsonProperty("cancelado")
	private String cancelado;
	@JsonProperty("cantidad")
	private String cantidad;
	@JsonProperty("co_importe")
	private String coImporte;
	@JsonProperty("comision_almacenaje")
	private String comisionAlmacenaje;
	@JsonProperty("comision_ds_extemporaneo")
	private String comisionDsExtemporaneo;
	@JsonProperty("comision_pase")
	private String comisionPase;
	@JsonProperty("condiciones_venta")
	private String condicionesVenta;
	@JsonProperty("cuenta_pago")
	private String cuentaPago;
	@JsonProperty("cuo")
	private String cuo;
	@JsonProperty("descripcion")
	private String descripcion;
	@JsonProperty("digito_verificador")
	private String digitoVerificador;
	@JsonProperty("fecha")
	private String fecha;
	@JsonProperty("forma_pago")
	private String formaPago;
	@JsonProperty("identificacion")
	private String identificacion;
	@JsonProperty("impuesto")
	private String impuesto;
	@JsonProperty("interes_almoneda")
	private String interesAlmoneda;
	@JsonProperty("interes_deposito")
	private String interesDeposito;
	@JsonProperty("it_importe")
	private String itImporte;
	@JsonProperty("iva_comision_almacenaje")
	private String ivaComisionAlmacenaje;
	@JsonProperty("iva_comision_ds_extemporaneo")
	private String ivaComisionDsExtemporaneo;
	@JsonProperty("iva_comision_pase")
	private String ivaComisionPase;
	@JsonProperty("iva_interes_almonedas")
	private String ivaInteresAlmoneda;
	@JsonProperty("iva_interes_deposito")
	private String ivaInteresDeposito;
	@JsonProperty("lugar_expedicion")
	private String lugarExpedicion;
	@JsonProperty("metodo_pago")
	private String metodoPago;
	@JsonProperty("tasa")
	private String tasa;
	@JsonProperty("tipo_comprobante")
	private String tipoComprobante;
	@JsonProperty("tipo_operacion_etiqueta")
	private String tipoOperacionEtiqueta;
	@JsonProperty("valor_unitario")
	private String valorUnitario;
	//////////////////////valores que aun no se definen
	private String partida;
	private String sku;
	//////////////////////////////////////////////
	public String getCancelado() {
		return cancelado;
	}
	public String getCantidad() {
		return cantidad;
	}
	public String getCoImporte() {
		return coImporte;
	}
	public String getComisionAlmacenaje() {
		return comisionAlmacenaje;
	}
	public String getComisionDsExtemporaneo() {
		return comisionDsExtemporaneo;
	}
	public String getComisionPase() {
		return comisionPase;
	}
	public String getCondicionesVenta() {
		return condicionesVenta;
	}
	public String getCuentaPago() {
		return cuentaPago;
	}
	public String getCuo() {
		return cuo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getDigitoVerificador() {
		return digitoVerificador;
	}
	public String getFecha() {
		return fecha;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public String getImpuesto() {
		return impuesto;
	}
	public String getInteresAlmoneda() {
		return interesAlmoneda;
	}
	public String getInteresDeposito() {
		return interesDeposito;
	}
	public String getItImporte() {
		return itImporte;
	}
	public String getIvaComisionAlmacenaje() {
		return ivaComisionAlmacenaje;
	}
	public String getIvaComisionDsExtemporaneo() {
		return ivaComisionDsExtemporaneo;
	}
	public String getIvaComisionPase() {
		return ivaComisionPase;
	}
	public String getIvaInteresAlmoneda() {
		return ivaInteresAlmoneda;
	}
	public String getIvaInteresDeposito() {
		return ivaInteresDeposito;
	}
	public String getLugarExpedicion() {
		return lugarExpedicion;
	}
	public String getMetodoPago() {
		return metodoPago;
	}
	public String getTasa() {
		return tasa;
	}
	public String getTipoComprobante() {
		return tipoComprobante;
	}
	public String getTipoOperacionEtiqueta() {
		return tipoOperacionEtiqueta;
	}
	public String getValorUnitario() {
		return valorUnitario;
	}
	public void setCancelado(String cancelado) {
		this.cancelado = cancelado;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public void setCoImporte(String coImporte) {
		this.coImporte = coImporte;
	}
	public void setComisionAlmacenaje(String comisionAlmacenaje) {
		this.comisionAlmacenaje = comisionAlmacenaje;
	}
	public void setComisionDsExtemporaneo(String comisionDsExtemporaneo) {
		this.comisionDsExtemporaneo = comisionDsExtemporaneo;
	}
	public void setComisionPase(String comisionPase) {
		this.comisionPase = comisionPase;
	}
	public void setCondicionesVenta(String condicionesVenta) {
		this.condicionesVenta = condicionesVenta;
	}
	public void setCuentaPago(String cuentaPago) {
		this.cuentaPago = cuentaPago;
	}
	public void setCuo(String cuo) {
		this.cuo = cuo;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}
	public void setInteresAlmoneda(String interesAlmoneda) {
		this.interesAlmoneda = interesAlmoneda;
	}
	public void setInteresDeposito(String interesDeposito) {
		this.interesDeposito = interesDeposito;
	}
	public void setItImporte(String itImporte) {
		this.itImporte = itImporte;
	}
	public void setIvaComisionAlmacenaje(String ivaComisionAlmacenaje) {
		this.ivaComisionAlmacenaje = ivaComisionAlmacenaje;
	}
	public void setIvaComisionDsExtemporaneo(String ivaComisionDsExtemporaneo) {
		this.ivaComisionDsExtemporaneo = ivaComisionDsExtemporaneo;
	}
	public void setIvaComisionPase(String ivaComisionPase) {
		this.ivaComisionPase = ivaComisionPase;
	}
	public void setIvaInteresAlmoneda(String ivaInteresAlmoneda) {
		this.ivaInteresAlmoneda = ivaInteresAlmoneda;
	}
	public void setIvaInteresDeposito(String ivaInteresDeposito) {
		this.ivaInteresDeposito = ivaInteresDeposito;
	}
	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}
	public void setTipoOperacionEtiqueta(String tipoOperacionEtiqueta) {
		this.tipoOperacionEtiqueta = tipoOperacionEtiqueta;
	}
	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public String getPartida() {
		return partida;
	}
	public String getSku() {
		return sku;
	}
	public void setPartida(String partida) {
		this.partida = partida;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	@Override
	public String toString() {
		return "IndexVentasVO [cancelado=" + cancelado + ", cantidad=" + cantidad + ", coImporte=" + coImporte
				+ ", comisionAlmacenaje=" + comisionAlmacenaje + ", comisionDsExtemporaneo=" + comisionDsExtemporaneo
				+ ", comisionPase=" + comisionPase + ", condicionesVenta=" + condicionesVenta + ", cuentaPago="
				+ cuentaPago + ", cuo=" + cuo + ", descripcion=" + descripcion + ", digitoVerificador="
				+ digitoVerificador + ", fecha=" + fecha + ", formaPago=" + formaPago + ", identificacion="
				+ identificacion + ", impuesto=" + impuesto + ", interesAlmoneda=" + interesAlmoneda
				+ ", interesDeposito=" + interesDeposito + ", itImporte=" + itImporte + ", ivaComisionAlmacenaje="
				+ ivaComisionAlmacenaje + ", ivaComisionDsExtemporaneo=" + ivaComisionDsExtemporaneo
				+ ", ivaComisionPase=" + ivaComisionPase + ", ivaInteresAlmoneda=" + ivaInteresAlmoneda
				+ ", ivaInteresDeposito=" + ivaInteresDeposito + ", lugarExpedicion=" + lugarExpedicion
				+ ", metodoPago=" + metodoPago + ", tasa=" + tasa + ", tipoComprobante=" + tipoComprobante
				+ ", tipoOperacionEtiqueta=" + tipoOperacionEtiqueta + ", valorUnitario=" + valorUnitario + ", partida="
				+ partida + ", sku=" + sku + "]";
	}

	
}

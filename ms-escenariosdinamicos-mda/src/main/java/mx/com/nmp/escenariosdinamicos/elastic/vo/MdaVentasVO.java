package mx.com.nmp.escenariosdinamicos.elastic.vo;

import java.util.Date;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public class MdaVentasVO {
	@JsonProperty("canal_ingreso")
	private String canalIngreso;

	@JsonProperty("credencial")
	private String credencial;

	@JsonProperty("edo_prenda")
	private String edoPrenda;

	@JsonProperty("factor")
	private String factor;

	@JsonProperty("fecha_op")
	private Date fechaOp;

	@JsonProperty("folio_partida")
	private String folioPartida;

	@JsonProperty("movimiento_sucursal")
	private String movimientoSucursal;

	@JsonProperty("numero_registro")
	private String numeroRegistro;

	@JsonProperty("operacion")
	private String operacion;

	@JsonProperty("ramo_des")
	private String ramoDes;

	@JsonProperty("subramo_des")
	private String subramoDes;

	public String getCanalIngreso() {
		return canalIngreso;
	}

	public void setCanalIngreso(String canalIngreso) {
		this.canalIngreso = canalIngreso;
	}

	public String getCredencial() {
		return credencial;
	}

	public void setCredencial(String credencial) {
		this.credencial = credencial;
	}

	public String getEdoPrenda() {
		return edoPrenda;
	}

	public void setEdoPrenda(String edoPrenda) {
		this.edoPrenda = edoPrenda;
	}

	public String getFactor() {
		return factor;
	}

	public void setFactor(String factor) {
		this.factor = factor;
	}

	public Date getFechaOp() {
		return fechaOp;
	}

	public void setFechaOp(Date fechaOp) {
		this.fechaOp = fechaOp;
	}

	public String getFolioPartida() {
		return folioPartida;
	}

	public void setFolioPartida(String folioPartida) {
		this.folioPartida = folioPartida;
	}

	public String getMovimientoSucursal() {
		return movimientoSucursal;
	}

	public void setMovimientoSucursal(String movimientoSucursal) {
		this.movimientoSucursal = movimientoSucursal;
	}

	public String getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(String numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getRamoDes() {
		return ramoDes;
	}

	public void setRamoDes(String ramoDes) {
		this.ramoDes = ramoDes;
	}

	public String getSubramoDes() {
		return subramoDes;
	}

	public void setSubramoDes(String subramoDes) {
		this.subramoDes = subramoDes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class MdaVentasVO {\n");
		sb.append("    canal_ingreso: ").append(toIndentedString(canalIngreso)).append("\n");
		sb.append("    credencial: ").append(toIndentedString(credencial)).append("\n");
		sb.append("    edo_prenda: ").append(toIndentedString(edoPrenda)).append("\n");
		sb.append("    factor: ").append(toIndentedString(factor)).append("\n");
		sb.append("    fecha_op: ").append(toIndentedString(fechaOp)).append("\n");
		sb.append("    folio_partida: ").append(toIndentedString(folioPartida)).append("\n");
		sb.append("    movimiento_sucursal: ").append(toIndentedString(movimientoSucursal)).append("\n");
		sb.append("    numero_registro: ").append(toIndentedString(numeroRegistro)).append("\n");
		sb.append("    operacion: ").append(toIndentedString(operacion)).append("\n");
		sb.append("    ramo_des: ").append(toIndentedString(ramoDes)).append("\n");
		sb.append("    subramo_des: ").append(toIndentedString(subramoDes)).append("\n");
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

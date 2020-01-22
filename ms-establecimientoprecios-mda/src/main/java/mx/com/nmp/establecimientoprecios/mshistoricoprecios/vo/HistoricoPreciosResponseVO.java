package mx.com.nmp.establecimientoprecios.mshistoricoprecios.vo;

public class HistoricoPreciosResponseVO {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "HistoricoPreciosResponseDTO [message=" + message + "]";
	}
}

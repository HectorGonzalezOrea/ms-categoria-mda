package mx.com.nmp.establecimientoprecios.exceptions;

public class TablasReferenciaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TablasReferenciaException() {
	}

	public TablasReferenciaException(String message) {
		super(message);
	}

	public TablasReferenciaException(Throwable cause) {
		super(cause);
	}

	public TablasReferenciaException(String message, Throwable cause) {
		super(message, cause);
	}

	public TablasReferenciaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

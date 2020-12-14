package mx.com.nmp.consolidados.excepcion;

public class ConsolidadosException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConsolidadosException(String message) {
        super(message);
    }
}
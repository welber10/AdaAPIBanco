package tech.ada.banco.exception;

public class ContaInexistenteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1822424417730657320L;
	
	public ContaInexistenteException(String message) {
		super(message);
	}

	public ContaInexistenteException(String message, Throwable cause) {
		super(message, cause);
	}

}

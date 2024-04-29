package tech.ada.banco.exception;

public class ValorInvalidoException extends Exception {

	public ValorInvalidoException(String message) {
		super(message);
	}

	public ValorInvalidoException(String message, Throwable cause) {
		super(message, cause);
	}

}

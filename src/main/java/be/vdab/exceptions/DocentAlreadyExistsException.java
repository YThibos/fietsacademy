package be.vdab.exceptions;

public class DocentAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DocentAlreadyExistsException() {
		super();
	}

	public DocentAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public DocentAlreadyExistsException(String message) {
		super(message);
	}

	public DocentAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
}

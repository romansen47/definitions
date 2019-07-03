package exceptions;

public class WrongClassException extends Exception {

	private static final long serialVersionUID = 2040640246012351126L;

	public WrongClassException() {
	}

	public WrongClassException(String message) {
		super(message);
	}

	public WrongClassException(Throwable cause) {
		super(cause);
	}

	public WrongClassException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

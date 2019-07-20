package exceptions;

public class WrongDimensionsExceptions extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongDimensionsExceptions() {
	}

	public WrongDimensionsExceptions(String message) {
		super(message);
	}

	public WrongDimensionsExceptions(Throwable cause) {
		super(cause);
	}

	public WrongDimensionsExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongDimensionsExceptions(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

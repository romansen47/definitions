package exceptions;

public class WronDimensionsExceptions extends Exception {

	public WronDimensionsExceptions() {
	}

	public WronDimensionsExceptions(String message) {
		super(message);
	}

	public WronDimensionsExceptions(Throwable cause) {
		super(cause);
	}

	public WronDimensionsExceptions(String message, Throwable cause) {
		super(message, cause);
	}

	public WronDimensionsExceptions(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

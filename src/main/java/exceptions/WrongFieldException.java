package exceptions;

public class WrongFieldException extends Exception {

	private static final long serialVersionUID = -6590640087738249288L;

	public WrongFieldException() {
	}

	public WrongFieldException(String message) {
		super(message);
	}

	public WrongFieldException(Throwable cause) {
		super(cause);
	}

	public WrongFieldException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

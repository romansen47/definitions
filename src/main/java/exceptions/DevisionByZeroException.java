package exceptions;

/**
 * custom exception for devision by zero
 *
 * @author roman
 */
public class DevisionByZeroException extends Exception {
	private static final long serialVersionUID = 1L;

	public DevisionByZeroException() {
		super("division by zero exception");
	}
}

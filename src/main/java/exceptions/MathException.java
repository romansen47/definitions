/**
 *
 */
package exceptions;

import definitions.settings.XmlPrintable;

/**
 * @author RoManski
 *
 */
public class MathException extends java.lang.Exception implements exceptions.Exception, XmlPrintable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	final private String message;

	/**
	 * Root type for custom exceptions
	 */
	public MathException(final String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toXml() {
		final String ans = "<mathException />";
		return ans;
	}

}

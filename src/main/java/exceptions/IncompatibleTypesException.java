/**
 *
 */
package exceptions;

import definitions.settings.XmlPrintable;

/**
 * @author ro
 *
 */
public class IncompatibleTypesException extends MathException implements XmlPrintable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5603370796834115033L;

	/**
	 * @param message
	 */
	public IncompatibleTypesException(final String message) {
		super(message);
	}

}

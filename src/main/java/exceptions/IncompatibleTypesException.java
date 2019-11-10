/**
 * 
 */
package exceptions;

import definitions.settings.XmlPrintable;

/**
 * @author ro
 *
 */
public class IncompatibleTypesException extends MathException implements XmlPrintable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public IncompatibleTypesException(String message) {
		super(message);
	}

}

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
	 * @param message
	 */
	public IncompatibleTypesException(final String message) {
		super(message);
	}

}

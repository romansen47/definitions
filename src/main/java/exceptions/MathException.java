/**
 * 
 */
package exceptions;

import definitions.settings.XmlPrintable;

/**
 * @author RoManski
 *
 */
public class MathException extends java.lang.Exception implements exceptions.Exception, XmlPrintable{

	final private String message;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  Root type for custom exceptions
	 */
	public MathException(String message) {
		super(message);
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toXml() {
		String ans= "<mathException/>";
		return ans;
	}
	
	

}

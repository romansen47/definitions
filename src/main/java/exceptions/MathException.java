/**
 * 
 */
package exceptions;

import java.lang.Exception;
/**
 * @author RoManski
 *
 */
public class MathException extends Exception implements exceptions.Exception {

	/**
	 *  Root type for custom exceptions
	 */
	public MathException(String message) {
		super(message);
	}

}

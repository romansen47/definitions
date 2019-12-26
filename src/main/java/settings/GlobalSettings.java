/**
 * 
 */
package settings;

/**
 * @author RoManski
 *
 *         Global settings as static attributes of settings class
 */
public class GlobalSettings {

	/**
	 * Logging
	 */
	public static final boolean LOGGING = true;

	/**
	 * Default depth
	 */
	public final static int LOGGING_DEPTH = 5;

	/**
	 * fineness parameter for aequidistant summation for integration
	 */
	public final static double INTEGRAL_FEINHEIT = 1.e-2;

	/**
	 * fineness parameter for differentiation
	 */
	public final static double DERIVATIVE_FEINHEIT = 1.e-3;

	/**
	 * fineness on real line
	 */
	public final static double REAL_EQUALITY_FEINHEIT = 1.e-2;

	/**
	 * pointwise equality
	 */
	public final static int FUNCTION_EQUALITY_FEINHEIT = 100;

	/**
	 * should cached spaces be restored?
	 */
	public static final boolean RESTORE_FROM_CACHED = true;

	/**
	 * path to cached spaces
	 */
	public final static String CACHEDSPACES = "src/main/resources/coordinateSpaces.data";

	/**
	 * path to output
	 */
	public final static String PLOTS = "target/";

}

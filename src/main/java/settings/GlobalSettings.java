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
	 * fineness parameter for aequidistant summation for integration
	 */
	public static final double INTEGRAL_FEINHEIT = 5.e-6;

	/**
	 * fineness parameter for differentiation
	 */
	public static final double DERIVATIVE_FEINHEIT = 1.e-6;

	/**
	 * fineness on real line
	 */
	public static final double REAL_EQUALITY_FEINHEIT = 1.e-7;

	/**
	 * pointwise equality
	 */
	public static final int FUNCTION_EQUALITY_FEINHEIT = 100;

	/**
	 * should cached spaces be restored?
	 */
	public static final boolean RESTORE_FROM_CACHED = false;

	/**
	 * path to cached spaces
	 */
	@Deprecated
	public static final String CACHEDSPACES = "src/main/resources/coordinateSpaces.data";

	/**
	 * path to output
	 */
	public static final String PLOTS = "target/";

}

/**
 * 
 */
package definitions.structures.abstr.impl;

/**
 * @author RoManski
 *
 */
public final class RealOne extends Real {

	private static Real one;

	public static Real getOne() {
		if (one == null) {
			one = new RealOne();
		}
		return one;
	};

	private RealOne() {
		super(1.);
	}

}
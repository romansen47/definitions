/**
 * 
 */
package definitions.structures.abstr.mappings.impl;

import definitions.structures.abstr.fields.scalars.impl.Real;

/**
 * @author RoManski
 *
 */
public final class RealOne extends Real {

	private static final long serialVersionUID = 1L;

	private static Real one;

	public static Real getOne() {
		if (one == null) {
			one = new RealOne();
		}
		return one;
	}

	private RealOne() {
		super(1.);
	}

}

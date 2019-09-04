/**
 * 
 */
package definitions.structures.abstr.mappings.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

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

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> ans = new HashMap<>();
		ans.put(this, this);
		return ans;
	}

}

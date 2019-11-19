/**
 * 
 */
package definitions.structures.abstr.fields.scalars.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * @author RoManski
 *
 */
@Component
public class RealOne extends Real {

	private static final long serialVersionUID = 1L;

	private static Real one;

	public static Real getOne() {
		if (one == null) {
			one = new RealOne();
		}
		return one;
	}

	public RealOne() {
		super();
		setValue(1d);
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> ans = new HashMap<>();
		ans.put(this, this);
		return ans;
	}

}

/**
 *
 */
package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
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
		if (RealOne.one == null) {
			RealOne.one = new RealOne();
		}
		return RealOne.one;
	}

	public RealOne() {
		super();
		this.setRepresentant(1d);
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> ans = new ConcurrentHashMap<>();
		ans.put(this, this);
		return ans;
	}

}

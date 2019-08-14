package definitions.structures.abstr.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public final class RealZero extends Real {

	private static Real zero;

	public static Real getZero() {
		if (zero == null) {
			zero = new RealZero();
		}
		return zero;
	}

	private RealZero() {
		super(0.);
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> ans = new HashMap<>();
		ans.put(RealLine.getInstance().getOne(), this);
		return ans;

	}

}

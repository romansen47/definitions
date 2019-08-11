package definitions.structures.abstr.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;

public final class RealZero extends Real {

	private static Real zero;

	public static Real getZero() {
		if (zero == null) {
			zero = new RealZero();
		}
		return zero;
	};

	private RealZero() {
		super(0.);
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		Map<Vector, Scalar> ans = new HashMap<>();
		ans.put(RealLine.getInstance().getOne(), this);
		return ans;

	}

}

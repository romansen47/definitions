package definitions.structures.abstr.fields.scalars.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

@Component
public class RealZero extends Real {

	private static final long serialVersionUID = 4784581571935827482L;
	private static Real zero;

	public static Real getZero() {
		if (zero == null) {
			zero = new RealZero();
		}
		return zero;
	}

	public RealZero() {
		super();
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> ans = new HashMap<>();
		ans.put(RealLine.getInstance().getOne(), this);
		return ans;
	}

}

package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;

@Component
public class RealZero extends Real {

	private static final long serialVersionUID = 4784581571935827482L;

	public RealZero() {
		super();
		this.setRepresentant(0d);
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		final Map<Vector, Scalar> ans = new ConcurrentHashMap<>();
		ans.put(Generator.getInstance().getFieldGenerator().getRealLine().getOne(), this);
		return ans;
	}

}

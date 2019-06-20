package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.Map;

import definitions.structures.abstr.LinearMapping;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public class FunctionTuple extends Tuple implements Function {

	private LinearMapping derivativeProjection = null;

	public FunctionTuple(final Map<Vector, Double> coordinates) throws Throwable {
		super(coordinates);
	}

	public FunctionTuple(final double[] coordinates) throws Throwable {
		super(coordinates);
	}

	@Override
	public double value(final double input) throws Throwable {
		double ans = 0;
		for (final Vector fun : this.getCoordinates().keySet()) {
			ans += ((Function) fun).value(input) * this.getCoordinates().get(fun);
		}
		return ans;
	}

	public LinearMapping getDerivative(VectorSpace space) throws Throwable {
		return new LinearMapping(space, space) {
			@Override
			public Vector get(Vector vec2) throws Throwable {
				return ((Function)vec2).getDerivative();
			}
			@Override
			public Map<Vector, Double> getLinearity(Vector vec1) {
				return null;
			}
		};
	}
}

package definitions.structures.finitedimensional.vectors.impl;

import java.util.Map;

import definitions.structures.abstr.LinearMapping;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public class FunctionTuple extends Tuple implements Function {

	public FunctionTuple(final Map<Vector, Double> coordinates) {
		super(coordinates);
	}

	public FunctionTuple(final double[] coordinates) {
		super(coordinates);
	}

	@Override
	public double value(final double input) {
		double ans = 0;
		for (final Vector fun : this.getCoordinates().keySet()) {
			ans += ((Function) fun).value(input) * this.getCoordinates().get(fun);
		}
		return ans;
	}

	public LinearMapping getDerivative(VectorSpace space) {
		return new LinearMapping(space, space) {
			@Override
			public Vector get(Vector vec2) {
				return ((Function) vec2).getDerivative();
			}

			@Override
			public Map<Vector, Double> getLinearity(Vector vec1) {
				return null;
			}
		};
	}

	@Override
	public Function getProjection(EuclideanSpace source) {
		return Function.super.getProjection(source);
	}
}

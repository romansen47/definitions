package definitions.structures.euclidean.vectors.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.impl.LinearMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class FunctionTuple extends Tuple implements Function {

	Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = new HashMap<>();

	public FunctionTuple(final Map<Vector, Scalar> coordinates, EuclideanSpace space) {
		super(coordinates);
		this.coordinatesMap.put(space, coordinates);
	}

	public FunctionTuple(final Scalar[] coordinates) {
		super(coordinates);
	}

	@Override
	public Scalar value(final Scalar input) {
		double ans = 0;
		for (final Vector fun : this.getCoordinates().keySet()) {
			ans += ((Function) fun).value(input).getValue() * this.getCoordinates().get(fun).getValue();
		}
		return new Real(ans);
	}

	public LinearMapping getDerivative(VectorSpace space) {
		return new FiniteDimensionalLinearMapping((EuclideanFunctionSpace) space, (EuclideanFunctionSpace) space) {
			@Override
			public Vector get(Vector vec2) {
				return ((Function) vec2).getDerivative();
			}

			@Override
			public Map<Vector, Scalar> getLinearity(Vector vec1) {
				return null;
			}
		};
	}

	@Override
	public Function getProjection(EuclideanSpace source) {
		return Function.super.getProjection(source);
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
		this.coordinatesMap.put(space, ((Function) coordinates).getCoordinates(space));
	}

	@Override
	public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
		return this.coordinatesMap;
	}

}

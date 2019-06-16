package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public class FunctionTuple extends Tuple implements Function {

	public FunctionTuple(final Map<Vector, Double> coordinates) throws Throwable {
		super(coordinates);
	}

	public FunctionTuple(final double[] coordinates) throws Throwable {
		super(coordinates);
	}

//	@Override
//	public Map<Vector, Double> getCoordinates(EuclideanSpace space) throws Throwable {
//		Map<Vector, Double> newCoordinates = new ConcurrentHashMap<>();
//		for (Vector baseVec : space.genericBaseToList()) {
//			newCoordinates.put(baseVec, space.product(this, baseVec));
//		}
//		return newCoordinates;
//	}

	@Override
	public double value(final double input) throws Throwable {
		double ans = 0;
		for (final Vector fun : this.getCoordinates().keySet()) {
			ans += ((Function) fun).value(input) * this.getCoordinates().get(fun);
		}
		return ans;
	}
}

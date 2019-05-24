package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace
		implements IFiniteDimensionalFunctionSpace {

	protected double[] intervall;
	protected final double eps = 1.e-5;

	@Override
	public double product(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof Function && vec2 instanceof Function) {
			return 1/(2*Math.PI)*getIntegral((Function) vec1, (Function) vec2);
		}
		throw new Throwable();
	}

	public FiniteDimensionalFunctionSpace(List<Vector> genericBase, double left, double right) throws Throwable {
		super(genericBase);
		for (Vector vec : genericBase) {
			Map<Vector, Double> tmpCoord = new HashMap<>();
			for (Vector otherVec : genericBase) {
				if (vec == otherVec) {
					tmpCoord.put(otherVec, 1.0);
				} else {
					tmpCoord.put(otherVec, 0.0);
				}
			}
			((Tuple) vec).setCoordinates(tmpCoord);
		}
		intervall = new double[2];
		intervall[0] = left;
		intervall[1] = right;
	}

	protected FiniteDimensionalFunctionSpace() throws Throwable {
	}

	@Override
	public double[] getIntervall() {
		return intervall;
	}

	@Override
	public double getEpsilon() {
		return eps;
	}

	@Override
	public Function stretch(Function vec, double r) throws Throwable {
		if (vec.getDim() == dim()) {
			final Map<Vector, Double> coordinates = getCoordinates(vec);
			final Map<Vector, Double> stretched = new HashMap<>();
			for (final Vector vec1 : getBase()) {
				stretched.put(vec1, coordinates.get(getBaseVec(vec1)) * r);
			}
			return new FunctionTuple(stretched);
		}
		throw new Throwable();
	}
}

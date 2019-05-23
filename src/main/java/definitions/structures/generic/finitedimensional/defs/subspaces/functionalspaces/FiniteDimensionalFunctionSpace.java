package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Tuple;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace
		implements IFiniteDimensionalFunctionSpace {

	protected double[] intervall;
	final protected double eps = 1.e-5;
	
	@Override
	public double product(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof Function && vec2 instanceof Function) {
			return getIntegral((Function)vec1,(Function)vec2);
		}
		throw new Throwable();
	}
	
	public FiniteDimensionalFunctionSpace(List<FiniteVector> list, double left, double right) throws Throwable {
		super(list);
		for (FiniteVector vec : list) {
			Map<FiniteVector, Double> tmpCoord = new HashMap<>();
			for (FiniteVector otherVec : list) {
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


	protected FiniteDimensionalFunctionSpace() throws Throwable{
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
			final Map<FiniteVector, Double> coordinates = getCoordinates(vec);
			final Map<FiniteVector, Double> stretched = new HashMap<>();
			for (final FiniteVector vec1 : getBase()) {
				stretched.put(vec1, coordinates.get(getBaseVec(vec1)) * r);
			}
			return new FunctionTuple(stretched);
		}
		throw new Throwable();
	}
}

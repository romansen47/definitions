package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.spaces.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace
		implements IFiniteDimensionalFunctionSpace {

	final double[] intervall;
	final double eps = 1.e-5;

	public FiniteDimensionalFunctionSpace(List<IFiniteVector> list, double left, double right) throws Throwable {
		super(list);
		List<IFiniteVector> newBase = new ArrayList<>();
		for (IFiniteVector vec : list) {
			Map<IFiniteVector, Double> tmpCoord = new HashMap<>();
			for (IFiniteVector otherVec : list) {
				if (vec == otherVec) {
					tmpCoord.put(otherVec, 1.0);
				} else {
					tmpCoord.put(otherVec, 0.0);
				}
			}
			((FiniteVector)vec).setCoordinates(tmpCoord);
		}
		intervall = new double[2];
		intervall[0] = left;
		intervall[1] = right;
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
	public IFunction stretch(IFunction vec, double r) throws Throwable {
		if (vec.getDim() == dim()) {
			final Map<IFiniteVector, Double> coordinates = getCoordinates(vec);
			final Map<IFiniteVector, Double> stretched = new HashMap<>();
//			final Set<IFiniteVector> base = getGenericBase();
			for (final IFiniteVector vec1 : getBase()) {
				stretched.put(vec1, coordinates.get(getBaseVec(vec1)) * r);
			}
			return new Function(stretched);
		}
		throw new Throwable();
	}

}

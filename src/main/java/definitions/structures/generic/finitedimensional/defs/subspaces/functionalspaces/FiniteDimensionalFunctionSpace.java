package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.spaces.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class FiniteDimensionalFunctionSpace extends FiniteDimensionalVectorSpace
		implements IFiniteDimensionalFunctionSpace {

	final double[] intervall;
	final double eps = 1.e-5;

	public FiniteDimensionalFunctionSpace(List<IFiniteVector> list, double left, double right) throws Throwable {
		super(list);
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
	public IVector stretch(IFiniteVector vec, double r) throws Throwable {
		if (vec.getDim() == dim()) {
			final Map<IFiniteVector, Double> coordinates = vec.getCoordinates();
			final Map<IFiniteVector, Double> stretched = new HashMap<>();
			final List<IFiniteVector> base = genericBaseToList();
			for (final IFiniteVector vec1 : base) {
				stretched.put(vec1, coordinates.get(getBaseVec(vec1)) * r);
			}
			return new Function(stretched);
		}
		throw new Throwable();
	}

}

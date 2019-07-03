package definitions.structures.generic.finitedimensional.defs.vectors;

import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public interface FiniteVector extends Vector {

	@Override
	Map<Vector, Double> getCoordinates();

	@Override
	default double[] getGenericCoordinates() {
		final double[] vector = new double[getDim()];
		int i = 0;
		for (final Vector basevec : getGenericBase()) {
			vector[i] = getCoordinates().get(basevec);
			i++;
		}
		return vector;
	}

	Set<Vector> getGenericBase();

	Map<Vector, Double> getCoordinates(EuclideanSpace source);

	default Function getProjection(EuclideanSpace source) {
		return new FunctionTuple(getCoordinates(source));
	}

}

package definitions.structures.generic.finitedimensional.defs.vectors;

import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public interface FiniteVector extends Vector {

	@Override
	Map<Vector, Double> getCoordinates();

	@Override
	default double[] getGenericCoordinates() throws Throwable {
		final double[] vector = new double[getDim()];
		int i = 0;
		for (final Vector basevec : getGenericBase()) {
			vector[i] = getCoordinates().get(basevec);
			i++;
		}
		return vector;
	}

	Set<Vector> getGenericBase() throws Throwable;

	Map<Vector, Double> getCoordinates(EuclideanSpace source) throws Throwable;

}

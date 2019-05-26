package definitions.structures.abstr;

import java.util.Map;

import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public interface Vector {

	int getDim();

	boolean elementOf(VectorSpace space) throws Throwable;

	boolean equals(Vector vec) throws Throwable;

	Map<Vector, Double> getCoordinates();

	double[] getGenericCoordinates() throws Throwable;

}

package definitions.structures.abstr;

import java.util.Map;

public interface Vector {

	int getDim();

	boolean elementOf(VectorSpace space) throws Throwable;

	boolean equals(Vector vec) throws Throwable;

	Map<Vector, Double> getCoordinates();

	double[] getGenericCoordinates() throws Throwable;

	@Override
	String toString();

}

package definitions.structures.abstr;

import java.util.Map;

public interface Vector {

	int getDim();

	boolean elementOf(VectorSpace space);

	boolean equals(Vector vec);

	Map<Vector, Double> getCoordinates();

	double[] getGenericCoordinates();

	@Override
	String toString();

	void setCoordinates(Map<Vector, Double> coordinates);

}

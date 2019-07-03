package definitions.structures.abstr;

import java.util.Map;

public interface Homomorphism {

	Vector get(Vector vec2);

	Map<Vector, Double> getLinearity(Vector vec1);

	Map<Vector, Map<Vector, Double>> getLinearity();

	VectorSpace getTarget();

	VectorSpace getSource();

}

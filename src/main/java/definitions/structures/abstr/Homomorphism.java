package definitions.structures.abstr;

import java.util.Map;

public interface Homomorphism {

	Vector get(Vector vec2) throws Throwable;

	Map<Vector, Double> getLinearity(Vector vec1) throws Throwable;

	Map<Vector, Map<Vector, Double>> getLinearity() throws Throwable;

	VectorSpace getTarget();

	VectorSpace getSource();

}

/**
 * 
 */
package definitions.structures.abstr.vectorspaces;

import java.util.Map;

import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public interface EuclideanAlgebra extends Algebra, EuclideanSpace {

	@Override
	default Vector product(Vector vec1, Vector vec2) {
		Vector ans = nullVec();
		if (vec1 == ans || vec2 == ans) {
			return ans;
		}
		for (final Vector vec : genericBaseToList()) {
			ans = add(ans, stretch(getMultiplicationMatrix().get(vec).get(vec2), vec1.getCoordinates().get(vec)));
		}
		return ans;
	}

	Map<Vector, Homomorphism> getMultiplicationMatrix();

	void setMultiplicationMatrix(Map<Vector, Homomorphism> multiplicationMatrix);

}

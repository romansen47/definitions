/**
 * 
 */
package definitions.structures.abstr;

import java.util.Map;

import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public interface EuclideanAlgebra extends Algebra, EuclideanSpace {

	@Override
	default Vector product(Vector vec1, Vector vec2) {
		Vector ans=nullVec();
		for (Vector vec:genericBaseToList()) {
			ans=add(ans,stretch(getMultiplicationMatrix().get(vec).get(vec2),vec1.getCoordinates().get(vec)));
		}
		return ans;
	}

	Map<Vector,Homomorphism> getMultiplicationMatrix();

	void setMultiplicationMatrix(Map<Vector,Homomorphism> multiplicationMatrix);

}

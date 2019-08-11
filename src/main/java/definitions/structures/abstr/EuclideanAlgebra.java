/**
 * 
 */
package definitions.structures.abstr;

import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public interface EuclideanAlgebra extends Algebra, EuclideanSpace {

	@Override
	default Vector product(Vector vec1, Vector vec2) {
		Vector newVec2 = getMultiplicationMatrix().get(vec2);
		return EuclideanSpace.super.innerProduct(vec1, newVec2);
	}

	Homomorphism getMultiplicationMatrix();

	void setMultiplicationMatrix(Homomorphism multiplicationMatrix);

}

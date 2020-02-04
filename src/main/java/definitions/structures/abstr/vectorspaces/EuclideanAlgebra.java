/**
 * 
 */
package definitions.structures.abstr.vectorspaces;

import java.util.Map;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public interface EuclideanAlgebra extends Algebra, EuclideanSpace {

	/**
	 * In case of a finitely dimensional vector space over the field, multiplication
	 * within the algebra can be described as a set of linear mappings.
	 * 
	 * In detail, we need to explain, how base elements are multiplied.
	 * 
	 * @return the multiplication map - for every base vector vec1 it gives a linear
	 *         mapping Vector vec2 - getField().product(vec1,vec2)
	 * 
	 */
	Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix();

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Vector product(final Vector vec1, final Vector vec2) {
		Vector ans = Algebra.super.product(vec1,vec2);
		if (ans!=null) {
			return ans;
		}
		else {
			ans=nullVec();
		}
		VectorSpaceHomomorphism tmp;
		for (final Vector vec : this.genericBaseToList()) {
			Vector vector=getBaseVec(vec);
			tmp=this.getMultiplicationMatrix().get(vector);
			Vector first=tmp.get(vec2);
			Scalar factor=((FiniteVectorMethods) vec1).getCoordinates().get(vector);
			Vector stretched=this.stretch(first,factor);
			ans = this.addition(ans,stretched);
		}
		return ans;
	}

	/**
	 * Setter for the multiplication map
	 * 
	 * @param multiplicationMatrix the input map
	 */
	void setMultiplicationMatrix(Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix);

}

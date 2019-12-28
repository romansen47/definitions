/**
 * 
 */
package definitions.structures.abstr.vectorspaces;

import java.util.Map;

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
	 * In case of being finitely dimensional as a vecor space over the field,
	 * Multiplication wihin the algebra can be discribed as a set of linear
	 * mappings.
	 * 
	 * In detail, we nned to explain, how base elements are multiplied.
	 * 
	 * @return the multiplication map - for every base vector vec1 it gives a linear
	 *         mapping Vector vec2 -> getField().product(vec1,vec2)
	 * 
	 */
	Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix();

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Vector product(final Vector vec1, final Vector vec2) {
		Vector ans = this.nullVec();
		if (vec1 == ans || vec2 == ans) {
			return ans;
		}
		for (final Vector vec : this.genericBaseToList()) {
			ans = this.add(ans, this.stretch(this.getMultiplicationMatrix().get(vec).get(vec2),
					((FiniteVectorMethods) vec1).getCoordinates().get(vec)));
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

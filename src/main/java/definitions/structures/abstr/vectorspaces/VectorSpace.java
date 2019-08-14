package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.Group;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * 
 * @author RoManski
 * 
 *         We consider real vector spaces. A vector space is a non-empty
 *         collection of 'things', which can be added and streched.
 */
public interface VectorSpace extends Group,VectorSpaceTechnicalProvider {

	Field getField();

	/**
	 * Addition of vectors.
	 * 
	 * @param vec1 summand a.
	 * @param vec2 summand b.
	 * @return the addition of a and b.
	 */
	Vector add(Vector vec1, Vector vec2);

	/**
	 * Scalar Multiplication by real numbers.
	 * 
	 * @param vec1 the vector to stretch.
	 * @param r    the factor.
	 * @return the stretched vector.
	 */
	Vector stretch(Vector vec1, Scalar r);
	
	default Integer getOrder() {
		return null;
	};

	@Override
	default GroupElement operation(GroupElement first, GroupElement second) {
		return (GroupElement) add((Vector)first,(Vector)second);
	}

}

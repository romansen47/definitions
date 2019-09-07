package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.Group;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * 
 * @author RoManski
 * 
 *         We consider real vector spaces. A vector space is a non-empty
 *         collection of 'things', which can be added and streched.
 */
public interface VectorSpace extends Group {

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
	 * @throws WrongFieldException
	 */
	Vector stretch(Vector vec1, Scalar r);

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Integer getOrder() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default GroupElement operation(GroupElement first, GroupElement second) {
		return add((Vector) first, (Vector) second);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default MonoidElement getIdentityElement() {
		return ((VectorSpaceMethods) this).nullVec();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default GroupElement getInverseElement(GroupElement element) {
		final Field field = getField();
		return stretch((Vector) element, (Scalar) field.getInverseElement(field.getOne()));
	}
}

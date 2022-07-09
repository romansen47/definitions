package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * {@link https://en.wikipedia.org/wiki/Vector_space}
 *
 * We consider real vector spaces. A F-vectorspace V is a group V and a field F
 * operating on it. There exist laws for this operation. In detail we have
 * 
 * (r+s)v=rv+sv, r(sv)=(rs)v, r(u+v)=ru+rv.
 * 
 * Checks for these laws will be neglegted for the moment.
 * 
 * @author RoManski
 */

public interface VectorSpace extends Group {

	/**
	 * Addition of vectors.
	 *
	 * @param vec1 summand a.
	 * @param vec2 summand b.
	 * @return the addition of a and b.
	 */
	default Vector addition(Vector vec1, Vector vec2) {
		return (Vector) Group.super.operation(vec1, vec2);
	}

	/**
	 * vector spaces are defined over fields.
	 *
	 * @return the field
	 */
	Field getField();

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Vector getInverseElement(final Element element) {
		if (element.equals(getNeutralElement())) {
			return (Vector) element;
		}
		final Field field = getField();
		return stretch((Vector) element, (Scalar) field.getInverseElement(field.getOne()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Element getNeutralElement() {
		return ((VectorSpaceMethods) this).nullVec();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Integer getOrder() {
		return null;
	}

	/**
	 * Scalar Multiplication by real numbers.
	 *
	 * @param vec1 the vector to stretch.
	 * @param r    the factor.
	 * @return the stretched vector.
	 */
	default Vector stretch(Vector vec1, Scalar r) {
		final FieldElement zero = getField().getNeutralElement();
		final FieldElement one = getField().getOne();
		if (r.equals(zero)) {
			return (Vector) getNeutralElement();
		}
		if (r.equals(one)) {
			return vec1;
		}
		return null;
	}
}

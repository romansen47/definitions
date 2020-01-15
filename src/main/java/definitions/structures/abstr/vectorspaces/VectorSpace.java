package definitions.structures.abstr.vectorspaces;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.Group; 
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * 
 * @author RoManski
 * 
 *         We consider real vector spaces. A vector space is a non-empty
 *         collection of 'things', which can be added and streched.
 */
public interface VectorSpace extends Group, XmlPrintable {

	/**
	 * Addition of vectors.
	 * 
	 * @param vec1 summand a.
	 * @param vec2 summand b.
	 * @return the addition of a and b.
	 */
	Vector add(Vector vec1, Vector vec2);

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
		final Field field = this.getField();
		return this.stretch((Vector) element, (Scalar) field.getInverseElement(field.getOne()));
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
	 * {@inheritDoc}
	 */
	@Override
	default Element operation(final Element first, final Element second) {
		return this.add((Vector) first, (Vector) second);
	}

	/**
	 * Scalar Multiplication by real numbers.
	 * 
	 * @param vec1 the vector to stretch.
	 * @param r    the factor.
	 * @return the stretched vector.
	 */
	Vector stretch(Vector vec1, Scalar r);
}

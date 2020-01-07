package definitions.structures.abstr.vectorspaces.vectors;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.VectorSpace;

/**
 * Vector interface.
 * 
 * @author ro
 *
 */
public interface Vector extends Element, XmlPrintable {

	/**
	 * Method to verify that the vector is contained by a specific vector space.
	 * 
	 * @param space the space
	 * @return true if is element of space
	 */
	default boolean elementOf(final VectorSpace space) {
		return true;
	}

	/**
	 * Method to verify, the vector is "essentially" the same as another.
	 * 
	 * @param vec the vector to check equality for.
	 * @return true if vector is essentially the same as this.
	 */
	@Override
	boolean equals(Object vec);

	/**
	 * Method to get the dimension of the underlying vector space.
	 * 
	 * @return the dimension of the underlying vector space, if finite dimensional.
	 *         Otherwise null.
	 */
	default Integer getDim() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default String toXml() {
		final String ans = "<" + getClass().toString().split("class ")[1] + "/>";
		return ans;
	}

}

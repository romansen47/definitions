package definitions.structures.abstr.vectorspaces.vectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.vectorspaces.VectorSpace;

/**
 * Vector interface.
 * 
 * @author ro
 *
 */
public interface Vector extends GroupElement {

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
	 * Method to verify that the vector is contained by a specific vector space.
	 * 
	 * @param space
	 * @return
	 */
	default boolean elementOf(VectorSpace space) {
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

}

package definitions.structures.abstr.vectorspaces.vectors;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import settings.annotations.Proceed;

/**
 * Vector interface.
 * 
 * @author ro
 *
 */
public interface Vector extends GroupElement,XmlPrintable {

	/**
	 * Method to get the dimension of the underlying vector space.
	 * 
	 * @return the dimension of the underlying vector space, if finite dimensional.
	 *         Otherwise null.
	 */
	@Proceed
	default Integer getDim() {
		return null;
	}

	/**
	 * Method to verify that the vector is contained by a specific vector space.
	 * 
	 * @param space
	 * @return
	 */
	@Proceed
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
	@Proceed
	boolean equals(Object vec);

	@Override
	@Proceed
	default String toXml(){
		String ans="<"+getClass().toString().split("class ")[1]+"/>";
		return ans;
	}
	
}

package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.SemiGroup;

public interface OrderedSemiGroup extends SemiGroup {

	/**
	 * Method to compare two elements.
	 * 
	 * @param smallerOne the assumed "smaller" element.
	 * @param biggerOne  the assumed "bigger" element.
	 * @return returns whether biggerOne is bigger or equals smallerOne.
	 */
	boolean isHigher(Element smallerOne, Element biggerOne);
	
}

package definitions.structures.abstr.algebra.semigroups;

public interface DiscreetSemiGroup extends SemiGroup {
	
	/**
	 * In a discreet monoid we have countably many elements and can define a getter
	 * for integers.
	 * 
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	Element get(Integer representant);

	default Integer getRepresentant(Element first) {
		return first.getRepresentant();
	};
}

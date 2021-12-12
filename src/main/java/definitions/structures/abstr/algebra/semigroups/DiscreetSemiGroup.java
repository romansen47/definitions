package definitions.structures.abstr.algebra.semigroups;

public interface DiscreetSemiGroup extends SemiGroup {

	/**
	 * In a discreet monoid we have countably many elements and can define a getter
	 * for integers.
	 *
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	Element get(Double representant);

	/**
	 * Method to use associated double values, if present
	 *
	 * @param first the element
	 * @return the corresponding Double value
	 *
	 * @deprecated Deprecated method - should be avoided. If You use this You don't
	 *             need the whole project anymore.
	 */
	// default Double getRepresentant(Element first) {
	// return first.getRepresentant();
	// };
}

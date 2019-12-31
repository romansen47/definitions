package definitions.structures.abstr.algebra.monoids;

public interface DiscreetMonoid extends Monoid {

	/**
	 * In a discreet monoid we have countably many elements and can define a getter for integers.
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	MonoidElement get(Integer representant);

}

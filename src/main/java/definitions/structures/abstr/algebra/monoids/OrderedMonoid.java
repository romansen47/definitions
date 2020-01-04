package definitions.structures.abstr.algebra.monoids;

public interface OrderedMonoid extends Monoid {

	/**
	 * Method to compare two elements.
	 * 
	 * @param smallerOne the assumed "smaller" element.
	 * @param biggerOne  the assumed "bigger" element.
	 * @return returns whether biggerOne is bigger or equals smallerOne.
	 */
	boolean isHigher(MonoidElement smallerOne, MonoidElement biggerOne);

}

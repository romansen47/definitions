package definitions.structures.abstr.algebra.semigroups;

import java.util.Map;

import definitions.structures.euclidean.Generator;

public interface FiniteSemiGroup extends DiscreetSemiGroup {

	/**
	 *
	 * Method to obtain a map to the elements.
	 *
	 * @return the map.
	 */
	default Map<Double, Element> getElements() {
		return null;
	}

	@Override
	default Integer getOrder() {
		return getElements().size();
	}

	/**
	 * Method to obtain the matrix of multiplication.
	 *
	 * @return the multiplication matrix.
	 */
	default Map<Element, Map<Element, Element>> getOperationMap() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
//	@Override
//	Element operation(final Element first, final Element second);

	/**
	 * method to present the monoid.
	 */
	default void print() {
		Generator.getInstance().getLogger().info("Operation matrix:\r");
		String ans = "  operation    ";
		for (double i = 0; i < getOperationMap().keySet().size(); i++) {
			final Element element1 = get(i);
			ans += element1 + "  ";
		}
		System.out.println(ans + "\r");
		for (double i = 0; i < getOperationMap().keySet().size(); i++) {
			final Element element1 = get(i);
			ans = element1 + "   ";
			for (double j = 0; j < getOperationMap().keySet().size(); j++) {
				final Element element2 = get(j);
				ans += " " + getOperationMap().get(element1).get(element2) + " ";
			}
			System.out.println(ans);
		}
	}

}

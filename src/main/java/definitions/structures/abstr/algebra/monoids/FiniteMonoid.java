package definitions.structures.abstr.algebra.monoids;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.euclidean.Generator;

public interface FiniteMonoid extends DiscreetMonoid {

	Map<MonoidElement, Map<MonoidElement, MonoidElement>> operationMap = new HashMap<>();
	Map<Integer, MonoidElement> elements = new HashMap<>();

	/**
	 * 
	 * Method to obtain a map to the elements.
	 * 
	 * @return the map.
	 */
	default Map<Integer, MonoidElement> getElements() {
		return elements;
	}

	/**
	 * Method to obtain the matrix of multiplication.
	 * 
	 * @return the multiplication matrix.
	 */
	default Map<MonoidElement, Map<MonoidElement, MonoidElement>> getOperationMap() {
		return operationMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default MonoidElement operation(final MonoidElement first, final MonoidElement second) {
		final Map<MonoidElement, MonoidElement> tmpMap = this.getOperationMap().get(first);
		if (tmpMap == null) {
			return null;
		}
		return tmpMap.get(second);
	}

	/**
	 * method to present the monoid.
	 */
	default void print() {
		Generator.getInstance().getLogger().info("Operation matrix:\r");
		String ans = "  operation    ";
		for (int i = 0; i < this.getOperationMap().keySet().size(); i++) {
			final MonoidElement element1 = this.get(i);
			ans += element1 + "  ";
		}
		System.out.println(ans + "\r");
		for (int i = 0; i < this.getOperationMap().keySet().size(); i++) {
			final MonoidElement element1 = this.get(i);
			ans = element1 + "   ";
			for (int j = 0; j < this.getOperationMap().keySet().size(); j++) {
				final MonoidElement element2 = this.get(j);
				ans += " " + this.getOperationMap().get(element1).get(element2) + " ";
			}
			System.out.println(ans);
		}
	}
}

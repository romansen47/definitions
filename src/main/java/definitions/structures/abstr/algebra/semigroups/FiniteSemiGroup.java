package definitions.structures.abstr.algebra.semigroups;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.euclidean.Generator;

public interface FiniteSemiGroup extends DiscreetSemiGroup{

	Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> operationMap = new HashMap<>();
//	Map<Integer, FiniteSemiGroupElement> elements = new HashMap<>();

	/**
	 * 
	 * Method to obtain a map to the elements.
	 * 
	 * @return the map.
	 */
	Map<Integer, FiniteSemiGroupElement> getElements();

	/**
	 * Method to obtain the matrix of multiplication.
	 * 
	 * @return the multiplication matrix.
	 */
	default Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> getOperationMap() {
		return operationMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	default FiniteSemiGroupElement operation(final SemiGroupElement first, final SemiGroupElement second) {
		final Map<SemiGroupElement, SemiGroupElement> tmpMap = this.getOperationMap().get(first); 
		if (tmpMap == null) {
			return null;
		}
		return (FiniteSemiGroupElement) tmpMap.get(second);
	}

	/**
	 * method to present the monoid.
	 */
	default void print() {
		Generator.getInstance().getLogger().info("Operation matrix:\r");
		String ans = "  operation    ";
		for (int i = 0; i < this.getOperationMap().keySet().size(); i++) {
			final SemiGroupElement element1 = this.get(i);
			ans += element1 + "  ";
		}
		System.out.println(ans + "\r");
		for (int i = 0; i < this.getOperationMap().keySet().size(); i++) {
			final SemiGroupElement element1 = this.get(i);
			ans = element1 + "   ";
			for (int j = 0; j < this.getOperationMap().keySet().size(); j++) {
				final SemiGroupElement element2 = this.get(j);
				ans += " " + this.getOperationMap().get(element1).get(element2) + " ";
			}
			System.out.println(ans);
		}
	}
	
}

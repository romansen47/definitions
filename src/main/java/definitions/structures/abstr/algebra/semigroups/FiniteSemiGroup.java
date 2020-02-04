package definitions.structures.abstr.algebra.semigroups;

import java.util.Map;

import definitions.structures.euclidean.Generator;

public interface FiniteSemiGroup extends DiscreetSemiGroup{

	/**
	 * 
	 * Method to obtain a map to the elements.
	 * 
	 * @return the map.
	 */
	default Map<Double, Element> getElements(){
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
	default Map<Element, Map<Element, Element>> getOperationMap(){
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
		for (double i = 0; i < this.getOperationMap().keySet().size(); i++) {
			final Element element1 = this.get(i);
			ans += element1 + "  ";
		}
		System.out.println(ans + "\r");
		for (double i = 0; i < this.getOperationMap().keySet().size(); i++) {
			final Element element1 = this.get(i);
			ans = element1 + "   ";
			for (double j = 0; j < this.getOperationMap().keySet().size(); j++) {
				final Element element2 = this.get(j);
				ans += " " + this.getOperationMap().get(element1).get(element2) + " ";
			}
			System.out.println(ans);
		}
	}
	
}

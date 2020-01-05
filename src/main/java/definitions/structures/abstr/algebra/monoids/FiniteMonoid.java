package definitions.structures.abstr.algebra.monoids;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroup;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroupElement;
import definitions.structures.abstr.algebra.semigroups.SemiGroupElement;
import definitions.structures.euclidean.Generator;

public interface FiniteMonoid extends Monoid, FiniteSemiGroup {

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
	Map<SemiGroupElement, Map<SemiGroupElement, SemiGroupElement>> getOperationMap();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FiniteMonoidElement operation(final SemiGroupElement first, final SemiGroupElement second);

	/**
	 * Getter for the identity element
	 * 
	 * @return the neutral element of the semi group
	 */
	FiniteMonoidElement getNeutralElement();
	
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

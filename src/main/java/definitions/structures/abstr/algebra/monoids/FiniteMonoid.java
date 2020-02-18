package definitions.structures.abstr.algebra.monoids;

import java.util.Map;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.FiniteSemiGroup;
import definitions.structures.euclidean.Generator;

public interface FiniteMonoid extends DiscreetMonoid, FiniteSemiGroup {

	
	
//	/**
//	 * method to present the monoid.
//	 */
//	default void print() {
//		Generator.getInstance().getLogger().info("Operation matrix:\r");
//		String ans = "  operation    ";
//		for (double i = 0; i < this.getOperationMap().keySet().size(); i++) {
//			final Element element1 = this.get(i);
//			ans += element1 + "  ";
//		}
//		System.out.println(ans + "\r");
//		for (double i = 0; i < this.getOperationMap().keySet().size(); i++) {
//			final Element element1 = this.get(i);
//			ans = element1 + "   ";
//			for (double j = 0; j < this.getOperationMap().keySet().size(); j++) {
//				final Element element2 = this.get(j);
//				ans += " " + this.getOperationMap().get(element1).get(element2) + " ";
//			}
//			System.out.println(ans);
//		}
//	}
 
}

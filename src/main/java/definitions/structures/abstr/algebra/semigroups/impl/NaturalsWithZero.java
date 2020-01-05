package definitions.structures.abstr.algebra.semigroups.impl;

import definitions.structures.abstr.algebra.groups.DiscreetMonoidElement;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.OrderedMonoid; 

public class NaturalsWithZero extends Naturals implements OrderedMonoid,DiscreetMonoid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4841497154221616818L;

	@Override
	public DiscreetMonoidElement getNeutralElement() {
		return get(0);
	}
	

	private static OrderedMonoid instanceWithZero;

	public static OrderedMonoid getInstance() {
		if (instanceWithZero == null) {
			instanceWithZero = new NaturalsWithZero();
		}
		return instanceWithZero;
	}

}

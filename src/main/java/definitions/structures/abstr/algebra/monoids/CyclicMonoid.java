package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.groups.GroupElement;

public interface CyclicMonoid extends Monoid {

	GroupElement getGenerator();
}

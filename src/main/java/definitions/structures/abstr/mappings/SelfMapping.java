package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.monoids.Monoid;

public interface SelfMapping extends Mapping {

	@Override
	default Monoid getTarget() {
		return getSource();
	}
}

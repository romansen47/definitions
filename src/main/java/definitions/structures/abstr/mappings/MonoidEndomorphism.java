package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.monoids.Monoid;

public interface MonoidEndomorphism extends MonoidHomomorphism {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Monoid getTarget() {
		return this.getSource();
	}

}

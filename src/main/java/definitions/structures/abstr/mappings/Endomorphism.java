package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.monoids.Monoid;

/**
 * Finite dimensional linear self mapping.
 * 
 * @author ro
 */
public interface Endomorphism extends VectorSpaceHomomorphism, SelfMapping {
	
	@Override
	default Monoid getTarget() {
		return SelfMapping.super.getTarget();
	}

}

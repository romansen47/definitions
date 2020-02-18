package definitions.structures.abstr.mappings;

import definitions.structures.abstr.vectorspaces.VectorSpace;

/**
 * Automorphism.
 * 
 * @author ro
 *
 */
public interface VectorSpaceAutomorphism extends GroupAutomorphism, VectorSpaceEndomorphism, VectorSpaceIsomorphism {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default VectorSpace getTarget() {
		return VectorSpaceEndomorphism.super.getTarget();
	}

}

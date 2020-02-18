package definitions.structures.abstr.mappings;

import definitions.structures.abstr.vectorspaces.VectorSpace;

/**
 * Finite dimensional linear self mapping.
 * 
 * @author ro
 */
public interface VectorSpaceEndomorphism extends VectorSpaceHomomorphism, GroupEndomorphism, VectorSpaceSelfMapping {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	default VectorSpace getTarget() {
		return VectorSpaceSelfMapping.super.getTarget();
	}

}

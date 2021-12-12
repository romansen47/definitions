package definitions.structures.abstr.mappings;

import definitions.structures.abstr.vectorspaces.VectorSpace;

public interface VectorSpaceSelfMapping extends VectorSpaceMapping {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default VectorSpace getTarget() {
		return this.getSource();
	}

}

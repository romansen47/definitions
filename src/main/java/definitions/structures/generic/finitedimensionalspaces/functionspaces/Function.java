package definitions.structures.generic.finitedimensionalspaces.functionspaces;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteDimensionalVectorSpace;

public abstract class Function implements IFunction{

	private final IFiniteDimensionalVectorSpace source;
	private final IFiniteDimensionalVectorSpace target;
	
	protected Function(IFiniteDimensionalVectorSpace source,IFiniteDimensionalVectorSpace target) {
		this.source=source;
		this.target=target;
	}

	/**
	 * @return the target
	 */
	@Override
	public final IFiniteDimensionalVectorSpace getTarget() {
		return target;
	}

	/**
	 * @return the source
	 */
	@Override
	public final IFiniteDimensionalVectorSpace getSource() {
		return source;
	}

	
}

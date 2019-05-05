package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import java.util.Map;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.IFiniteDimensionalLinearMapping;

public class FiniteDimensionalLinearMapping implements IFiniteDimensionalLinearMapping {

	private final IFiniteDimensionalVectorSpace source;
	private final IFiniteDimensionalVectorSpace target;
	private final Map<FiniteVector, Map<FiniteVector, Double>> linearity;

	protected FiniteDimensionalLinearMapping(IFiniteDimensionalVectorSpace source, IFiniteDimensionalVectorSpace target,
			Map<FiniteVector, Map<FiniteVector, Double>> linearity) throws Throwable {
		this.source = source;
		this.target = target;
		this.linearity = linearity;
	}

	@Override
	final public IFiniteDimensionalVectorSpace getSource() {
		return source;
	}

	@Override
	final public IFiniteDimensionalVectorSpace getTarget() {
		return target;
	}

	@Override
	final public Map<FiniteVector, Map<FiniteVector, Double>> getLinearity() {
		return linearity;
	}

}

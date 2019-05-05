package definitions.structures.generic;

import java.util.Map;

public class FiniteDimensionalLinearMapping implements IFiniteDimensionalLinearMapping {

	private final IFiniteDimensionalVectorSpace source;
	private final IFiniteDimensionalVectorSpace target;
	private final Map<RealVec, Map<RealVec, Double>> linearity;

	protected FiniteDimensionalLinearMapping(IFiniteDimensionalVectorSpace source, IFiniteDimensionalVectorSpace target,
			Map<RealVec, Map<RealVec, Double>> linearity) throws Throwable {
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
	final public Map<RealVec, Map<RealVec, Double>> getLinearity() {
		return linearity;
	}

	@Override
	final public int getRang() {
		return 0;
	}

}

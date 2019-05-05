package definitions.structures.generic;

import java.util.Map;

public class InvertibleFiniteDimensionalLinearMapping extends FiniteDimensionalLinearMapping implements Isomorphism {

	public InvertibleFiniteDimensionalLinearMapping(IFiniteDimensionalVectorSpace source,
			Map<RealVec, Map<RealVec, Double>> linearity) throws Throwable {
		super(source, source, linearity);
		if (det() == 0) {
			throw new Throwable();
		}
	}

}

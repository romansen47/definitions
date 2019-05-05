package definitions.structures.generic;

import java.util.Map;

import definitions.structures.abstr.IVec;

public interface IFiniteDimensionalSubSpace extends IFiniteDimensionalVectorSpace {

	IFiniteDimensionalVectorSpace getSuperSpace();

	default IFiniteDimensionalLinearMapping create(Map<RealVec, Map<RealVec, Double>> coordinates) throws Throwable {
		return new FiniteDimensionalLinearMapping(getSuperSpace(), getSuperSpace(), coordinates);
	};

	@Override
	boolean contains(IVec vec);

}

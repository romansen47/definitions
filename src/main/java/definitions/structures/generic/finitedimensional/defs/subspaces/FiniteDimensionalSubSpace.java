package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class FiniteDimensionalSubSpace extends FiniteDimensionalVectorSpace implements IFiniteDimensionalSubSpace {

	protected IFiniteDimensionalInjectiveLinearMapping parametrization;
	protected final List<IFiniteVector> genericBase = new ArrayList<>();
	protected Map<IFiniteVector, IFiniteVector> parametrizationBaseVectorMapping = new HashMap<>();

	public FiniteDimensionalSubSpace(IFiniteDimensionalLinearMapping map) throws Throwable {
		super(SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(map.getRank()).genericBaseToList());
		this.parametrization = (IFiniteDimensionalInjectiveLinearMapping) map;

		for (IFiniteVector vec : parametrization.getSource().genericBaseToList()) {
			IFiniteVector newBaseVec = (IFiniteVector) parametrization.get(vec);
			genericBase.add(newBaseVec);
			getParametrizationBaseVectorMapping().put(vec, newBaseVec);
		}
	}

	@Override
	public IFiniteDimensionalVectorSpace getSuperSpace() {
		return (IFiniteDimensionalVectorSpace) parametrization.getTarget();
	}

	@Override
	public int dim() throws Throwable {
		return parametrization.getRank();
	}

	@Override
	public final IFiniteDimensionalLinearMapping getParametrization() {
		return parametrization;
	}

	@Override
	public boolean contains(IVector vec) throws Throwable {
		try {
			return getSuperSpace().contains(vec) && this.parametrization.solve((IFiniteVector) vec) != null;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<IFiniteVector> genericBaseToList() throws Throwable {
		return genericBase;
	}

	@Override
	public final Map<IFiniteVector, IFiniteVector> getParametrizationBaseVectorMapping() {
		return parametrizationBaseVectorMapping;
	}

}

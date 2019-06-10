package definitions.structures.generic.finitedimensional.defs.subspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.ParameterizedSpace;

public class FiniteDimensionalSubSpace extends FiniteDimensionalVectorSpace implements ParameterizedSpace {

	protected IFiniteDimensionalInjectiveLinearMapping parametrization;
	protected final List<Vector> genericBase = new ArrayList<>();
	protected Map<Vector, Vector> parametrizationBaseVectorMapping = new ConcurrentHashMap<>();

	public FiniteDimensionalSubSpace(IFiniteDimensionalLinearMapping map) throws Throwable {
		super(SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(map.getRank()).genericBaseToList());
		this.parametrization = (IFiniteDimensionalInjectiveLinearMapping) map;
		for (Vector vec : parametrization.getSource().genericBaseToList()) {
			Vector newBaseVec = parametrization.get(vec);
			genericBase.add(newBaseVec);
			getParametrizationBaseVectorMapping().put(vec, newBaseVec);
		}
	}

	@Override
	public EuclideanSpace getSuperSpace() {
		return (EuclideanSpace) parametrization.getTarget();
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
	public boolean contains(Vector vec) throws Throwable {
		try {
			return getSuperSpace().contains(vec) && this.parametrization.solve(vec) != null;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Vector> genericBaseToList() throws Throwable {
		return genericBase;
	}

	@Override
	public final Map<Vector, Vector> getParametrizationBaseVectorMapping() {
		return parametrizationBaseVectorMapping;
	}

}

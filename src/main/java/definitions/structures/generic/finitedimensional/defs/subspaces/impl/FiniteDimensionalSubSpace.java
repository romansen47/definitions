package definitions.structures.generic.finitedimensional.defs.subspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.ParameterizedSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;

public class FiniteDimensionalSubSpace extends FiniteDimensionalVectorSpace implements ParameterizedSpace {

	protected IFiniteDimensionalInjectiveLinearMapping parametrization;
	protected final List<Vector> genericBase = new ArrayList<>();
	protected Map<Vector, Vector> parametrizationBaseVectorMapping = new HashMap<>();

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
	public CoordinateSpace getSuperSpace() {
		return (CoordinateSpace) parametrization.getTarget();
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
			return getSuperSpace().contains(vec) && this.parametrization.solve((FiniteVector) vec) != null;
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

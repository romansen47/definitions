package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.ArrayList;
import java.util.List;
import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class FiniteDimensionalSubSpace extends FiniteDimensionalVectorSpace implements IFiniteDimensionalSubSpace {

	private final IFiniteDimensionalLinearMapping parametrization;

	public FiniteDimensionalSubSpace(IFiniteDimensionalLinearMapping map) throws Throwable {
		super(SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(map.getRank())
					.genericBaseToList());
		this.parametrization = map;
	}

	@Override
	public IFiniteDimensionalVectorSpace getSuperSpace() {
		return parametrization.getTarget();
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
	public boolean contains(IVector vec) {
		try {
			return getSuperSpace().contains(vec) && this.parametrization.solve((IFiniteVector) vec) != null;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<IFiniteVector> genericBaseToList() throws Throwable {
		List<IFiniteVector> baseAsList = new ArrayList<>();
		for (IFiniteVector vec : parametrization.getSource().genericBaseToList()) {
				baseAsList.add(parametrization.get(vec));
		}
		return baseAsList;
	}

}

package definitions.structures.finitedimensional.subspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalEmbedding;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.ParameterizedSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.FiniteDimensionalVectorSpace;

public class FiniteDimensionalSubSpace extends FiniteDimensionalVectorSpace implements ParameterizedSpace {

	protected FiniteDimensionalEmbedding parametrization;
	
	protected final List<Vector> genericBase = new ArrayList<>();
	
	protected Map<Vector, Vector> parametrizationBaseVectorMapping = new ConcurrentHashMap<>();

	public FiniteDimensionalSubSpace(final FiniteDimensionalEmbedding map) throws Throwable {
		super(Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(map.getRank())
				.genericBaseToList());
		this.parametrization = map;
		for (final Vector vec : ((EuclideanSpace) this.parametrization.getSource()).genericBaseToList()) {
			final Vector newBaseVec = this.parametrization.get(vec);
			this.genericBase.add(newBaseVec);
			this.getParametrizationBaseVectorMapping().put(vec, newBaseVec);
		}
	}

	@Override
	public EuclideanSpace getSuperSpace() {
		return (EuclideanSpace) this.parametrization.getTarget();
	}

	@Override
	public int dim() {
		return this.parametrization.getRank();
	}

	@Override
	public final FiniteDimensionalEmbedding getParametrization() {
		return this.parametrization;
	}

	@Override
	public boolean contains(final Vector vec) {
		try {
			return this.getSuperSpace().contains(vec) && (this.parametrization.solve(vec) != null);
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Vector> genericBaseToList() {
		return this.genericBase;
	}

	@Override
	public final Map<Vector, Vector> getParametrizationBaseVectorMapping() {
		return this.parametrizationBaseVectorMapping;
	}

}

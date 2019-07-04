package definitions.structures.finitedimensional.subspaces.impl;

//package definitions.structures.generic.finitedimensional.defs.subspaces.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import definitions.structures.abstr.Vector;
//import definitions.structures.generic.finitedimensional.defs.Generator;
//import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
//import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
//import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
//import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
//import definitions.structures.generic.finitedimensional.defs.subspaces.ParameterizedSpace;
//
//public class FiniteDimensionalSubSpace extends FiniteDimensionalVectorSpace implements ParameterizedSpace {
//
//	protected IFiniteDimensionalInjectiveLinearMapping parametrization;
//	protected final List<Vector> genericBase = new ArrayList<>();
//	protected Map<Vector, Vector> parametrizationBaseVectorMapping = new ConcurrentHashMap<>();
//
//	public FiniteDimensionalSubSpace(final IFiniteDimensionalLinearMapping map) throws Throwable {
//		super(Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(map.getRank())
//				.genericBaseToList());
//		this.parametrization = (IFiniteDimensionalInjectiveLinearMapping) map;
//		for (final Vector vec : ((EuclideanSpace) this.parametrization.getSource()).genericBaseToList()) {
//			final Vector newBaseVec = this.parametrization.get(vec);
//			this.genericBase.add(newBaseVec);
//			this.getParametrizationBaseVectorMapping().put(vec, newBaseVec);
//		}
//	}
//
//	@Override
//	public EuclideanSpace getSuperSpace() {
//		return (EuclideanSpace) this.parametrization.getTarget();
//	}
//
//	@Override
//	public int dim() throws Throwable {
//		return this.parametrization.getRank();
//	}
//
//	@Override
//	public final IFiniteDimensionalLinearMapping getParametrization() {
//		return this.parametrization;
//	}
//
//	@Override
//	public boolean contains(final Vector vec) throws Throwable {
//		try {
//			return this.getSuperSpace().contains(vec) && (this.parametrization.solve(vec) != null);
//		} catch (final Throwable e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	@Override
//	public List<Vector> genericBaseToList() throws Throwable {
//		return this.genericBase;
//	}
//
//	@Override
//	public final Map<Vector, Vector> getParametrizationBaseVectorMapping() {
//		return this.parametrizationBaseVectorMapping;
//	}
//
//}

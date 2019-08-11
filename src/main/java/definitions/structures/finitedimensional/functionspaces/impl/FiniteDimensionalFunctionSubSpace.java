package definitions.structures.finitedimensional.functionspaces.impl;

///**
// *
// */
//package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;
//
//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//import definitions.structures.abstr.Vector;
//import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
//import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
//import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
//import definitions.structures.generic.finitedimensional.defs.subspaces.impl.FiniteDimensionalSubSpace;
//import definitions.structures.generic.finitedimensional.defs.vectors.Function;
//import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
//
///**
// * @author ro
// *
// */
//public class FiniteDimensionalFunctionSubSpace extends FiniteDimensionalSubSpace
//		implements IFiniteDimensionalFunctionSpace, Serializable {
//
//	private static final long serialVersionUID = 8782137998323986519L;
//
//	final double[] intervall;
//
//	final double eps;
//
//	final String PATH = "vectorSpaces.data";
//
//	FileOutputStream f_out = new FileOutputStream(PATH);
//
//	ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
//
//	public FiniteDimensionalFunctionSubSpace(final IFiniteDimensionalLinearMapping map,
//			final IFiniteDimensionalFunctionSpace superSpace) throws Throwable {
//		super(map);
//		this.intervall = superSpace.getInterval();
//		this.eps = superSpace.getEpsilon();
//		this.parametrization = (IFiniteDimensionalInjectiveLinearMapping) map;
//		this.genericBase.clear();
//		for (final Vector vec : ((definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace) this.parametrization
//				.getSource()).genericBaseToList()) {
//			final Vector newBaseVec = this.parametrization.get(vec);
//			this.genericBase.add(newBaseVec);
//			this.getParametrizationBaseVectorMapping().put(vec, newBaseVec);
//		}
//	}
//
//	@Override
//	public int dim() throws Throwable {
//		return super.dim();
//	}
//
//	@Override
//	public boolean contains(final Vector vec) throws Throwable {
//		return true;
//	}
//
//	@Override
//	public double[] getInterval() {
//		return this.intervall;
//	}
//
//	@Override
//	public Function stretch(final Vector vec, final double r) throws Throwable {
//		return (Function) this.getSuperSpace().stretch(vec, r);
//	}
//
//	@Override
//	public Vector add(final Vector vec1, final Vector vec2) throws Throwable {
//		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
//			return this.getSuperSpace().add(vec1, vec2);
//		}
//		return super.add(vec1, vec2);
//	}
//
//	@Override
//	public double getEpsilon() {
//		return this.eps;
//	}
//
//	@Override
//	public Vector copyVector(final Vector vec) throws Throwable {
//		final Map<Vector, Double> coordinates = new ConcurrentHashMap<>();
//		for (final Vector baseVec : this.genericBase) {
//			coordinates.put(baseVec, this.product(vec, baseVec));
//		}
//		return new FunctionTuple(coordinates);
//	}
//
//	@Override
//	public Function nullFunction() throws Throwable {
//		final Map<Vector, Double> nul = new ConcurrentHashMap<>();
//		for (final Vector baseVec : this.getSuperSpace().genericBaseToList()) {
//			nul.put(baseVec, 0.0);
//		}
//		return new FunctionTuple(nul);
//	}
//
//}

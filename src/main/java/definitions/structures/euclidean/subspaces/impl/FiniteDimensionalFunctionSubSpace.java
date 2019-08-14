package definitions.structures.euclidean.subspaces.impl;

//package definitions.structures.finitedimensional.real.subspaces.impl;
//
//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import definitions.structures.abstr.InnerProductSpace;
//import definitions.structures.abstr.Scalar;
//import definitions.structures.abstr.Vector;
//import definitions.structures.field.impl.RealLine;
//import definitions.structures.field.scalar.impl.Real;
//import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
//import definitions.structures.finitedimensional.real.mappings.FiniteDimensionalEmbedding;
//import definitions.structures.finitedimensional.real.vectors.Function;
//import definitions.structures.finitedimensional.real.vectors.impl.FunctionTuple;
//import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
//
///**
// * @author ro
// *
// */
//public class FiniteDimensionalFunctionSubSpace extends FiniteDimensionalSubSpace
//		implements EuclideanFunctionSpace {
//
//	final double[] intervall;
//
//	final double eps;
//
//	final String PATH = "vectorSpaces.data";
//
//	FileOutputStream f_out = new FileOutputStream(this.PATH);
//
//	ObjectOutputStream obj_out = new ObjectOutputStream(this.f_out);
//
////	public FiniteDimensionalFunctionSubSpace(final FiniteDimensionalEmbedding map,
////			final EuclideanFunctionSpace superSpace) throws Throwable {
////		super(map);
////		this.intervall = superSpace.getInterval();
////		this.eps = superSpace.getEpsilon();
////		this.parametrization = map;
////		this.genericBase.clear();
////		for (final Vector vec : ((EuclideanSpace) this.parametrization.getSource()).genericBaseToList()) {
////			final Vector newBaseVec = this.parametrization.get(vec);
////			this.genericBase.add(newBaseVec);
////			this.getParametrizationBaseVectorMapping().put(vec, newBaseVec);
////		}
////	}
//
//	@Override
//	public Integer getDim() {
//		return super.getDim();
//	}
//
//	@Override
//	public boolean contains(final Vector vec) {
//		return true;
//	}
//
//	@Override
//	public double[] getInterval() {
//		return this.intervall;
//	}
//
//	@Override
//	public Function stretch(final Vector vec, final Scalar r) {
//		return (Function) this.getSuperSpace().stretch(vec, r);
//	}
//
//	@Override
//	public Vector add(final Vector vec1, final Vector vec2) {
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
//	public Function nullFunction() {
//		final Map<Vector, Scalar> nul = new ConcurrentHashMap<>();
//		for (final Vector baseVec : this.getSuperSpace().genericBaseToList()) {
//			nul.put(baseVec, RealLine.getInstance().getZero());
//		}
//		return new FunctionTuple(nul);
//	}
//
//	@Override
//	public double getLeft() {
//		return this.getInterval()[0];
//	}
//
//	@Override
//	public double getRight() {
//		return this.getInterval()[1];
//	}
//
//	@Override
//	public Scalar innerProduct(Vector vec1, Vector vec2) {
////		if (vec1 instanceof FunctionTuple && vec2 instanceof FunctionTuple) {
////			return super.product(vec1, vec2);
////		}
////		return this.integral((Function) vec1, (Function) vec2);
//		return ((InnerProductSpace) this.getParametrization().getTarget()).innerProduct(vec1, vec2);
//	}
//
//}

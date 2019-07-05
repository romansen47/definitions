package definitions.structures.finitedimensional.subspaces.impl;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalEmbedding;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

//public class FiniteDimensionalFunctionSubSpace extends FiniteDimensionalSubSpace implements EuclideanFunctionSpace {
//
//	final double left;
//	final double right;
//	final double eps;
//	final double[] interval;
//
//	public FiniteDimensionalFunctionSubSpace(FiniteDimensionalEmbedding map,double right,double eps) throws Throwable {
//		super(map);
//		this.right=right;
//		this.left=-right;
//		this.eps=eps;
//		interval=new double[] {-right,right};
//	}
//
//	@Override
//	public double[] getInterval() {
//		return interval;
//	}
//
//	@Override
//	public double getEpsilon() {
//		// TODO Auto-generated method stub
//		return eps;
//	}
//
//	@Override
//	public double getLeft() {
//		return left;
//	}
//
//	@Override
//	public double getRight() {
//		return right;
//	}
//
//	@Override
//	public Vector add(Vector vec1, Vector vec2) {
//		return EuclideanFunctionSpace.super.add(vec1, vec2);
//	}
//
//}
/**
* @author ro
*
*/
public class FiniteDimensionalFunctionSubSpace extends FiniteDimensionalSubSpace
		implements EuclideanFunctionSpace, Serializable {

	private static final long serialVersionUID = 8782137998323986519L;

	final double[] intervall;

	final double eps;

	final String PATH = "vectorSpaces.data";

	FileOutputStream f_out = new FileOutputStream(this.PATH);

	ObjectOutputStream obj_out = new ObjectOutputStream(this.f_out);

	public FiniteDimensionalFunctionSubSpace(final FiniteDimensionalEmbedding map,
			final EuclideanFunctionSpace superSpace) throws Throwable {
		super(map);
		this.intervall = superSpace.getInterval();
		this.eps = superSpace.getEpsilon();
		this.parametrization = (FiniteDimensionalEmbedding) map;
		this.genericBase.clear();
		for (final Vector vec : ((EuclideanSpace) this.parametrization
				.getSource()).genericBaseToList()) {
			final Vector newBaseVec = this.parametrization.get(vec);
			this.genericBase.add(newBaseVec);
			this.getParametrizationBaseVectorMapping().put(vec, newBaseVec);
		}
	}

	@Override
	public int dim() {
		return super.dim();
	}

	@Override
	public boolean contains(final Vector vec) {
		return true;
	}

	@Override
	public double[] getInterval() {
		return this.intervall;
	}

	@Override
	public Function stretch(final Vector vec, final double r){
		return (Function) this.getSuperSpace().stretch(vec, r);
	}

	@Override
	public Vector add(final Vector vec1, final Vector vec2){
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			return this.getSuperSpace().add(vec1, vec2);
		}
		return super.add(vec1, vec2);
	}

	@Override
	public double getEpsilon() {
		return this.eps;
	}

	@Override
	public Function nullFunction() {
		final Map<Vector, Double> nul = new ConcurrentHashMap<>();
		for (final Vector baseVec : this.getSuperSpace().genericBaseToList()) {
			nul.put(baseVec, 0.0);
		}
		return new FunctionTuple(nul);
	}

	@Override
	public double getLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRight() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Complex;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

public final class ComplexPlane extends FiniteDimensionalVectorSpace implements Field, RealSpace {

	static private EuclideanSpace instance;

	private final Vector zero;
	private final Vector one;
	private final Vector i;

	private Map<Vector, Homomorphism> multiplicationMatrix = null;

	public Vector getI() {
		return this.i;
	}

	private ComplexPlane() {
		super(RealLine.getInstance());
		this.dim = 2;
		this.base = new ArrayList<>();
		this.one = new Complex(RealLine.getInstance().getOne(), RealLine.getInstance().getZero());
		this.zero = new Complex(RealLine.getInstance().getZero(), RealLine.getInstance().getZero());
		this.i = new Complex(RealLine.getInstance().getZero(), RealLine.getInstance().getOne());
		this.base.add(this.one);
		this.base.add(this.i);
	}

	@Override
	public Vector add(Vector vec1,Vector vec2) {
		Vector ans=super.add(vec1,vec2);
		return new Complex(ans.getCoordinates().get(one),ans.getCoordinates().get(i));
	}
	
	@Override
	public Vector stretch(Vector vec1,Scalar r) {
		Vector ans=super.stretch(vec1,r);
		return new Complex(ans.getCoordinates().get(one),ans.getCoordinates().get(i));
	}
	
	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new ComplexPlane();
		}
		return instance;
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		return Field.super.product(vec1, vec2);
	}
	
	private Vector conjugate(Vector factor) {
		final Scalar newRe = new Real(((Complex) factor).getReal().getValue());
		final Scalar newIm = new Real(-((Complex) factor).getImag().getValue());
		return new Complex(newRe, newIm);
	}

	@Override
	public Vector getOne() {
		return this.one;
	}

	@Override
	public String toString() {
		return "ComplexPlane as 2-dimensional real vector space";
	}

	@Override
	public boolean contains(Vector vec) {
		return vec instanceof Complex || vec == this.zero || vec == this.one || vec == null;
	}

	@Override
	public Vector nullVec() {
		return this.zero;
	}

	/**
	 * @return the multiplicationMatrix
	 */
	@Override
	public Map<Vector, Homomorphism> getMultiplicationMatrix() {

		if (this.multiplicationMatrix == null) {

			final Scalar realOne = RealLine.getInstance().getOne();
			final Scalar realZero = RealLine.getInstance().getZero();
			final Scalar neg = new Real(-1);

			final Scalar[][] oneMat = new Scalar[][] { { realOne, realZero }, { realZero, realOne } };
			final Homomorphism oneHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this,
					oneMat);

			final Scalar[][] iMat = new Scalar[][] { { realZero, neg }, { realOne, realZero } };
			final Homomorphism iHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this,
					iMat);

			final Map<Vector, Homomorphism> newMap = new HashMap<>();

			newMap.put(this.one, oneHom);
			newMap.put(this.i, iHom);

			this.setMultiplicationMatrix(newMap);
		}
		return this.multiplicationMatrix;
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(Map<Vector, Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

}
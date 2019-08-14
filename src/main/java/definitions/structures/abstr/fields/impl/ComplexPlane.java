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

//	static private EuclideanSpace space;

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

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new ComplexPlane();
		}
		return instance;
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
//		if (this.multiplicationMatrix == null) {
//			Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
//			Scalar re = new Real(((Complex) vec1).getReal().getValue() * ((Complex) vec2).getReal().getValue()
//					- ((Complex) vec1).getImag().getValue() * ((Complex) vec2).getImag().getValue());
//			Scalar im = new Real(((Complex) vec1).getReal().getValue() * ((Complex) vec2).getImag().getValue()
//					+ ((Complex) vec1).getImag().getValue() * ((Complex) vec2).getReal().getValue());
//			coordinates.put(this.one, re);
//			coordinates.put(this.i, im);
//			return this.get(coordinates);
//		} else {
		return Field.super.product(vec1, vec2);
//		}
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		final Scalar newRe = new Real(((Complex) vec1).getReal().getValue() + ((Complex) vec2).getReal().getValue());
		final Scalar newIm = new Real(((Complex) vec1).getImag().getValue() + ((Complex) vec2).getImag().getValue());
		return new Complex(newRe, newIm);
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		final Scalar newRe = new Real(((Complex) vec1).getReal().getValue() * r.getValue());
		final Scalar newIm = new Real(((Complex) vec1).getImag().getValue() * r.getValue());
		return new Complex(newRe, newIm);
	}

	@Override
	public Vector inverse(Vector factor) {
		return this.stretch(this.conjugate(factor), new Real(Math.pow(this.norm(factor).getValue(), -2)));
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

	@Override
	public Integer getDim() {
		return this.dim;
	}

	@Override
	public Vector projection(Vector w, Vector v) {
		return null;
	}

	@Override
	public Vector getCoordinates(Vector vec) {
		return vec;
	}

	/**
	 * @return the multiplicationMatrix
	 */
	@Override
	public Map<Vector, Homomorphism> getMultiplicationMatrix() {
		final Scalar oneTmp = RealLine.getInstance().getOne();
		final Scalar zeroTmp = RealLine.getInstance().getZero();
		if (this.multiplicationMatrix == null) {
			final Map<Vector, Map<Vector, Scalar>> multiplicationMap = new HashMap<>();
			final Map<Vector, Scalar> a = new HashMap<>();
			final Map<Vector, Scalar> b = new HashMap<>();
			a.put(this.one, oneTmp);
			a.put(this.i, zeroTmp);
			b.put(this.one, zeroTmp);
			b.put(this.i, oneTmp);
			multiplicationMap.put(this.one, a);
			multiplicationMap.put(this.i, b);
			final Map<Vector, Homomorphism> newMap = new HashMap<>();
			newMap.put(this.one,
					MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap));

			final Map<Vector, Map<Vector, Scalar>> multiplicationMap2 = new HashMap<>();
			final Map<Vector, Scalar> c = new HashMap<>();
			final Map<Vector, Scalar> d = new HashMap<>();
			c.put(this.one, zeroTmp);
			c.put(this.i, oneTmp);
			d.put(this.one, new Real(-1));
			d.put(this.i, zeroTmp);
			multiplicationMap2.put(this.one, c);
			multiplicationMap2.put(this.i, d);

			newMap.put(this.i,
					MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap2));
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

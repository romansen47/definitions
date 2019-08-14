package definitions.structures.field.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.RealSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.field.scalar.impl.Complex;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.FiniteDimensionalVectorSpace;

public final class ComplexPlane extends FiniteDimensionalVectorSpace implements Field, RealSpace {

//	static private EuclideanSpace space;

	static private EuclideanSpace instance;

	private final Vector zero;
	private final Vector one;
	private final Vector i;

	private Map<Vector,Homomorphism> multiplicationMatrix = null;

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
		Scalar newRe = new Real(((Complex) vec1).getReal().getValue() + ((Complex) vec2).getReal().getValue());
		Scalar newIm = new Real(((Complex) vec1).getImag().getValue() + ((Complex) vec2).getImag().getValue());
		return new Complex(newRe, newIm);
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		Scalar newRe = new Real(((Complex) vec1).getReal().getValue() * r.getValue());
		Scalar newIm = new Real(((Complex) vec1).getImag().getValue() * r.getValue());
		return new Complex(newRe, newIm);
	}

	@Override
	public Vector inverse(Vector factor) {
		return this.stretch(this.conjugate(factor), new Real(Math.pow(this.norm(factor).getValue(), -2)));
	}

	private Vector conjugate(Vector factor) {
		Scalar newRe = new Real(((Complex) factor).getReal().getValue());
		Scalar newIm = new Real(-((Complex) factor).getImag().getValue());
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
	public Map<Vector,Homomorphism> getMultiplicationMatrix() {
		final Scalar oneTmp=RealLine.getInstance().getOne();
		final Scalar zeroTmp=RealLine.getInstance().getZero();
		if (this.multiplicationMatrix == null) {
			Map<Vector, Map<Vector, Scalar>> multiplicationMap = new HashMap<>();
			Map<Vector, Scalar> a = new HashMap<>();
			Map<Vector, Scalar> b = new HashMap<>();
			a.put(one, oneTmp);
			a.put(i, zeroTmp);
			b.put(one, zeroTmp);
			b.put(i, oneTmp);
			multiplicationMap.put(one, a);
			multiplicationMap.put(i, b);
			Map<Vector,Homomorphism> newMap=new HashMap<>();
			newMap.put(one, MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap));
			
			Map<Vector, Map<Vector, Scalar>> multiplicationMap2 = new HashMap<>();
			Map<Vector, Scalar> c = new HashMap<>();
			Map<Vector, Scalar> d = new HashMap<>();
			c.put(one, zeroTmp);
			c.put(i, oneTmp);
			d.put(one, new Real(-1));
			d.put(i, zeroTmp);
			multiplicationMap2.put(one, c);
			multiplicationMap2.put(i, d);

			newMap.put(i, MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap2));
			this.setMultiplicationMatrix(newMap);
		}
		return this.multiplicationMatrix;
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(Map<Vector,Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

}

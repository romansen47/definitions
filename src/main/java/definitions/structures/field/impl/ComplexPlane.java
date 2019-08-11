package definitions.structures.field.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.RealSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.EuclideanField;
import definitions.structures.field.scalar.impl.Complex;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.FiniteDimensionalVectorSpace;

public final class ComplexPlane extends FiniteDimensionalVectorSpace implements EuclideanField, RealSpace {

//	static private EuclideanSpace space;

	static private EuclideanSpace instance;

	private final Vector zero;
	private final Vector one;
	private final Vector i;

	private Homomorphism multiplicationMatrix = null;

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
		if (this.multiplicationMatrix == null) {
			Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
			Scalar re = new Real(((Complex) vec1).getReal().getValue() * ((Complex) vec2).getReal().getValue()
					- ((Complex) vec1).getImag().getValue() * ((Complex) vec2).getImag().getValue());
			Scalar im = new Real(((Complex) vec1).getReal().getValue() * ((Complex) vec2).getImag().getValue()
					+ ((Complex) vec1).getImag().getValue() * ((Complex) vec2).getReal().getValue());
			coordinates.put(this.one, re);
			coordinates.put(this.i, im);
			return this.get(coordinates);
		} else {
			return EuclideanField.super.product(vec1, vec2);
		}
	}

//	@Override
//	public Vector get(Scalar[] coordinates) {
//		return new Tuple(coordinates) {
//			@Override
//			public String toString() {
//				return coordinates[0].getValue() + " + i * ( " + coordinates[1] + ")";
//			}
//		};
//	}

//	@Override
//	public Field getField() {
//		return RealLine.getInstance();
//	}

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
//	@Override
//	public Field getField() {
//		return RealLine.getInstance();
//	}

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
	public Homomorphism getMultiplicationMatrix() {
		if (this.multiplicationMatrix == null) {
			Map<Vector, Map<Vector, Scalar>> multiplicationMap = new HashMap<>();
			Map<Vector, Scalar> a = new HashMap<>();
			a.put(this.one, (Scalar) this.one);
			a.put(this.i, (Scalar) this.i);
			multiplicationMap.put(this.one, a);
			Map<Vector, Scalar> b = new HashMap<>();
			b.put(this.one, (Scalar) this.i);
			b.put(this.i, new Complex(-1, 0));
			multiplicationMap.put(this.i, b);
			this.setMultiplicationMatrix(
					MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap));
		}
		return this.multiplicationMatrix;
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(Homomorphism multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

}

package definitions.structures.field.impl;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.field.scalar.impl.Complex;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.FiniteDimensionalVectorSpace;

public final class ComplexPlane extends FiniteDimensionalVectorSpace implements Field {

//	static private EuclideanSpace space;

	static private EuclideanSpace instance;

	final Vector zero;
	final Vector one;
	final Vector i;

	public Vector getI() {
		return this.i;
	}

	private ComplexPlane() {
		super(RealLine.getInstance());
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
		Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
		Scalar re = new Real(((Complex) vec1).getReal().getValue() * ((Complex) vec2).getReal().getValue()
				- ((Complex) vec1).getImag().getValue() * ((Complex) vec2).getImag().getValue());
		Scalar im = new Real(((Complex) vec1).getReal().getValue() * ((Complex) vec2).getImag().getValue()
				+ ((Complex) vec1).getImag().getValue() * ((Complex) vec2).getReal().getValue());
		coordinates.put(this.one, re);
		coordinates.put(this.i, im);
		return this.get(coordinates);
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
		Scalar newRe = new Real(((Complex) factor).getImag().getValue());
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
	public Field getField() {
		return RealLine.getInstance();
	}

	@Override
	public Vector projection(Vector w, Vector v) {
		return null;
	}

	@Override
	public Vector getCoordinates(Vector vec) {
		return vec;
	}

}

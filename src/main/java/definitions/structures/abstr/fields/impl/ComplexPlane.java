package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Complex;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 * 
 * @author ro
 *
 *         Implementation of the field of complex numbers as a singleton class.
 */
public class ComplexPlane extends FiniteDimensionalVectorSpace implements Field, RealSpace {

	private static final long serialVersionUID = -6528124823296735558L;

	static private EuclideanSpace instance;

	private static RealLine realLine;

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new ComplexPlane();
		}
		return instance;
	}

	public static void setRealLine(final RealLine realLine) {
		ComplexPlane.realLine = realLine;
	}

	private final Vector zero;

	private final Vector one;

	private final Vector i;

	private Map<Vector, Homomorphism> multiplicationMatrix = null;

	public ComplexPlane() {
		this.dim = 2;
		this.base = new ArrayList<>();
		this.one = new Complex(realLine.getOne(), realLine.getZero());
		this.zero = new Complex(realLine.getZero(), realLine.getZero());
		this.i = new Complex(realLine.getZero(), realLine.getOne());
		this.base.add(this.one);
		this.base.add(this.i);
	}

	@Override
	public Complex add(final Vector vec1, final Vector vec2) {
		final Vector ans = super.add(vec1, vec2);
		return new Complex(((FiniteVectorMethods) ans).getCoordinates().get(this.one),
				((FiniteVectorMethods) ans).getCoordinates().get(this.i));
	}

	public Complex complex() {
		return new Complex(0, 0);
	}

	@Override
	public Scalar conjugate(final Scalar value) {
		final Complex v = (Complex) value;
		return new Complex(v.getReal().getValue(), -v.getImag().getValue());
	}

	@Override
	public boolean contains(final Vector vec) {
		return vec instanceof Complex || vec == this.zero || vec == this.one || vec == null;
	}

	@Override
	public Complex get(final double realValue) {
		final Complex newComplex = this.complex();
		newComplex.setValue(realValue, 0);
		return newComplex;
	}

	public Complex get(final double realValue, final double imValue) {
		final Complex newComplex = this.complex();
		newComplex.setValue(realValue, imValue);
		return newComplex;
	}

	public Vector getI() {
		return this.i;
	}

	@Override
	public Map<Vector, Homomorphism> getMultiplicationMatrix() {

		if (this.multiplicationMatrix == null) {

			final Scalar realOne = RealLine.getInstance().getOne();
			final Scalar realZero = RealLine.getInstance().getZero();
			final Scalar neg = RealLine.getInstance().get(-1);

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

	@Override
	public Vector getOne() {
		return this.one;
	}

	@Override

	public Scalar inverse(final Vector factor) {
		return (Scalar) Field.super.inverse(factor);
	}

	@Override
	public Vector nullVec() {
		return this.zero;
	}

	@Override
	public Complex product(final Vector vec1, final Vector vec2) {
		return (Complex) Field.super.product(vec1, vec2);
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(final Map<Vector, Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

	@Override
	public Complex stretch(final Vector vec1, final Scalar r) {
		final Vector ans = super.stretch(vec1, r);
		return new Complex(((FiniteVectorMethods) ans).getCoordinates().get(this.one),
				((FiniteVectorMethods) ans).getCoordinates().get(this.i));

	}

	@Override
	public String toString() {
		return "the field of complex numbers as a 2-dimensional real vector space.";
	}

	@Override
	public String toXml() {
		return "<complexPlane />";
	}

}

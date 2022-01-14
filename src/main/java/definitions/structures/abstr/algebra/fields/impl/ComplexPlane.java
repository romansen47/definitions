package definitions.structures.abstr.algebra.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
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

	/*
	 * this one is a singleton
	 */
	static private EuclideanSpace instance;

	/*
	 * complex plane here is modeeled as a 2 dimensional real vector space
	 */
	private static RealLine realLine;

	/**
	 * Getter for the instance
	 *
	 * @return the instance
	 */
	public static EuclideanSpace getInstance() {
		if (ComplexPlane.instance == null) {
			ComplexPlane.instance = new ComplexPlane();
		}
		return ComplexPlane.instance;
	}

	/**
	 * Setter for the field. We choose to work with the complex plane as a simple 2
	 * dimensional real vector space equipped with a suitable product.
	 *
	 * @param realLine the real line
	 */
	public static void setRealLine(final RealLine realLine) {
		ComplexPlane.realLine = realLine;
	}

	/**
	 * the zero element (0,0)
	 */
	private final Complex zero;

	/**
	 * the unit (1,0)
	 */
	private final Complex one;

	/**
	 * the imaginary unit (0,1)
	 */
	private final Complex i;

	/**
	 * the maltrix used to multiply elements
	 */
	private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = null;

	/**
	 * the constructor
	 */
	public ComplexPlane() {
		dim = 2;
		base = new ArrayList<>();
		one = new Complex(ComplexPlane.realLine.getOne(), ComplexPlane.realLine.getZero());
		zero = new Complex(ComplexPlane.realLine.getZero(), ComplexPlane.realLine.getZero());
		i = new Complex(ComplexPlane.realLine.getZero(), ComplexPlane.realLine.getOne());
		base.add(one);
		base.add(i);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex addition(final Vector vec1, final Vector vec2) {
		final Vector ans = super.addition(vec1, vec2);
		return new Complex(((FiniteVectorMethods) ans).getCoordinates().get(one),
				((FiniteVectorMethods) ans).getCoordinates().get(i));
	}

	/**
	 * Generic constructor, returns complex number equal to
	 * {@link definitions.structures.abstr.algebra.fields.impl.ComplexPlane#getOne}
	 *
	 * @return
	 */
	public Complex complex() {
		return new Complex(0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex conjugate(final Scalar value) {
		final Complex v = (Complex) value;
		return new Complex(v.getReal(), getField().getInverseElement(v.getImag()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final Vector vec) {
		return (vec == zero) || (vec == one) || (vec == null) || (vec instanceof Complex);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex get(final double realValue) {
		final Complex newComplex = complex();
		newComplex.setValue(realValue, 0);
		return newComplex;
	}

	/**
	 * method to create a complex number using two real number as real and imaginary
	 * parts
	 *
	 * @param realValue
	 * @param imValue
	 * @return
	 */
	public Complex get(final double realValue, final double imValue) {
		final Complex newComplex = complex();
		newComplex.setValue(realValue, imValue);
		return newComplex;
	}

	/**
	 * get the imaginary unit
	 *
	 * @return the imaginary unit
	 */
	public Complex getI() {
		return i;
	}

	@Override
	public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {

		if (multiplicationMatrix == null) {

			final Scalar realOne = RealLine.getInstance().getOne();
			final Scalar realZero = RealLine.getInstance().getZero();
			final Scalar neg = (Scalar) RealLine.getInstance().getMinusOne();

			final Scalar[][] oneMat = new Scalar[][] { { realOne, realZero }, { realZero, realOne } };
			final VectorSpaceHomomorphism oneHom = MappingGenerator.getInstance()
					.getFiniteDimensionalLinearMapping(this, this, oneMat);

			final Scalar[][] iMat = new Scalar[][] { { realZero, neg }, { realOne, realZero } };
			final VectorSpaceHomomorphism iHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this,
					this, iMat);

			final Map<Vector, VectorSpaceHomomorphism> newMap = new HashMap<>();

			newMap.put(one, oneHom);
			newMap.put(i, iHom);

			setMultiplicationMatrix(newMap);
		}
		return multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex getOne() {
		return one;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex getMultiplicativeInverseElement(final Vector factor) {
		return (Complex) Field.super.getMultiplicativeInverseElement(factor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex nullVec() {
		return zero;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex product(final Vector vec1, final Vector vec2) {
		return (Complex) Field.super.product(vec1, vec2);
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(final Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex stretch(final Vector vec1, final Scalar r) {
		final Vector ans = Field.super.stretch(vec1, r);
		return new Complex(((FiniteVectorMethods) ans).getCoordinates().get(one),
				((FiniteVectorMethods) ans).getCoordinates().get(i));

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<ComplexPlane />\r";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex getMinusOne() {
		return new Complex(-1., 0.);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex getNeutralElement() {
		return (Complex) getZero();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeField getPrimeField() {
		return Generator.getInstance().getGroupGenerator().getRationals();
	}

}

package definitions.structures.abstr.algebra.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 *
 * @author ro
 *
 *         Implementation of the field of complex numbers as a singleton class.
 */
public class ComplexPlane extends FiniteDimensionalVectorSpace implements Field, RealSpace {

	/**
	 * this one is a singleton
	 */
	private static EuclideanSpace instance;

	/**
	 * Getter for the instance
	 *
	 * @return the instance
	 */
	public static EuclideanSpace getInstance() {
		return ComplexPlane.instance;
	}

	public static void setInstance(ComplexPlane complexPlane) {
		logger.debug(complexPlane);
		instance = complexPlane;
	}

	/**
	 * the field of real numbers
	 */
	@Autowired
	private static RealLine realLine;

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
		this.dim = 2;
		this.base = new ArrayList<>();
		this.one = new Complex(ComplexPlane.realLine.getOne(), ComplexPlane.realLine.getZero());
		this.zero = new Complex(ComplexPlane.realLine.getZero(), ComplexPlane.realLine.getZero());
		this.i = new Complex(ComplexPlane.realLine.getZero(), ComplexPlane.realLine.getOne());
		this.base.add(this.one);
		this.base.add(this.i);
		ComplexPlane.instance = this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex addition(final Vector vec1, final Vector vec2) {
		final Vector ans = super.addition(vec1, vec2);
		return new Complex(((FiniteVectorMethods) ans).getCoordinates().get(this.one),
				((FiniteVectorMethods) ans).getCoordinates().get(this.i));
	}

	/**
	 * Generic constructor, returns complex number equal to
	 * {@link definitions.structures.abstr.algebra.fields.impl.ComplexPlane#getOne}
	 *
	 * @return zero as a complex number
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
		return new Complex(v.getReal(), this.getField().getInverseElement(v.getImag()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final Vector vec) {
		return (vec == this.zero) || (vec == this.one) || (vec == null) || (vec instanceof Complex);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex get(final double realValue) {
		final Complex newComplex = this.complex();
		newComplex.setValue(realValue, 0);
		return newComplex;
	}

	/**
	 * method to create a complex number using two real number as real and imaginary
	 * parts
	 *
	 * @param realValue the real part
	 * @param imValue   the imaginary part
	 * @return the complex number realValue+i*imValue
	 */
	public Complex get(final double realValue, final double imValue) {
		final Complex newComplex = this.complex();
		newComplex.setValue(realValue, imValue);
		return newComplex;
	}

	/**
	 * get the imaginary unit
	 *
	 * @return the imaginary unit
	 */
	public Complex getI() {
		return this.i;
	}

	@Override
	public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {

		if (this.multiplicationMatrix == null) {

			final Scalar realOne = realLine.getOne();
			final Scalar realZero = realLine.getZero();
			final Scalar neg = (Scalar) realLine.getMinusOne();

			final Scalar[][] oneMat = new Scalar[][] { { realOne, realZero }, { realZero, realOne } };
			final VectorSpaceHomomorphism oneHom = Generator.getInstance().getMappingGenerator()
					.getFiniteDimensionalLinearMapping(this, this, oneMat);

			final Scalar[][] iMat = new Scalar[][] { { realZero, neg }, { realOne, realZero } };
			final VectorSpaceHomomorphism iHom = Generator.getInstance().getMappingGenerator()
					.getFiniteDimensionalLinearMapping(this, this, iMat);

			final Map<Vector, VectorSpaceHomomorphism> newMap = new HashMap<>();

			newMap.put(this.one, oneHom);
			newMap.put(this.i, iHom);

			this.setMultiplicationMatrix(newMap);
		}
		return this.multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Complex getOne() {
		return this.one;
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
		return this.zero;
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
		return new Complex(((FiniteVectorMethods) ans).getCoordinates().get(this.one),
				((FiniteVectorMethods) ans).getCoordinates().get(this.i));

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
		return (Complex) this.getZero();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeField getPrimeField() {
		return Generator.getInstance().getGroupGenerator().getRationals();
	}

}

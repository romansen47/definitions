package definitions.structures.abstr.algebra.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;
import exceptions.DevisionByZeroException;

/**
 *
 * @author ro
 *
 *         Implementation of the field of Quaternion numbers as a singleton
 *         class.
 */
public class QuaternionSpace extends FiniteDimensionalVectorSpace implements Field, RealSpace {

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
		if (QuaternionSpace.instance == null) {
			QuaternionSpace.instance = new QuaternionSpace();
		}
		return QuaternionSpace.instance;
	}

	/**
	 * the zero element (0,0,0,0)
	 */
	private final Quaternion zero;

	/**
	 * the unit (1,0)
	 */
	private final Quaternion one;

	/**
	 * the imaginary unit (0,1,0,0)
	 */
	private final Quaternion i;

	/**
	 * the imaginary unit (0,0,1,0)
	 */
	private final Quaternion j;

	/**
	 * the imaginary unit (0,0,0,1)
	 */
	private final Quaternion k;

	/**
	 * the maltrix used to multiply elements
	 */
	private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = null;

	/**
	 * the constructor
	 */
	public QuaternionSpace() {
		super(Generator.getInstance().getFieldGenerator().getRealLine());

		final Scalar realOne = Generator.getInstance().getFieldGenerator().getRealLine().getOne();
		final Scalar realZero = Generator.getInstance().getFieldGenerator().getRealLine().getZero();
		dim = 4;
		base = new ArrayList<>();
		one = new Quaternion(realOne, realZero, realZero, realZero) {
			@Override
			public String toString() {
				return "quaternion base element: r ";
			}
		};
		zero = new Quaternion(realZero, realZero, realZero, realZero) {
			@Override
			public String toString() {
				return "quaternion zero element ";
			}
		};
		i = new Quaternion(realZero, realOne, realZero, realZero) {
			@Override
			public String toString() {
				return "quaternion base element: i ";
			}
		};
		j = new Quaternion(realZero, realZero, realOne, realZero) {
			@Override
			public String toString() {
				return "quaternion base element: j ";
			}
		};
		k = new Quaternion(realZero, realZero, realZero, realOne) {
			@Override
			public String toString() {
				return "quaternion base element: k ";
			}
		};
		base.add(one);
		base.add(i);
		base.add(j);
		base.add(k);
		assignOrthonormalCoordinates(base, getField());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Quaternion addition(final Vector vec1, final Vector vec2) {
		final Vector ans = super.addition(vec1, vec2);
		Quaternion asQuaternion = new Quaternion(((FiniteVectorMethods) ans).getCoordinates().get(one),
				((FiniteVectorMethods) ans).getCoordinates().get(i),
				((FiniteVectorMethods) ans).getCoordinates().get(j),
				((FiniteVectorMethods) ans).getCoordinates().get(k));
		asQuaternion.getCoordinates();
		return asQuaternion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Deprecated
	@Override
	public Quaternion conjugate(final Scalar value) {
		final Quaternion v = (Quaternion) value;
		return new Quaternion(v.getReal(), this.getField().getInverseElement(v.getI()),
				this.getField().getInverseElement(v.getJ()), this.getField().getInverseElement(v.getK()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Deprecated
	@Override
	public boolean contains(final Vector vec) {
		return (vec == this.zero) || (vec == this.one) || (vec == null) || (vec instanceof Complex);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Quaternion get(final double realValue) {
		Quaternion ans = new Quaternion(realValue, 0, 0, 0);
		ans.getCoordinates();
		return ans;
	}

	/**
	 * method to create a Quaternion number using two real number as real and
	 * imaginary parts
	 *
	 * @param r the r
	 * @param i the i
	 * @param j the j
	 * @param k the k
	 * @return the Quaternion with coordinates (r,i,j,k)
	 */
	public Quaternion get(final double r, final double i, final double j, final double k) {
		return new Quaternion(r, i, j, k);
	}

	/**
	 * get the imaginary unit
	 *
	 * @return the imaginary unit
	 */
	public Quaternion getI() {
		return this.i;
	}

	@Override
	public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {

		if (this.multiplicationMatrix == null) {

			final Scalar realOne = Generator.getInstance().getFieldGenerator().getRealLine().getOne();
			final Scalar realZero = Generator.getInstance().getFieldGenerator().getRealLine().getZero();
			final Scalar neg = Generator.getInstance().getFieldGenerator().getRealLine().get(-1);

			final Scalar[][] oneMat = new Scalar[][] { { realOne, realZero, realZero, realZero },
					{ realZero, realOne, realZero, realZero }, { realZero, realZero, realOne, realZero },
					{ realZero, realZero, realZero, realOne } };

			final VectorSpaceHomomorphism oneHom = Generator.getInstance().getMappingGenerator()
					.getFiniteDimensionalLinearMapping(this, this, oneMat);

			final Scalar[][] iMat = new Scalar[][] { { realZero, neg, realZero, realZero },
					{ realOne, realZero, realZero, realZero }, { realZero, realZero, realZero, realOne },
					{ realZero, realZero, neg, realZero } };

			final VectorSpaceHomomorphism iHom = Generator.getInstance().getMappingGenerator()
					.getFiniteDimensionalLinearMapping(this, this, iMat);

			final Scalar[][] jMat = new Scalar[][] { { realZero, realZero, neg, realZero },
					{ realZero, realZero, realZero, neg }, { realOne, realZero, realZero, realZero },
					{ realZero, realOne, realZero, realZero } };

			final VectorSpaceHomomorphism jHom = Generator.getInstance().getMappingGenerator()
					.getFiniteDimensionalLinearMapping(this, this, jMat);

			final Scalar[][] kMat = new Scalar[][] { { realZero, realZero, realZero, neg },
					{ realZero, realZero, realOne, realZero }, { realZero, neg, realZero, realZero },
					{ realOne, realZero, realZero, realZero } };

			final VectorSpaceHomomorphism kHom = Generator.getInstance().getMappingGenerator()
					.getFiniteDimensionalLinearMapping(this, this, kMat);

			final Map<Vector, VectorSpaceHomomorphism> newMap = new HashMap<>();

			newMap.put(one, oneHom);
			newMap.put(i, iHom);
			newMap.put(j, jHom);
			newMap.put(k, kHom);

			this.setMultiplicationMatrix(newMap);
		}
		return this.multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Quaternion getOne() {
		return this.one;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws DevisionByZeroException
	 */
	@Override
	public Quaternion getMultiplicativeInverseElement(final Vector factor) throws DevisionByZeroException {
		return (Quaternion) Field.super.getMultiplicativeInverseElement(factor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Quaternion nullVec() {
		return this.zero;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Quaternion product(final Vector vec1, final Vector vec2) {
		return (Quaternion) Field.super.product(vec1, vec2);
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
	public Quaternion stretch(final Vector vec1, final Scalar r) {
		final Vector ans = Field.super.stretch(vec1, r);
		return new Quaternion(((FiniteVectorMethods) ans).getCoordinates().get(this.one),
				((FiniteVectorMethods) ans).getCoordinates().get(this.i),
				((FiniteVectorMethods) ans).getCoordinates().get(this.j),
				((FiniteVectorMethods) ans).getCoordinates().get(this.k));

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
	public Quaternion getMinusOne() {
		return new Quaternion(-1., 0., 0., 0.);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Quaternion getNeutralElement() {
		return (Quaternion) this.getZero();
	}

	public Quaternion getJ() {
		return j;
	}

	public Quaternion getK() {
		return k;
	}

	@Override
	public Field getField() {
		return Generator.getInstance().getFieldGenerator().getRealLine();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeField getPrimeField() {
		return Generator.getInstance().getGroupGenerator().getRationals();
	}

}

package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;

/**
 * Sobolev version of trigonometric function space.
 *
 * @author ro
 *
 */
public class TrigonometricSobolevSpace extends FiniteDimensionalSobolevSpace {

	/**
	 *
	 */
	private static final long serialVersionUID = -6195850038689778521L;

	/**
	 * Constructor.
	 *
	 * @param f      the field.
	 * @param n      the highest degree of the trigonometric polynomials.
	 * @param left   the inf of the interval.
	 * @param right  the sup of the interval.
	 * @param degree the sobolev degree.
	 */
	public TrigonometricSobolevSpace(final Field f, final int n, final double left, final double right,
			final int degree) {
		super(f, degree);
		final List<Vector> tmpBase = new ArrayList<>();
		dim = (2 * n) + 1;
		interval = new double[] { left, right };
		tmpBase.add(new GenericFunction() {
			/**
			 *
			 */
			private static final long serialVersionUID = -2594116178838181589L;
			final Scalar value = getField().get(1. / Math.sqrt(2. * Math.PI));

			@Override
			public Field getField() {
				return f;
			}

			@Override
			public String toString() {
				return "Normed constant Function: x -> " + ((Real) value).getDoubleValue();
			}

			@Override
			public Scalar value(final Scalar input) {
				return value;
			}
		});
		this.getSineFunctions(n, tmpBase);
		this.getCosineFunctions(n, tmpBase);
		base = tmpBase;
		assignOrthonormalCoordinates(tmpBase, f);
	}

	/**
	 * Method to fill a list with sine functions.
	 *
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param tmpBase the list.
	 */

	protected void getCosineFunctions(final int n, final List<Vector> tmpBase) {
		final Field f = getField();
		for (int i = 1; i < (n + 1); i++) {
			double factor = 0;
			for (int j = 0; j < (getDegree() + 1); j++) {
				factor += Math.pow(i, 2 * j);
			}
			factor = 1 / Math.sqrt(factor * Math.PI);
			final Vector cos = new Sine(f.get(factor), f.get(0.5 * Math.PI), f.get(i)) {

				/**
				 *
				 */
				private static final long serialVersionUID = -344838499735956273L;

				@Override
				public Field getField() {
					return TrigonometricSobolevSpace.this.getField();
				}
			};
			tmpBase.add(cos);

		}
	}
	/**
	 * Method to fill a list with sine functions.
	 *
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param tmpBase the list.
	 */

	protected void getSineFunctions(final int n, final List<Vector> tmpBase) {
		final Field f = getField();
		for (int i = 1; i < (n + 1); i++) {
			double factor = 0;
			for (int j = 0; j < (getDegree() + 1); j++) {
				factor += Math.pow(i, 2 * j);
			}
			factor = 1 / Math.sqrt(factor * Math.PI);
			final Vector sin = new Sine(f.get(factor), (Scalar) f.getZero(), f.get(i)) {

				/**
				 *
				 */
				private static final long serialVersionUID = -3675768767280698458L;

				@Override
				public Field getField() {
					return f;
				}
			};
			tmpBase.add(sin);
		}
	}

}
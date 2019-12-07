package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
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
	 * @param n      the highest degree of the trigonometric polynomials.
	 * @param left   the inf of the interval.
	 * @param right  the sup of the interval.
	 * @param degree the sobolev degree.
	 */
	public TrigonometricSobolevSpace(Field f, final int n, final double left, final double right, int degree) {
		super(f, degree);
		final List<Vector> tmpBase = new ArrayList<>();
		this.dim = (2 * n) + 1;
		// final EuclideanSpace space = (EuclideanSpace)
		// Generator.getGenerator().getSpacegenerator()
		// .getFiniteDimensionalVectorSpace(this.dim);
		this.interval = new double[] { left, right };
		tmpBase.add(new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2594116178838181589L;
			final Scalar value = this.getField().get(1. / Math.sqrt(2 * Math.PI));

			@Override
			public Scalar value(final Scalar input) {
				return this.value;
			}

			@Override
			public String toString() {
				return "Normed constant Function: x -> " + this.value.getValue();
			}

			@Override
			public Field getField() {
				return f;
			}
		});
		this.getSineFunctions(n, tmpBase);
		this.getCosineFunctions(n, tmpBase);
		this.base = tmpBase;
		this.setOrthoCoordinates();
	}

	private void setOrthoCoordinates() {
		for (final Vector vec1 : this.genericBaseToList()) {
			final Map<Vector, Scalar> map = new HashMap<>();
			final Scalar zero = RealLine.getInstance().getZero();
			for (final Vector vec2 : this.genericBaseToList()) {
				if (vec2.equals(vec1)) {
					map.put(vec1, RealLine.getInstance().getOne());
				} else {
					map.put(vec2, zero);
				}
			}
			((FiniteVectorMethods) vec1).setCoordinates(map);
		}
	}

	// /**
	// * Method to fill a list with sine functions.
	// *
	// * @param n the highest degree of the trigonometric polynomials.
	// * @param tmpBase the list.
	// */
	// private void getSineFunctions(final int n, final List<Vector> tmpBase) {
	// for (int i = 1; i < (n + 1); i++) {
	// final Vector sin = new Sine(Math.sqrt(Math.PI * (1 + Math.pow(i, 2))), 0, i);
	// tmpBase.add(sin);
	// }
	// }
	//
	// /**
	// * Method to fill a list with cosine functions.
	// *
	// * @param n the highest degree of the trigonometric polynomials.
	// * @param tmpBase the list.
	// */
	// private void getCosineFunctions(final int n, final List<Vector> tmpBase) {
	// for (int i = 1; i < (n + 1); i++) {
	// final Vector cos = new Sine(Math.sqrt(Math.PI * (1 + Math.pow(i, 2))), 0.5 *
	// Math.PI, i);
	// tmpBase.add(cos);
	// }
	// }

	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param tmpBase the list.
	 */
	
	protected void getSineFunctions(final int n, final List<Vector> tmpBase) {
		Field f=getField();
		for (int i = 1; i < (n + 1); i++) {
			double factor = 0;
			for (int j = 0; j < (this.getDegree() + 1); j++) {
				factor += Math.pow(i, 2 * j);
			}
			factor = 1 / Math.sqrt(factor * Math.PI);
			final Vector sin = new Sine(f.get(factor), (Scalar)f.getZero(), f.get(i)) {

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

	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d
	 * @param tmpBase the list.
	 */
	
	protected void getCosineFunctions(final int n, final List<Vector> tmpBase) {
		Field f=getField();
		for (int i = 1; i < (n + 1); i++) {
			double factor = 0;
			for (int j = 0; j < (this.getDegree() + 1); j++) {
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

}
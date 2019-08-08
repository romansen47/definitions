package definitions.structures.finitedimensional.real.vectorspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.finitedimensional.real.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;

/**
 * Sobolev version of trigonometric function space.
 * 
 * @author ro
 *
 */
public class TrigonometricSobolevSpace extends FiniteDimensionalSobolevSpace {

	/**
	 * Constructor.
	 * 
	 * @param n      the highest degree of the trigonometric polynomials.
	 * @param left   the inf of the interval.
	 * @param right  the sup of the interval.
	 * @param degree the sobolev degree.
	 */
	public TrigonometricSobolevSpace(Field field, final int n, final double left, final double right, int degree) {
		super(field, degree);
		final List<Vector> tmpBase = new ArrayList<>();
		this.dim = (2 * n) + 1;
//		final EuclideanSpace space = (EuclideanSpace) Generator.getGenerator().getSpacegenerator()
//				.getFiniteDimensionalVectorSpace(this.dim);
		this.interval = new double[] { left, right };
		tmpBase.add(new GenericFunction() {
			final Scalar value = new Real(1. / Math.sqrt(2 * Math.PI));

			@Override
			public Scalar value(final Scalar input) {
				return this.value;
			}

			@Override
			public String toString() {
				return "Normed constant Function: x -> " + this.value.getValue();
			}
		});
		this.getSineFunctions(n,tmpBase);
		this.getCosineFunctions(n,tmpBase);
		this.base = tmpBase;
		this.setOrthoCoordinates();
	}

	private void setOrthoCoordinates() {
		for (Vector vec1 : this.genericBaseToList()) {
			Map<Vector, Scalar> map = new HashMap<>();
			Scalar zero = RealLine.getInstance().getZero();
			for (Vector vec2 : this.genericBaseToList()) {
				if (vec2.equals(vec1)) {
					map.put(vec1, RealLine.getInstance().getOne());
				} else {
					map.put(vec2, zero);
				}
			}
			vec1.setCoordinates(map);
		}
	}

//	/**
//	 * Method to fill a list with sine functions.
//	 *
//	 * @param n       the highest degree of the trigonometric polynomials.
//	 * @param tmpBase the list.
//	 */
//	private void getSineFunctions(final int n, final List<Vector> tmpBase) {
//		for (int i = 1; i < (n + 1); i++) {
//			final Vector sin = new Sine(Math.sqrt(Math.PI * (1 + Math.pow(i, 2))), 0, i);
//			tmpBase.add(sin);
//		}
//	}
//
//	/**
//	 * Method to fill a list with cosine functions.
//	 *
//	 * @param n       the highest degree of the trigonometric polynomials.
//	 * @param tmpBase the list.
//	 */
//	private void getCosineFunctions(final int n, final List<Vector> tmpBase) {
//		for (int i = 1; i < (n + 1); i++) {
//			final Vector cos = new Sine(Math.sqrt(Math.PI * (1 + Math.pow(i, 2))), 0.5 * Math.PI, i);
//			tmpBase.add(cos);
//		}
//	}

	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param tmpBase the list.
	 */
	protected void getSineFunctions(final int n, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			double factor = 0;
			for (int j = 0; j < (getDegree() + 1); j++) {
				factor += Math.pow(i, 2 * j);
			}
			factor =1/( Math.sqrt(factor)*Math.sqrt(Math.PI));
			final Vector sin = new Sine(new Real(factor), RealLine.getInstance().getZero(), new Real(i));
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

		for (int i = 1; i < (n + 1); i++) {
			double factor = 0;
			for (int j = 0; j < (getDegree() + 1); j++) {
				factor += Math.pow(i, 2 * j);
			}
			factor =1/( Math.sqrt(factor)*Math.sqrt(Math.PI));
			final Vector cos = new Sine(new Real(factor), new Real(0.5 * Math.PI), new Real(i));
			tmpBase.add(cos);

		}
	}

}
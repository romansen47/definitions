package definitions.structures.finitedimensional.real.vectorspaces.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.field.impl.RealLine;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.finitedimensional.real.vectors.Real;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

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
	public TrigonometricSobolevSpace(final int n, final double left, final double right, int degree) {
		super(degree);
		final List<Vector> tmpBase = new ArrayList<>();
		this.dim = (2 * n) + 1;
		final EuclideanSpace space = (EuclideanSpace) Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(this.dim);
		this.interval = new double[] { left, right };
		tmpBase.add(new GenericFunction() {
			@Override
			public Scalar value(final Scalar input) {
				return new Real(1. / Math.sqrt(2 * Math.PI));
			}

			@Override
			public String toString() {
				return "Normed constant Function: ";
			}
		});
		this.getSineFunctions(n, 1, tmpBase);
		this.getCosineFunctions(n, 1, tmpBase);
		this.base = this.getOrthonormalBase(tmpBase);
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
	 * @param d
	 * @param tmpBase the list.
	 */
	private void getSineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector sin = new Sine(new Real(Math.sqrt(Math.abs(d) / Math.PI)),
					RealLine.getRealLine().getZero(), 
					new Real(d * i));
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
	private void getCosineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector cos = new Sine(
					new Real(Math.sqrt(Math.abs(d) / Math.PI)),
					new Real(0.5 * Math.PI),
					new Real(d * i));
			tmpBase.add(cos);
		}
	}

}
package definitions.structures.finitedimensional.real.vectorspaces.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Vector;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.Real;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Constant;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

/**
 * Fourier space.
 * 
 * @author ro
 *
 */
public class TrigonometricSpace extends FiniteDimensionalFunctionSpace {

	/**
	 * Constructor.
	 * 
	 * @param n     the highest degree of the trigonometric polynomials.
	 * @param left  the inf of the interval.
	 * @param right the sup of the interval.
	 */
	public TrigonometricSpace(final int n, final double right) {
		final double left = -right;
		final List<Vector> tmpBase = new ArrayList<>();
		this.dim = (2 * n) + 1;
		final EuclideanSpace space = (EuclideanSpace) Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(this.dim);
		final List<Vector> coordinates = space.genericBaseToList();
		this.interval = new double[] { left, right };
		tmpBase.add(new Constant(new Real(1. / Math.sqrt(2 * right))));
		this.getSineFunctions(n, Math.PI / right, tmpBase);
		this.getCosineFunctions(n, Math.PI / right, tmpBase);
		this.base = tmpBase;
	}

	/**
	 * Method to fill a list with sine functions.
	 * 
	 * @param n       the highest degree of the trigonometric polynomials.
	 * @param d
	 * @param tmpBase the list.
	 */
	private void getSineFunctions(final int n, double d, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final Vector sin = new Sine(
					new Real(Math.sqrt(Math.abs(d) / Math.PI)),
					RealLine.getInstance().getZero(),
					new Real( d * i));
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

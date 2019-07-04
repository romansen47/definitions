package definitions.structures.finitedimensional.vectorspaces;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.finitedimensional.vectors.functions.Constant;
import definitions.structures.finitedimensional.vectors.functions.Sine;

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
		final EuclideanSpace space = Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(this.dim);
		final List<Vector> coordinates = space.genericBaseToList();
		this.interval = new double[] { left, right };
		tmpBase.add(new Constant(1. / Math.sqrt(2 * right)));
		this.getSineFunctions(n, Math.PI / left, tmpBase);
		this.getCosineFunctions(n, Math.PI / left, tmpBase);
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
			final Vector sin = new Sine(Math.sqrt(Math.abs(d) / Math.PI), 0, d * i);
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
			final Vector cos = new Sine(Math.sqrt(Math.abs(d) / Math.PI), 0.5 * Math.PI, d * i);
			tmpBase.add(cos);
		}
	}

}

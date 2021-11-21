package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.vectors.specialfunctions.Constant;

/**
 * Fourier space.
 *
 * @author ro
 *
 */

public class TrigonometricSpace extends FiniteDimensionalFunctionSpace {

	/**
	 *
	 */
	private static final long serialVersionUID = 6752082006058465558L;

	/**
	 * Constructor.
	 *
	 * @param field the field.
	 * @param n     the highest degree of the trigonometric polynomials.
	 * @param right the supremum of the interval.
	 */
	public TrigonometricSpace(final Field field, final int n, final double right) {
		super(field);
		final double left = -right;
		final List<Vector> tmpBase = new ArrayList<>();
		dim = (2 * n) + 1;
		interval = new double[] { left, right };
		final Field f = field;
		tmpBase.add(new Constant(getField().get(1. / Math.sqrt(2 * right))) {
			private static final long serialVersionUID = 7393292837814311224L;

			@Override
			public Field getField() {
				return f;
			}
		});
		getSineFunctions(n, Math.PI / right, tmpBase);
		getCosineFunctions(n, Math.PI / right, tmpBase);
		base = tmpBase;
		assignOrthonormalCoordinates(base, field);
	}

}

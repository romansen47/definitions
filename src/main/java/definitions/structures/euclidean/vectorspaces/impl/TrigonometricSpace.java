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
		this.dim = (2 * n) + 1;
		this.interval = new double[] { left, right };
		final Field f = field;
		tmpBase.add(new Constant(this.getField().get(1. / Math.sqrt(2 * right))) {
			@Override
			public Field getField() {
				return f;
			}
		});
		this.getSineFunctions(n, Math.PI / right, tmpBase);
		this.getCosineFunctions(n, Math.PI / right, tmpBase);
		this.base = tmpBase;
		this.assignOrthonormalCoordinates(this.base, field);
	}

}

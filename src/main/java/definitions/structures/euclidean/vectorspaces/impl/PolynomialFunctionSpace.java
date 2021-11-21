package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.vectors.impl.Monome;

public class PolynomialFunctionSpace extends FiniteDimensionalFunctionSpace {

	/**
	 *
	 */
	private static final long serialVersionUID = 4394456226881571619L;

	protected PolynomialFunctionSpace(final Field field, final int maxDegree, final double right) {
		super(field);
		base = new ArrayList<>();
		prepare(maxDegree, right);
	}

	public PolynomialFunctionSpace(final Field field, final int maxDegree, final double right, final boolean ortho) {
		this(field, maxDegree, right);
		if (ortho) {
			base = getOrthonormalBase(base);
		}
	}

	private void prepare(final int maxDegree, final double right) {
		final Field f = getField();
		interval = new double[] { -right, right };
		base.clear();
		for (int i = 0; i < (maxDegree + 1); i++) {
			base.add(new Monome(i) {
				private static final long serialVersionUID = -3227300408323170816L;

				@Override
				public Field getField() {
					return f;
				}
			});
		}
		dim = base.size();
	}

}

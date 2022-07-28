package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.vectors.impl.Monome;
import exceptions.DevisionByZeroException;

public class PolynomialFunctionSpace extends FiniteDimensionalFunctionSpace {

	protected PolynomialFunctionSpace(final Field field, final int fourierDegree, final double right) {
		super(field);
		this.base = new ArrayList<>();
		this.prepare(fourierDegree, right);
	}

	public PolynomialFunctionSpace(final Field field, final int fourierDegree, final double right, final boolean ortho)
			throws DevisionByZeroException {
		this(field, fourierDegree, right);
		if (ortho) {
			this.base = this.getOrthonormalBase(this.base);
		}
	}

	private void prepare(final int maxDegree, final double right) {
		final Field f = this.getField();
		this.interval = new double[] { -right, right };
		this.base.clear();
		for (int i = 0; i < (maxDegree + 1); i++) {
			this.base.add(new Monome(i) {
				@Override
				public Field getField() {
					return f;
				}
			});
		}
		this.dim = this.base.size();
	}

}

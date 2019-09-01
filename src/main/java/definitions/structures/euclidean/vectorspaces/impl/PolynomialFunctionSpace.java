package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;
import java.util.Objects;

import definitions.structures.abstr.fields.Field;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.vectors.impl.Monome;

public class PolynomialFunctionSpace extends FiniteDimensionalFunctionSpace {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4394456226881571619L;

	protected PolynomialFunctionSpace(Field field, int maxDegree, double right) {
		super(field);
		this.base = new ArrayList<>();
		this.prepare(maxDegree, right);
	}

	public PolynomialFunctionSpace(Field field, int maxDegree, double right, boolean ortho) {
		this(field, maxDegree, right);
		if (ortho) {
			this.base = this.getOrthonormalBase(this.base);
		}
	}

	private void prepare(int maxDegree, double right) {
		final Field f = this.getField();
		this.interval = new double[] { -right, right };
		this.base.clear();
		for (int i = 0; i < (maxDegree + 1); i++) {
			this.base.add(new Monome(i) {
				private static final long serialVersionUID = -3227300408323170816L;

				@Override
				public Field getField() {
					return f;
				}
			});
		}
		this.dim = this.base.size();
	}

}

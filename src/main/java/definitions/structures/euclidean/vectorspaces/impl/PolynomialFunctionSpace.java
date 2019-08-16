package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;

import definitions.structures.abstr.fields.Field;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.vectors.impl.Monome;

public class PolynomialFunctionSpace extends FiniteDimensionalFunctionSpace {

	protected PolynomialFunctionSpace(Field field, int maxDegree, double right) {
		super(field);
		this.base = new ArrayList<>();
		this.prepare(maxDegree, right);
	}

	protected PolynomialFunctionSpace(Field field, int maxDegree, double right, boolean ortho) {
		this(field, maxDegree, right);
		if (ortho) {
			this.base = this.getOrthonormalBase(this.base);
		}
	}

	private void prepare(int maxDegree, double right) {
		this.interval = new double[] { -right, right };
		this.base.clear();
		for (int i = 0; i < (maxDegree + 1); i++) {
			this.base.add(new Monome(i) {
				@Override
				public Field getField() {
					return field;
				}
			});
		}
		this.dim = this.base.size();
	}

}

package definitions.structures.finitedimensional.real.vectorspaces.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.impl.Monome;

public class PolynomialFunctionSpace extends FiniteDimensionalFunctionSpace {

	protected PolynomialFunctionSpace(int maxDegree, double right) {
		this.interval = new double[] { -right, right };
		final List<Vector> tmpBase = new ArrayList<>();
		for (int i = 0; i < (maxDegree + 1); i++) {
			tmpBase.add(new Monome(i));
		}
		this.base = this.getOrthonormalBase(tmpBase);
		this.dim=this.base.size();
	}
}

package definitions.structures.finitedimensional.real.vectorspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.impl.Monome;

public class PolynomialFunctionSpace extends FiniteDimensionalFunctionSpace {

	protected PolynomialFunctionSpace(int maxDegree, double right) {
		super();
		base=new ArrayList<>();
		prepare(maxDegree, right);
	}

	protected PolynomialFunctionSpace(int maxDegree, double right, boolean ortho) {
		this(maxDegree, right);
		if (ortho) {
			this.base = getOrthonormalBase(this.base);
		}
	}

	private void prepare(int maxDegree, double right) {
		this.interval = new double[] { -right, right };
		this.base.clear();
		for (int i = 0; i < (maxDegree + 1); i++) {
			this.base.add(new Monome(i));
		}
		this.dim = this.base.size();
	}

}

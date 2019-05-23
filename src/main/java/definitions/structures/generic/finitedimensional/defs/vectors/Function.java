package definitions.structures.generic.finitedimensional.defs.vectors;

import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;

public interface Function extends FiniteVector {

	default double value(double input) throws Throwable {
		double ans = 0;
		for (FiniteVector fun : this.getCoordinates().keySet()) {
			ans += ((Function) fun).value(input) * getCoordinates().get(fun);
		}
		return ans;
	}

	default boolean equals(Function other, IFiniteDimensionalFunctionSpace source) throws Throwable {
		final int n = 100;
		double a = source.getIntervall()[0];
		double b = source.getIntervall()[1];
		for (int i = 0; i < n; i++) {
			if (value(a + i * (b - a) / 99.) != other.value(a + i * (b - a) / 99.)) {
				return false;
			}
		}
		return true;
	}

}

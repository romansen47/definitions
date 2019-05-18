package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions;

import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFunction extends IFiniteVector {

	default double value(double input) throws Throwable {
		double ans = 0;
		for (IFiniteVector fun : getCoordinates().keySet()) {
			ans += ((IFunction) fun).value(input) * getCoordinates().get(fun);
		}
		return ans;
	}

}

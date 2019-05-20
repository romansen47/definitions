package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.subspaces.IFiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFunction extends IFiniteVector {

	default double value(double input) throws Throwable {
		double ans = 0;
		for (IFiniteVector fun : this.getCoordinates().keySet()) {
			ans += ((IFunction)fun).value(input) * getCoordinates().get(fun);
		}
		return ans;
	}

	default boolean equals(IFunction other,IFiniteDimensionalFunctionSpace source) throws Throwable {
		final int n=100;
		boolean ans=true;
		double a=source.getIntervall()[0];
		double b=source.getIntervall()[1];
		for (int i=0;i<n;i++) {
			if (value(a+i/99.)!=other.value(a+i/99.)) {
				return false;
			}
		}
		return true;
	}
	

}

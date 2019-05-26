package definitions.structures.generic.finitedimensional.defs.vectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public interface Function extends FiniteVector {

	default double getLeft() {
		return -Math.PI;
	}

	default double getRight() {
		return Math.PI;
	}
	
	double value(double input) throws Throwable;

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
	
//	@Override
//	default double[] getGenericCoordinates() throws Throwable {
//		final double[] vector = new double[getDim()];
//		int i = 0;
//		List<Vector> base=new ArrayList<>();
//		for (Vector baseVec:getCoordinates().keySet()) {
//			base.add(baseVec);
//		}
//		IFiniteDimensionalFunctionSpace space=
//				SpaceGenerator.getInstance().
//				getFiniteDimensionalFunctionSpace(base,getLeft(),getRight());
//		for (final Vector basevec : getGenericBase()) {
//			vector[i] = space.product(this,basevec);
//			i++;
//		}
//		return vector;
//	}

}

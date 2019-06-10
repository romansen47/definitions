package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.Constant;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public class TrigonometricSpace extends FiniteDimensionalFunctionSpace {

	public TrigonometricSpace(int n, double left, double right) throws Throwable {
		List<Vector> tmpBase = new ArrayList<>();
		this.dim = 2 * n + 1;
		EuclideanSpace space = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(this.dim);
		List<Vector> coordinates = space.genericBaseToList();
		tmpBase.add(new Constant(coordinates.get(0).getGenericCoordinates(), 1.));
		getSineFunctions(n, coordinates, tmpBase);
		getCosineFunctions(n, coordinates, tmpBase);
		base = tmpBase;
		base = getOrthonormalBase(tmpBase);
	}

	private void getSineFunctions(int n, List<Vector> coordinates, List<Vector> tmpBase) throws Throwable {
		for (int i = 0; i < n; i++) {
			final int j = i;
			Vector sin = new FunctionTuple(coordinates.get(2 * j + 1).getCoordinates()) {
				@Override
				public double value(double input) {
					return Math.sin(j * input);
				}
			};
			tmpBase.add(sin);
		}
	}

	private void getCosineFunctions(int n, List<Vector> coordinates, List<Vector> tmpBase) throws Throwable {
		for (int i = 1; i < n + 1; i++) {
			final int j = i;
			Vector sin = new FunctionTuple(coordinates.get(2 * j).getCoordinates()) {
				@Override
				public double value(double input) {
					return Math.cos(j * input);
				}
			};
			tmpBase.add(sin);
		}
	}

}

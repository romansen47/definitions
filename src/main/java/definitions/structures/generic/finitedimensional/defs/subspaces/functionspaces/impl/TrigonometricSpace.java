package definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class TrigonometricSpace extends FiniteDimensionalFunctionSpace {

	public TrigonometricSpace(int n, double left, double right) throws Throwable {
		List<Vector> tmpBase = new ArrayList<>();
		this.dim = 2 * n + 1;
		EuclideanSpace space = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(this.dim);
		List<Vector> coordinates = space.genericBaseToList();
		intervall = new double[] { left, right };
		tmpBase.add(new GenericFunction() {
			@Override
			public double value(double input) {
				return 1. / Math.sqrt(2 * Math.PI);
			}
		});
		getSineFunctions(n, coordinates, tmpBase);
		getCosineFunctions(n, coordinates, tmpBase);
		base = tmpBase;
	}

	private void getSineFunctions(int n, List<Vector> coordinates, List<Vector> tmpBase) throws Throwable {
		for (int i = 1; i < n + 1; i++) {
			final int j = i;
			Vector sin = new GenericFunction() {
				@Override
				public double value(double input) {
					return Math.sin(j * input) / Math.sqrt(Math.PI);
				}

				@Override
				public String toString() {
					return "x -> " + 1 / Math.sqrt(Math.PI) + "*sin(" + j + "*x)";
				}
			};
			tmpBase.add(sin);
		}
	}

	private void getCosineFunctions(int n, List<Vector> coordinates, List<Vector> tmpBase) throws Throwable {
		for (int i = 1; i < n + 1; i++) {
			final int j = i;
			Vector sin = new GenericFunction() {
				@Override
				public double value(double input) {
					return Math.cos(j * input) / Math.sqrt(Math.PI);
				}

				@Override
				public String toString() {
					return "x -> " + 1 / Math.sqrt(Math.PI) + "*cos(" + j + "*x)";
				}
			};
			tmpBase.add(sin);
		}
	}

}

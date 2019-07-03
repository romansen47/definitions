package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class TrigonometricSpace extends FiniteDimensionalFunctionSpace {

	public TrigonometricSpace(final int n, final double left, final double right) {
		final List<Vector> tmpBase = new ArrayList<>();
		this.dim = (2 * n) + 1;
		final EuclideanSpace space = Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(this.dim);
		final List<Vector> coordinates = space.genericBaseToList();
		this.interval = new double[] { left, right };
		tmpBase.add(new GenericFunction() {
			@Override
			public double value(final double input) {
				return 1. / Math.sqrt(2 * Math.PI);
			}

			@Override
			public String toString() {
				return "Normed constant Function: ";
			}
		});
		this.getSineFunctions(n, coordinates, tmpBase);
		this.getCosineFunctions(n, coordinates, tmpBase);
		this.base = tmpBase;
	}

	private void getSineFunctions(final int n, final List<Vector> coordinates, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final int j = i;
			final Vector sin = new GenericFunction() {
				@Override
				public double value(final double input) {
					return Math.sin(j * input) / Math.sqrt(Math.PI);
				}

				@Override
				public String toString() {
					return "x -> " + (1 / Math.sqrt(Math.PI)) + "*sin(" + j + "*x)";
				}
			};
			tmpBase.add(sin);
		}
	}

	private void getCosineFunctions(final int n, final List<Vector> coordinates, final List<Vector> tmpBase) {
		for (int i = 1; i < (n + 1); i++) {
			final int j = i;
			final Vector sin = new GenericFunction() {
				@Override
				public double value(final double input) {
					return Math.cos(j * input) / Math.sqrt(Math.PI);
				}

				@Override
				public String toString() {
					return "x -> " + (1 / Math.sqrt(Math.PI)) + "*cos(" + j + "*x)";
				}
			};
			tmpBase.add(sin);
		}
	}

}
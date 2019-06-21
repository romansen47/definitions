package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class TrigonometricSobolevSpace extends FiniteDimensionalSobolevSpace {

	public TrigonometricSobolevSpace(final int n, final double left, final double right) throws Throwable {
		final List<Vector> tmpBase = new ArrayList<>();
		this.dim = (2 * n) + 1;
		final EuclideanSpace space = Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(this.dim);
		final List<Vector> coordinates = space.genericBaseToList();
		this.interval = new double[] { left, right };
		tmpBase.add(new GenericFunction() {
			@Override
			public double value(final double input) {
				return 1. / Math.sqrt(2*Math.PI);
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

	private void getSineFunctions(final int n, final List<Vector> coordinates, final List<Vector> tmpBase)
			throws Throwable {
		for (int i = 1; i < (n + 1); i++) {
			final int j = i;
			final Vector sin = new GenericFunction() {
				private double norm=Math.sqrt(Math.PI*(1+Math.pow(j,2)));
				@Override
				public double value(final double input) {
					return Math.sin(j * input) / norm;
				}

				@Override
				public String toString() {
					return "x -> " + 1 / norm + "*sin(" + j + "*x)";
				}
			};
			tmpBase.add(sin);
		}
	}

	private void getCosineFunctions(final int n, final List<Vector> coordinates, final List<Vector> tmpBase)
			throws Throwable {
		for (int i = 1; i < (n + 1); i++) {
			final int j = i;
			final Vector cos = new GenericFunction() {
				private double norm=Math.sqrt(Math.PI*(1+Math.pow(j,2)));
				@Override
				public double value(final double input) {
					return Math.cos(j * input) / norm;
				}

				@Override
				public String toString() {
					return "x -> " + 1/norm + "*cos(" + j + "*x)";
				}
			};
			tmpBase.add(cos);
		}
	}

}
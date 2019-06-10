package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Constant;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.VectorGenerator;

public interface ISpaceGenerator {

	Map<Integer, EuclideanSpace> getCachedCoordinateSpaces();

	Map<Integer, IFiniteDimensionalFunctionSpace> getCachedFunctionSpaces();

	default EuclideanSpace getFiniteDimensionalVectorSpace(int dim) throws Throwable {
		if (!getCachedCoordinateSpaces().containsKey(dim)) {
			final List<Vector> basetmp = new ArrayList<Vector>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(VectorGenerator.getInstance().getFiniteVector(dim));
			}
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (i == j) {
						basetmp.get(i).getCoordinates().put(basetmp.get(i), 1.);
					} else {
						basetmp.get(i).getCoordinates().put(basetmp.get(j), 0.);
					}
				}
			}
			getCachedCoordinateSpaces().put(Integer.valueOf(dim), new FiniteDimensionalVectorSpace(basetmp));
		}
		return getCachedCoordinateSpaces().get(dim);
	}

	default IFiniteDimensionalFunctionSpace getFiniteDimensionalFunctionSpace(List<Vector> genericBase, double left,
			double right) throws Throwable {
		IFiniteDimensionalFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if (space != null && space.getIntervall()[0] == left && space.getIntervall()[1] == right) {
			return space;
		}
		FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalFunctionSpace(genericBase, left, right);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default IFiniteDimensionalFunctionSpace getTrigonometricSpace(int n) throws Throwable {
		List<Vector> tmpBase = new ArrayList<>();
		EuclideanSpace space = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(2 * n + 1);
		List<Vector> coordinates = space.genericBaseToList();
		tmpBase.add(new Constant(coordinates.get(0).getGenericCoordinates(), 1. / Math.sqrt(2 * Math.PI)));
		for (int i = 0; i < n; i++) {
			final int j = i + 1;
			Vector sin = new FunctionTuple(coordinates.get(2 * i + 1).getCoordinates()) {
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
		for (int i = 1; i < n + 1; i++) {
			final int j = i;
			Vector cos = new FunctionTuple(coordinates.get(2 * j).getCoordinates()) {
				@Override
				public double value(double input) {
					return Math.cos(j * input) / Math.sqrt(Math.PI);
				}

				@Override
				public String toString() {
					return "x -> " + 1 / Math.sqrt(Math.PI) + "*cos(" + j + "*x)";
				}
			};
			tmpBase.add(cos);
		}
		return getFiniteDimensionalFunctionSpace(tmpBase, -Math.PI, Math.PI);
	}

	default IFiniteDimensionalFunctionSpace getTrigonometricFunctionSpaceWithLinearGrowth(int n, Function fun)
			throws Throwable {
		List<Vector> tmpBase = new ArrayList<>();
		EuclideanSpace space = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(2 * n + 2);
		List<Vector> coordinates = space.genericBaseToList();
		tmpBase.add(new Constant(coordinates.get(0).getGenericCoordinates(), 1. / Math.sqrt(2 * Math.PI)));
		for (int i = 0; i < n; i++) {
			final int j = i + 1;
			Vector sin = new FunctionTuple(coordinates.get(2 * i + 1).getCoordinates()) {
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
		for (int i = 1; i < n + 1; i++) {
			final int j = i;
			Vector cos = new FunctionTuple(coordinates.get(2 * j).getCoordinates()) {
				@Override
				public double value(double input) {
					return Math.cos(j * input) / Math.sqrt(Math.PI);
				}

				@Override
				public String toString() {
					return "x -> " + 1 / Math.sqrt(Math.PI) + "*cos(" + j + "*x)";
				}
			};
			tmpBase.add(cos);
		}
		double[] lastCoords = new double[2 * n + 2];
		lastCoords[lastCoords.length - 1] = 1;
		Function newFun = new FunctionTuple(lastCoords) {
			@Override
			public double value(double input) throws Throwable {
				return fun.value(input);
			}
		};

		tmpBase.add(fun);
		return SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(tmpBase, -Math.PI, Math.PI);
	}

}

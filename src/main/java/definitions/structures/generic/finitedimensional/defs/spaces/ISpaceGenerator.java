package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.TrigonometricSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Constant;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Monome;

public interface ISpaceGenerator {

	Map<Integer, EuclideanSpace> getCachedCoordinateSpaces();

	Map<Integer, IFiniteDimensionalFunctionSpace> getCachedFunctionSpaces();

	default EuclideanSpace getFiniteDimensionalVectorSpace(final int dim) throws Throwable {
		if (!getCachedCoordinateSpaces().containsKey(dim)) {
			final List<Vector> basetmp = new ArrayList<Vector>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(Generator.getGenerator().getVectorgenerator().getFiniteVector(dim));
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

	default IFiniteDimensionalFunctionSpace getFiniteDimensionalFunctionSpace(final List<Vector> genericBase,
			final double left, final double right) throws Throwable {
		final IFiniteDimensionalFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if ((space != null) && (space.getInterval()[0] == left) && (space.getInterval()[1] == right)) {
			return space;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalFunctionSpace(genericBase, left, right);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default IFiniteDimensionalFunctionSpace getFiniteDimensionalSobolevSpace(final List<Vector> genericBase,
			final double left, final double right,final int degree) throws Throwable {
		final IFiniteDimensionalFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if ((space != null) && (space instanceof FiniteDimensionalSobolevSpace) && (space.getInterval()[0] == left)
				&& (space.getInterval()[1] == right)) {
			return space;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalSobolevSpace(genericBase, left, right,degree);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default IFiniteDimensionalFunctionSpace getTrigonometricSpace(final int n) throws Throwable {
		return new TrigonometricSpace(n, -Math.PI, Math.PI);
	}
	
	default IFiniteDimensionalFunctionSpace getTrigonometricSobolevSpace(final int n,final int degree) throws Throwable {
		return new TrigonometricSobolevSpace(n, -Math.PI, Math.PI,degree);
	}

	default IFiniteDimensionalFunctionSpace getFiniteDimensionalSobolevSpace(
			final IFiniteDimensionalFunctionSpace space,final int degree) throws Throwable {
		return new FiniteDimensionalSobolevSpace(space,degree);
	}

	default IFiniteDimensionalFunctionSpace getTrigonometricFunctionSpaceWithLinearGrowth(final int n,
			final Function fun) throws Throwable {
		final List<Vector> tmpBase = new ArrayList<>();
		final EuclideanSpace space = Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace((2 * n) + 2);
		final List<Vector> coordinates = space.genericBaseToList();
		tmpBase.add(new Constant(coordinates.get(0).getGenericCoordinates(), 1. / Math.sqrt(2 * Math.PI)));
		for (int i = 0; i < n; i++) {
			final int j = i + 1;
			final Vector sin = new FunctionTuple(coordinates.get((2 * i) + 1).getCoordinates()) {
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
		for (int i = 1; i < (n + 1); i++) {
			final int j = i;
			final Vector cos = new FunctionTuple(coordinates.get(2 * j).getCoordinates()) {
				@Override
				public double value(final double input) {
					return Math.cos(j * input) / Math.sqrt(Math.PI);
				}

				@Override
				public String toString() {
					return "x -> " + (1 / Math.sqrt(Math.PI)) + "*cos(" + j + "*x)";
				}
			};
			tmpBase.add(cos);
		}
		final double[] lastCoords = new double[(2 * n) + 2];
		lastCoords[lastCoords.length - 1] = 1;
		final Function newFun = new FunctionTuple(lastCoords) {
			@Override
			public double value(final double input) throws Throwable {
				return fun.value(input);
			}
		};
		tmpBase.add(newFun);
		return SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(tmpBase, -Math.PI, Math.PI);
	}

	void setCachedCoordinateSpaces(ISpaceGenerator readObject);

	void setCachedFunctionSpaces(ISpaceGenerator gen);

	default VectorSpace getPolynomialFunctionSpace(int maxDegree,double left,double right) throws Throwable {
		int key=(int) (maxDegree*(1.e3+left)*(1.e3+right));
		List<Vector> base = new ArrayList<>();
		if (getCachedFunctionSpaces().containsKey(key)) {
			return getCachedFunctionSpaces().get(key);
		}
		for (int i = 0; i < maxDegree + 1; i++) {
			base.add(new Monome(i));
		}
		VectorSpace ans=Generator.getGenerator().getFiniteDimensionalFunctionSpace(base, left, right);
		getCachedFunctionSpaces().put(key,(IFiniteDimensionalFunctionSpace) ans);
		return ans;
	}
	
	default VectorSpace getPolynomialSobolevSpace(int maxDegree,double left,double right,int degree) throws Throwable {
		return Generator.getGenerator()
				.getFiniteDimensionalSobolevSpace((IFiniteDimensionalFunctionSpace) getPolynomialFunctionSpace(maxDegree, left, right),degree);
	}
}

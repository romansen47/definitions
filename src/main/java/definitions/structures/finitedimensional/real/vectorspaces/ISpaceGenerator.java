package definitions.structures.finitedimensional.real.vectorspaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.NormedSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.Real;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.LinearFunction;
import definitions.structures.finitedimensional.real.vectorspaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;
import definitions.structures.finitedimensional.real.vectorspaces.impl.TrigonometricSobolevSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.TrigonometricSpace;
import exceptions.WrongClassException;

public interface ISpaceGenerator {

	Map<Integer, EuclideanSpace> getCachedCoordinateSpaces();

	Map<Integer, EuclideanFunctionSpace> getCachedFunctionSpaces();

	default VectorSpace getFiniteDimensionalVectorSpace(final int dim) {
		if (dim == 1) {
			return RealLine.getInstance();
		}
		if (!getCachedCoordinateSpaces().containsKey(dim)) {
			final List<Vector> basetmp = new ArrayList<>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(Generator.getGenerator().getVectorgenerator().getFiniteVector(dim));
			}
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (i == j) {
						basetmp.get(i).getCoordinates().put(basetmp.get(i), RealLine.getInstance().getOne());
					} else {
						basetmp.get(i).getCoordinates().put(basetmp.get(j), RealLine.getInstance().getZero());
					}
				}
			}
			getCachedCoordinateSpaces().put(Integer.valueOf(dim), new FiniteDimensionalVectorSpace(basetmp));
		}
		return getCachedCoordinateSpaces().get(dim);
	}

	default EuclideanFunctionSpace getFiniteDimensionalFunctionSpace(final List<Vector> genericBase, final double left,
			final double right) {
		final EuclideanFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if ((space != null) && (space.getInterval()[0] == left) && (space.getInterval()[1] == right)) {
			return space;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalFunctionSpace(genericBase, left, right,true);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(final List<Vector> genericBase, final double left,
			final double right, final int degree) {
		final EuclideanFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if ((space != null) && (space instanceof FiniteDimensionalSobolevSpace) && (space.getInterval()[0] == left)
				&& (space.getInterval()[1] == right)) {
			return space;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalSobolevSpace(genericBase, left, right,
				degree);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default EuclideanFunctionSpace getTrigonometricSpace(final int n) {
		return new TrigonometricSpace(n, Math.PI);
	}

	default EuclideanFunctionSpace getTrigonometricSpace(final int n, double right) {
		return new TrigonometricSpace(n, right);
	}

	default EuclideanFunctionSpace getTrigonometricSobolevSpace(final int n, final int degree) {
		if (degree == 0) {
			return getTrigonometricSpace(n);
		}
		EuclideanFunctionSpace ans = new TrigonometricSobolevSpace(n, -Math.PI, Math.PI, degree);
		((FiniteDimensionalSobolevSpace) ans).getDerivativeBuilder();
		return ans;
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(final EuclideanFunctionSpace space,
			final int degree) {
		if (degree == 0) {
			return space;
		}
		FiniteDimensionalSobolevSpace ans = new FiniteDimensionalSobolevSpace(space, degree,true);
		ans.getDerivativeBuilder();
		return ans;
	}

	default EuclideanFunctionSpace getTrigonometricFunctionSpaceWithLinearGrowth(final int n)
			throws WrongClassException {
		return (EuclideanFunctionSpace) extend(getTrigonometricSpace(n),
				new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()));
	}

	default EuclideanFunctionSpace getTrigonometricFunctionSpaceWithLinearGrowth(final int n, double right)
			throws WrongClassException {
		return (EuclideanFunctionSpace) extend(getTrigonometricSpace(n, right),
				new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()));
	}

	EuclideanFunctionSpace getPolynomialFunctionSpace(final int n, double right,boolean ortho);

	void setCachedCoordinateSpaces(ISpaceGenerator readObject);

	void setCachedFunctionSpaces(ISpaceGenerator gen);

	default VectorSpace getPolynomialSobolevSpace(int maxDegree, double right, int degree) {
		EuclideanFunctionSpace polynoms=getPolynomialFunctionSpace(maxDegree, right,false);
		VectorSpace ans= Generator.getGenerator().getFiniteDimensionalSobolevSpace(polynoms,degree);
		((FiniteDimensionalVectorSpace) ans).setBase(((EuclideanSpace)ans).getOrthonormalBase(((EuclideanSpace) ans).genericBaseToList()));
		return ans;
	}

	default EuclideanSpace extend(VectorSpace space, Vector fun) throws WrongClassException {
		final EuclideanSpace asEuclidean = (EuclideanSpace) space;
		if (fun instanceof Function) {
			final List<Vector> base = asEuclidean.genericBaseToList();
			final List<Vector> newBase = new ArrayList<>();
			for (Vector vec : base) {
				newBase.add(vec);
			}
//			// ???????????????????????
//			Vector ortho = asEuclidean.normalize(fun);
//			// ???????????????????????
//			for (final Vector baseVec : base) {
//				final Vector product = asEuclidean.stretch(baseVec, asEuclidean.innerProduct(ortho, baseVec));
//				final Vector summand = asEuclidean.stretch(product, new Real(-1.));
//				ortho = asEuclidean.normalize(asEuclidean.add(ortho, summand));
////				newBase.add(baseVec);
//				//@TODO: nur EIN Vector muss hinzugefügt werden, urspruengliche Basis ist bereits orthonormal.
//			}
//			base.add(ortho);
//			if (asEuclidean instanceof FiniteDimensionalSobolevSpace) {
//				return SpaceGenerator.getInstance().getFiniteDimensionalSobolevSpace(base,
//						((FiniteDimensionalSobolevSpace) asEuclidean).getInterval()[0],
//						((FiniteDimensionalSobolevSpace) asEuclidean).getInterval()[1],
//						((FiniteDimensionalSobolevSpace) asEuclidean).getDegree());
//			} else {
//				if (asEuclidean instanceof FiniteDimensionalFunctionSpace) {
//					return SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(base,
//							((FiniteDimensionalFunctionSpace) asEuclidean).getInterval()[0],
//							((FiniteDimensionalFunctionSpace) asEuclidean).getInterval()[1]);
//				}
//			}
//			return getFiniteDimensionalVectorSpace(base);
			final Function projection = ((Function) fun).getProjection((EuclideanSpace) space);
			final Function diff = (Function) space.add(fun, space.stretch(projection, new Real(-1.)));
			final Function newBaseElement = (Function) ((NormedSpace) space).normalize(diff);
			newBase.add(newBaseElement);
			if (space instanceof FunctionSpace) {
				return SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(newBase,((FunctionSpace) space).getLeft(),((FunctionSpace) space).getRight());
			}
			return SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(newBase);
		} else {
			throw new WrongClassException("Input should be a function, not a vector.");
		}
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(List<Vector> newBase) {
		return new FiniteDimensionalVectorSpace(newBase);
	}
}
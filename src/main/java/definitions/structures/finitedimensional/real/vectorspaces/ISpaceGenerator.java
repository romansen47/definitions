package definitions.structures.finitedimensional.real.vectorspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.FunctionSpace;
import definitions.structures.abstr.NormedSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.abstr.impl.LinearMapping;
import definitions.structures.field.Field;
import definitions.structures.field.impl.ComplexPlane;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.finitedimensional.real.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.finitedimensional.real.mappings.impl.DerivativeOperator;
import definitions.structures.finitedimensional.real.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.LinearFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;
import definitions.structures.finitedimensional.real.vectorspaces.impl.TrigonometricSobolevSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.TrigonometricSpace;
import exceptions.WrongClassException;

public interface ISpaceGenerator {

	final EuclideanSpace realSpace = RealLine.getInstance();

	Map<Integer, EuclideanSpace> getCachedCoordinateSpaces();

	Map<Integer, EuclideanFunctionSpace> getCachedFunctionSpaces();


	
	default VectorSpace getFiniteDimensionalComplexSpace(final int dim) {
		final Field field = (Field) ComplexPlane.getInstance();
		if (dim == 1) {
			return field;
		}
		if (!getCachedCoordinateSpaces().containsKey(dim)) {
			final List<Vector> basetmp = new ArrayList<>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(Generator.getGenerator().getVectorgenerator().getFiniteVector(dim));
			}
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (i == j) {
						basetmp.get(i).getCoordinates().put(basetmp.get(i), (Scalar) field.getOne());
					} else {
						basetmp.get(i).getCoordinates().put(basetmp.get(j), (Scalar) field.getZero());
					}
				}
			}
			getCachedCoordinateSpaces().put(Integer.valueOf(dim), new FiniteDimensionalVectorSpace(field, basetmp));
		}
		return getCachedCoordinateSpaces().get(dim);
	}
	
	default VectorSpace getFiniteDimensionalVectorSpace(final int dim) {
		final Field field = RealLine.getInstance();
		if (dim == 1) {
			return field;
		}
		if (!getCachedCoordinateSpaces().containsKey(dim)) {
			final List<Vector> basetmp = new ArrayList<>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(Generator.getGenerator().getVectorgenerator().getFiniteVector(dim));
			}
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (i == j) {
						basetmp.get(i).getCoordinates().put(basetmp.get(i), (Scalar) field.getOne());
					} else {
						basetmp.get(i).getCoordinates().put(basetmp.get(j), (Scalar) field.getZero());
					}
				}
			}
			getCachedCoordinateSpaces().put(Integer.valueOf(dim), new FiniteDimensionalVectorSpace(field, basetmp));
		}
		return getCachedCoordinateSpaces().get(dim);
	}

	default EuclideanFunctionSpace getFiniteDimensionalFunctionSpace(Field field, final List<Vector> genericBase,
			final double left, final double right) {
		final EuclideanFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if ((space != null) && (space.getInterval()[0] == left) && (space.getInterval()[1] == right)) {
			return space;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalFunctionSpace(field, genericBase, left,
				right, true);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree) {
		final EuclideanFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if ((space != null) && (space instanceof FiniteDimensionalSobolevSpace) && (space.getInterval()[0] == left)
				&& (space.getInterval()[1] == right)) {
			return space;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalSobolevSpace(field, genericBase, left,
				right, degree);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default EuclideanFunctionSpace getTrigonometricSpace(Field field, final int n) {
		return new TrigonometricSpace(field, n, Math.PI);
	}

	default EuclideanFunctionSpace getTrigonometricSpace(Field field, final int n, double right) {
		return new TrigonometricSpace(field, n, right);
	}

	default EuclideanFunctionSpace getTrigonometricSobolevSpace(Field field, final int n, final int degree) {
		if (degree == 0) {
			return getTrigonometricSpace(field, n);
		}
		EuclideanFunctionSpace ans = new TrigonometricSobolevSpace(field, n, -Math.PI, Math.PI, degree);
		createTrigonometricDerivativeBuilder(ans);// ((FiniteDimensionalSobolevSpace) ans).getDerivativeBuilder();
		return ans;
	}

	default void createTrigonometricDerivativeBuilder(EuclideanFunctionSpace ans) {
		List<Vector> base = ans.genericBaseToList();
		final Field realLine = RealLine.getInstance();
		final Scalar unit = (Scalar) realLine.getOne();
		final Scalar zero = (Scalar) realLine.getZero();
		Map<Vector, Map<Vector, Scalar>> coordinatesMap = new HashMap<>();
		for (Vector vec : base) {
			Map<Vector, Scalar> tmp = new HashMap<>();
			if (vec instanceof Sine) {
				final Scalar freq = ((Sine) vec).getFrequency();
				final boolean isSine = ((Sine) vec).getTranslation().getValue() == 0.;
				for (Vector otherVec : base) {
					tmp.put(otherVec, zero);
					if (otherVec instanceof Sine) {
						final Scalar otherFreq = ((Sine) otherVec).getFrequency();
						final boolean otherIsSine = ((Sine) otherVec).getTranslation().getValue() == 0.;
						if (freq.getValue() == otherFreq.getValue()) {
							if (!isSine && otherIsSine) {
								tmp.put(otherVec, new Real(-freq.getValue()));
							}
							if (isSine && !otherIsSine) {
								tmp.put(otherVec, freq);
							}
						}
					}
				}
				coordinatesMap.put(vec,tmp);
			} else {
				tmp = new HashMap<>();
				for (Vector otherVec : base) {
					tmp.put(otherVec, zero);
				}
			}
			coordinatesMap.put(vec, tmp);
		}
		((TrigonometricSobolevSpace) ans)
				.setDerivativeBuilder(new DerivativeOperator(ans, ans, coordinatesMap));
	};

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(Field field, final EuclideanFunctionSpace space,
			final int degree) {
		if (degree == 0) {
			return space;
		}
		FiniteDimensionalSobolevSpace ans = new FiniteDimensionalSobolevSpace(field, space, degree, false);
		ans.getDerivativeBuilder();
		return ans;
	}

	default EuclideanFunctionSpace getTrigonometricFunctionSpaceWithLinearGrowth(Field field, final int n)
			throws WrongClassException {
		return (EuclideanFunctionSpace) extend(getTrigonometricSpace(field, n),
				new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()));
	}

	default EuclideanFunctionSpace getTrigonometricFunctionSpaceWithLinearGrowth(Field field, final int n, double right)
			throws WrongClassException {
		return (EuclideanFunctionSpace) extend(getTrigonometricSpace(field, n, right),
				new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()));
	}

	EuclideanFunctionSpace getPolynomialFunctionSpace(Field field, final int n, double right, boolean ortho);

	void setCachedCoordinateSpaces(ISpaceGenerator readObject);

	void setCachedFunctionSpaces(ISpaceGenerator gen);

	default VectorSpace getPolynomialSobolevSpace(Field field, int maxDegree, double right, int degree) {
		EuclideanFunctionSpace polynoms = getPolynomialFunctionSpace(field, maxDegree, right, false);
		VectorSpace ans = Generator.getGenerator().getFiniteDimensionalSobolevSpace(field, polynoms, degree);
		((FiniteDimensionalVectorSpace) ans)
				.setBase(((EuclideanSpace) ans).getOrthonormalBase(((EuclideanSpace) ans).genericBaseToList()));
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
				return SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(space.getField(), newBase,
						((FunctionSpace) space).getLeft(), ((FunctionSpace) space).getRight());
			}
			return SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(space.getField(), newBase);
		} else {
			throw new WrongClassException("Input should be a function, not a vector.");
		}
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(Field field, List<Vector> newBase) {
		return new FiniteDimensionalVectorSpace(field, newBase);
	}
}

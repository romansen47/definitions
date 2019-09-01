package definitions.structures.euclidean.vectorspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.ehcache.Cache;
import org.ehcache.CacheManager;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.NormedSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.vectors.specialfunctions.LinearFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.euclidean.vectorspaces.impl.PolynomialFunctionSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.TrigonometricSobolevSpace;
import definitions.structures.euclidean.vectorspaces.impl.TrigonometricSpace;
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
		if (!getCachedCoordinateSpaces().containsKey(-dim)) {
			final List<Vector> basetmp = new ArrayList<>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(Generator.getGenerator().getVectorgenerator().getFiniteVector(dim));
			}
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (i == j) {
						((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(i),
								(Scalar) field.getOne());
					} else {
						((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(j),
								(Scalar) field.getZero());
					}
				}
			}
			getCachedCoordinateSpaces().put(Integer.valueOf(-dim), new FiniteDimensionalVectorSpace(field, basetmp));
		}
		return getCachedCoordinateSpaces().get(-dim);
	}

	@SuppressWarnings("deprecation")
	default EuclideanSpace getFiniteDimensionalVectorSpace(final int dim) {
		final Field field = RealLine.getInstance();
		if (getMyCache().get(new Long(dim)) == null) {
			final List<Vector> basetmp = new ArrayList<>();
			for (int i = 0; i < dim; i++) {
				basetmp.add(Generator.getGenerator().getVectorgenerator().getFiniteVector(dim));
			}
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					if (i == j) {
						((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(i),
								(Scalar) field.getOne());
					} else {
						((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(j),
								(Scalar) field.getZero());
					}
				}
			}
			EuclideanSpace space;
			switch (dim) {
//			case 1:
//				space = RealLine.getInstance();
//				break;
//			case 2:
//				space = ComplexPlane.getInstance();
//				break;
//			case 4:
//				space = QuaternionSpace.getInstance();
//				break;
			default:
				space = new FiniteDimensionalVectorSpace(field, basetmp);
			}
			getCachedCoordinateSpaces().put(Integer.valueOf(dim), space);
			this.getMyCache().put(Integer.valueOf(dim).longValue(), space);
		}
		try {
			final EuclideanSpace ans = this.getMyCache().get(new Long(dim));
			System.out.println(
					"Successfully restored from ehcache! " + dim + "-dimensional euclidean space " + ans.toString());
			return ans;
		} catch (final Exception e) {
			System.out.println("Restore from ehcache failed!\r");

			return getCachedCoordinateSpaces().get(dim);
		}
	}

	default VectorSpace getFiniteDimensionalVectorSpace(Field field, final int dim) {
		final List<Vector> basetmp = new ArrayList<>();
		for (int i = 0; i < dim; i++) {
			basetmp.add(Generator.getGenerator().getVectorgenerator().getFiniteVector(dim));
		}
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (i == j) {
					((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(i),
							(Scalar) field.getOne());
				} else {
					((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(j),
							(Scalar) field.getZero());
				}
			}
		}
		return new FiniteDimensionalVectorSpace(field, basetmp);
	}

	default int getHashCode(Field field, List<Vector> base, Double[] interval) {
		int result = 1;
		result = Objects.hash(field, base, interval);
		return result;
	}

	default EuclideanFunctionSpace getFiniteDimensionalFunctionSpace(Field field, final List<Vector> genericBase,
			final double left, final double right, final boolean ortho) {
		final EuclideanFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if ((space != null) && (space.getInterval()[0] == left) && (space.getInterval()[1] == right)) {
			return space;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalFunctionSpace(field, genericBase, left,
				right, ortho);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default EuclideanFunctionSpace getFiniteDimensionalFunctionSpace(Field field, final List<Vector> genericBase,
			final double left, final double right) {
		return getFiniteDimensionalFunctionSpace(field, genericBase, left, right, true);
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree, final boolean ortho) {
		final EuclideanFunctionSpace space = getCachedFunctionSpaces().get(genericBase.hashCode());
		if ((space != null) && (space instanceof FiniteDimensionalSobolevSpace) && (space.getInterval()[0] == left)
				&& (space.getInterval()[1] == right)) {
			return space;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalSobolevSpace(field, genericBase, left,
				right, degree, ortho);
		getCachedFunctionSpaces().put(genericBase.hashCode(), newSpace);
		return newSpace;
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree) {
		return getFiniteDimensionalSobolevSpace(field, genericBase, right, right, degree, false);
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
		final EuclideanFunctionSpace ans = new TrigonometricSobolevSpace(field, n, -Math.PI, Math.PI, degree);
		createTrigonometricDerivativeBuilder(ans);// ((FiniteDimensionalSobolevSpace) ans).getDerivativeBuilder();
		return ans;
	}

	default void createTrigonometricDerivativeBuilder(EuclideanFunctionSpace ans) {
		final List<Vector> base = ans.genericBaseToList();
		final Field realLine = RealLine.getInstance();
		final Scalar unit = (Scalar) realLine.getOne();
		final Scalar zero = (Scalar) realLine.getZero();
		final Map<Vector, Map<Vector, Scalar>> coordinatesMap = new HashMap<>();
		for (final Vector vec : base) {
			Map<Vector, Scalar> tmp = new HashMap<>();
			if (vec instanceof Sine) {
				final Scalar freq = ((Sine) vec).getFrequency();
				final boolean isSine = ((Sine) vec).getTranslation().getValue() == 0.;
				for (final Vector otherVec : base) {
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
				coordinatesMap.put(vec, tmp);
			} else {
				tmp = new HashMap<>();
				for (final Vector otherVec : base) {
					tmp.put(otherVec, zero);
				}
			}
			coordinatesMap.put(vec, tmp);
		}
		((TrigonometricSobolevSpace) ans).setDerivativeBuilder(new DerivativeOperator(ans, ans, coordinatesMap));
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(Field field, final EuclideanFunctionSpace space,
			final int degree) {
		if (degree == 0) {
			return space;
		}
		final FiniteDimensionalSobolevSpace ans = new FiniteDimensionalSobolevSpace(field, space, degree, true);
		ans.getDerivativeBuilder();
		return ans;
	}

	default EuclideanFunctionSpace getTrigonometricFunctionSpaceWithLinearGrowth(Field f, final int n)
			throws WrongClassException {
		final EuclideanSpace ans = extend(getTrigonometricSpace(f, n),
				new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()) {
					private static final long serialVersionUID = 8254610780535405982L;

					@Override
					public Field getField() {
						return f;
					}
				});
//		ans.assignOrthonormalCoordinates(ans.genericBaseToList(), f);
		return (EuclideanFunctionSpace) ans;
	}

	default EuclideanFunctionSpace getTrigonometricFunctionSpaceWithLinearGrowth(Field f, final int n, double right)
			throws WrongClassException {

		return (EuclideanFunctionSpace) extend(getTrigonometricSpace(f, n, right),
				new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()) {
					private static final long serialVersionUID = -3586909911186595426L;

					@Override
					public Field getField() {
						return f;
					}
				});
	}

	default EuclideanFunctionSpace getPolynomialFunctionSpace(Field field, int n, double right, boolean ortho) {
		return new PolynomialFunctionSpace(field, n, right, ortho);
	}

	void setCachedCoordinateSpaces(Map<Integer, EuclideanSpace> readObject);

	void setCachedFunctionSpaces(Map<Integer, EuclideanFunctionSpace> gen);

	default VectorSpace getPolynomialSobolevSpace(Field field, int maxDegree, double right, int degree) {
		final EuclideanFunctionSpace polynoms = getPolynomialFunctionSpace(field, maxDegree, right, false);
		final VectorSpace ans = Generator.getGenerator().getFiniteDimensionalSobolevSpace(field, polynoms, degree);
		((FiniteDimensionalVectorSpace) ans)
				.setBase(((EuclideanSpace) ans).getOrthonormalBase(((EuclideanSpace) ans).genericBaseToList()));
		return ans;
	}

	default EuclideanSpace extend(VectorSpace space, Vector fun) throws WrongClassException {
		final EuclideanSpace asEuclidean = (EuclideanSpace) space;
		if (fun instanceof Function) {
			final List<Vector> base = asEuclidean.genericBaseToList();
			final List<Vector> newBase = new ArrayList<>();
			for (final Vector vec : base) {
				newBase.add(vec);
			}
			final Function projection = ((Function) fun).getProjection((EuclideanSpace) space);
			final Function diff = (Function) space.add(fun, space.stretch(projection, space.getField().get(-1.)));
			final Function newBaseElement = (Function) ((NormedSpace) space).normalize(diff);
			newBase.add(newBaseElement);
			if (space instanceof FunctionSpace) {
				if (space instanceof FiniteDimensionalSobolevSpace) {
					return SpaceGenerator.getInstance().getFiniteDimensionalSobolevSpace(space.getField(), newBase,
							((FunctionSpace) space).getLeft(), ((FunctionSpace) space).getRight(),
							((FiniteDimensionalSobolevSpace) space).getDegree(), false);
				}
				return SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(space.getField(), newBase,
						((FunctionSpace) space).getLeft(), ((FunctionSpace) space).getRight(), false);
			}
			space.assignOrthonormalCoordinates(newBase, space.getField());
			return SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(space.getField(), newBase);
		} else {
			throw new WrongClassException("Input should be a function, not a vector.");
		}
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(Field field, List<Vector> newBase) {
		return new FiniteDimensionalVectorSpace(field, newBase);
	}

	EuclideanSpace convert(EuclideanSpace complexSpace, SubField subField);

	CacheManager getCacheManager();

	void setCacheManager(CacheManager cacheManager);

	void setMyCache(Cache<Long, EuclideanSpace> myCache);

	Cache<Long, EuclideanSpace> getMyCache();
}

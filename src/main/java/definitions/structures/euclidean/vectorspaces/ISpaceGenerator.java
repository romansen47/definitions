package definitions.structures.euclidean.vectorspaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.texen.Generator;

import definitions.cache.MyCache;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.euclidean.functionspaces.impl.FiniteDimensionalSobolevSpace;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalDerivativeOperator;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.euclidean.vectorspaces.impl.PolynomialFunctionSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.TrigonometricSobolevSpace;
import definitions.structures.euclidean.vectorspaces.impl.TrigonometricSpace;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

public interface ISpaceGenerator {

	EuclideanSpace realSpace = RealLine.getInstance();

	default void createTrigonometricDerivativeBuilder(final EuclideanFunctionSpace ans) {
		final List<Vector> base = ans.genericBaseToList();
		final Field realLine = RealLine.getInstance();
		realLine.getOne();
		final Scalar zero = realLine.getZero();
		final Map<Vector, Map<Vector, Scalar>> coordinatesMap = new ConcurrentHashMap<>();
		for (final Vector vec : base) {
			Map<Vector, Scalar> tmp = new ConcurrentHashMap<>();
			if (vec instanceof Sine) {
				final Scalar freq = ((Sine) vec).getFrequency();
				final boolean isSine = ((Real) ((Sine) vec).getTranslation()).getDoubleValue() == 0.;
				for (final Vector otherVec : base) {
					tmp.put(otherVec, zero);
					if (otherVec instanceof Sine) {
						final Scalar otherFreq = ((Sine) otherVec).getFrequency();
						final boolean otherIsSine = ((Real) ((Sine) otherVec).getTranslation()).getDoubleValue() == 0.;
						if (((Real) freq).getDoubleValue() == ((Real) otherFreq).getDoubleValue()) {
							if (!isSine && otherIsSine) {
								tmp.put(otherVec, ((RealLine) realLine).get(-((Real) freq).getDoubleValue()));
							}
							if (isSine && !otherIsSine) {
								tmp.put(otherVec, freq);
							}
						}
					}
				}
				coordinatesMap.put(vec, tmp);
			} else {
				tmp = new ConcurrentHashMap<>();
				for (final Vector otherVec : base) {
					tmp.put(otherVec, zero);
				}
			}
			coordinatesMap.put(vec, tmp);
		}
		((TrigonometricSobolevSpace) ans)
				.setDerivativeBuilder(new FiniteDimensionalDerivativeOperator(ans, ans, coordinatesMap));
	}

	default EuclideanSpace extend(final VectorSpace space, final Vector fun)
			throws DevisionByZeroException, ExtendingFailedException {
		final EuclideanSpace asEuclidean = (EuclideanSpace) space;
		if (fun instanceof Function) {
			final List<Vector> base = asEuclidean.genericBaseToList();
			List<Vector> newBase = new ArrayList<>();
			newBase.addAll(base);
			newBase.add(fun);
			newBase = ((EuclideanSpace) space).getOrthonormalBase(newBase);
			if (space instanceof FunctionSpace) {
				if (space instanceof FiniteDimensionalSobolevSpace) {
					return SpaceGenerator.getInstance().getFiniteDimensionalSobolevSpace(space.getField(), newBase,
							((FunctionSpace) space).getLeft(), ((FunctionSpace) space).getRight(),
							((FiniteDimensionalSobolevSpace) space).getDegree(), false);
				}
				return SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(space.getField(), newBase,
						((FunctionSpace) space).getLeft(), ((FunctionSpace) space).getRight(), false);
			}
			return SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(space.getField(), newBase);
		} else {
			throw new ExtendingFailedException(space, fun);
		}
	}

	default VectorSpace getFiniteDimensionalComplexSpace(final int dim) {
		final Field field = (Field) ComplexPlane.getInstance();
		if (dim == 1) {
			return field;
		}
		final List<Vector> basetmp = new ArrayList<>();
		for (int i = 0; i < dim; i++) {
			/*
			 * Direct usage of constructor instead of get method in order to avoid cycles.
			 * Don't touch this
			 */
			basetmp.add(new Tuple(dim));
		}
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (i == j) {
					((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(i), field.getOne());
				} else {
					((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(j), field.getZero());
				}
			}
		}
		return new FiniteDimensionalVectorSpace(field, basetmp);
	}

	default EuclideanFunctionSpace getFiniteDimensionalFunctionSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right) throws DevisionByZeroException {
		return this.getFiniteDimensionalFunctionSpace(field, genericBase, left, right, true);
	}

	default EuclideanFunctionSpace getFiniteDimensionalFunctionSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right, final boolean ortho) throws DevisionByZeroException {
		return new FiniteDimensionalFunctionSpace(field, genericBase, left, right, ortho);
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(final Field field,
			final EuclideanFunctionSpace space, final int degree) throws DevisionByZeroException {
		if (degree == 0) {
			return space;
		}
		final FiniteDimensionalSobolevSpace ans = new FiniteDimensionalSobolevSpace(field, space, degree, true);
		ans.getDerivativeBuilder();
		return ans;
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree) throws DevisionByZeroException {
		return this.getFiniteDimensionalSobolevSpace(field, genericBase, right, right, degree, false);
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree, final boolean ortho)
			throws DevisionByZeroException {
		final EuclideanSpace space = this.getMyCache().getConcreteCache().get(genericBase.hashCode());
		final EuclideanFunctionSpace funSpace = (EuclideanFunctionSpace) space;
		if ((space != null) && (funSpace instanceof FiniteDimensionalSobolevSpace)
				&& (funSpace.getInterval()[0] == left) && (funSpace.getInterval()[1] == right)) {
			return funSpace;
		}
		return new FiniteDimensionalSobolevSpace(field, genericBase, left, right, degree, ortho);
	}

	default EuclideanSpace getFiniteDimensionalVectorSpaceAsProduct(final Field field, final int dim) {
		EuclideanSpace ans = field;
		for (int i = 1; i < dim; i++) {
			ans = this.getOuterProduct(ans, field);
		}
		return ans;
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(final Field field, final int dim) {
		throw new NullPointerException("Caching aspect missed!");
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(final Field field, final List<Vector> newBase) {
		return new FiniteDimensionalVectorSpace(field, newBase);
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(final int dim) {
		RealLine.getInstance();
		return this.getFiniteDimensionalVectorSpace(RealLine.getInstance(), dim);
	}

	default int getHashCode(final Field field, final List<Vector> base, final Double[] interval) {
		int result = 1;
		result = Objects.hash(field, base, interval);
		return result;
	}

	Logger getLogger();

	MyCache getMyCache();

	default EuclideanFunctionSpace getPolynomialFunctionSpace(final Field field, final int n, final double right,
			final boolean ortho) throws DevisionByZeroException {
		return new PolynomialFunctionSpace(field, n, right, ortho);
	}

	default VectorSpace getPolynomialSobolevSpace(final Field field, final int maxDegree, final double right,
			final int degree) throws DevisionByZeroException {
		final EuclideanFunctionSpace polynoms = this.getPolynomialFunctionSpace(field, maxDegree, right, false);
		final VectorSpace ans = SpaceGenerator.getInstance().getFiniteDimensionalSobolevSpace(field, polynoms, degree);
		((FiniteDimensionalVectorSpace) ans)
				.setBase(((EuclideanSpace) ans).getOrthonormalBase(((EuclideanSpace) ans).genericBaseToList()));
		return ans;
	}

	default EuclideanSpace getTrigonometricFunctionSpaceWithLinearGrowth(final Field f, final int n)
			throws DevisionByZeroException, ExtendingFailedException {
		return this.getTrigonometricFunctionSpaceWithLinearGrowth(f, n, Math.PI);
	}

	default EuclideanSpace getTrigonometricFunctionSpaceWithLinearGrowth(final Field f, final int n, final double right)
			throws DevisionByZeroException, ExtendingFailedException {
		Generator.getInstance();
		final EuclideanSpace space = this.getMyCache().getConcreteCache().get(n);
		if (space != null) {
			LogManager.getLogger(ISpaceGenerator.class).info(
					"Successfully restored {}-dimensional trigonometric space {} with linear functions from cache",
					((2 * n) + 1), space);
			return space;
		}
		return this.extend(this.getTrigonometricSpace(f, n, right), new GenericFunction() {
			@Override
			public Field getField() {
				return f;
			}

			@Override
			public Vector value(Scalar input) {
				return input;
			}
		});

	}

	default EuclideanFunctionSpace getTrigonometricSobolevSpace(final Field field, final int n, final int degree) {
		if (degree == 0) {
			return this.getNormedTrigonometricSpace(field, n);
		}
		final EuclideanFunctionSpace ans = new TrigonometricSobolevSpace(field, n, -Math.PI, Math.PI, degree);
		this.createTrigonometricDerivativeBuilder(ans);// ((FiniteDimensionalSobolevSpace) ans).getDerivativeBuilder();
		return ans;
	}

	default EuclideanSpace getTrigonometricSobolevSpaceWithLinearGrowth(final Field f, final int sobolevDegree,
			final double right, final int fourierDegree) throws DevisionByZeroException, ExtendingFailedException {

		return this.extend(this.getTrigonometricSobolevSpace(f, fourierDegree, sobolevDegree), new GenericFunction() {

			@Override
			public Field getField() {
				return f;
			}

			@Override
			public Scalar value(final Scalar input) {
				return input;
			}

		});
	}

	/**
	 * "Normed" here means the domain of this function space is (-pi,pi)
	 *
	 * @param field the given field
	 * @param n     the trigonometric degree
	 * @return the trigonometric space with respect to (-pi,pi)
	 */
	default EuclideanFunctionSpace getNormedTrigonometricSpace(final Field field, final int n) {
		return new TrigonometricSpace(field, n, Math.PI);
	}

	default EuclideanFunctionSpace getTrigonometricSpace(final Field field, final int n, final double right) {
		return new TrigonometricSpace(field, n, right);
	}

	default EuclideanSpace getOuterProduct(EuclideanSpace first, EuclideanSpace second) {
		if (!first.getField().equals(second.getField())) {
			return null;
		}

		return new EuclideanSpace() {

			final protected EuclideanSpace outerThis = this;

			@Override
			public String toString() {
				String ans = "Product space UxV, where\r";
				ans += "U is " + first.toString() + "\rand\r" + "V is " + second.toString();
				return ans;
			}

			class ProductVector implements FiniteVector {

				@Override
				public String toXml() {
					return this.left.toXml() + this.right.toXml();
				}

				@Override
				public String toString() {
					return this.toXml();
				}

				private final Vector left;
				private final Vector right;
				private Map<Vector, Scalar> coordinates;
				private Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap;

				ProductVector(Vector l, Vector r) {
					this.left = l;
					this.right = r;
				}

				@Override
				public Integer getDim() {
					return outerThis.getDim();
				}

				/**
				 * @return the left
				 */
				public Vector getLeft() {
					return this.left;
				}

				/**
				 * @return the right
				 */
				public Vector getRight() {
					return this.right;
				}

				@Override
				public Map<Vector, Scalar> getCoordinates() {
					if (this.coordinates == null) {
						this.coordinates = new ConcurrentHashMap<>();
						for (final Vector vec : genericBaseToList()) {
							final FiniteVector tmpLeft = (FiniteVector) ((ProductVector) vec).left;
							final FiniteVector tmpRight = (FiniteVector) ((ProductVector) vec).right;
							Scalar val;
							if (tmpRight.equals(second.nullVec())) {
								final Map<Vector, Scalar> tmpMap = ((FiniteVectorMethods) this.getLeft())
										.getCoordinates();
								val = tmpMap.get(tmpLeft);
							} else {
								final Map<Vector, Scalar> tmpMap = ((FiniteVectorMethods) this.getRight())
										.getCoordinates();
								val = tmpMap.get(tmpRight);
							}
							this.coordinates.put(vec, val);
						}
					}
					this.setCoordinates(this.coordinates, outerThis);
					return this.coordinates;
				}

				@Override
				public void setCoordinates(Map<Vector, Scalar> coordinates) {
					this.coordinates = coordinates;
				}

				@Override
				public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
					if (this.coordinatesMap == null) {
						this.coordinatesMap = new ConcurrentHashMap<>();
					}
					this.coordinatesMap.putIfAbsent(space, coordinates);
				}

			}

			@Override
			public Field getField() {
				return first.getField();
			}

			@Override
			public boolean contains(Vector vec) {
				boolean ans = false;
				if (vec instanceof ProductVector) {
					ans = first.contains(((ProductVector) vec).getLeft())
							&& second.contains(((ProductVector) vec).getRight());
				}
				return ans;
			}

			private Vector nullVec;

			@Override
			public Vector nullVec() {
				if (this.nullVec == null) {
					this.nullVec = new ProductVector(first.nullVec(), second.nullVec());
				}
				return this.nullVec;
			}

			private List<Vector> base;

			@Override
			public List<Vector> genericBaseToList() {
				if (this.base == null) {
					this.base = new ArrayList<>();
					for (int i = 0; i < first.getDim(); i++) {
						this.base.add(new ProductVector(first.genericBaseToList().get(i), second.nullVec()));
					}
					for (int j = 0; j < second.getDim(); j++) {
						this.base.add(new ProductVector(first.nullVec(), second.genericBaseToList().get(j)));
					}
				}
				return this.base;
			}

			@Override
			public Vector getCoordinates(Vector vec) {
				return new ProductVector(first.getCoordinates(vec), second.getCoordinates(vec));
			}

			@Override
			public Integer getDim() {
				int k, j = 0;
				if (first instanceof Field) {
					j = 1;
				} else {
					j = first.getDim();
				}
				if (second instanceof Field) {
					k = 1;
				} else {
					k = second.getDim();
				}
				return j + k;
			}

			EuclideanSpace dualSpace;

			@Override
			public EuclideanSpace getDualSpace() {
				if (this.dualSpace == null) {
					this.dualSpace = ISpaceGenerator.this.getOuterProduct(first.getDualSpace(), second.getDualSpace());
				}
				return this.dualSpace;
			}

		};

	}

	void setMyCache(MyCache ans);

}

package definitions.structures.euclidean.vectorspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import definitions.cache.MyCache;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.NormedSpace;
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
import definitions.structures.euclidean.vectors.specialfunctions.LinearFunction;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.euclidean.vectorspaces.impl.PolynomialFunctionSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.TrigonometricSobolevSpace;
import definitions.structures.euclidean.vectorspaces.impl.TrigonometricSpace;

public interface ISpaceGenerator {

	EuclideanSpace realSpace = RealLine.getInstance();

	default void createTrigonometricDerivativeBuilder(final EuclideanFunctionSpace ans) {
		final List<Vector> base = ans.genericBaseToList();
		final Field realLine = RealLine.getInstance();
		realLine.getOne();
		final Scalar zero = (Scalar) realLine.getZero();
		final Map<Vector, Map<Vector, Scalar>> coordinatesMap = new HashMap<>();
		for (final Vector vec : base) {
			Map<Vector, Scalar> tmp = new HashMap<>();
			if (vec instanceof Sine) {
				final Scalar freq = ((Sine) vec).getFrequency();
				final boolean isSine = ((Sine) vec).getTranslation().getDoubleValue() == 0.;
				for (final Vector otherVec : base) {
					tmp.put(otherVec, zero);
					if (otherVec instanceof Sine) {
						final Scalar otherFreq = ((Sine) otherVec).getFrequency();
						final boolean otherIsSine = ((Sine) otherVec).getTranslation().getDoubleValue() == 0.;
						if (freq.getDoubleValue() == otherFreq.getDoubleValue()) {
							if (!isSine && otherIsSine) {
								tmp.put(otherVec, ((RealLine) realLine).get(-freq.getDoubleValue()));
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
		((TrigonometricSobolevSpace) ans)
				.setDerivativeBuilder(new FiniteDimensionalDerivativeOperator(ans, ans, coordinatesMap));
	}

	default EuclideanSpace extend(final VectorSpace space, final Vector fun) throws Exception {
		final EuclideanSpace asEuclidean = (EuclideanSpace) space;
		if (fun instanceof Function) {
			final List<Vector> base = asEuclidean.genericBaseToList();
			final List<Vector> newBase = new ArrayList<>();
			for (final Vector vec : base) {
				newBase.add(vec);
			}
			final Function projection = ((Function) fun).getProjection((EuclideanSpace) space);
			final Function diff = (Function) space.addition(fun, space.stretch(projection, ((Scalar) space.getField()
					.getInverseElement(space.getField().getMuliplicativeMonoid().getNeutralElement()))));
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
			((EuclideanSpace) space).assignOrthonormalCoordinates(newBase, space.getField());
			return SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(space.getField(), newBase);
		} else {
			throw new Exception("Input should be a function, not a vector.");
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
					((FiniteVectorMethods) basetmp.get(i)).getCoordinates().put(basetmp.get(j),
							(Scalar) field.getZero());
				}
			}
		}
		return new FiniteDimensionalVectorSpace(field, basetmp);// getCachedCoordinateSpaces().get(-dim);
	}

	default EuclideanFunctionSpace getFiniteDimensionalFunctionSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right) {
		return this.getFiniteDimensionalFunctionSpace(field, genericBase, left, right, true);
	}

	default EuclideanFunctionSpace getFiniteDimensionalFunctionSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right, final boolean ortho) {
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalFunctionSpace(field, genericBase, left,
				right, ortho);
		return newSpace;
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(final Field field,
			final EuclideanFunctionSpace space, final int degree) {
		if (degree == 0) {
			return space;
		}
		final FiniteDimensionalSobolevSpace ans = new FiniteDimensionalSobolevSpace(field, space, degree, true);
		ans.getDerivativeBuilder();
		return ans;
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree) {
		return this.getFiniteDimensionalSobolevSpace(field, genericBase, right, right, degree, false);
	}

	default EuclideanFunctionSpace getFiniteDimensionalSobolevSpace(final Field field, final List<Vector> genericBase,
			final double left, final double right, final int degree, final boolean ortho) {
		final EuclideanSpace space = this.getMyCache().getConcreteCache().get(genericBase.hashCode());
		final EuclideanFunctionSpace funSpace = (EuclideanFunctionSpace) space;
		if ((space != null) && (funSpace instanceof FiniteDimensionalSobolevSpace)
				&& (funSpace.getInterval()[0] == left) && (funSpace.getInterval()[1] == right)) {
			return funSpace;
		}
		final FiniteDimensionalFunctionSpace newSpace = new FiniteDimensionalSobolevSpace(field, genericBase, left,
				right, degree, ortho);
		return newSpace;
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(final Field field, final int dim) {
		return null;
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(final Field field, final List<Vector> newBase) {
		return new FiniteDimensionalVectorSpace(field, newBase);
	}

	default EuclideanSpace getFiniteDimensionalVectorSpace(final int dim) {
		RealLine.getInstance();
		return (EuclideanSpace) this.getFiniteDimensionalVectorSpace(RealLine.getInstance(), dim);
	}

	default int getHashCode(final Field field, final List<Vector> base, final Double[] interval) {
		int result = 1;
		result = Objects.hash(field, base, interval);
		return result;
	}

	org.apache.log4j.Logger getLogger();

	MyCache getMyCache();

	default EuclideanFunctionSpace getPolynomialFunctionSpace(final Field field, final int n, final double right,
			final boolean ortho) {
		return new PolynomialFunctionSpace(field, n, right, ortho);
	}

	default VectorSpace getPolynomialSobolevSpace(final Field field, final int maxDegree, final double right,
			final int degree) {
		final EuclideanFunctionSpace polynoms = this.getPolynomialFunctionSpace(field, maxDegree, right, false);
		final VectorSpace ans = SpaceGenerator.getInstance().getFiniteDimensionalSobolevSpace(field, polynoms, degree);
		((FiniteDimensionalVectorSpace) ans)
				.setBase(((EuclideanSpace) ans).getOrthonormalBase(((EuclideanSpace) ans).genericBaseToList()));
		return ans;
	}

	default EuclideanSpace getTrigonometricFunctionSpaceWithLinearGrowth(final Field f, final int n) throws Exception {
		return this.getTrigonometricFunctionSpaceWithLinearGrowth(f, n, Math.PI);
	}

	default EuclideanSpace getTrigonometricFunctionSpaceWithLinearGrowth(final Field f, final int n, final double right)
			throws Exception {
		final EuclideanSpace space = this.getMyCache().getConcreteCache().get(n);
		if (space != null) {
			this.getLogger().info("Successfully restored from cache! " + (2 * n + 1)
					+ "-dimensional trigonometric space with linear functions " + space.toString());
			return space;
		}
		final EuclideanSpace newSpace = this.extend(this.getTrigonometricSpace(f, n, right),
				new LinearFunction(RealLine.getInstance().getZero(), ((RealLine) f).get(1. / Math.sqrt(2 * Math.PI))) {
					private long serialVersionUID = 8254610780535405982L;

					@Override
					public Field getField() {
						return f;
					}

					@Override
					public void setRepresentant(Double representant) {
					}
				});
		this.getMyCache().getConcreteCache().put(n, newSpace);
		System.out.println(
				"Saved " + (2 * n + 1) + "-dimensional trigonometric space equipped with linear functions to cache!");
		return this.getMyCache().getConcreteCache().get(n);

	}

	default EuclideanFunctionSpace getTrigonometricSobolevSpace(final Field field, final int n, final int degree) {
		if (degree == 0) {
			return this.getTrigonometricSpace(field, n);
		}
		final EuclideanFunctionSpace ans = new TrigonometricSobolevSpace(field, n, -Math.PI, Math.PI, degree);
		this.createTrigonometricDerivativeBuilder(ans);// ((FiniteDimensionalSobolevSpace) ans).getDerivativeBuilder();
		return ans;
	}

	default EuclideanSpace getTrigonometricSobolevSpaceWithLinearGrowth(final Field f, final int sobolevDegree,
			final double right, final int fourierDegree) throws Exception {

		return this.extend(this.getTrigonometricSobolevSpace(f, fourierDegree, sobolevDegree), new GenericFunction() {
			private long serialVersionUID = 5812927097511303688L;

			@Override
			public Field getField() {
				return f;
			}

			@Override
			public Scalar value(final Scalar input) {
				return input;
			}

			@Override
			public void setRepresentant(Double representant) {
			}

		});
	}

	default EuclideanFunctionSpace getTrigonometricSpace(final Field field, final int n) {
		return new TrigonometricSpace(field, n, Math.PI);
	}

	default EuclideanFunctionSpace getTrigonometricSpace(final Field field, final int n, final double right) {
		return new TrigonometricSpace(field, n, right);
	}

	default EuclideanSpace getOuterProduct(EuclideanSpace first, EuclideanSpace second) {
		if (!first.getField().equals(second.getField())) {
			return null;
		}

		EuclideanSpace product = new EuclideanSpace() {

			final protected EuclideanSpace outerThis = this;

			@Override
			public String toString() {
				String ans = "Product space UxV, where\r";
				ans += "U is " + first.toString() + "\rans\r" + "V is " + second.toString();
				return ans;
			}

			class ProductVector implements FiniteVector {

				final private Vector left;
				final private Vector right;
				private Map<Vector, Scalar> coordinates;
				private Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap;

				ProductVector(Vector l, Vector r) {
					left = l;
					right = r;
				}

				@Override
				public Integer getDim() {
					return left.getDim() + right.getDim();
				}

				/**
				 * @return the left
				 */
				public Vector getLeft() {
					return left;
				}

				/**
				 * @return the right
				 */
				public Vector getRight() {
					return right;
				}

				@Override
				public Map<Vector, Scalar> getCoordinates() {
					if (coordinates == null) {
						coordinates = new ConcurrentHashMap<>();
						for (Vector vec : genericBaseToList()) {
							FiniteVector tmpLeft = (FiniteVector) ((ProductVector) vec).left;
							FiniteVector tmpRight = (FiniteVector) ((ProductVector) vec).right;
							Scalar val;
							if (tmpRight.equals(second.nullVec())) {
								Map<Vector, Scalar> tmpMap = ((FiniteVectorMethods) getLeft()).getCoordinates();
								val = tmpMap.get(tmpLeft);
							} else {
								Map<Vector, Scalar> tmpMap = ((FiniteVectorMethods) getRight()).getCoordinates();
								val = tmpMap.get(tmpRight);
							}
							coordinates.put(vec, val);
						}
					}
					setCoordinates(coordinates, outerThis);
					return coordinates;
				}

				@Override
				public void setCoordinates(Map<Vector, Scalar> coordinates) {
					this.coordinates = coordinates;
				}

				@Override
				public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
					if (coordinatesMap == null) {
						coordinatesMap = new ConcurrentHashMap<>();
					}
					coordinatesMap.putIfAbsent(space, coordinates);
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
				if (nullVec == null) {
					nullVec = new ProductVector(first.nullVec(), second.nullVec());
				}
				return nullVec;
			}

			List<Vector> base;

			@Override
			public List<Vector> genericBaseToList() {
				if (base == null) {
					base = new ArrayList<>();
					for (int i = 0; i < first.getDim(); i++) {
						base.add(new ProductVector(first.genericBaseToList().get(i), second.nullVec()));
					}
					for (int j = 0; j < second.getDim(); j++) {
						base.add(new ProductVector(first.nullVec(), second.genericBaseToList().get(j)));
					}
				}
				return base;
			}

			@Override
			public Vector getCoordinates(Vector vec) {
				return new ProductVector(first.getCoordinates(vec), second.getCoordinates(vec));
			}

			@Override
			public Integer getDim() {
				return first.getDim() + second.getDim();
			}

			EuclideanSpace dualSpace;

			@Override
			public EuclideanSpace getDualSpace() {
				if (dualSpace == null) {
					dualSpace = getOuterProduct(first.getDualSpace(), second.getDualSpace());
				}
				return dualSpace;
			}

		};
		return product;

	}

	void setMyCache(MyCache ans);

}

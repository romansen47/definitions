package definitions.structures.finitedimensional.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.vectors.impl.GenericFunction;

/**
 * Concrete implementation of a finite dimensional sobolev function space.
 * 
 * @author ro
 *
 */
public class FiniteDimensionalSobolevSpace extends FiniteDimensionalFunctionSpace {

	/**
	 * The sobolev degree.
	 */
	final private int degree;

	/**
	 * Constructor.
	 * 
	 * @param genericBase the base
	 * @param left        the inf of the interval.
	 * @param right       the sup of the intervall.
	 * @param degree      the sobolev degree.
	 */
	public FiniteDimensionalSobolevSpace(final List<Vector> genericBase, final double left, final double right,
			int degree) {
		super(genericBase, left, right);
		this.degree = degree;
	}

	/**
	 * Constructor. Converts function space to sobolev space.
	 * 
	 * @param space  the function space.
	 * @param degree the sobolev degree of the converted space.
	 */
	public FiniteDimensionalSobolevSpace(final EuclideanFunctionSpace space, int degree) {
		super(space.genericBaseToList(), space.getInterval()[0], space.getInterval()[1]);
		this.degree = degree;
		final List<Vector> newBaseTmp = this.base;// this.getOrthonormalBase(this.base);
		final List<Vector> newBase = new ArrayList<>();
		final List<Vector> newCoordinates = Generator.getGenerator().getSpacegenerator()
				.getFiniteDimensionalVectorSpace(this.dim).genericBaseToList();
		final int dim = space.genericBaseToList().size();
		for (int i = 0; i < dim; i++) {
			final int j = i;
			final Function baseFunction = new FunctionTuple(newCoordinates.get(i).getGenericCoordinates()) {
				@Override
				public double value(final double input) {
					return ((Function) newBaseTmp.get(j)).value(input);
				}
			};
			newBase.add(baseFunction);
		}
		for (final Vector vec : newBase) {
			final Map<Vector, Double> coordinates = new ConcurrentHashMap<>();
			coordinates.put(vec, 1.);
			for (final Vector vec2 : newBase) {
				if (!vec.equals(vec2)) {
					coordinates.put(vec2, 0.);
				}
			}
			vec.setCoordinates(coordinates);
		}
		this.setBase(newBase);
	}

	/**
	 * Constructor.
	 * 
	 * @param degree the sobolev degree.
	 */
	protected FiniteDimensionalSobolevSpace(int degree) {
		this.degree = degree;
	}

	@Override
	public double product(final Vector vec1, final Vector vec2) {
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			if ((vec1 instanceof FunctionTuple) && (vec2 instanceof FunctionTuple)
					&& (((FunctionTuple) vec1).getGenericBase() == ((FunctionTuple) vec2).getGenericBase())) {
				return super.product(vec1, vec2);
			} else {
				double product = 0;
				Function tmp1 = new GenericFunction() {
					@Override
					public double value(double input) {
						return ((Function) vec1).value(input);
					}
				};
				Function tmp2 = new GenericFunction() {
					@Override
					public double value(double input) {
						return ((Function) vec2).value(input);
					}
				};
				for (int i = 0; i < this.getDegree(); i++) {
					product += super.product(tmp1, tmp2);
					tmp1 = tmp1.getDerivative();
					tmp2 = tmp2.getDerivative();
				}
			}
		}
		return super.product(vec1, vec2);
	}

	@Override
	public Vector projection(final Vector w, final Vector v) {
		return this.stretch(v, this.product(w, v));
	}

	/**
	 * Getter for the sobolev degree.
	 * 
	 * @return the sobolev degree
	 */
	public final int getDegree() {
		return this.degree;
	}

}

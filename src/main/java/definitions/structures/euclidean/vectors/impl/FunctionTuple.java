package definitions.structures.euclidean.vectors.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.impl.LinearMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import plotter.Plotter;

/**
 * 
 * @author RoManski
 *
 */
public class FunctionTuple extends Tuple implements Function {

	private static final long serialVersionUID = -3953250098421804886L;
	final private Field field;
	Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = new HashMap<>();

	public FunctionTuple(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		super(coordinates);
		this.coordinatesMap.put(space, coordinates);
		this.field = space.getField();
	}

	public FunctionTuple(final Scalar[] coordinates, final Field field) {
		super(coordinates);
		this.field = field;
	}

	@Override
	public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
		return this.coordinatesMap;
	}

	public LinearMapping getDerivative(final VectorSpace space) {
		return new FiniteDimensionalLinearMapping((EuclideanFunctionSpace) space, (EuclideanFunctionSpace) space) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8910643729270807923L;

			@Override
			public Vector get(final Vector vec2) {
				return ((Function) vec2).getDerivative();
			}

			@Override
			public Map<Vector, Scalar> getLinearity(final Vector vec1) {
				return null;
			}
		};
	}

	@Override
	public Field getField() {
		return this.field;
	}

	@Override

	public Function getProjection(final EuclideanSpace source) {
		return Function.super.getProjection(source);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override

	public void plot(final double left, final double right) {
		((Plotter) gen).plot(this, left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override

	public void plotCompare(final double left, final double right, final Function fun) {
		((Plotter) gen).plotCompare(this, fun, left, right);
	}

	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		this.coordinatesMap.put(space, ((Function) coordinates).getCoordinates(space));
	}

	@Override

	public Scalar value(final Scalar input) {
		Scalar ans = (Scalar) this.getField().getZero();
		for (final Vector fun : this.getCoordinates().keySet()) {
			ans = (Scalar) this.getField().add(ans,
					this.getField().product(((Function) fun).value(input), this.getCoordinates().get(fun)));
		}
		return ans;
	}

}

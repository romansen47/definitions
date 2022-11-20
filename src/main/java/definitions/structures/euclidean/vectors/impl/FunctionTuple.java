package definitions.structures.euclidean.vectors.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import plotter.Plotter;

/**
 * technically a tuple. elements of a finite dimensional function space
 *
 * @author RoManski
 *
 */
public class FunctionTuple extends Tuple implements Function {

	private final Field field;
	private final Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = new ConcurrentHashMap<>();

	public FunctionTuple(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		super(coordinates);
		this.coordinatesMap.put(space, coordinates);
		this.field = space.getField();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
		return this.coordinatesMap;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field getField() {
		return this.field;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Function getProjection(final EuclideanSpace source) {
		return Function.super.getProjection(source);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void plot(final double left, final double right) {
		((Plotter) Generator.getInstance()).plot(this, left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		this.coordinatesMap.put(space, ((Function) coordinates).getCoordinates(space));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar value(final Scalar input) {
		Scalar ans = this.getField().getZero();
		for (final Vector fun : this.getCoordinates().keySet()) {
			ans = (Scalar) this.getField().addition(ans,
					this.getField().product(((Function) fun).value(input), this.getCoordinates().get(fun)));
		}
		return ans;
	}

}

package definitions.structures.euclidean.vectors.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import plotter.Plotter;

public abstract class GenericFunction implements Function {

	private static final long serialVersionUID = 1L;
	private Field field = RealLine.getInstance();
	Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = new HashMap<>();
	private Map<Vector, Scalar> coordinates;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean elementOf(final VectorSpace space) {
		return space instanceof FunctionSpace;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object vec) {
		return super.equals(vec);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates() {
		return this.coordinates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getCoordinates(final EuclideanSpace space) {
		if (this.coordinatesMap == null) {
			this.coordinatesMap = new ConcurrentHashMap<>();
		}
		Map<Vector, Scalar> coordinates = coordinatesMap.get(space);
		if (coordinates == null) {
			coordinates = new ConcurrentHashMap<>();
			for (final Vector baseVec : space.genericBaseToList()) {
				coordinates.put(baseVec, ((EuclideanFunctionSpace) space).innerProduct(this, baseVec));
			}
		coordinatesMap.put(space, coordinates);
		}
		return coordinates;
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
	public Integer getDim() {
		return null;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		this.coordinatesMap.put(space, coordinates);
		this.setCoordinates(coordinates);
	}

	public void setField(final Field field) {
		this.field = field;
	}

}

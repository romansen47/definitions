package definitions.structures.euclidean.vectors.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import plotter.Plotter;

/**
 * a generic function is DEFINED without coordinates but using
 * value()-implementation, yet may be carrying coordinates around later in the
 * process
 *
 * @author roman
 *
 */
public abstract class GenericFunction implements Function, Element {

	private Field field;
	private Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = new ConcurrentHashMap<>();
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
		Map<Vector, Scalar> tmpCoordinates = this.coordinatesMap.getOrDefault(space, null);
		if (tmpCoordinates == null) {
			tmpCoordinates = new ConcurrentHashMap<>();
			for (final Vector baseVec : space.genericBaseToList()) {
				final Scalar tmp = ((EuclideanFunctionSpace) space).innerProduct(this, baseVec);
				tmpCoordinates.put(baseVec, tmp);
			}
			this.coordinatesMap.put(space, tmpCoordinates);
		}
		return tmpCoordinates;
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
		((Plotter) Generator.getInstance()).plot(this, left, right);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override

	public void plotCompare(final double left, final double right, final Function fun) {
		((Plotter) Generator.getInstance()).plotCompare(this, fun, left, right);
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

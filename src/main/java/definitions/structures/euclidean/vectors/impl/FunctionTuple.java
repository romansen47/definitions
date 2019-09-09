package definitions.structures.euclidean.vectors.impl;

import java.util.HashMap;
import java.util.Map;

import com.aop.lib.Wrap;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.impl.LinearMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * 
 * @author RoManski
 *
 */
public class FunctionTuple extends Tuple implements Function {

	private static final long serialVersionUID = -3953250098421804886L;
	final private Field field;
	Map<EuclideanSpace, Map<Vector, Scalar>> coordinatesMap = new HashMap<>();

	public FunctionTuple(final Map<Vector, Scalar> coordinates, EuclideanSpace space) {
		super(coordinates);
		this.coordinatesMap.put(space, coordinates);
		this.field = space.getField();
	}

	public FunctionTuple(final Scalar[] coordinates, Field field) {
		super(coordinates);
		this.field = field;
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

	public LinearMapping getDerivative(VectorSpace space) {
		return new FiniteDimensionalLinearMapping((EuclideanFunctionSpace) space, (EuclideanFunctionSpace) space) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8910643729270807923L;

			@Override
			public Vector get(Vector vec2) {
				return ((Function) vec2).getDerivative();
			}

			@Override
			public Map<Vector, Scalar> getLinearity(Vector vec1) {
				return null;
			}
		};
	}

	@Override
	@Wrap
	public Function getProjection(EuclideanSpace source) {
		return Function.super.getProjection(source);
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
		this.coordinatesMap.put(space, ((Function) coordinates).getCoordinates(space));
	}

	@Override
	public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
		return this.coordinatesMap;
	}

	@Override
	public Field getField() {
		return this.field;
	}

}

package definitions.structures.euclidean.functionspaces.impl;

import java.util.List;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;

/**
 * Concrete implementation of a finite dimensional sobolev function space.
 * 
 * @author ro
 *
 */
public class FiniteDimensionalSobolevSpace extends FiniteDimensionalFunctionSpace {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2547484050898391066L;
	private DerivativeOperator derivativeBuilder;
	/**
	 * The sobolev degree.
	 */
	private final Integer degree;

	/**
	 * Constructor.
	 * 
	 * @param genericBase the base
	 * @param left        the inf of the interval.
	 * @param right       the sup of the intervall.
	 * @param degree      the sobolev degree.
	 */
	public FiniteDimensionalSobolevSpace(Field field, final List<Vector> genericBase, final double left,
			final double right, int degree) {
		super(field, genericBase, left, right, true);
		this.degree = degree;
		this.getDerivativeBuilder();
	}

	/**
	 * Constructor.
	 * 
	 * @param genericBase the base
	 * @param left        the inf of the interval.
	 * @param right       the sup of the intervall.
	 * @param degree      the sobolev degree.
	 */
	public FiniteDimensionalSobolevSpace(Field field, final List<Vector> genericBase, final double left,
			final double right, int degree, boolean ortho) {
		super(field, genericBase, left, right, ortho);
		this.degree = degree;
		// this.getDerivativeBuilder();
	}

	/**
	 * Constructor. Converts function space to sobolev space.
	 * 
	 * @param space  the function space.
	 * @param degree the sobolev degree of the converted space.
	 */
	public FiniteDimensionalSobolevSpace(Field field, final EuclideanFunctionSpace space, int degree, boolean ortho) {
		super(field, space.genericBaseToList(), space.getInterval()[0], space.getInterval()[1], false);
		this.degree = degree;
		if (ortho) {
			this.setBase(this.getOrthonormalBase(this.base));
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param degree the sobolev degree.
	 */
	protected FiniteDimensionalSobolevSpace(Field field, int degree) {
		super(field);
		this.degree = degree;
	}

	@Override
	public Scalar innerProduct(final Vector vec1, final Vector vec2) {
		if ((vec1 instanceof Function) && (vec2 instanceof Function)) {
			if ((vec1.getCoordinates() != null) && (vec2.getCoordinates() != null)) {
				return super.innerProduct(vec1, vec2);
			} else {
				double product = 0;
				Vector tmp1 = vec1;
				Vector tmp2 = vec2;
				product += super.innerProduct(tmp1, tmp2).getValue();
				for (int i = 0; i < this.getDegree(); i++) {
					if ((tmp1.getCoordinates() == null && tmp2.getCoordinates() == null)
							|| this.derivativeBuilder == null) {
						tmp1 = ((Function) tmp1).getDerivative();
						tmp2 = ((Function) tmp2).getDerivative();
					} else {
						if (tmp1.getCoordinates() == null && tmp2.getCoordinates() != null) {
							tmp2 = this.stretch(this.derivativeBuilder.get(tmp2, 2), this.getField().get(-1.));
						} else {
							if (tmp1.getCoordinates() != null && tmp2.getCoordinates() == null) {
								tmp1 = this.stretch(this.derivativeBuilder.get(tmp1, 2), this.getField().get(-1));
							} else {
								tmp1 = this.derivativeBuilder.get(tmp1);
								tmp2 = this.derivativeBuilder.get(tmp2);
							}
						}
					}
					product += super.innerProduct(tmp1, tmp2).getValue();
				}
				return this.getField().get(product);
			}
		}
		return super.innerProduct(vec1, vec2);
	}

	@Override
	public Vector projection(final Vector w, final Vector v) {
		return this.stretch(v, this.innerProduct(w, v));
	}

	/**
	 * Getter for the sobolev degree.
	 * 
	 * @return the sobolev degree
	 */
	public final Integer getDegree() {
		return this.degree;
	}

	public DerivativeOperator getDerivativeBuilder() {
		if (this.derivativeBuilder == null) {
			this.setDerivativeBuilder(new DerivativeOperator(this, this));
		}
		return this.derivativeBuilder;
	}

	public void setDerivativeBuilder(DerivativeOperator derivativeBuilder) {
		this.derivativeBuilder = derivativeBuilder;
	}

}

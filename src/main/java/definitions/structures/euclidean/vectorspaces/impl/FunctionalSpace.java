/**
 * 
 */
package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.Proceed;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.Functional;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public class FunctionalSpace extends FiniteDimensionalVectorSpace {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2891504884821572359L;
	final EuclideanSpace source;

	public FunctionalSpace(final EuclideanSpace source) {
		this.source = source;
		this.dim = source.getDim();
		final List<Vector> base = new ArrayList<>();
		for (final Vector baseVec : source.genericBaseToList()) {
			final Vector functional = new Functional() {
				private static final long serialVersionUID = 1925595967834154425L;
				final EuclideanSpace sourceSpace = source;
				final EuclideanSpace target = source.getField();
				final Vector sourceVec = baseVec;

				@Override
				public Vector get(final Element vec) {
					return this.sourceSpace.innerProduct(baseVec, (Vector) vec);
				}

				@Override
				public Scalar[][] getGenericMatrix() {
					final Scalar[][] mat = new Scalar[((EuclideanSpace) this.getSource())
							.getDim()][((EuclideanSpace) this.getSource()).getDim()];
					for (int i = 0; i < ((EuclideanSpace) this.getSource()).getDim(); i++) {
						for (int j = 0; j < ((EuclideanSpace) this.getSource()).getDim(); j++) {
							if (i != j) {
								mat[i][j] = (Scalar) this.getTarget().nullVec();
							} else {
								mat[i][j] = ((Field) this.getTarget()).getOne();
							}
						}
					}
					return mat;
				}

				@Override
				public Map<Vector, Map<Vector, Scalar>> getLinearity() {
					final Map<Vector, Map<Vector, Scalar>> newMap = new HashMap<>();
					for (final Vector bv1 : this.sourceSpace.genericBaseToList()) {
						final Map<Vector, Scalar> coord = new HashMap<>();
						for (final Vector bv2 : this.sourceSpace.genericBaseToList()) {
							if (bv1 != bv2) {
								coord.put(bv2, (Scalar) this.target.nullVec());
							} else {
								coord.put(bv2, ((Field) this.target).getOne());
							}
						}
						newMap.put(bv1, coord);
					}
					return newMap;
				}

				@Override
				public VectorSpace getSource() {
					return source;
				}

				@Override
				public Vector getSourceVec() {
					return this.sourceVec;
				}

				@Override
				public EuclideanSpace getTarget() {
					return this.target;
				}

			};
			base.add(functional);
		}
		this.setBase(base);
	}

	@Override
	@Proceed
	public EuclideanSpace getDualSpace() {
		return this.source;
	}
}

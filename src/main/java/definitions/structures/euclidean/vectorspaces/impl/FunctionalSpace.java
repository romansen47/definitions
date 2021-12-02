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

	private static final long serialVersionUID = 2891504884821572359L;
	final EuclideanSpace source;

	public FunctionalSpace(final EuclideanSpace source) {
		this.source = source;
		dim = source.getDim();
		final List<Vector> base = new ArrayList<>();
		for (final Vector baseVec : source.genericBaseToList()) {
			final Vector functional = new Functional() {
				final EuclideanSpace sourceSpace = source;
				final EuclideanSpace target = source.getField();
				final Vector sourceVec = baseVec;

				@Override
				public Vector get(final Element vec) {
					return sourceSpace.innerProduct(baseVec, (Vector) vec);
				}

				@Override
				public Scalar[][] getGenericMatrix() {
					final Scalar[][] mat = new Scalar[((EuclideanSpace) getSource())
							.getDim()][((EuclideanSpace) getSource()).getDim()];
					for (int i = 0; i < ((EuclideanSpace) getSource()).getDim(); i++) {
						for (int j = 0; j < ((EuclideanSpace) getSource()).getDim(); j++) {
							if (i != j) {
								mat[i][j] = (Scalar) getTarget().nullVec();
							} else {
								mat[i][j] = ((Field) getTarget()).getOne();
							}
						}
					}
					return mat;
				}

				@Override
				public Map<Vector, Map<Vector, Scalar>> getLinearity() {
					final Map<Vector, Map<Vector, Scalar>> newMap = new HashMap<>();
					for (final Vector bv1 : sourceSpace.genericBaseToList()) {
						final Map<Vector, Scalar> coord = new HashMap<>();
						for (final Vector bv2 : sourceSpace.genericBaseToList()) {
							if (bv1 != bv2) {
								coord.put(bv2, (Scalar) target.nullVec());
							} else {
								coord.put(bv2, ((Field) target).getOne());
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
					return sourceVec;
				}

				@Override
				public EuclideanSpace getTarget() {
					return target;
				}

			};
			base.add(functional);
		}
		setBase(base);
	}

	@Override
	@Proceed
	public EuclideanSpace getDualSpace() {
		return source;
	}
}

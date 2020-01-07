package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.impl.Int;
import definitions.structures.abstr.algebra.rings.impl.Integers;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class PrimeTest {

	Group integers = Integers.getInstance();

	final Function distribution = new Function() {

		private static final long serialVersionUID = 1L;
		Map<Int, Boolean> map = new HashMap<>();

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Map<Vector, Scalar> getCoordinates() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setCoordinates(final Map<Vector, Scalar> coordinates) {

		}

		@Override
		public void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Scalar value(final Scalar input) {
			int ans = 0;
			for (Int i = ((Integers) PrimeTest.this.integers).get(2); i.getRepresentant() < input
					.getDoubleValue(); i = ((Integers) PrimeTest.this.integers).get(i.getRepresentant() + 1)) {
				final Boolean prime = this.map.get(i);
				if (prime == null) {
					if (i.isPrime()) {
						this.map.put(i, Boolean.TRUE);
						ans += 1;
					} else {
						this.map.put(i, Boolean.FALSE);
					}
				} else {
					if (prime) {
						ans += 1;
					}
				}
			}
			return RealLine.getInstance().get(ans);
		}

	};

	@Test
	public void test() {
		this.distribution.plot(0, 1.e6);
	}

}

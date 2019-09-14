package definitions.structures.abstr.groups.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.Group;
import definitions.structures.abstr.groups.impl.Integers.Int;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class PrimeTest {

	Group integers = Integers.getInstance();

	final Function distribution = new Function() {

		private static final long serialVersionUID = 1L;
		Map<Int, Boolean> map = new HashMap<>();

		@Override
		public Scalar value(Scalar input) {
			int ans = 0;
			for (Int i = (Int) ((Integers) PrimeTest.this.integers).get(2); i.getValue() < input
					.getValue(); i = (Int) ((Integers) PrimeTest.this.integers).get(i.getValue() + 1)) {
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

		@Override
		public void setCoordinates(Map<Vector, Scalar> coordinates) {

		}

		@Override
		public Map<EuclideanSpace, Map<Vector, Scalar>> getCoordinatesMap() {
			return null;
		}

	};

	@Test
	@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test() {
		this.distribution.plot(0, 1.e6);
	}

}

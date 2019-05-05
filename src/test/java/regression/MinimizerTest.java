package regression;

import org.junit.BeforeClass;
import org.junit.Test;

import functions.Function;
import junit.framework.Assert;

public class MinimizerTest {

	final static Function fun = new Function() {
		@Override
		public double value(double[] input) {
			double tmp = 0;
			for (final double coord : input) {
				tmp += Math.pow(coord - 1, 2);
			}
			return tmp;
		}
	};

	static double[] minimum;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		final Minimizer min = new Minimizer(fun);
		minimum = min.find(new double[] { 2.0 });// ,1.001 });
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		Assert.assertTrue(Math.abs(minimum[0] - 1) < 1.e-3);// && minimum[1]==1);
	}

}

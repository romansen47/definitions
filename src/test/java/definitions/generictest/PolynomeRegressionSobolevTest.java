package definitions.generictest;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.Monome;

public class PolynomeRegressionSobolevTest extends PolynomeRegressionTest {

	public static final Logger logger = LogManager.getLogger(PolynomeRegressionSobolevTest.class);

	@Override
	public Logger getLogger() {
		return PolynomeRegressionSobolevTest.logger;
	}

	protected int degree = 2;
	protected int sobolevDegree = 1;

	@Override
	public void setUp() throws Exception {
		setDegree(degree);
		setSobolevDegree(sobolevDegree);
		setField(realLine);

		final List<Vector> base = new ArrayList<>();
		for (int i = 0; i < (degree + 1); i++) {
			base.add(new Monome(i) {

				@Override
				public Field getField() {
					return realLine;
				}
			});
		}

		setSpace((EuclideanFunctionSpace) Generator.getInstance().getFiniteDimensionalFunctionSpace(realLine, base,
				left, right));
		super.setUp();
	}
}

package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.QuaternionSpace;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Quaternion;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class QuaternionSpaceTest extends GenericTest {

	public static final Logger logger = LogManager.getLogger(QuaternionSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	public static QuaternionSpace quaternionSpace = (QuaternionSpace) QuaternionSpace.getInstance();

	public static Function f = new GenericFunction() {

		public Field field = getRealLine();

		@Override
		public Field getField() {
			return field;
		}

		@Override
		public Vector value(Scalar input) {
			double x = ((Real) input).getRepresentant();
			Quaternion in = new Quaternion(x, x, x, x);
			return ((Quaternion) quaternionSpace.multiplication(in, in)).getReal();
		}

	};

	@Test
	public void test() {
		var x = quaternionSpace.get(0, 0, 2, 0);
		logger.info(x.toString());
		var y = quaternionSpace.get(0, 0, 3, 0);
		logger.info(y.toString());
		var z1 = quaternionSpace.addition(x, y);
		logger.info(z1.toString());
		var z2 = quaternionSpace.multiplication(x, y);
		logger.info(z2.toString());
		int i = 0;
	}

	@Test
	public void test2() {
		f.plot(-1, 1);
	}

}

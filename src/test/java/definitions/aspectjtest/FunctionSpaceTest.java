package definitions.aspectjtest;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.mappings.VectorSpaceSelfMapping;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.dynamicsystems.DynamicSystem;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class FunctionSpaceTest extends AspectJTest {

	final EuclideanSpace functionSpace = SpaceGenerator.getInstance()
			.getTrigonometricSobolevSpace((Field) ComplexPlane.getInstance(), 3, 1);

	@SuppressWarnings("serial")
	final Function functio = new GenericFunction() {

		double support = 10.0;

		@Override
		public Scalar value(Scalar input) {
			final double val = ((Real) input).getDoubleValue();
			if ((val <= (-support / 2)) || (val >= (support / 2))) {
				return RealLine.getInstance().get(0);
			}
			return RealLine.getInstance().get(1 * Math.exp(-1 / (Math.pow(support / 2, 2) - Math.pow(val, 2))));
			//			return RealLine.getInstance().get(0.5+0.2*Math.cos(input.getDoubleValue()));
		}
	};

	DynamicSystem kdv = new DynamicSystem() {

		@Override
		public Group getPhaseSpace() {
			return functionSpace;
		}

		@Override
		public VectorSpaceSelfMapping getDefiningMapping() {
			return null;
		}

		@Override
		public Group getTimeSpace() {
			return GroupGenerator.getInstance().getIntegers();
		}

	};
}

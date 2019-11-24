package definitions;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class TestRunner {

	static EuclideanSpace space;
	static  List<Vector> base = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		int dim = 15;
		space = SpaceGenerator.getInstance()
				.getTrigonometricFunctionSpaceWithLinearGrowth((Field) RealLine.getInstance(), dim);
//				.getFiniteDimensionalVectorSpace(3);
		final Vector baseVector1 = space.genericBaseToList().get(2);
		final Vector baseVector2 = space.add(baseVector1, space.genericBaseToList().get(1));
		base.add(baseVector1);
		base.add(baseVector2);
//		Traced.getLoglist().clear();
//		Traced.getLog().clear();
		new TestRunner().testRun();
//		Traced.show();
	}

	
	public void testRun() throws Exception {
		space.getOrthonormalBase(base);
	}
}

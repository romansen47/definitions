package definitions;

import java.util.ArrayList;
import java.util.List;

import com.aop.lib.Wrap;
import com.aop.lib.WrapDef;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class TestRunner {

	static EuclideanSpace space;
	static  List<Vector> base = new ArrayList<>();
	public static void main(String[] args) throws Exception {
		int dim = 3;
		space = Generator.getGenerator().getSpacegenerator()
				.getTrigonometricFunctionSpaceWithLinearGrowth(RealLine.getInstance(), dim);
//				.getFiniteDimensionalVectorSpace(10);
		final Vector baseVector1 = space.genericBaseToList().get(2);
		final Vector baseVector2 = space.add(baseVector1, space.genericBaseToList().get(3));
		base.add(baseVector1);
		base.add(baseVector2);
		WrapDef.getLoglist().clear();
		WrapDef.getLog().clear();
		testRun();
		WrapDef.getLoglist().add(WrapDef.getLog());
		for (List<String> list : WrapDef.getLoglist()) {
			for (String str : list) {
				System.out.println(str);
			}
		}
	}

	@Wrap
	public static void testRun() throws Exception {
		space.getOrthonormalBase(base);
	}
}

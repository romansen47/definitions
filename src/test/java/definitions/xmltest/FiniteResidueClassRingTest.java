package definitions.xmltest;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.groups.impl.FiniteResidueClassRing;
import definitions.structures.abstr.groups.impl.GroupGenerator;
import definitions.structures.abstr.vectorspaces.Ring;

public class FiniteResidueClassRingTest extends AspectJTest {

	final static int index = 80;

	static Ring finiteResidueClassRing;

	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
		finiteResidueClassRing = GroupGenerator.getInstance().getFiniteResidueClassRing(index);
	}

	@Test
	public void test() {

		getLogger().info(((FiniteResidueClassRing) finiteResidueClassRing).toString());

		System.out.println("ring is a field: "+(finiteResidueClassRing instanceof Field));
		((FiniteResidueClassRing) finiteResidueClassRing).print(); 
	}

}

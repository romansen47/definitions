package definitions.xmltest;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.groups.impl.FiniteResidueClassRing;
import definitions.structures.abstr.groups.impl.GroupGenerator;
import definitions.structures.abstr.vectorspaces.Ring;

public class FiniteResidueClassRingTest extends AspectJTest{

	final static int index=25;
	
	static Ring finiteResidueClassRing;
	
	@BeforeClass 
	public static void prepare() {
		AspectJTest.prepare();
		finiteResidueClassRing = new FiniteResidueClassRing(index);
	}
	
	@Test
	public void test() {
		((FiniteResidueClassRing) finiteResidueClassRing).print(); 
		((FiniteResidueClassRing) finiteResidueClassRing).draw();
	}

}

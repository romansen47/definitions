package definitions.xmltest;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.groups.impl.FiniteResidueClassRing;
import definitions.structures.abstr.groups.impl.GroupGenerator;
import definitions.structures.abstr.vectorspaces.Ring;

public class FiniteResidueClassRingTest extends AspectJTest{

	final static int index=15;
	
	static Ring finiteCyclicGroup;
	
	@BeforeClass 
	public static void prepare() {
		AspectJTest.prepare();
		finiteCyclicGroup = new FiniteResidueClassRing(index);
	}

	private int highestOrder=10;
	
	@Test
	public void test() {
		((FiniteResidueClassRing) finiteCyclicGroup).print();
//		for (int i=0;i<highestOrder;i++) {
//			GroupGenerator.getInstance().getFiniteResidueClassRing(i).draw();
//		}
		GroupGenerator.getInstance().getFiniteResidueClassRing(highestOrder).draw();
	}

}

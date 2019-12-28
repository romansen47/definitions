package definitions.aspectjtest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing;
import definitions.structures.abstr.vectorspaces.Ring;

public class GroupGeneratorTest extends AspectJTest {

	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
	}

	private Group group;

	private final int order = 100;

	@Before
	public void before() {
		this.setGroup(getGenerator().getGroupGenerator().getFiniteResidueClassRing(this.order));
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	@Test
	public void test() {
		((FiniteResidueClassRing) this.group).print();
	}

	@Test
	public void outerProductTest() {
		Ring a=getGenerator().getGroupGenerator().getFiniteResidueClassRing(3);
		Ring b=getGenerator().getGroupGenerator().getFiniteResidueClassRing(2);
		Monoid c= getGenerator().getGroupGenerator().outerProduct(a,b);
		((FiniteMonoid) c).print();
		MonoidElement o1=((DiscreetMonoid) c).get(3);
		MonoidElement o2=((DiscreetMonoid) c).get(5);
		MonoidElement test=c.operation(o1,o2); 
	}
	
}

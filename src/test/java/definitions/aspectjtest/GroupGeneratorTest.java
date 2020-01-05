package definitions.aspectjtest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.DiscreetGroupElement;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupElement;
import definitions.structures.abstr.algebra.groups.impl.GroupGenerator;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.monoids.FiniteMonoid;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing;
import definitions.structures.abstr.algebra.semigroups.DiscreetSemiGroup;
import definitions.structures.abstr.algebra.semigroups.DiscreetSemiGroupElement;
import definitions.structures.abstr.algebra.semigroups.impl.NaturalsWithZero;

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

	@Test
	public void outerProductTest() {
		final FiniteResidueClassRing a = getGenerator().getGroupGenerator().getFiniteResidueClassRing(3);
		final FiniteResidueClassRing b = getGenerator().getGroupGenerator().getFiniteResidueClassRing(11);
		final FiniteMonoid c = getGenerator().getGroupGenerator().outerProduct(a, b);
		((FiniteMonoid) c).print();
		final DiscreetSemiGroupElement o1 = ((DiscreetSemiGroup) c).get(3);
		final DiscreetSemiGroupElement o2 = ((DiscreetSemiGroup) c).get(5);
		final DiscreetSemiGroupElement test = (DiscreetSemiGroupElement)c.operation(o1, o2);
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	@Test
	public void test() {
		((FiniteResidueClassRing) this.group).print();
	}
	
	@Test
	public void completionTest() {
		DiscreetGroup completionOfNaturals = GroupGenerator.getInstance().completion((DiscreetMonoid)NaturalsWithZero.getInstance());
		DiscreetGroupElement neutralElement = (DiscreetGroupElement) completionOfNaturals.getNeutralElement();
		for (int i=-10;i<10;i++) {
			System.out.println(completionOfNaturals.get(i));
		}
		DiscreetGroupElement a = completionOfNaturals.get(-5);
		DiscreetGroupElement b = completionOfNaturals.get(15);
		System.out.println(completionOfNaturals.operation(a,b));
		
	}

}

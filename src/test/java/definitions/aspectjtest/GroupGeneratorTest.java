package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.groups.DiscreetGroup;
import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.groups.GroupGenerator;
import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.impl.Naturals;

public class GroupGeneratorTest extends AspectJTest {

	@BeforeClass
	public static void prepare() {
		AspectJTest.prepare();
	}

	private Group group;

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	@Test
	public void completionTest() {
		DiscreetMonoid NaturalsWithZero = new Naturals();
	}

	@Test
	public void completionTest2() {
		DiscreetMonoid multiplicatioveMonoid = GroupGenerator.getInstance().getNaturals().getMuliplicativeMonoid();
		DiscreetGroup rationals = GroupGenerator.getInstance().completeToGroup(multiplicatioveMonoid);
		Element neutralElement = rationals.getNeutralElement();
		for (double i = 1; i < 10; i++) {
			Element tmp = rationals.get(i);
			Element inverse = rationals.getInverseElement(tmp);
			Element product = rationals.operation(tmp, inverse);
			boolean ans = neutralElement.equals(product);

			Assert.assertTrue(ans);
			System.out.println(
					"rational: " + tmp + ", inverse: " + inverse + ", product: " + product + "," + " check: " + ans);
		}
		Element a = rationals.get(-5.);
		Element b = rationals.get(15.);
		System.out.println(rationals.operation(a, b));
	}
	
	@Test
	public void integersTest() {
		SemiRing naturals=new Naturals();
		Ring integers=GroupGenerator.getInstance().completeToRing(naturals);
		Element one=integers.getOne();
		Element minusOne=integers.getInverseElement(integers.getOne());
		Element zero=integers.getNeutralElement();
		Element two=integers.addition(one,one);
		Element three=integers.addition(one,two);
		Element four1=integers.multiplication(two,two);
		Element four2=integers.addition(two,two);
		Element minusFour=integers.multiplication(four2,minusOne);
		boolean addSameAsMult=four1.equals(four2);
		boolean sumIsZero=integers.addition(four1, minusFour).equals(zero);
		Assert.assertTrue(addSameAsMult);
		Assert.assertTrue(sumIsZero);
	}

}
